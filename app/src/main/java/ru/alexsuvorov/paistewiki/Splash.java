package ru.alexsuvorov.paistewiki;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import ru.alexsuvorov.paistewiki.utils.NewsLoader;
import ru.alexsuvorov.paistewiki.utils.Utils;

public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        if (Utils.isNetworkAvailable(this)) {
            NewsLoader checkMonth = new NewsLoader();
            String urlNews = "http://paiste.com/e/news.php?menuid=39";
            try {
                if (checkMonth.execute(urlNews).get()) {
                    Intent i = new Intent(Splash.this, StartDrawer.class);
                    startActivity(i);
                    finish();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "No Network Connection", Toast.LENGTH_LONG).show();
        }
    }
}
