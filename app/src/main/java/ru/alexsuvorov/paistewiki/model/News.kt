package ru.alexsuvorov.paistewiki.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "news_table", indices = [Index(value = ["news_url"], unique = true)])
class News {
    @JvmField
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "news_id")
    var news_id: Long = 1

    @JvmField
    @ColumnInfo(name = "news_title")
    var title: String? = null

    @JvmField
    @ColumnInfo(name = "news_category")
    var category: String? = null

    @JvmField
    @ColumnInfo(name = "news_url")
    var url: String? = null

    @JvmField
    @ColumnInfo(name = "news_index")
    var news_index: Long = 0

    constructor()

    @Ignore
    constructor(news_id: Long, title: String?, category: String?, url: String?, news_index: Long) {
        this.news_id = news_id
        this.title = title
        this.category = category
        this.url = url
        this.news_index = news_index
    }
}