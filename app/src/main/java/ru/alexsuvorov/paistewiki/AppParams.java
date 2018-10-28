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

    // callType = 1; Loader called by User
    // callType = 2; Loader called by Service
    public static int callType = 2;

    public static int NOTIFICATION_ID_NEWS_UPDATED = 1;
    public static String CHANNEL_ID_NEWS_UPDATED = "1";
    public static String CHANNEL_NAME_NEWS_UPDATED = "Updated news";

}
