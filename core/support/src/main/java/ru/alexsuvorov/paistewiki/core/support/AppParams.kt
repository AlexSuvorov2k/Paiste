package ru.alexsuvorov.paistewiki.core.support

import android.content.Context
import java.util.Locale

object AppParams {

    @JvmField
    val LANG = arrayOf("en", "ru")

    @JvmStatic
    fun setLocale(choosedLang: String): String {
        var lang = choosedLang
        
        if (lang.isEmpty()) {
            val defaultLang = Locale.getDefault().language
            for (item in LANG) {
                if (item == defaultLang) {
                    lang = item
                    break
                }
            }
        }
        return lang
    }

    @JvmStatic
    fun getLangLabel(context: Context, index: Int): String {
        // We'll pass the labels or use context.resources.getIdentifier if we want to avoid direct R dependency
        // Or we can move the strings to core:support as well.
        val res = context.resources
        val packageName = context.packageName
        val labels = arrayOf(
            res.getString(res.getIdentifier("language_en", "string", packageName)),
            res.getString(res.getIdentifier("language_ru", "string", packageName))
        )
        return labels[index]
    }

    @JvmField
    var newsUrl = "http://paiste.com/e/news.php?menuid=39"

    @JvmField
    var callType = 2

    @JvmField
    var CHANNEL_ID_NEWS_UPDATED = "556"

    @JvmField
    var newsUpdated: Boolean = false

    @JvmField
    var errorCode: Int = 0
}
