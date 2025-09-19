package ru.alexsuvorov.paistewiki.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "support_table")
class SupportModel {
    @JvmField
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "support_id")
    var support_id: Long = 1

    @JvmField
    @ColumnInfo(name = "support_title")
    var title: String? = null

    @JvmField
    @ColumnInfo(name = "support_text")
    var text: String? = null

    @JvmField
    @ColumnInfo(name = "support_image")
    var supportImage: String? = null

    constructor()

    @Ignore
    constructor(support_id: Long, title: String?, text: String?, supportImage: String?) {
        this.support_id = support_id
        this.title = title
        this.text = text
        this.supportImage = supportImage
    }
}