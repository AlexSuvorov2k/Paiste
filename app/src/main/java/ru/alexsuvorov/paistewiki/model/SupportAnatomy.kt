package ru.alexsuvorov.paistewiki.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "support_anatomy")
class SupportAnatomy {
    @JvmField
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "anatomy_id")
    var anatomyId: Long = 1

    @JvmField
    @ColumnInfo(name = "anatomy_title")
    var anatomyTitle: String? = null

    @JvmField
    @ColumnInfo(name = "anatomy_subtitle")
    var anatomySubtitle: String? = null

    @JvmField
    @ColumnInfo(name = "anatomy_text")
    var anatomyText: String? = null

    constructor()

    @Ignore
    constructor(anatomy_id: Long, anatomy_title: String?, anatomy_subtitle: String?, anatomy_text: String?) {
        this.anatomyId = anatomy_id
        this.anatomyTitle = anatomy_title
        this.anatomySubtitle = anatomy_subtitle
        this.anatomyText = anatomy_text
    }
}