package ru.alexsuvorov.paistewiki;

import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.Locale;

import ru.alexsuvorov.paistewiki.tools.AppPreferences;

public class App extends Application {

    private AppPreferences appPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        appPreferences = new AppPreferences(this);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        res.updateConfiguration(conf, dm);

        if (appPreferences.getText("choosed_lang").length() == 0) {
            String lang = Locale.getDefault().getLanguage();
            int count = 0;
            for (String item : AppParams.LANG) {
                Log.d(getClass().getSimpleName(), "ITEM IS: " + item);
                if (item.equals(lang)) {
                    appPreferences.saveText("choosed_lang", item);
                    break;
                }
                count++;
            }
        } else {
            conf.locale = new Locale(appPreferences.getText("choosed_lang"));
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }
}