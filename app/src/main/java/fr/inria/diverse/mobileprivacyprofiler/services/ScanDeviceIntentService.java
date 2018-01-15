package fr.inria.diverse.mobileprivacyprofiler.services;

import android.app.AppOpsManager;
import android.app.IntentService;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Intent;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import fr.inria.diverse.mobileprivacyprofiler.datamodel.ApplicationHistory;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.OrmLiteDBHelper;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ScanDeviceIntentService extends IntentService {

    private static final String TAG = ScanDeviceIntentService.class.getSimpleName();

    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_SCAN_INSTALLED_APPLICATIONS = "fr.inria.diverse.mobileprivacyprofiler.services.action.SCAN_INSTALLED_APPLICATIONS";
    private static final String ACTION_SCAN_APP_USAGE ="fr.inria.diverse.mobileprivacyprofiler.services.action.SCAN_APP_USAGE";
    private static final String ACTION_BAZ = "fr.inria.diverse.mobileprivacyprofiler.services.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "fr.inria.diverse.mobileprivacyprofiler.services.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "fr.inria.diverse.mobileprivacyprofiler.services.extra.PARAM2";


    private static final int MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS = 100;

    private volatile OrmLiteDBHelper dbHelper;

    public ScanDeviceIntentService() {
        super("ScanDeviceIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionScanInstalledApplications(Context context) {
        Intent intent = new Intent(context, ScanDeviceIntentService.class);
        intent.setAction(ACTION_SCAN_INSTALLED_APPLICATIONS);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionScanAppUsage(Context context) {
        Intent intent = new Intent(context, ScanDeviceIntentService.class);
        intent.setAction(ACTION_SCAN_APP_USAGE);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, ScanDeviceIntentService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        OpenHelperManager.getHelper(this, OrmLiteDBHelper.class);
        new OrmLiteDBHelper(this);
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SCAN_INSTALLED_APPLICATIONS.equals(action)) {
                handleActionScanInstalledApplications();
            } else if (ACTION_SCAN_APP_USAGE.equals(action)) {
                handleActionScanAppUsage();
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }



    /**
     * Handle action ScanInstalledApplications in the provided background thread with the provided
     * parameters.
     */
    private void handleActionScanInstalledApplications() {

        // get list of installed applications
        // https://stackoverflow.com/questions/2695746/how-to-get-a-list-of-installed-android-applications-and-pick-one-to-run
        final PackageManager pm = getPackageManager();
        //get a list of installed apps.
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo packageInfo : packages) {
            String appName = packageInfo.loadLabel(pm).toString();
            Log.d(TAG, "App name :" + appName);
            Log.d(TAG, "Installed package :" + packageInfo.packageName);
            Log.d(TAG, "Source dir : " + packageInfo.sourceDir);
            Log.d(TAG, "Launch Activity :" + pm.getLaunchIntentForPackage(packageInfo.packageName));
            ApplicationHistory applicationHistory = getDBHelper().getMobilePrivacyProfilerDBHelper().queryApplicationHistoryByPackageName(packageInfo.packageName);
            if(applicationHistory == null){
                // create
                applicationHistory = new ApplicationHistory(appName, packageInfo.packageName);
                getDBHelper().getApplicationHistoryDao().create(applicationHistory);
            } else {
                // update
                // TODO
            }
        }

    }

    /**
     * https://medium.com/@quiro91/show-app-usage-with-usagestatsmanager-d47294537dab
     */
    private void handleActionScanAppUsage() {
        Log.d(TAG,"handleActionScanAppUsage");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {



            UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(USAGE_STATS_SERVICE);

            Log.d(TAG,"got usageStatsManager "+usageStatsManager.toString());
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -1);
            long start = calendar.getTimeInMillis();
            long end = System.currentTimeMillis();
            Map<String, UsageStats> stats = usageStatsManager.queryAndAggregateUsageStats(start, end);
            Log.d(TAG,"usageStatsManager query returned "+stats.size()+" elements; hasPermission= "+hasPermission());
            for(Map.Entry<String, UsageStats> appUsageStatsentry : stats.entrySet()){
                Log.d(TAG,"stats for "+appUsageStatsentry.getValue().getPackageName());
            }
        }
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public OrmLiteDBHelper getDBHelper(){
        if(dbHelper == null){
            dbHelper = OpenHelperManager.getHelper(this, OrmLiteDBHelper.class);
        }
        return dbHelper;
    }

    private boolean hasPermission() {
        AppOpsManager appOps = (AppOpsManager)
                getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(), getPackageName());
        return mode == AppOpsManager.MODE_ALLOWED;
    }
}
