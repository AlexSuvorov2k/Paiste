package ru.alexsuvorov.paistewiki;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import ru.alexsuvorov.paistewiki.tools.AppPreferences;
import ru.alexsuvorov.paistewiki.tools.NewsLoader;
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

        Runnable runnable = () -> {
            NewsLoader checkMonth = new NewsLoader();
            try {
                if (checkMonth.execute(AppParams.newsUrl, context).get()) {
                    Intent i = new Intent(Splash.this, StartDrawer.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                    if(!isServiceRunning(NewsService.class)) {
                        Log.d("MyLogs","Service is start now");
                        Intent serviceIntent = new Intent(Splash.this, NewsService.class);
                        startService(serviceIntent);
                    }
                    finish();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

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

    private boolean isServiceRunning(Class<?> serviceClass) {
        boolean active = false;
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            Log.d("MyLogs","Service: "+" "+service.service.getClassName());
            if (serviceClass.getName().equals(service.service.getClassName())) {
                active = true;
            }
        }
        return active;
    }
}
