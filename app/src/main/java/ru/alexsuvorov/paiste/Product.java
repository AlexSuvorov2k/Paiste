package ru.alexsuvorov.paiste;

import android.graphics.drawable.Drawable;

public class Product {

    String cymbalName;      //Название серии
    String cymbalSubName;   //Девиз серии
    Drawable cymbalImage;   //Тарелка
    Drawable seriesImage;   //Серия

    Product(String _cymName, String _cymSubName, Drawable _cymImage, Drawable _seriesImage) {
        cymbalName = _cymName;
        cymbalSubName = _cymSubName;
        cymbalImage = _cymImage;
        seriesImage = _seriesImage;
    }

}