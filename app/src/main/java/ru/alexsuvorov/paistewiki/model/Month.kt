package ru.alexsuvorov.paistewiki.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "news_month_table", indices = [Index(value = ["month_index"], unique = true)])
class Month {
    @JvmField
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "month_id")
    var month_id: Long = 0

    @JvmField
    @ColumnInfo(name = "month_name")
    var monthName: String? = null

    @JvmField
    @ColumnInfo(name = "month_url")
    var monthURL: String? = null

    @JvmField
    @ColumnInfo(name = "month_index")
    var monthIndex: Int = 0

    @Ignore
    var mMonthPosts: MutableList<News?>? = null

    @Ignore
    constructor(month_id: Long, monthName: String?, monthURL: String?, monthIndex: Int) {
        this.month_id = month_id
        this.monthName = monthName
        this.monthURL = monthURL
        this.monthIndex = monthIndex
    }

    constructor()

    fun getmMonthPosts(): MutableList<News?>? {
        return mMonthPosts
    }

    fun setmMonthPosts(mMonthPosts: MutableList<News?>?) {
        this.mMonthPosts = mMonthPosts
    }
}