package fr.inria.diverse.mobileprivacyprofiler.job;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

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
            default:
                return null;
        }
    }
}
