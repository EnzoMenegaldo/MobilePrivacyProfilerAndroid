package fr.inria.diverse.mobileprivacyprofiler.job;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;
import com.evernote.android.job.JobManager;

/**
 * Created by dvojtise on 30/01/18.
 * Edit by gohier   on 24/04/18.
 */

public class MobilePrivacyProfilerJobCreator implements JobCreator {

    @Override
    @Nullable
    public Job create(@NonNull String tag) {
        switch (tag) {
            case ScanAppUsageJob.TAG:
                return new ScanAppUsageJob();
            case ScanBatteryJob.TAG:
                return new ScanBatteryJob();
            case ScanBluetoothJob.TAG:
                return new ScanBluetoothJob();
            case ScanCalendarJob.TAG:
                return new ScanCalendarJob();
            case ScanCellJob.TAG:
                return new ScanCellJob();
            case ScanContactJob.TAG:
                return new ScanContactJob();
            case ScanGeolocationJob.TAG:
                return new ScanGeolocationJob();
            case ScanPhoneCallLogJob.TAG:
                return new ScanPhoneCallLogJob();
            case ScanSMSJob.TAG:
                return new ScanSMSJob();
            case ExportDBJob.TAG:
                return new ExportDBJob();
            default:
                return null;
        }
    }

    public static final class AddReceiver extends AddJobCreatorReceiver {
        @Override
        protected void addJobCreator(@NonNull Context context, @NonNull JobManager manager) {
            // manager.addJobCreator(new DemoJobCreator());
            JobManager.instance().addJobCreator(new MobilePrivacyProfilerJobCreator());
        }
    }

}
