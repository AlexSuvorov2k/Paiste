package ru.alexsuvorov.paistewiki.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import lombok.Getter;
import lombok.Setter;

@Entity(tableName = "support_table")
public class SupportModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "support_id")
    @Getter @Setter @NonNull
    public long support_id = 1;
    @ColumnInfo(name = "support_title")
    @Getter @Setter
    public String title;
    @ColumnInfo(name = "support_text")
    @Getter @Setter
    public String text;
    @ColumnInfo(name = "support_image")
    @Getter @Setter
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
}
