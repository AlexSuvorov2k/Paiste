package ru.alexsuvorov.paistewiki.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import lombok.Getter;
import lombok.Setter;

@Entity(tableName = "cymbalseries")
public class CymbalSeries {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cymbalseries_id")
    @Getter @Setter
    public int cymbalseries_id;   //Ид серии
    @ColumnInfo(name = "cymbalseries_name")
    @Getter @Setter @NonNull
    public String cymbalName;      //Название серии
    @ColumnInfo(name = "cymbalseries_subname")
    @Getter @Setter
    public String cymbalSubName;   //Девиз серии
    @ColumnInfo(name = "cymbalseries_singleimageuri")
    @Getter @Setter
    public String cymbalImage;   //Картинка одной тарелки
    @ColumnInfo(name = "cymbalseries_imageuri")
    @Getter @Setter
    public String seriesImage;   //Картинка серии
    @ColumnInfo(name = "cymbalseries_description")
    @Getter @Setter
    public String seriesDescription;   //Описание серии
    @ColumnInfo(name = "cymbalseries_isproduced")
    @Getter @Setter
    public int seriesIsProduced = 1;   //В производстве?
    @ColumnInfo(name = "cymbalseries_description_application")
    @Getter @Setter
    public String seriesDescriptionApplication;
    @ColumnInfo(name = "cymbalseries_description_since")
    @Getter @Setter
    public String seriesDescriptionSince;
    @ColumnInfo(name = "cymbalseries_description_sound")
    @Getter @Setter
    public String seriesDescriptionSound;
    @ColumnInfo(name = "cymbalseries_description_alloy")
    @Getter @Setter
    public String seriesDescriptionAlloy;

    public CymbalSeries() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CymbalSeries cymbalSeries = (CymbalSeries) o;
        if (cymbalseries_id != cymbalSeries.cymbalseries_id) return false;
        return cymbalName != null ? cymbalName.equals(cymbalSeries.cymbalName) : cymbalSeries.cymbalName == null;
    }

    @Override
    public int hashCode() {
        int result = cymbalseries_id;
        result = 31 * result + (cymbalName != null ? cymbalName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CymbalSeries{" +
                "cymbalseries_id=" + cymbalseries_id +
                ", cymbalName='" + cymbalName + '\'' +
                ", cymbalSubName='" + cymbalSubName + '\'' +
                ", cymbalImage='" + cymbalImage + '\'' +
                ", seriesImage='" + seriesImage + '\'' +
                ", seriesDescription='" + seriesDescription + '\'' +
                ", seriesIsProduced=" + seriesIsProduced +
                '}';
    }
}