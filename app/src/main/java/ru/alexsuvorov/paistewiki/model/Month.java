package ru.alexsuvorov.paistewiki.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Entity(tableName = "news_month_table", indices = @Index(value = {"month_index"}, unique = true))
public class Month {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "month_id")
    @Getter @Setter
    public long month_id;
    @ColumnInfo(name = "month_name")
    @Getter @Setter
    public String monthName;
    @ColumnInfo(name = "month_url")
    @Getter @Setter
    public String monthURL;
    @ColumnInfo(name = "month_index")
    @Getter @Setter
    public int monthIndex;
    @Ignore
    @Getter @Setter
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
}