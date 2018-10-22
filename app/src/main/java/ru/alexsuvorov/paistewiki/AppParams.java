package ru.alexsuvorov.paistewiki;

import android.content.Context;

public class AppParams {

    public static String[] LANG = {"en", "ru"};

    public static String getLangLabel(Context context, int index) {
        String[] LANG = {context.getString(R.string.language_en),
                context.getString(R.string.language_ru)};
        return LANG[index];
    }

    public static String newsUrl = "http://paiste.com/e/news.php?menuid=39";
}
