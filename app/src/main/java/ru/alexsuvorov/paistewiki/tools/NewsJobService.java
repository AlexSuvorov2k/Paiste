package ru.alexsuvorov.paistewiki.tools;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class NewsJobService extends JobService {

    private static final String TAG = NewsJobService.class.getSimpleName();

    public NewsJobService() {
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(TAG, "onStartJob: starting job with id: " + params.getJobId());

        NewsService.startActionCheck(getApplicationContext());
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG, "onStopJob: stopping job with id: " + params.getJobId());
        return true;
    }

}