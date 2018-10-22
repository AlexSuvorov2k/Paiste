package ru.alexsuvorov.paistewiki.tools;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.concurrent.ExecutionException;

import ru.alexsuvorov.paistewiki.AppParams;
import ru.alexsuvorov.paistewiki.StartDrawer;

public class NewsService extends Service {


    //http://www.vogella.com/tutorials/AndroidServices/article.html
    final String LOG_TAG = "myLogs";

    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "onCreate");

        /*Intent myIntent = new Intent(Current.this , NewsService.class);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        PendingIntent pendingIntent = PendingIntent.getService(getApplication().getApplicationContext(), 0, myIntent, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24*60*60**5000 , pendingIntent);  //set repeating every 24 hours*/
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "onStartCommand");
        loadingNewsTask();
        return Service.START_STICKY;
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    void loadingNewsTask(){
        Runnable runnable = () -> {
            NewsLoader checkMonth = new NewsLoader();
            String urlNews = AppParams.newsUrl;
            try {
                if (checkMonth.execute(urlNews, this).get()) {
                    Intent i = new Intent(this, StartDrawer.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    stopSelf();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
