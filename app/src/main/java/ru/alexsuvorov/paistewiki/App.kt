package ru.alexsuvorov.paistewiki

import android.app.Application
import android.content.res.Configuration
import ru.alexsuvorov.paistewiki.tools.AppPreferences
import java.util.Locale

class App : Application() {
    private var appPreferences: AppPreferences? = null
    override fun onCreate() {
        super.onCreate()
        appPreferences = AppPreferences(this)
        setLocale()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setLocale()
    }

    fun setLocale() {
        //Log.d("APP.CLASS", "setLocale");
        val res = getResources()
        val configuration = res.getConfiguration()
        if (appPreferences!!.getText("choosed_lang").length == 0) {
            val lang = Locale.getDefault().getLanguage()
            for (item in AppParams.LANG) {
                //Log.d("APP.CLASS", "ITEM IS: " + item);
                if (item == lang) {
                    appPreferences!!.saveText("choosed_lang", item)
                    break
                }
            }
        } else {
            configuration.locale = Locale(appPreferences!!.getText("choosed_lang"))
        }
        //Log.d("APP.CLASS", "UODATE IS: " + appPreferences.getText("choosed_lang"));
        res.updateConfiguration(configuration, null)
    }

    companion object {
        @JvmField
        var newsUpdated: Boolean = false
        @JvmField
        var errorCode: Int = 0
    }
}