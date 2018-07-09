package fr.inria.diverse.mobileprivacyprofiler.job;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import fr.inria.diverse.mobileprivacyprofiler.BuildConfig;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.OrmLiteDBHelper;
import fr.inria.diverse.mobileprivacyprofiler.rest.MobilePrivacyRestClient;
import fr.inria.diverse.mobileprivacyprofiler.services.ScanSocialIntentService;

import static fr.inria.diverse.mobileprivacyprofiler.activities.Starting_CustomViewActivity.context;

/**
 * Created by gohier on 27/06/18.
 */

public class ExportDBJob extends Job {
    public static final String TAG = "ExportDBJob";


    private static final boolean DEBUG = BuildConfig.DEBUG;

    @Override
    @NonNull
    protected Result onRunJob(@NonNull final Params params) {
        Date now = new Date();
        Date lastScan = OpenHelperManager.getHelper(context, OrmLiteDBHelper.class).getMobilePrivacyProfilerDBHelper().getDeviceDBMetadata().getLastTransmissionDate();
        if(null==lastScan||now.after(new Date(lastScan.getTime()+10800000))){
            try {
                new  MobilePrivacyRestClient().exportDB(getContext());
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        else{
            Log.d(TAG,"Too early for a new Export now : "+now+"("+now.getTime()+") waiting : "+new Date(lastScan.getTime()+10800000).toString());
        }
        return Result.SUCCESS;
    }

    public static int schedule() {
        Set<JobRequest> jobRequests = JobManager.instance().getAllJobRequestsForTag(TAG);
        if (!jobRequests.isEmpty()) {
            return jobRequests.iterator().next().getJobId();
        }
        // adapt the triggering of the update
        long interval = TimeUnit.MINUTES.toMillis(20); // every 12 h
        long flex = TimeUnit.MINUTES.toMillis(5); // -+ 1h to execute

        if (DEBUG) {
            interval = JobRequest.MIN_INTERVAL;
            flex = JobRequest.MIN_FLEX;
        }

        return new JobRequest.Builder(TAG)
                .setPeriodic(interval, flex)
                //.setUpdateCurrent(true)
                //.setRequiresBatteryNotLow(true)
                //.setRequiresStorageNotLow(true)
                .setRequiredNetworkType(JobRequest.NetworkType.UNMETERED)
                //.setRequiresCharging(true)
                .setRequirementsEnforced(true)
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
