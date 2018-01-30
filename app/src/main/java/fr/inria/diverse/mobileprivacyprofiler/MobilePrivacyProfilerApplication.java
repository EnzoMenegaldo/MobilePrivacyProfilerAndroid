package fr.inria.diverse.mobileprivacyprofiler;

import android.app.Application;

import com.evernote.android.job.JobManager;

import fr.inria.diverse.mobileprivacyprofiler.job.MobilePrivacyProfilerJobCreator;
import fr.inria.diverse.mobileprivacyprofiler.job.ScanAppUsageJob;

/**
 * Created by dvojtise on 30/01/18.
 */

public class MobilePrivacyProfilerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JobManager.create(this).addJobCreator(new MobilePrivacyProfilerJobCreator());

        // if application is activated, then schedule jobs
        ScanAppUsageJob.cancelRequest();
        ScanAppUsageJob.schedule();
    }
}
