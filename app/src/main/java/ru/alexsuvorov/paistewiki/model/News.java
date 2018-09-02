package ru.alexsuvorov.paistewiki.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import lombok.Getter;
import lombok.Setter;

@Entity(tableName = "news_table", indices = @Index(value = {"news_url"}, unique = true))
public class News {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "news_id")
    @Getter @Setter @NonNull
    public long news_id = 1;
    @ColumnInfo(name = "news_title")
    @Getter @Setter
    public String title;
    @ColumnInfo(name = "news_category")
    @Getter @Setter
    public String category;
    @ColumnInfo(name = "news_url")
    @Getter @Setter
    public String url;
    @ColumnInfo(name = "news_index")
    @Getter @Setter
    public long news_index;

    public News() {
    }

    @Ignore
    public News(@NonNull long news_id, String title, String category, String url, long news_index) {
        this.news_id = news_id;
        this.title = title;
        this.category = category;
        this.url = url;
        this.news_index = news_index;
    }
}