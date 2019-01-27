package ru.alexsuvorov.paistewiki;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import ru.alexsuvorov.paistewiki.activity.ContentActivity;
import ru.alexsuvorov.paistewiki.tools.AppPreferences;
import ru.alexsuvorov.paistewiki.tools.NewsLoader;
import ru.alexsuvorov.paistewiki.tools.NewsService;

public class SplashActivity extends Activity {

    private AppPreferences appPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context context = this.getApplicationContext();
        appPreferences = new AppPreferences(this);

        if (appPreferences.getText("enable_notifications").length() == 0) {
            appPreferences.saveText("enable_notifications", "1");
        }

        ((App) getApplication()).setLocale();

        setContentView(R.layout.activity_splash);

        Runnable runnable = () -> {
            NewsLoader checkMonth = new NewsLoader();
            try {
                if (checkMonth.execute(AppParams.newsUrl, context).get()) {
                    Intent i = new Intent(SplashActivity.this, ContentActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                    if (appPreferences.getText("enable_notifications").equals("1")) {
                        if (!isServiceRunning(NewsService.class)) {
                            Log.d("MyLogs", "Service is start now");
                            Intent newsService = new Intent(context, NewsService.class);
                            context.startService(newsService);
                        }
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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ((App) getApplication()).setLocale();
    }

    public void setServiceAlarm(boolean flag) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent slIntent = new Intent(this, NewsService.class);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        PendingIntent slPendingIntent = PendingIntent.getService(this, 1, slIntent, PendingIntent.FLAG_ONE_SHOT);
        if (flag) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, slPendingIntent);
        } else {
            alarmManager.cancel(slPendingIntent);
        }
    }

    public boolean isServiceRunning(Class<?> serviceClass) {
        boolean active = false;
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            Log.d("MyLogs", "Service: " + " " + service.service.getClassName());
            if (serviceClass.getName().equals(service.service.getClassName())) {
                active = true;
            }
        }
        return active;
    }
}
