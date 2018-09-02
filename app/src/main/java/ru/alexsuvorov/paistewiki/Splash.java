package ru.alexsuvorov.paistewiki;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.concurrent.ExecutionException;

import ru.alexsuvorov.paistewiki.tools.NewsLoader;

public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Context context = this.getApplicationContext();
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
}
