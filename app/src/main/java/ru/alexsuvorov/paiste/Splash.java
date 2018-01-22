package ru.alexsuvorov.paiste;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Log.d("Splash", "Splash now");
                Intent i = new Intent(Splash.this, StartDrawer.class);
                startActivity(i);
                //Log.d("Splash", "Start now");
                finish();
                //Log.d("Splash","Finish now");
            }
        }, 2*1000);

    }

}
