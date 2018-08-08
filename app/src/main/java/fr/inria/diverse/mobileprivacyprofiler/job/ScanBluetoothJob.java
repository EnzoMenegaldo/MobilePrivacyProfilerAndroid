package fr.inria.diverse.mobileprivacyprofiler.job;

import android.bluetooth.BluetoothDevice;
import android.content.IntentFilter;
import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import fr.inria.diverse.mobileprivacyprofiler.BuildConfig;
import fr.inria.diverse.mobileprivacyprofiler.activities.Home_CustomViewActivity;
import fr.inria.diverse.mobileprivacyprofiler.broadcastReceiver.BluetoothScanReceiver;
import fr.inria.diverse.mobileprivacyprofiler.services.ScanConnectionIntentService;

/**
 * Created by gohier on 23/04/18.
 */

public class ScanBluetoothJob extends Job {
    public static final String TAG = "ScanBluetooth";


    private static final boolean DEBUG = BuildConfig.DEBUG;

    @Override
    @NonNull
    protected Result onRunJob(@NonNull final Params params) {
        Home_CustomViewActivity.getContext().registerReceiver(BluetoothScanReceiver.INSTANCE,new IntentFilter(BluetoothDevice.ACTION_ACL_CONNECTED));
        ScanConnectionIntentService.startActionScanBluetooth();

        return Result.SUCCESS;
    }

    public static int schedule() {
        Set<JobRequest> jobRequests = JobManager.instance().getAllJobRequestsForTag(TAG);
        if (!jobRequests.isEmpty()) {
            return jobRequests.iterator().next().getJobId();
        }

        return new JobRequest.Builder(TAG)
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
    public static void cancelRequest() {
        Home_CustomViewActivity.getContext().unregisterReceiver(BluetoothScanReceiver.INSTANCE);

        Set<JobRequest> jobRequests = JobManager.instance().getAllJobRequestsForTag(TAG);
        if (!jobRequests.isEmpty()) {
            JobManager.instance().cancel(jobRequests.iterator().next().getJobId());
        }


    }
}
