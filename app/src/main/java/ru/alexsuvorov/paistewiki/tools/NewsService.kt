package ru.alexsuvorov.paistewiki.tools

import android.app.IntentService
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import ru.alexsuvorov.paistewiki.App
import ru.alexsuvorov.paistewiki.AppParams
import ru.alexsuvorov.paistewiki.R
import ru.alexsuvorov.paistewiki.SplashActivity
import ru.alexsuvorov.paistewiki.activity.ContentActivity
import java.util.Timer
import java.util.TimerTask
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit

class NewsService : IntentService("PaisteNewsUpdater") {
    private var notificationManager: NotificationManager? = null

    val LOG_TAG: String = "myLogs"
    private val mHandler = Handler()
    private var mTimer: Timer? = null
    var urlNews: String = AppParams.newsUrl

    override fun onCreate() {
        super.onCreate()
        if (mTimer != null) {
            mTimer!!.cancel()
        } else {
            mTimer = Timer() //recreate new
            mTimer!!.scheduleAtFixedRate(TimeDisplay(), 0, timeout.toLong())
        }
    }

    // https://habr.com/ru/post/339012/
    // https://github.com/iHandy/JobSchedulerDemo/blob/master/app/src/main/java/com/github/ihandy/jobschedulerdemo/ExerciseIntentService.java
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //Log.d(LOG_TAG, "onStartCommand");
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mTimer!!.cancel()
        //Log.d(LOG_TAG, "onDestroy");
    }

    override fun onHandleIntent(intent: Intent?) {
        if (intent != null) {
            val action = intent.getAction()
            if (NewsService.Companion.ACTION_CHECK_NEWS == action) {
                checkNews()
            }
        }
    }

    private fun checkNews() {
        val newsLoader = NewsLoader()
        try {
            newsLoader.execute(urlNews, getApplicationContext()).get()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        }
    }

    internal inner class TimeDisplay : TimerTask() {
        override fun run() {
            mHandler.post(Runnable {
                try {
                    AppParams.callType = 2
                    val newsLoader = NewsLoader()
                    newsLoader.execute(urlNews, getApplicationContext()).get()
                    /*Intent newsIntent = new Intent("onNewsLoaded");
                    newsIntent.putExtra("token", "");*/
                    if (!App.newsUpdated && AppParams.callType == 1) {
                        //Log.d(getClass().getSimpleName(), "NEWS IS NOT UPDATED BY USER");
                        val i = Intent(getApplicationContext(), ContentActivity::class.java)
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(i)
                        AppParams.callType = 2
                        //stopSelf();
                        //LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(newsIntent);
                    } else if (App.newsUpdated && AppParams.callType == 1) {
                        val intent = Intent(getApplicationContext(), ContentActivity::class.java)
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                        AppParams.callType = 2
                        //stopSelf();
                        //LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(newsIntent);
                    } else if (App.newsUpdated && AppParams.callType == 2) {
                        sendNotification(true)
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } catch (e: ExecutionException) {
                    e.printStackTrace()
                }
            })
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
    class ExerciseRequestsReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Log.d(ExerciseRequestsReceiver.Companion.TAG, "onReceive: action: " + intent.getAction())

            when (intent.getAction()) {
                ACTION_PERFORM_EXERCISE -> scheduleJob(context)
                else -> throw IllegalArgumentException("Unknown action.")
            }
        }

        private fun scheduleJob(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val jobService = ComponentName(context, NewsService::class.java)
                val exerciseJobBuilder = JobInfo.Builder(ExerciseRequestsReceiver.Companion.sJobId++, jobService)

                exerciseJobBuilder.setMinimumLatency(TimeUnit.SECONDS.toMillis(1))

                exerciseJobBuilder.setOverrideDeadline(TimeUnit.SECONDS.toMillis(5))
                exerciseJobBuilder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                exerciseJobBuilder.setRequiresDeviceIdle(false)
                exerciseJobBuilder.setRequiresCharging(false)
                exerciseJobBuilder.setBackoffCriteria(TimeUnit.SECONDS.toMillis(10), JobInfo.BACKOFF_POLICY_LINEAR)

                Log.i(ExerciseRequestsReceiver.Companion.TAG, "scheduleJob: adding job to scheduler")

                val jobScheduler = context.getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
                jobScheduler.schedule(exerciseJobBuilder.build())
            }
        }

        companion object {
            private val TAG: String = ExerciseRequestsReceiver::class.java.getSimpleName()

            const val ACTION_PERFORM_EXERCISE: String = "ACTION_PERFORM_EXERCISE"

            private var sJobId = 1
        }
    }

    private fun sendNotification(flag: Boolean) {
        val pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, Intent(getApplicationContext(), SplashActivity::class.java), PendingIntent.FLAG_IMMUTABLE)
        val builder: NotificationCompat.Builder?
        if (notificationManager == null) {
            notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager?
        }
        var channelId = ""
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelId = createNotificationChannel()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = NotificationCompat.Builder(getApplicationContext(), channelId)
            builder.setSmallIcon(R.mipmap.ic_launcher)
                .setTicker(getString(R.string.notification_label))
                .setAutoCancel(true)
                .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.marimba_chord))
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(getString(R.string.notification_label))
                )
                .setContentIntent(pendingIntent)
        } else {
            builder = NotificationCompat.Builder(getApplicationContext(), channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker(getString(R.string.notification_label))
                .setAutoCancel(true)
                .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.marimba_chord))
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(getString(R.string.notification_label))
                )
                .setContentIntent(pendingIntent)
        }
        notificationManager!!.notify(AppParams.NOTIFICATION_ID_NEWS_UPDATED, builder.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(): String {
        val channelId = AppParams.CHANNEL_ID_NEWS_UPDATED
        val channelName = getString(R.string.nav_header_newsbutton)

        val att = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
            .build()
        val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
        channel.enableLights(true)
        channel.enableVibration(true)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        channel.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.marimba_chord), att)
        notificationManager.createNotificationChannel(channel)
        return channelId
    }

    companion object {
        private const val ACTION_CHECK_NEWS = "ru.alexsuvorov.paistewiki.service.action.ACTION_CHECK_NEWS"
        private const val ACTION_SCHEDULE = "ru.alexsuvorov.paistewiki.service.action.ACTION_SCHEDULE"

        //public static final int timeout = 86400000;  // 24 hours
        const val timeout: Int = 43200000 // 12 hours
        fun startActionCheck(context: Context) {
            val intent = Intent(context, NewsService::class.java)
            intent.setAction(NewsService.Companion.ACTION_CHECK_NEWS)
            context.startService(intent)
        }
    }
}