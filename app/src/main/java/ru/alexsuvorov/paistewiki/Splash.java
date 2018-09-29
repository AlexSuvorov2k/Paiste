package ru.alexsuvorov.paistewiki;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import ru.alexsuvorov.paistewiki.tools.AppPreferences;
import ru.alexsuvorov.paistewiki.tools.NewsLoader;

public class Splash extends Activity {


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
                    //appPreferences.getText("choosed_lang")
                    appPreferences.saveText("choosed_lang", lang);
                    locale = new Locale(lang);
                } else {
                    locale = new Locale("en");
                    appPreferences.saveText("choosed_lang", lang);
                }
            }
        }

        //locale = new Locale(appPreferences.getText("choosed_lang"));
        Configuration config = new Configuration();
        config.locale = locale;
        this.getBaseContext().getResources().updateConfiguration(config,
                this.getBaseContext().getResources().getDisplayMetrics());
        setContentView(R.layout.splash);
        Runnable runnable = new Runnable() {
            public void run() {
                NewsLoader checkMonth = new NewsLoader();
                String urlNews = "http://paiste.com/e/news.php?menuid=39";
                try {
                    if (checkMonth.execute(urlNews, context).get()) {
                        Intent i = new Intent(Splash.this, StartDrawer.class);
                        startActivity(i);
                        finish();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
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
        // your code here, you can use newConfig.locale if you need to check the language
        // or just re-set all the labels to desired string resource
    }
}
