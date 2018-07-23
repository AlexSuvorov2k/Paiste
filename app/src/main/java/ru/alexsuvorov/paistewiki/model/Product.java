package ru.alexsuvorov.paistewiki.model;

import android.graphics.drawable.Drawable;

public class Product {

    private String cymbalName;      //Название серии
    private String cymbalSubName;   //Девиз серии
    private Drawable cymbalImage;   //Тарелка
    private Drawable seriesImage;   //Серия

    public Product(String _cymName, String _cymSubName, Drawable _cymImage, Drawable _seriesImage) {
        cymbalName = _cymName;
        cymbalSubName = _cymSubName;
        cymbalImage = _cymImage;
        seriesImage = _seriesImage;
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

    public Drawable getCymbalImage() {
        return cymbalImage;
    }

    public void setCymbalImage(Drawable cymbalImage) {
        this.cymbalImage = cymbalImage;
    }

    public Drawable getSeriesImage() {
        return seriesImage;
    }

    public void setSeriesImage(Drawable seriesImage) {
        this.seriesImage = seriesImage;
    }
}