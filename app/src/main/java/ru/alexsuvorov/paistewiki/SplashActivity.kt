package ru.alexsuvorov.paistewiki

import android.app.Activity
import android.app.ActivityManager
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import ru.alexsuvorov.paistewiki.activity.ContentActivity
import ru.alexsuvorov.paistewiki.tools.AppPreferences
import ru.alexsuvorov.paistewiki.tools.NewsLoader
import ru.alexsuvorov.paistewiki.tools.NewsService
import java.util.Calendar
import java.util.concurrent.ExecutionException

class SplashActivity : Activity() {
    private var appPreferences: AppPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = this.getApplicationContext()
        appPreferences = AppPreferences(this)

        if (appPreferences!!.getText("enable_notifications").length == 0) {
            appPreferences!!.saveText("enable_notifications", "1")
        }

        (getApplication() as App).setLocale()

        setContentView(R.layout.activity_splash)

        val runnable = Runnable {
            val checkMonth = NewsLoader()
            try {
                if (checkMonth.execute(AppParams.newsUrl, context).get()!!) {
                    val i = Intent(this@SplashActivity, ContentActivity::class.java)
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(i)

                    if (appPreferences!!.getText("enable_notifications") == "1") {
                        if (!isServiceRunning(NewsService::class.java)) {
                            Log.d("MyLogs", "Service is start now")
                            val newsService = Intent(context, NewsService::class.java)
                            context.startService(newsService)
                        }
                    }
                    finish()
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } catch (e: ExecutionException) {
                e.printStackTrace()
            }
        }

        val thread = Thread(runnable)
        thread.start()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        (getApplication() as App).setLocale()
    }

    fun setServiceAlarm(flag: Boolean) {
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val slIntent = Intent(this, NewsService::class.java)
        val calendar = Calendar.getInstance()
        calendar.setTimeInMillis(System.currentTimeMillis())
        calendar.set(Calendar.HOUR_OF_DAY, 11)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        val slPendingIntent = PendingIntent.getService(this, 1, slIntent, PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)
        if (flag) {
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, slPendingIntent
            )
        } else {
            alarmManager.cancel(slPendingIntent)
        }
    }

    fun isServiceRunning(serviceClass: Class<*>): Boolean {
        var active = false
        val manager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.Companion.MAX_VALUE)) {
            Log.d("MyLogs", "Service: " + " " + service.service.getClassName())
            if (serviceClass.getName() == service.service.getClassName()) {
                active = true
            }
        }
        return active
    }
}
