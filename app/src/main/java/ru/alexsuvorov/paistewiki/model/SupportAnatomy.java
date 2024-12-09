package ru.alexsuvorov.paistewiki.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "support_anatomy")
public class SupportAnatomy {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "anatomy_id")
    public long anatomyId = 1;
    @ColumnInfo(name = "anatomy_title")
    public String anatomyTitle;
    @ColumnInfo(name = "anatomy_subtitle")
    public String anatomySubtitle;
    @ColumnInfo(name = "anatomy_text")
    public String anatomyText;

    public SupportAnatomy() {
    }

    @Ignore
    public SupportAnatomy(long anatomy_id, String anatomy_title, String anatomy_subtitle, String anatomy_text) {
        this.anatomyId = anatomy_id;
        this.anatomyTitle = anatomy_title;
        this.anatomySubtitle = anatomy_subtitle;
        this.anatomyText = anatomy_text;
    }
}