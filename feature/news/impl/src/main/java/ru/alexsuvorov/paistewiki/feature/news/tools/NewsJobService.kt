package ru.alexsuvorov.paistewiki.feature.news.tools

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log

class NewsJobService : JobService() {

    override fun onStartJob(params: JobParameters): Boolean {
        Log.i(TAG, "onStartJob: starting job with id: ${params.jobId}")
        NewsService.startActionCheck(applicationContext)
        return true
    }

    override fun onStopJob(params: JobParameters): Boolean {
        Log.i(TAG, "onStopJob: stopping job with id: ${params.jobId}")
        return true
    }

    companion object {
        private val TAG = NewsJobService::class.java.simpleName
    }
}