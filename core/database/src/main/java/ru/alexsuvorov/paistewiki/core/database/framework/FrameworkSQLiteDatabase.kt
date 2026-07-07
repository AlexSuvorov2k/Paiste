package ru.alexsuvorov.paistewiki.core.database.framework

import android.content.ContentValues
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteCursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteTransactionListener
import android.os.Build
import android.os.CancellationSignal
import android.util.Pair
import androidx.annotation.RequiresApi
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteQuery
import androidx.sqlite.db.SupportSQLiteStatement
import java.util.Locale

internal class FrameworkSQLiteDatabase(private val mDelegate: SQLiteDatabase) : SupportSQLiteDatabase {

    override fun compileStatement(sql: String): SupportSQLiteStatement {
        return FrameworkSQLiteStatement(mDelegate.compileStatement(sql))
    }

    override fun beginTransaction() {
        mDelegate.beginTransaction()
    }

    override fun beginTransactionNonExclusive() {
        mDelegate.beginTransactionNonExclusive()
    }

    override fun beginTransactionWithListener(transactionListener: SQLiteTransactionListener) {
        mDelegate.beginTransactionWithListener(transactionListener)
    }

    override fun beginTransactionWithListenerNonExclusive(transactionListener: SQLiteTransactionListener) {
        mDelegate.beginTransactionWithListenerNonExclusive(transactionListener)
    }

    override fun endTransaction() {
        mDelegate.endTransaction()
    }

    override fun setTransactionSuccessful() {
        mDelegate.setTransactionSuccessful()
    }

    override fun inTransaction(): Boolean {
        return mDelegate.inTransaction()
    }

    override val isDbLockedByCurrentThread: Boolean
        get() = mDelegate.isDbLockedByCurrentThread

    override fun yieldIfContendedSafely(): Boolean {
        return mDelegate.yieldIfContendedSafely()
    }

    override fun yieldIfContendedSafely(sleepAfterYieldDelayMillis: Long): Boolean {
        return mDelegate.yieldIfContendedSafely(sleepAfterYieldDelayMillis)
    }

    override var version: Int
        get() = mDelegate.version
        set(version) {
            mDelegate.version = version
        }

    override fun setMaximumSize(numBytes: Long): Long {
        return mDelegate.setMaximumSize(numBytes)
    }

    override val maximumSize: Long
        get() = mDelegate.maximumSize

    override var pageSize: Long
        get() = mDelegate.pageSize
        set(numBytes) {
            mDelegate.pageSize = numBytes
        }

    override fun query(query: String): Cursor {
        return query(SimpleSQLiteQuery(query))
    }

    override fun query(query: String, bindArgs: Array<out Any?>): Cursor {
        return query(SimpleSQLiteQuery(query, bindArgs))
    }

    override fun query(query: SupportSQLiteQuery): Cursor {
        return mDelegate.rawQueryWithFactory({ _, masterQuery, editTable, queryDelegate ->
            query.bindTo(FrameworkSQLiteProgram(queryDelegate))
            SQLiteCursor(masterQuery, editTable, queryDelegate)
        }, query.sql, EMPTY_STRING_ARRAY, "")
    }

    override fun query(query: SupportSQLiteQuery, cancellationSignal: CancellationSignal?): Cursor {
        return mDelegate.rawQueryWithFactory({ _, masterQuery, editTable, queryDelegate ->
            query.bindTo(FrameworkSQLiteProgram(queryDelegate))
            SQLiteCursor(masterQuery, editTable, queryDelegate)
        }, query.sql, EMPTY_STRING_ARRAY, "", cancellationSignal)
    }

    @Throws(SQLException::class)
    override fun insert(table: String, conflictAlgorithm: Int, values: ContentValues): Long {
        return mDelegate.insertWithOnConflict(table, null, values, conflictAlgorithm)
    }

    override fun delete(table: String, whereClause: String?, whereArgs: Array<out Any?>?): Int {
        val query = "DELETE FROM " + table + (if (isEmpty(whereClause)) "" else " WHERE $whereClause")
        val statement = compileStatement(query)
        SimpleSQLiteQuery.bind(statement, whereArgs)
        return statement.executeUpdateDelete()
    }

    override fun update(
        table: String,
        conflictAlgorithm: Int,
        values: ContentValues,
        whereClause: String?,
        whereArgs: Array<out Any?>?
    ): Int {
        if (values.size() == 0) {
            throw IllegalArgumentException("Empty values")
        }
        val sql = StringBuilder(120)
        sql.append("UPDATE ")
        sql.append(CONFLICT_VALUES[conflictAlgorithm])
        sql.append(table)
        sql.append(" SET ")

        val setValuesSize = values.size()
        val bindArgsSize = if (whereArgs == null) setValuesSize else setValuesSize + whereArgs.size
        val bindArgs = arrayOfNulls<Any>(bindArgsSize)
        var i = 0
        for (colName in values.keySet()) {
            sql.append(if (i > 0) "," else "")
            sql.append(colName)
            bindArgs[i++] = values[colName]
            sql.append("=?")
        }
        if (whereArgs != null) {
            for (j in setValuesSize until bindArgsSize) {
                bindArgs[j] = whereArgs[j - setValuesSize]
            }
        }
        if (!isEmpty(whereClause)) {
            sql.append(" WHERE ")
            sql.append(whereClause)
        }
        val stmt = compileStatement(sql.toString())
        SimpleSQLiteQuery.bind(stmt, bindArgs)
        return stmt.executeUpdateDelete()
    }

    @Throws(SQLException::class)
    override fun execSQL(sql: String) {
        mDelegate.execSQL(sql)
    }

    @Throws(SQLException::class)
    override fun execSQL(sql: String, bindArgs: Array<out Any?>) {
        mDelegate.execSQL(sql, bindArgs)
    }

    override val isReadOnly: Boolean
        get() = mDelegate.isReadOnly

    override val isOpen: Boolean
        get() = mDelegate.isOpen

    override fun needUpgrade(newVersion: Int): Boolean {
        return mDelegate.needUpgrade(newVersion)
    }

    override val path: String?
        get() = mDelegate.path

    override fun setLocale(locale: Locale) {
        mDelegate.setLocale(locale)
    }

    override fun setMaxSqlCacheSize(cacheSize: Int) {
        mDelegate.setMaxSqlCacheSize(cacheSize)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun setForeignKeyConstraintsEnabled(enable: Boolean) {
        mDelegate.setForeignKeyConstraintsEnabled(enable)
    }

    override fun enableWriteAheadLogging(): Boolean {
        return mDelegate.enableWriteAheadLogging()
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun disableWriteAheadLogging() {
        mDelegate.disableWriteAheadLogging()
    }

    override val isWriteAheadLoggingEnabled: Boolean
        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
        get() = mDelegate.isWriteAheadLoggingEnabled

    override val attachedDbs: List<Pair<String, String>>?
        get() = mDelegate.attachedDbs

    override val isDatabaseIntegrityOk: Boolean
        get() = mDelegate.isDatabaseIntegrityOk

    override fun close() {
        mDelegate.close()
    }

    companion object {
        private val CONFLICT_VALUES = arrayOf("", " OR ROLLBACK ", " OR ABORT ", " OR FAIL ", " OR IGNORE ", " OR REPLACE ")
        private val EMPTY_STRING_ARRAY = arrayOf<String>()

        private fun isEmpty(input: String?): Boolean {
            return input == null || input.isEmpty()
        }
    }
}
