package ru.alexsuvorov.paistewiki.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "news_table", indices = @Index(value = {"news_url"}, unique = true))
public class News {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "news_id")
    public long news_id = 1;
    @ColumnInfo(name = "news_title")
    public String title;
    @ColumnInfo(name = "news_category")
    public String category;
    @ColumnInfo(name = "news_url")
    public String url;
    @ColumnInfo(name = "news_index")

    public long news_index;

    public News() {
    }

    @Ignore
    public News(long news_id, String title, String category, String url, long news_index) {
        this.news_id = news_id;
        this.title = title;
        this.category = category;
        this.url = url;
        this.news_index = news_index;
    }

    public long getNews_id() {
        return news_id;
    }

    public void setNews_id(long news_id) {
        this.news_id = news_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getNews_index() {
        return news_index;
    }

    public void setNews_index(long news_index) {
        this.news_index = news_index;
    }
}