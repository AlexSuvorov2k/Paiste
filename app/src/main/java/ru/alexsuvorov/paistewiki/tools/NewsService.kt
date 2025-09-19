package ru.alexsuvorov.paistewiki.tools;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import ru.alexsuvorov.paistewiki.App;
import ru.alexsuvorov.paistewiki.AppParams;
import ru.alexsuvorov.paistewiki.R;
import ru.alexsuvorov.paistewiki.SplashActivity;
import ru.alexsuvorov.paistewiki.activity.ContentActivity;

public class NewsService extends IntentService {

    public NewsService() {
        super("PaisteNewsUpdater");
    }

    private NotificationManager notificationManager;

    private static final String ACTION_CHECK_NEWS =
            "ru.alexsuvorov.paistewiki.service.action.ACTION_CHECK_NEWS";
    private static final String ACTION_SCHEDULE =
            "ru.alexsuvorov.paistewiki.service.action.ACTION_SCHEDULE";

    final String LOG_TAG = "myLogs";
    private final Handler mHandler = new Handler();
    //public static final int timeout = 86400000;  // 24 hours
    public static final int timeout = 43200000;  // 12 hours
    private Timer mTimer = null;
    String urlNews = AppParams.newsUrl;

    public void onCreate() {
        super.onCreate();
        if (mTimer != null) {
            mTimer.cancel();
        } else {
            mTimer = new Timer();   //recreate new
            mTimer.scheduleAtFixedRate(new TimeDisplay(), 0, timeout);
        }
    }

    // https://habr.com/ru/post/339012/

    // https://github.com/iHandy/JobSchedulerDemo/blob/master/app/src/main/java/com/github/ihandy/jobschedulerdemo/ExerciseIntentService.java

    public int onStartCommand(Intent intent, int flags, int startId) {
        //Log.d(LOG_TAG, "onStartCommand");
        return Service.START_STICKY;
    }

    public static void startActionCheck(Context context) {
        Intent intent = new Intent(context, NewsService.class);
        intent.setAction(ACTION_CHECK_NEWS);
        context.startService(intent);
    }

    public void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
        //Log.d(LOG_TAG, "onDestroy");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_CHECK_NEWS.equals(action)) {
                checkNews();
            }
        }
    }

    private void checkNews() {
        NewsLoader newsLoader = new NewsLoader();
        try {
            newsLoader.execute(urlNews, getApplicationContext()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
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
                        Intent i = new Intent(getApplicationContext(), ContentActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        AppParams.callType = 2;
                        //stopSelf();
                        //LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(newsIntent);
                    } else if (App.newsUpdated && AppParams.callType == 1) {
                        Intent intent = new Intent(getApplicationContext(), ContentActivity.class);
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

    /*public static class BootBroadcast extends BroadcastReceiver {

        public BootBroadcast() {
            super();
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
                Intent newsService = new Intent(context, NewsService.class);
                context.startService(newsService);
                AppParams.callType = 2;
            }
        }
    }*/

    public static class ExerciseRequestsReceiver extends BroadcastReceiver {

        private static final String TAG = ExerciseRequestsReceiver.class.getSimpleName();

        public static final String ACTION_PERFORM_EXERCISE = "ACTION_PERFORM_EXERCISE";

        private static int sJobId = 1;

        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d(TAG, "onReceive: action: " + intent.getAction());

            switch (intent.getAction()) {
                case ACTION_PERFORM_EXERCISE:
                    scheduleJob(context);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown action.");
            }
        }

        private void scheduleJob(Context context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ComponentName jobService = new ComponentName(context, NewsService.class);
                JobInfo.Builder exerciseJobBuilder = new JobInfo.Builder(sJobId++, jobService);

                exerciseJobBuilder.setMinimumLatency(TimeUnit.SECONDS.toMillis(1));

                exerciseJobBuilder.setOverrideDeadline(TimeUnit.SECONDS.toMillis(5));
                exerciseJobBuilder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);
                exerciseJobBuilder.setRequiresDeviceIdle(false);
                exerciseJobBuilder.setRequiresCharging(false);
                exerciseJobBuilder.setBackoffCriteria(TimeUnit.SECONDS.toMillis(10), JobInfo.BACKOFF_POLICY_LINEAR);

                Log.i(TAG, "scheduleJob: adding job to scheduler");

                JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
                jobScheduler.schedule(exerciseJobBuilder.build());
            }
        }
    }

    private void sendNotification(boolean flag) {
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, new Intent(getApplicationContext(), SplashActivity.class), PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder;
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        String channelId = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelId = createNotificationChannel();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new NotificationCompat.Builder(getApplicationContext(), channelId);
            builder.setSmallIcon(R.mipmap.ic_launcher)
                    .setTicker(getString(R.string.notification_label))
                    .setAutoCancel(true)
                    .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.marimba_chord))
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(getString(R.string.notification_label)))
                    .setContentIntent(pendingIntent);
        } else {
            builder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setTicker(getString(R.string.notification_label))
                    .setAutoCancel(true)
                    .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.marimba_chord))
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(getString(R.string.notification_label)))
                    .setContentIntent(pendingIntent);
        }
        notificationManager.notify(AppParams.NOTIFICATION_ID_NEWS_UPDATED, builder.build());
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private String createNotificationChannel() {
        String channelId = AppParams.CHANNEL_ID_NEWS_UPDATED;
        String channelName = getString(R.string.nav_header_newsbutton);

        AudioAttributes att = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build();
        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
        channel.enableLights(true);
        channel.enableVibration(true);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        channel.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.marimba_chord), att);
        notificationManager.createNotificationChannel(channel);
        return channelId;
    }
}