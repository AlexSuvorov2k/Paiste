package ru.alexsuvorov.paistewiki.ItemModel;

import android.graphics.drawable.Drawable;

public class Product {

    public String cymbalName;      //Название серии
    public String cymbalSubName;   //Девиз серии
    public Drawable cymbalImage;   //Тарелка
    public Drawable seriesImage;   //Серия

    public Product(String _cymName, String _cymSubName, Drawable _cymImage, Drawable _seriesImage) {
        cymbalName = _cymName;
        cymbalSubName = _cymSubName;
        cymbalImage = _cymImage;
        seriesImage = _seriesImage;
    }

}