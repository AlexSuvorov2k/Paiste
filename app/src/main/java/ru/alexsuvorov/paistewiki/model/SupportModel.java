package ru.alexsuvorov.paistewiki.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "support_table")
public class SupportModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "support_id")
    public long support_id = 1;
    @ColumnInfo(name = "support_title")
    public String title;
    @ColumnInfo(name = "support_text")
    public String text;
    @ColumnInfo(name = "support_image")
    public String supportImage;

    public SupportModel() {
    }

    @Ignore
    public SupportModel(long support_id, String title, String text, String supportImage) {
        this.support_id = support_id;
        this.title = title;
        this.text = text;
        this.supportImage = supportImage;
    }

    public long getSupport_id() {
        return support_id;
    }

    public void setSupport_id(long support_id) {
        this.support_id = support_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSupportImage() {
        return supportImage;
    }

    public void setSupportImage(String supportImage) {
        this.supportImage = supportImage;
    }
}