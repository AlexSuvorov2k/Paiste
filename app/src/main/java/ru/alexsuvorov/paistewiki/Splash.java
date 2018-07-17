package ru.alexsuvorov.paistewiki;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import ru.alexsuvorov.paistewiki.Utils.GetMonth;
import ru.alexsuvorov.paistewiki.Utils.Utils;

public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        if (Utils.isNetworkAvailable(this)) {
            GetMonth asyncTask = new GetMonth();
            asyncTask.execute();
        } else {
            Toast.makeText(this, "No Network Connection", Toast.LENGTH_LONG).show();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Splash.this, StartDrawer.class);
                startActivity(i);
                finish();
            }
        }, 2 * 1000);

    }
}
