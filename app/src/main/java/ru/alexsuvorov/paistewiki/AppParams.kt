package ru.alexsuvorov.paistewiki

import android.content.Context

object AppParams {
    @JvmField
    var LANG: Array<String?> = arrayOf<String?>("en", "ru")

    @JvmStatic
    fun getLangLabel(context: Context, index: Int): String {
        val LANG = arrayOf<String>(
            context.getString(R.string.language_en),
            context.getString(R.string.language_ru)
        )
        return LANG[index]
    }

    @JvmField
    var newsUrl: String = "http://paiste.com/e/news.php?menuid=39"

    // callType = 1; Loader called by User
    // callType = 2; Loader called by Service
    @JvmField
    var callType: Int = 2

    @JvmField
    var NOTIFICATION_ID_NEWS_UPDATED: Int = 1
    @JvmField
    var CHANNEL_ID_NEWS_UPDATED: String = "556"
}