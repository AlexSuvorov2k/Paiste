package ru.alexsuvorov.paistewiki.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "support_table")
data class SupportModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "support_id")
    var support_id: Long = 0,
    @ColumnInfo(name = "support_title")
    var title: String? = null,
    @ColumnInfo(name = "support_text")
    var text: String? = null,
    @ColumnInfo(name = "support_image")
    var supportImage: String? = null
)
