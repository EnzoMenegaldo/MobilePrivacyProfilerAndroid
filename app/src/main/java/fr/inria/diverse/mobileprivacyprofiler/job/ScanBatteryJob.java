package fr.inria.diverse.mobileprivacyprofiler.job;

import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import fr.inria.diverse.mobileprivacyprofiler.BuildConfig;
import fr.inria.diverse.mobileprivacyprofiler.services.ScanDeviceIntentService;

/**
 * Created by gohier on 23/04/18.
 */

public class ScanBatteryJob extends Job {
    public static final String TAG = "ScanBatteryJob";


    private static final boolean DEBUG = BuildConfig.DEBUG;

    @Override
    @NonNull
    protected Result onRunJob(@NonNull final Params params) {

        ScanDeviceIntentService.startActionScanBatteryUsage(getContext());

        return Result.SUCCESS;
    }

    public static int schedule() {
        Set<JobRequest> jobRequests = JobManager.instance().getAllJobRequestsForTag(TAG);
        if (!jobRequests.isEmpty()) {
            return jobRequests.iterator().next().getJobId();
        }
        // adapt the triggering of the update
        long interval = TimeUnit.MINUTES.toMillis(30); // every 40 min
        long flex = TimeUnit.MINUTES.toMillis(10); // wait 10 min before job runs again

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
