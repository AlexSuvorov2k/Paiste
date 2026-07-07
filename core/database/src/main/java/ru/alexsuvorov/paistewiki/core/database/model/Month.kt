package ru.alexsuvorov.paistewiki.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "news_month_table", indices = [Index(value = ["month_index"], unique = true)])
data class Month(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "month_id")
    var month_id: Long = 0,
    @ColumnInfo(name = "month_name")
    var monthName: String? = null,
    @ColumnInfo(name = "month_url")
    var monthURL: String? = null,
    @ColumnInfo(name = "month_index")
    var monthIndex: Int = 0
) {
    @Ignore
    var mMonthPosts: List<News>? = null
}
