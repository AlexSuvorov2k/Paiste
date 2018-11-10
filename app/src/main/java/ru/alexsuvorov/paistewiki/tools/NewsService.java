package ru.alexsuvorov.paistewiki.tools;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import ru.alexsuvorov.paistewiki.App;
import ru.alexsuvorov.paistewiki.AppParams;
import ru.alexsuvorov.paistewiki.R;
import ru.alexsuvorov.paistewiki.Splash;
import ru.alexsuvorov.paistewiki.StartDrawer;

public class NewsService extends Service {

    public NewsService() {
        super();
    }

    private NotificationManager notificationManager;
    final String LOG_TAG = "myLogs";
    private Handler mHandler = new Handler();
    //public static final int timeout = 86400000;  // 24 hours
    public static final int timeout = 43200000;  // 12 hours
    private Timer mTimer = null;
    String urlNews = AppParams.newsUrl;

    public void onCreate() {
        super.onCreate();
        //Log.d(LOG_TAG, "onCreate");
        //sendNotification(false);
        if (mTimer != null) {
            mTimer.cancel();
        } else {
            mTimer = new Timer();   //recreate new
            mTimer.scheduleAtFixedRate(new TimeDisplay(), 0, timeout);
        }
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        //Log.d(LOG_TAG, "onStartCommand");
        return Service.START_STICKY;
    }

    public void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
        //Log.d(LOG_TAG, "onDestroy");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class TimeDisplay extends TimerTask {
        @Override
        public void run() {
            mHandler.post(() -> {
                try {
                    AppParams.callType = 2;
                    NewsLoader newsLoader = new NewsLoader();
                    newsLoader.execute(urlNews, getApplicationContext()).get();
                    /*Intent newsIntent = new Intent("onNewsLoaded");
                    newsIntent.putExtra("token", "");*/
                    if (!App.newsUpdated && AppParams.callType == 1) {
                        //Log.d(getClass().getSimpleName(), "NEWS IS NOT UPDATED BY USER");
                        Intent i = new Intent(getApplicationContext(), StartDrawer.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        AppParams.callType = 2;
                        //stopSelf();
                        //LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(newsIntent);
                    } else if (App.newsUpdated && AppParams.callType == 1) {
                        Intent intent = new Intent(getApplicationContext(), StartDrawer.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        AppParams.callType = 2;
                        //stopSelf();
                        //LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(newsIntent);
                    } else if (App.newsUpdated && AppParams.callType == 2) {
                        sendNotification(true);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public static class BootBroadcast extends BroadcastReceiver {

        public BootBroadcast() {
            super();
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
                context.startService(new Intent(context, NewsService.class));
                AppParams.callType = 2;
            }
        }
    }

    private void sendNotification(boolean flag) {
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, new Intent(getApplicationContext(), Splash.class), 0);
        NotificationCompat.Builder builder;
        String id = AppParams.CHANNEL_ID_NEWS_UPDATED;
        String name = AppParams.CHANNEL_NAME_NEWS_UPDATED;
        String message;
        if (flag) {
            message = getString(R.string.notification_label);
        } else {
            message = "TEST TEST";
        }
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new NotificationCompat.Builder(getBaseContext(), name);
            builder.setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(message)
                    .setTicker(getString(R.string.notification_label))
                    //.setContentText(message)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);
        } else {
            builder =
                    new NotificationCompat.Builder(getBaseContext())
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(message)
                            //.setContentText(getString(R.string.notification_label))
                            .setTicker(getString(R.string.notification_label))
                            .setAutoCancel(true)
                            /*.setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText(getString(R.string.notification_label))).setContentText(getString(R.string.notification_label))*/
                            .setContentIntent(pendingIntent);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_DEFAULT);
            AudioAttributes att = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build();
            notificationChannel.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.marimba_chord), att);
            builder.setChannelId(id);
        } else {
            builder.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.marimba_chord));
        }
        notificationManager.notify(AppParams.NOTIFICATION_ID_NEWS_UPDATED, builder.build());
    }
}
