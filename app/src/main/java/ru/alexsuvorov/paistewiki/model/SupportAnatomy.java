package ru.alexsuvorov.paistewiki.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import lombok.Getter;
import lombok.Setter;

@Entity(tableName = "support_anatomy")
public class SupportAnatomy {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "anatomy_id")
    @Getter @Setter @NonNull
    public long anatomyId = 1;

    @ColumnInfo(name = "anatomy_title")
    @Getter @Setter
    public String anatomyTitle;

    @ColumnInfo(name = "anatomy_subtitle")
    @Getter @Setter
    public String anatomySubtitle;

    @ColumnInfo(name = "anatomy_text")
    @Getter @Setter
    public String anatomyText;

    public SupportAnatomy() {
    }

    @Ignore
    public SupportAnatomy(@NonNull long anatomy_id, String anatomy_title, String anatomy_subtitle, String anatomy_text) {
        this.anatomyId = anatomy_id;
        this.anatomyTitle = anatomy_title;
        this.anatomySubtitle = anatomy_subtitle;
        this.anatomyText = anatomy_text;
    }
}
