package ru.alexsuvorov.paistewiki.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
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
    private int seriesIsProduced = 1;   //В производстве?
    @ColumnInfo(name = "cymbalseries_description_application")
    private String seriesDescriptionApplication;
    @ColumnInfo(name = "cymbalseries_description_since")
    private String seriesDescriptionSince;
    @ColumnInfo(name = "cymbalseries_description_sound")
    private String seriesDescriptionSound;
    @ColumnInfo(name = "cymbalseries_description_alloy")
    private String seriesDescriptionAlloy;

    public CymbalSeries() {
    }

    public int getCymbalseries_id() {
        return cymbalseries_id;
    }

    public void setCymbalseries_id(int cymbalseries_id) {
        this.cymbalseries_id = cymbalseries_id;
    }

    @NonNull
    public String getCymbalName() {
        return cymbalName;
    }

    public void setCymbalName(@NonNull String cymbalName) {
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

    public int getSeriesIsProduced() {
        return seriesIsProduced;
    }

    public void setSeriesIsProduced(int seriesIsProduced) {
        this.seriesIsProduced = seriesIsProduced;
    }

    public String getSeriesDescriptionApplication() {
        return seriesDescriptionApplication;
    }

    public void setSeriesDescriptionApplication(String seriesDescriptionApplication) {
        this.seriesDescriptionApplication = seriesDescriptionApplication;
    }

    public String getSeriesDescriptionSince() {
        return seriesDescriptionSince;
    }

    public void setSeriesDescriptionSince(String seriesDescriptionSince) {
        this.seriesDescriptionSince = seriesDescriptionSince;
    }

    public String getSeriesDescriptionSound() {
        return seriesDescriptionSound;
    }

    public void setSeriesDescriptionSound(String seriesDescriptionSound) {
        this.seriesDescriptionSound = seriesDescriptionSound;
    }

    public String getSeriesDescriptionAlloy() {
        return seriesDescriptionAlloy;
    }

    public void setSeriesDescriptionAlloy(String seriesDescriptionAlloy) {
        this.seriesDescriptionAlloy = seriesDescriptionAlloy;
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