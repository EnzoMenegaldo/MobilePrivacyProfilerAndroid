package fr.inria.diverse.mobileprivacyprofiler.job;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import fr.inria.diverse.mobileprivacyprofiler.BuildConfig;
import fr.inria.diverse.mobileprivacyprofiler.services.PacketSnifferService.PacketSnifferService;
import fr.inria.diverse.mobileprivacyprofiler.services.ScanActivityIntentService;
import fr.inria.diverse.mobileprivacyprofiler.services.ScanSocialIntentService;

public class ScanNetActivityJob extends Job{

    public static final String TAG = "NetActivityJob";


    private static final boolean DEBUG = BuildConfig.DEBUG;

    public ScanNetActivityJob(){}

    @Override
    @NonNull
    protected Job.Result onRunJob(@NonNull final Job.Params params) {
        ScanActivityIntentService.startActionNetActivity();
        return Job.Result.SUCCESS;
    }


    public static int schedule() {
        Set<JobRequest> jobRequests = JobManager.instance().getAllJobRequestsForTag(TAG);
        if (!jobRequests.isEmpty()) {
            return jobRequests.iterator().next().getJobId();
        }
        // adapt the triggering of the update
        long interval = TimeUnit.HOURS.toMillis(12); // every 12 h
        long flex = TimeUnit.HOURS.toMillis(1); // -+ 1h to execute

        if (DEBUG) {
            interval = JobRequest.MIN_INTERVAL;
            flex = JobRequest.MIN_FLEX;
        }

        return new JobRequest.Builder(TAG)
                //.setPeriodic(interval, flex)
                .startNow()
                //.setUpdateCurrent(true)
                //.setRequiresBatteryNotLow(true)
                //.setRequiresStorageNotLow(true)
                //.setRequiredNetworkType(JobRequest.NetworkType.UNMETERED)
                //.setRequiresCharging(true)
                //.setRequirementsEnforced(true)
                .build()
                .schedule();
    }

    public static void cancelRequest(Context context) {
        //Boadcast an Intent to the PacketSnifferService to kill it
        Intent intent = new Intent(PacketSnifferService.STOP_SERVICE_INTENT);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

        Set<JobRequest> jobRequests = JobManager.instance().getAllJobRequestsForTag(TAG);
        if (!jobRequests.isEmpty()) {
            JobManager.instance().cancel(jobRequests.iterator().next().getJobId());
        }
    }
}
