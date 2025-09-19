package ru.alexsuvorov.paistewiki.tools

import android.app.job.JobParameters
import android.app.job.JobService
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
class NewsJobService : JobService() {
    override fun onStartJob(params: JobParameters): Boolean {
        Log.i(NewsJobService.Companion.TAG, "onStartJob: starting job with id: " + params.getJobId())

        NewsService.Companion.startActionCheck(getApplicationContext())
        return true
    }

    override fun onStopJob(params: JobParameters): Boolean {
        Log.i(NewsJobService.Companion.TAG, "onStopJob: stopping job with id: " + params.getJobId())
        return true
    }

    companion object {
        private val TAG: String = NewsJobService::class.java.getSimpleName()
    }
}