package ru.alexsuvorov.paistewiki.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "support_anatomy")
data class SupportAnatomy(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "anatomy_id")
    var anatomyId: Long = 0,
    @ColumnInfo(name = "anatomy_title")
    var anatomyTitle: String? = null,
    @ColumnInfo(name = "anatomy_subtitle")
    var anatomySubtitle: String? = null,
    @ColumnInfo(name = "anatomy_text")
    var anatomyText: String? = null
)
