package ru.alexsuvorov.paistewiki.core.database.framework

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper

internal class AssetSQLiteOpenHelper(
    context: Context,
    name: String,
    version: Int,
    callback: SupportSQLiteOpenHelper.Callback
) : SupportSQLiteOpenHelper {
    private val delegate: AssetHelper = createDelegate(context, name, version, callback)

    private fun createDelegate(
        context: Context,
        name: String,
        version: Int,
        callback: SupportSQLiteOpenHelper.Callback
    ): AssetHelper {
        return object : AssetHelper(context, name, version) {
            override fun onCreate(db: SQLiteDatabase) {
                wrappedDb = FrameworkSQLiteDatabase(db)
                callback.onCreate(wrappedDb!!)
            }

            override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
                callback.onUpgrade(getWrappedDb(db), oldVersion, newVersion)
            }

            override fun onConfigure(db: SQLiteDatabase) {
                callback.onConfigure(getWrappedDb(db))
            }

            override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
                callback.onDowngrade(getWrappedDb(db), oldVersion, newVersion)
            }

            override fun onOpen(db: SQLiteDatabase) {
                callback.onOpen(getWrappedDb(db))
            }
        }
    }

    override val databaseName: String?
        get() = delegate.databaseName

    override fun setWriteAheadLoggingEnabled(enabled: Boolean) {
        delegate.setWriteAheadLoggingEnabled(enabled)
    }

    override val writableDatabase: SupportSQLiteDatabase
        get() = delegate.getWritableSupportDatabase()

    override val readableDatabase: SupportSQLiteDatabase
        get() = delegate.getReadableSupportDatabase()

    override fun close() {
        delegate.close()
    }

    abstract class AssetHelper(context: Context, name: String, version: Int) :
        SQLiteAssetHelper(context, name, null, null, version, null) {
        var wrappedDb: FrameworkSQLiteDatabase? = null

        fun getWritableSupportDatabase(): SupportSQLiteDatabase {
            val db = super.getWritableDatabase()
            return getWrappedDb(db)
        }

        fun getReadableSupportDatabase(): SupportSQLiteDatabase {
            val db = super.getReadableDatabase()
            return getWrappedDb(db)
        }

        fun getWrappedDb(sqLiteDatabase: SQLiteDatabase): FrameworkSQLiteDatabase {
            if (wrappedDb == null) {
                wrappedDb = FrameworkSQLiteDatabase(sqLiteDatabase)
            }
            return wrappedDb!!
        }

        @Synchronized
        override fun close() {
            super.close()
            wrappedDb = null
        }
    }
}
