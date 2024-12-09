package ru.alexsuvorov.paistewiki.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "news_month_table", indices = @Index(value = {"month_index"}, unique = true))
public class Month {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "month_id")
    public long month_id;
    @ColumnInfo(name = "month_name")
    public String monthName;
    @ColumnInfo(name = "month_url")
    public String monthURL;
    @ColumnInfo(name = "month_index")
    public int monthIndex;
    @Ignore

    public List<News> mMonthPosts;

    @Ignore
    public Month(long month_id, String monthName, String monthURL, int monthIndex) {
        this.month_id = month_id;
        this.monthName = monthName;
        this.monthURL = monthURL;
        this.monthIndex = monthIndex;
    }

    public Month() {
    }

    public long getMonth_id() {
        return month_id;
    }

    public void setMonth_id(long month_id) {
        this.month_id = month_id;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public String getMonthURL() {
        return monthURL;
    }

    public void setMonthURL(String monthURL) {
        this.monthURL = monthURL;
    }

    public int getMonthIndex() {
        return monthIndex;
    }

    public void setMonthIndex(int monthIndex) {
        this.monthIndex = monthIndex;
    }

    public List<News> getmMonthPosts() {
        return mMonthPosts;
    }

    public void setmMonthPosts(List<News> mMonthPosts) {
        this.mMonthPosts = mMonthPosts;
    }
}