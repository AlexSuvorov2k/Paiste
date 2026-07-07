package ru.alexsuvorov.paistewiki

import android.app.Application
import ru.alexsuvorov.paistewiki.tools.AppPreferences

class App : Application() {

    private val appPreferences: AppPreferences by lazy {
        AppPreferences(this)
    }

    override fun onCreate() {
        super.onCreate()
        // AppCompat 1.7.0+ with autoStoreLocales=true in AndroidManifest.xml 
        // handles locale restoration automatically.
    }
}
