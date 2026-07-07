package ru.alexsuvorov.paistewiki.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "news_table", indices = [Index(value = ["news_url"], unique = true)])
data class News(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "news_id")
    var news_id: Long = 0,
    @ColumnInfo(name = "news_title")
    var title: String? = null,
    @ColumnInfo(name = "news_category")
    var category: String? = null,
    @ColumnInfo(name = "news_url")
    var url: String? = null,
    @ColumnInfo(name = "news_index")
    var news_index: Long = 0
)
