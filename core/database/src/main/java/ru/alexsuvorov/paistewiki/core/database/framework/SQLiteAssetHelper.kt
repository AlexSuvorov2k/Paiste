package ru.alexsuvorov.paistewiki.core.database.framework

import android.content.Context
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.Collections

/**
 * A helper class to manage database creation and version management using
 * an application's raw asset files.
 */
internal open class SQLiteAssetHelper @JvmOverloads constructor(
    private val mContext: Context,
    private val mName: String,
    storageDirectory: String? = null,
    private val mFactory: CursorFactory? = null,
    private val mNewVersion: Int,
    errorHandler: DatabaseErrorHandler? = null
) : SQLiteOpenHelper(mContext, mName, mFactory, mNewVersion, errorHandler) {

    private var mDatabase: SQLiteDatabase? = null
    private var mIsInitializing = false
    private val mDatabasePath: String
    private val mAssetPath: String = "$ASSET_DB_PATH/$mName"
    private val mUpgradePathFormat: String = "$ASSET_DB_PATH/${mName}_upgrade_%s-%s.sql"
    private var mForcedUpgradeVersion = 0

    init {
        require(mNewVersion >= 1) { "Version must be >= 1, was $mNewVersion" }

        mDatabasePath = storageDirectory ?: (mContext.applicationInfo.dataDir + "/databases")
    }

    @Synchronized
    override fun getWritableDatabase(): SQLiteDatabase {
        if (mDatabase != null && mDatabase!!.isOpen && !mDatabase!!.isReadOnly) {
            return mDatabase!!
        }

        if (mIsInitializing) {
            throw IllegalStateException("getWritableDatabase called recursively")
        }

        var success = false
        var db: SQLiteDatabase? = null
        try {
            mIsInitializing = true
            db = createOrOpenDatabase(false)

            var version = db.version

            // do force upgrade
            if (version != 0 && version < mForcedUpgradeVersion) {
                db = createOrOpenDatabase(true)
                db.version = mNewVersion
                version = db.version
            }

            if (version != mNewVersion) {
                db.beginTransaction()
                try {
                    if (version == 0) {
                        onCreate(db)
                    } else {
                        if (version > mNewVersion) {
                            Log.w(TAG, "Can't downgrade read-only database from version $version to $mNewVersion: ${db.path}")
                        }
                        onUpgrade(db, version, mNewVersion)
                    }
                    db.version = mNewVersion
                    db.setTransactionSuccessful()
                } finally {
                    db.endTransaction()
                }
            }

            onOpen(db)
            success = true
            return db
        } finally {
            mIsInitializing = false
            if (success) {
                if (mDatabase != null) {
                    try {
                        mDatabase!!.close()
                    } catch (e: Exception) {
                    }
                }
                mDatabase = db
            } else {
                db?.close()
            }
        }
    }

    @Synchronized
    override fun getReadableDatabase(): SQLiteDatabase {
        if (mDatabase != null && mDatabase!!.isOpen) {
            return mDatabase!!
        }

        if (mIsInitializing) {
            throw IllegalStateException("getReadableDatabase called recursively")
        }

        try {
            return getWritableDatabase()
        } catch (e: SQLiteException) {
            Log.e(TAG, "Couldn't open $mName for writing (will try read-only):", e)
        }

        var db: SQLiteDatabase? = null
        try {
            mIsInitializing = true
            val path = mContext.getDatabasePath(mName).path
            db = SQLiteDatabase.openDatabase(path, mFactory, SQLiteDatabase.OPEN_READONLY)
            if (db.version != mNewVersion) {
                throw SQLiteException("Can't upgrade read-only database from version ${db.version} to $mNewVersion: $path")
            }

            onOpen(db)
            Log.w(TAG, "Opened $mName in read-only mode")
            mDatabase = db
            return mDatabase!!
        } finally {
            mIsInitializing = false
            if (db != null && db != mDatabase) db.close()
        }
    }

    @Synchronized
    override fun close() {
        if (mIsInitializing) throw IllegalStateException("Closed during initialization")

        if (mDatabase != null && mDatabase!!.isOpen) {
            mDatabase!!.close()
            mDatabase = null
        }
    }

    override fun onConfigure(db: SQLiteDatabase) {
        // not supported!
    }

    override fun onCreate(db: SQLiteDatabase) {
        // do nothing - createOrOpenDatabase() is called in
        // getWritableDatabase() to handle database creation.
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.w(TAG, "Upgrading database $mName from version $oldVersion to $newVersion...")

        val paths = ArrayList<String>()
        getUpgradeFilePaths(oldVersion, newVersion - 1, newVersion, paths)

        if (paths.isEmpty()) {
            Log.e(TAG, "no upgrade script path from $oldVersion to $newVersion")
            throw SQLiteAssetException("no upgrade script path from $oldVersion to $newVersion")
        }

        Collections.sort(paths, VersionComparator())
        for (path in paths) {
            try {
                Log.w(TAG, "processing upgrade: $path")
                val `is` = mContext.assets.open(path)
                val sql = Utils.convertStreamToString(`is`)
                if (sql != null) {
                    val cmds = Utils.splitSqlScript(sql, ';')
                    for (cmd in cmds) {
                        if (cmd.trim { it <= ' ' }.isNotEmpty()) {
                            db.execSQL(cmd)
                        }
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        Log.w(TAG, "Successfully upgraded database $mName from version $oldVersion to $newVersion")
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // not supported!
    }

    @Deprecated("use setForcedUpgrade instead.")
    fun setForcedUpgradeVersion(version: Int) {
        setForcedUpgrade(version)
    }

    fun setForcedUpgrade(version: Int) {
        mForcedUpgradeVersion = version
    }

    fun setForcedUpgrade() {
        setForcedUpgrade(mNewVersion)
    }

    @Throws(SQLiteAssetException::class)
    private fun createOrOpenDatabase(force: Boolean): SQLiteDatabase {
        var db: SQLiteDatabase? = null
        val file = File("$mDatabasePath/$mName")
        if (file.exists()) {
            db = returnDatabase()
        }

        return if (db != null) {
            if (force) {
                Log.w(TAG, "forcing database upgrade!")
                copyDatabaseFromAssets()
                returnDatabase()!!
            } else {
                db
            }
        } else {
            copyDatabaseFromAssets()
            returnDatabase()!!
        }
    }

    private fun returnDatabase(): SQLiteDatabase? {
        return try {
            val db = SQLiteDatabase.openDatabase("$mDatabasePath/$mName", mFactory, SQLiteDatabase.OPEN_READWRITE)
            Log.i(TAG, "successfully opened database $mName")
            db
        } catch (e: SQLiteException) {
            Log.w(TAG, "could not open database $mName - ${e.message}")
            null
        }
    }

    @Throws(SQLiteAssetException::class)
    private fun copyDatabaseFromAssets() {
        Log.w(TAG, "copying database from assets...")

        val path = mAssetPath
        val dest = "$mDatabasePath/$mName"
        var `is`: InputStream? = null
        var isZip = false

        try {
            `is` = mContext.assets.open(path)
        } catch (e: IOException) {
            try {
                `is` = mContext.assets.open("$path.zip")
                isZip = true
            } catch (e2: IOException) {
                try {
                    `is` = mContext.assets.open("$path.gz")
                } catch (e3: IOException) {
                    val se = SQLiteAssetException("Missing $mAssetPath file (or .zip, .gz archive) in assets, or target folder not writable")
                    se.stackTrace = e3.stackTrace
                    throw se
                }
            }
        }

        try {
            val f = File("$mDatabasePath/")
            if (!f.exists()) {
                f.mkdir()
            }
            if (isZip) {
                val zis = Utils.getFileFromZip(`is`!!)
                if (zis == null) {
                    throw SQLiteAssetException("Archive is missing a SQLite database file")
                }
                Utils.writeExtractedFileToDisk(zis, FileOutputStream(dest))
            } else {
                Utils.writeExtractedFileToDisk(`is`!!, FileOutputStream(dest))
            }

            Log.w(TAG, "database copy complete")

        } catch (e: IOException) {
            val se = SQLiteAssetException("Unable to write $dest to data directory")
            se.stackTrace = e.stackTrace
            throw se
        }
    }

    private fun getUpgradeSQLStream(oldVersion: Int, newVersion: Int): InputStream? {
        val path = String.format(mUpgradePathFormat, oldVersion, newVersion)
        return try {
            mContext.assets.open(path)
        } catch (e: IOException) {
            Log.w(TAG, "missing database upgrade script: $path")
            null
        }
    }

    private fun getUpgradeFilePaths(baseVersion: Int, start: Int, end: Int, paths: ArrayList<String>) {
        val a: Int
        val b: Int

        val `is` = getUpgradeSQLStream(start, end)
        if (`is` != null) {
            val path = String.format(mUpgradePathFormat, start, end)
            paths.add(path)
            a = start - 1
            b = start
        } else {
            a = start - 1
            b = end
        }

        if (a < baseVersion) {
            return
        } else {
            getUpgradeFilePaths(baseVersion, a, b, paths)
        }
    }

    internal open class SQLiteAssetException : SQLiteException {
        constructor()
        constructor(error: String?) : super(error)
    }

    companion object {
        private val TAG = SQLiteAssetHelper::class.java.simpleName
        private const val ASSET_DB_PATH = "databases"
    }
}
