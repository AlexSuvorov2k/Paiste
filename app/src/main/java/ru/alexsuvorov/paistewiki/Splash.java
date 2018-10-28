package ru.alexsuvorov.paistewiki;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.Locale;

import ru.alexsuvorov.paistewiki.tools.AppPreferences;
import ru.alexsuvorov.paistewiki.tools.NewsService;

public class Splash extends Activity {

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private AppPreferences appPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context context = this.getApplicationContext();
        appPreferences = new AppPreferences(this);

        Locale current = getResources().getConfiguration().locale;
        Locale locale = null;
        if (appPreferences.getText("choosed_lang").length() == 0) {
            Log.d(getClass().getSimpleName(),"Preferences = 0");
            for (String lang : AppParams.LANG) {
                if (lang.equals(current.getLanguage())) {
                    appPreferences.saveText("choosed_lang", lang);
                    locale = new Locale(lang);
                } else {
                    locale = new Locale("en");
                    appPreferences.saveText("choosed_lang", lang);
                }
            }
        }

        Configuration config = new Configuration();
        config.locale = locale;
        this.getBaseContext().getResources().updateConfiguration(config,
                this.getBaseContext().getResources().getDisplayMetrics());
        setContentView(R.layout.splash);

        Intent i= new Intent(context, NewsService.class);
        AppParams.callType=1;
        context.startService(i);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                        Intent intentEntrance = new Intent(getApplicationContext(), StartDrawer.class);
                        startActivity(intentEntrance);
                }
            };
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        newConfig.locale = new Locale(AppParams.LANG[Integer.valueOf(appPreferences.getText("choosed_lang"))]);
    }
}
