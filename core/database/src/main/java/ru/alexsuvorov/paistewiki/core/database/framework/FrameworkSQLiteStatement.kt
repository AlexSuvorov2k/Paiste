package ru.alexsuvorov.paistewiki.core.database.framework

import android.database.sqlite.SQLiteStatement
import androidx.sqlite.db.SupportSQLiteStatement

internal class FrameworkSQLiteStatement(private val mDelegate: SQLiteStatement) : FrameworkSQLiteProgram(mDelegate), SupportSQLiteStatement {

    override fun execute() {
        mDelegate.execute()
    }

    override fun executeUpdateDelete(): Int {
        return mDelegate.executeUpdateDelete()
    }

    override fun executeInsert(): Long {
        return mDelegate.executeInsert()
    }

    override fun simpleQueryForLong(): Long {
        return mDelegate.simpleQueryForLong()
    }

    override fun simpleQueryForString(): String? {
        return mDelegate.simpleQueryForString()
    }
}
