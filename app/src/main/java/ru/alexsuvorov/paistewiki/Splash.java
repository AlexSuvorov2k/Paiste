package ru.alexsuvorov.paistewiki;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import java.util.Locale;

import ru.alexsuvorov.paistewiki.tools.AppPreferences;
import ru.alexsuvorov.paistewiki.tools.NewsService;

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
        // use this to start and trigger a service
        Intent i= new Intent(context, NewsService.class);
        // potentially add data to the intent
        i.putExtra("KEY1", "Value to be used by the service");
        context.startService(i);

        /*Runnable runnable = () -> {
            NewsLoader checkMonth = new NewsLoader();
            String urlNews = AppParams.newsUrl;
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
        };
        Thread thread = new Thread(runnable);
        thread.start();*/
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
