package ru.alexsuvorov.paistewiki.core.database.framework

import androidx.sqlite.db.SupportSQLiteOpenHelper

internal class AssetSQLiteOpenHelperFactory : SupportSQLiteOpenHelper.Factory {
    override fun create(configuration: SupportSQLiteOpenHelper.Configuration): SupportSQLiteOpenHelper {
        return AssetSQLiteOpenHelper(
            configuration.context, configuration.name?:"",
            configuration.callback.version, configuration.callback
        )
    }
}
