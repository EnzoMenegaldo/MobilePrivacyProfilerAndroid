package fr.inria.diverse.mobileprivacyprofiler;

import android.app.Application;

import com.evernote.android.job.JobManager;

import fr.inria.diverse.mobileprivacyprofiler.job.MobilePrivacyProfilerJobCreator;

/**
 * Created by dvojtise on 30/01/18.
 */

public class MobilePrivacyProfilerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JobManager.create(this).addJobCreator(new MobilePrivacyProfilerJobCreator());

    }
}
