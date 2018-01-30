package fr.inria.diverse.mobileprivacyprofiler.job;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import fr.inria.diverse.mobileprivacyprofiler.BuildConfig;
import fr.inria.diverse.mobileprivacyprofiler.R;
import fr.inria.diverse.mobileprivacyprofiler.activities.Home_CustomViewActivity;
import fr.inria.diverse.mobileprivacyprofiler.services.ScanDeviceIntentService;

/**
 * Created by dvojtise on 30/01/18.
 */

public class ScanAppUsageJob extends Job {
    public static final String TAG = "ScanAppUsageJob";


    private static final boolean DEBUG = BuildConfig.DEBUG;

    @Override
    @NonNull
    protected Result onRunJob(@NonNull final Params params) {

        ScanDeviceIntentService.startActionScanInstalledApplications(getContext());
        ScanDeviceIntentService.startActionScanAppUsage(getContext());

        return Result.SUCCESS;
    }

    public static int schedule() {
        Set<JobRequest> jobRequests = JobManager.instance().getAllJobRequestsForTag(TAG);
        if (!jobRequests.isEmpty()) {
            return jobRequests.iterator().next().getJobId();
        }

        long interval = TimeUnit.HOURS.toMillis(3); // every 3 hours
        long flex = TimeUnit.HOURS.toMillis(1); // wait 1 hours before job runs again

        if (DEBUG) {
            interval = JobRequest.MIN_INTERVAL;
            flex = JobRequest.MIN_FLEX;
        }

        return new JobRequest.Builder(TAG)
                .setPeriodic(interval, flex)
                //.setUpdateCurrent(true)
                //.setRequiresBatteryNotLow(true)
                //.setRequiresStorageNotLow(true)
                //.setRequiredNetworkType(JobRequest.NetworkType.UNMETERED)
                //.setRequiresCharging(true)
                //.setRequirementsEnforced(true)
                .build()
                .schedule();
    }
    public static void cancelRequest() {
        Set<JobRequest> jobRequests = JobManager.instance().getAllJobRequestsForTag(TAG);
        if (!jobRequests.isEmpty()) {
            JobManager.instance().cancel(jobRequests.iterator().next().getJobId());
        }


    }
}
