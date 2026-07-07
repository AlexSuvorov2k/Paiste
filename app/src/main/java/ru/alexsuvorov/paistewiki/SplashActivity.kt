package ru.alexsuvorov.paistewiki

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import ru.alexsuvorov.paistewiki.activity.ContentActivity
import ru.alexsuvorov.paistewiki.core.support.AppParams
import ru.alexsuvorov.paistewiki.feature.news.tools.NewsLoader
import ru.alexsuvorov.paistewiki.feature.news.tools.NewsService
import ru.alexsuvorov.paistewiki.tools.AppPreferences
import java.util.concurrent.ExecutionException

class SplashActivity : Activity() {

    private lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = this.applicationContext
        appPreferences = AppPreferences(this)

        if (appPreferences.getText("enable_notifications").isEmpty()) {
            appPreferences.saveText("enable_notifications", "1")
        }

        setContentView(R.layout.activity_splash)

        val runnable = Runnable {
            val checkMonth = NewsLoader()
            try {
                if (checkMonth.execute(AppParams.newsUrl, context).get()) {
                    val i = Intent(this@SplashActivity, ContentActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(i)

                    if (appPreferences.getText("enable_notifications") == "1") {
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
    }

    private fun isServiceRunning(serviceClass: Class<*>): Boolean {
        var active = false
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            Log.d("MyLogs", "Service:  ${service.service.className}")
            if (serviceClass.name == service.service.className) {
                active = true
            }
        }
        return active
    }
}
