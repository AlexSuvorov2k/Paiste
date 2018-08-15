package ru.alexsuvorov.paistewiki;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.ExecutionException;

import ru.alexsuvorov.paistewiki.tools.NewsLoader;

public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);


        Runnable runnable = new Runnable() {
            public void run() {
                /*Log.d("Splash", "Running, checking network");
                if (Utils.isNetworkAvailable(Splash.this)) {*/
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
                /*} else {
                    Toast.makeText(Splash.this, "No Network Connection", Toast.LENGTH_LONG).show();
                }*/

                /*Message msg = handler.obtainMessage();
                Bundle bundle = new Bundle();
                SimpleDateFormat dateFormat =
                        new SimpleDateFormat("HH:mm:ss MM/dd/yyyy",
                                Locale.US);
                String dateString =
                        dateFormat.format(new Date());
                bundle.putString("Key", dateString);
                msg.setData(bundle);
                handler.sendMessage(msg);*/
            }
        };
        Thread thread = new Thread(runnable);
        Log.d("Splash", "Thread start");
        thread.start();
        Log.d("Splash", "Thread started");


    /*Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            String date = bundle.getString("Key");
            TextView infoTextView =
                    (TextView) findViewById(R.id.textViewInfo);
            infoTextView.setText(date);
        }
    };*/


    }
}
