package ru.alexsuvorov.paistewiki.feature.news.tools

import android.app.IntentService
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.os.Build
import android.os.Handler
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import ru.alexsuvorov.paistewiki.core.support.AppParams
import ru.alexsuvorov.paistewiki.feature.news.R
import java.util.Timer
import java.util.TimerTask
import java.util.concurrent.ExecutionException
import kotlin.time.Duration.Companion.hours

class NewsService : IntentService("PaisteNewsUpdater") {

    private val notificationManager: NotificationManager by lazy {
        getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }
    private val mHandler = Handler()
    private var mTimer: Timer? = null
    private val urlNews = AppParams.newsUrl

    override fun onCreate() {
        super.onCreate()
        if (mTimer != null) {
            mTimer!!.cancel()
        } else {
            mTimer = Timer()
            mTimer!!.scheduleAtFixedRate(TimeDisplay(), 0, timeout)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mTimer?.cancel()
    }

    override fun onHandleIntent(intent: Intent?) {
        if (intent != null) {
            val action = intent.action
            if (ACTION_CHECK_NEWS == action) {
                checkNews()
            }
        }
    }

    private fun checkNews() {
        val newsLoader = NewsLoader()
        try {
            newsLoader.execute(urlNews, applicationContext).get()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        }
    }

    internal inner class TimeDisplay : TimerTask() {
        override fun run() {
            mHandler.post {
                try {
                    AppParams.callType = 2
                    val newsLoader = NewsLoader()
                    newsLoader.execute(urlNews, applicationContext).get()
                    if (!AppParams.newsUpdated && AppParams.callType == 1) {
                        startActivityByName("ru.alexsuvorov.paistewiki.activity.ContentActivity")
                        AppParams.callType = 2
                    } else if (AppParams.newsUpdated && AppParams.callType == 1) {
                        startActivityByName("ru.alexsuvorov.paistewiki.activity.ContentActivity")
                        AppParams.callType = 2
                    } else if (AppParams.newsUpdated && AppParams.callType == 2) {
                        sendNotification(true)
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } catch (e: ExecutionException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun startActivityByName(className: String) {
        try {
            val intent = Intent(applicationContext, Class.forName(className))
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
    }

    private fun sendNotification(flag: Boolean) {
        val intent = try {
            Intent(applicationContext, Class.forName("ru.alexsuvorov.paistewiki.SplashActivity"))
        } catch (e: ClassNotFoundException) {
            Intent()
        }

        val pendingIntent = PendingIntent.getActivity(
            applicationContext, 0, intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val builder: NotificationCompat.Builder
        var channelId = ""
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelId = createNotificationChannel()
        }

        builder = NotificationCompat.Builder(applicationContext, channelId)
            .setTicker(getString(R.string.notification_label))
            .setAutoCancel(true)
            .setSound("android.resource://$packageName/${ru.alexsuvorov.paistewiki.core.support.R.raw.marimba_chord}".toUri())
            .setStyle(NotificationCompat.BigTextStyle().bigText(getString(R.string.notification_label)))
            .setContentIntent(pendingIntent)

        notificationManager.notify(NOTIFICATION_ID_NEWS_UPDATED, builder.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(): String {
        val channelId = AppParams.CHANNEL_ID_NEWS_UPDATED
        val channelName = getString(ru.alexsuvorov.paistewiki.core.support.R.string.nav_header_newsbutton)
        val att = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
            .build()
        val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
        channel.enableLights(true)
        channel.enableVibration(true)
        channel.setSound("android.resource://$packageName/${ru.alexsuvorov.paistewiki.core.support.R.raw.marimba_chord}".toUri(), att)
        notificationManager.createNotificationChannel(channel)
        return channelId
    }

    companion object {
        private const val ACTION_CHECK_NEWS = "ru.alexsuvorov.paistewiki.service.action.ACTION_CHECK_NEWS"
        private val timeout = 12.hours.inWholeMilliseconds
        private const val NOTIFICATION_ID_NEWS_UPDATED: Int = 1

        @JvmStatic
        internal fun startActionCheck(context: Context) {
            val intent = Intent(context, NewsService::class.java)
            intent.action = ACTION_CHECK_NEWS
            context.startService(intent)
        }
    }
}