package ru.alexsuvorov.paistewiki;

import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.Log;

import java.util.Locale;

import ru.alexsuvorov.paistewiki.tools.AppPreferences;

public class App extends Application {

    private AppPreferences appPreferences;
    public static boolean newsUpdated = false;
    public static int errorCode = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        appPreferences = new AppPreferences(this);
        setLocale();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setLocale();
    }

    public void setLocale() {
        //Log.d("APP.CLASS", "setLocale");
        final Resources res = getResources();
        final Configuration configuration = res.getConfiguration();
        if (appPreferences.getText("choosed_lang").length() == 0) {
            String lang = Locale.getDefault().getLanguage();
            for (String item : AppParams.LANG) {
                //Log.d("APP.CLASS", "ITEM IS: " + item);
                if (item.equals(lang)) {
                    appPreferences.saveText("choosed_lang", item);
                    break;
                }
            }
        } else {
            configuration.locale = new Locale(appPreferences.getText("choosed_lang"));
        }
        Log.d("APP.CLASS", "UODATE IS: " + appPreferences.getText("choosed_lang"));
        res.updateConfiguration(configuration, null);
        /*
        final Resources resources = getResources();
        final Configuration configuration = resources.getConfiguration();
        final Locale locale = getLocale(this);
        if (!configuration.locale.equals(locale)) {
            configuration.setLocale(locale);
            resources.updateConfiguration(configuration, null);
        }*/
    }
}