package ru.alexsuvorov.paistewiki.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "cymbalseries")
public class CymbalSeries {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cymbalseries_id")
    private int cymbalseries_id;   //Ид серии
    @ColumnInfo(name = "cymbalseries_name")
    @NonNull
    private String cymbalName;      //Название серии
    @ColumnInfo(name = "cymbalseries_subname")
    private String cymbalSubName;   //Девиз серии
    @ColumnInfo(name = "cymbalseries_singleimageuri")
    private String cymbalImage;   //Картинка одной тарелки
    @ColumnInfo(name = "cymbalseries_imageuri")
    private String seriesImage;   //Картинка серии
    @ColumnInfo(name = "cymbalseries_description")
    private String seriesDescription;   //Описание серии
    @ColumnInfo(name = "cymbalseries_isproduced")
    @NonNull
    private String seriesIsProduced;   //В производстве?

    public CymbalSeries() {
    }

    @Ignore
    public CymbalSeries(int cymbalseries_id, @NonNull String cymbalName, String cymbalSubName, String cymbalImage, String seriesImage) {
        this.cymbalseries_id = cymbalseries_id;
        this.cymbalName = cymbalName;
        this.cymbalSubName = cymbalSubName;
        this.cymbalImage = cymbalImage;
        this.seriesImage = seriesImage;
    }

    public int getCymbalseries_id() {
        return cymbalseries_id;
    }

    public void setCymbalseries_id(int cymbalseries_id) {
        this.cymbalseries_id = cymbalseries_id;
    }

    public String getCymbalName() {
        return cymbalName;
    }

    public void setCymbalName(String cymbalName) {
        this.cymbalName = cymbalName;
    }

    public String getCymbalSubName() {
        return cymbalSubName;
    }

    public void setCymbalSubName(String cymbalSubName) {
        this.cymbalSubName = cymbalSubName;
    }

    public String getCymbalImage() {
        return cymbalImage;
    }

    public void setCymbalImage(String cymbalImage) {
        this.cymbalImage = cymbalImage;
    }

    public String getSeriesImage() {
        return seriesImage;
    }

    public void setSeriesImage(String seriesImage) {
        this.seriesImage = seriesImage;
    }

    public String getSeriesDescription() {
        return seriesDescription;
    }

    public void setSeriesDescription(String seriesDescription) {
        this.seriesDescription = seriesDescription;
    }

    @NonNull
    public String getSeriesIsProduced() {
        return seriesIsProduced;
    }

    public void setSeriesIsProduced(@NonNull String seriesIsProduced) {
        this.seriesIsProduced = seriesIsProduced;
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
                '}';
    }
}