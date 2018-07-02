package fr.inria.diverse.mobileprivacyprofiler.services;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorDescription;
import android.app.Activity;
import android.app.AppOpsManager;
import android.app.IntentService;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Intent;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.BatteryManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.inria.diverse.mobileprivacyprofiler.activities.Home_CustomViewActivity;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.ApplicationHistory;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.ApplicationUsageStats;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.Authentification;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.BatteryUsage;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.Geolocation;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.MobilePrivacyProfilerDBHelper;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.MobilePrivacyProfilerDB_metadata;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.OrmLiteDBHelper;
import fr.inria.diverse.mobileprivacyprofiler.services.PacketSnifferService.PacketSnifferService;
import fr.inria.diverse.mobileprivacyprofiler.utils.DateUtils;
import fr.inria.diverse.mobileprivacyprofiler.utils.PhoneStateUtils;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ScanActivityIntentService extends IntentService {

    private static final String TAG = ScanActivityIntentService.class.getSimpleName();
    private volatile OrmLiteDBHelper dbHelper;

    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_SCAN_INSTALLED_APPLICATIONS = "fr.inria.diverse.mobileprivacyprofiler.services.action.SCAN_INSTALLED_APPLICATIONS";
    private static final String ACTION_SCAN_APP_USAGE = "fr.inria.diverse.mobileprivacyprofiler.services.action.SCAN_APP_USAGE";
    private static final String ACTION_SCAN_BATTERY_USAGE = "fr.inria.diverse.mobileprivacyprofiler.services.action.SCAN_BATTERY_USAGE";
    private static final String ACTION_RECORD_LOCATION = "fr.inria.diverse.mobileprivacyprofiler.services.action.RECORD_LOCATION";
    private static final String ACTION_SCAN_AUTHENTICATORS = "fr.inria.diverse.mobileprivacyprofiler.services.action.ACTION_SCAN_AUTHENTICATORS";
    private static final String ACTION_BAZ = "fr.inria.diverse.mobileprivacyprofiler.action.BAZ";
    private static final String ACTION_NET_ACTIVITY = "fr.inria.diverse.mobileprivacyprofiler.action.NET_ACTIVITY";


    public ScanActivityIntentService() {
        super("ScanSocialIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static Void startActionScanInstalledApplications() {
        System.out.println("ok");
        Intent intent = new Intent(Home_CustomViewActivity.getContext(), ScanActivityIntentService.class);
        intent.setAction(ACTION_SCAN_INSTALLED_APPLICATIONS);
        Home_CustomViewActivity.getContext().startService(intent);
        return null;
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static Void startActionScanAppUsage() {
        Intent intent = new Intent(Home_CustomViewActivity.getContext(), ScanActivityIntentService.class);
        intent.setAction(ACTION_SCAN_APP_USAGE);
        Home_CustomViewActivity.getContext().startService(intent);
        return null;
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static Void startActionScanBatteryUsage() {
        Intent intent = new Intent(Home_CustomViewActivity.getContext(), ScanActivityIntentService.class);
        intent.setAction(ACTION_SCAN_BATTERY_USAGE);
        Home_CustomViewActivity.getContext().startService(intent);
        return null;
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static Void startActionRecordLocation() {
        Intent intent = new Intent(Home_CustomViewActivity.getContext(), ScanActivityIntentService.class);
        intent.setAction(ACTION_RECORD_LOCATION);
        Home_CustomViewActivity.getContext().startService(intent);
        return null;
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static Void startActionScanAuthenticators() {
        Intent intent = new Intent(Home_CustomViewActivity.getContext(), ScanActivityIntentService.class);
        intent.setAction(ACTION_SCAN_AUTHENTICATORS);
        Home_CustomViewActivity.getContext().startService(intent);
        return null;
    }

    /**
     * Starts this service to perform action NetActivity with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static Void startActionNetActivity() {
        Intent intent = new Intent(Home_CustomViewActivity.getContext(), ScanActivityIntentService.class);
        intent.setAction(ACTION_NET_ACTIVITY);
        Home_CustomViewActivity.getContext().startService(intent);
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SCAN_BATTERY_USAGE.equals(action)) {
                handleActionScanBatteryUsage();
            } else if (ACTION_SCAN_INSTALLED_APPLICATIONS.equals(action)) {
                handleActionScanInstalledApplications();
            } else if (ACTION_SCAN_APP_USAGE.equals(action)) {
                handleActionScanAppUsage();
            } else if (ACTION_RECORD_LOCATION.equals(action)) {
                handleActionRecordLocation(intent);
            } else if (ACTION_SCAN_AUTHENTICATORS.equals(action)) {
                handleActionScanAuthenticators(intent);
            }else if(ACTION_NET_ACTIVITY.equals(action)){
                handleActionNetActivity();
            }
        }
    }

    /**
     * Handle action ScanInstalledApplications in the provided background thread with the provided
     * parameters.
     */
    private void handleActionScanInstalledApplications() {
        // store in DB the last scan date
        MobilePrivacyProfilerDB_metadata metadata = MobilePrivacyProfilerDBHelper.getDBHelper(this).getMobilePrivacyProfilerDBHelper().getDeviceDBMetadata();
        metadata.setLastScanInstalledApplications(new Date());
        MobilePrivacyProfilerDBHelper.getDBHelper(this).getMobilePrivacyProfilerDB_metadataDao().update(metadata);
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
            ApplicationHistory applicationHistory = MobilePrivacyProfilerDBHelper.getDBHelper(this).getMobilePrivacyProfilerDBHelper().queryApplicationHistoryByPackageName(packageInfo.packageName);
            if (applicationHistory == null) {
                // create
                applicationHistory = new ApplicationHistory(appName, packageInfo.packageName, MobilePrivacyProfilerDBHelper.getDeviceDBMetadata(this).getUserId());
                MobilePrivacyProfilerDBHelper.getDBHelper(this).getApplicationHistoryDao().create(applicationHistory);
            } else {
                // update
                // TODO
                //getDBHelper().getApplicationHistoryDao().update(applicationHistory);
            }
        }

    }

    /**
     * https://medium.com/@quiro91/show-app-usage-with-usagestatsmanager-d47294537dab
     */
    private void handleActionScanAppUsage() {
        Log.d(TAG, "handleActionScanAppUsage");
        // store in DB the last scan date
        MobilePrivacyProfilerDB_metadata metadata = MobilePrivacyProfilerDBHelper.getDBHelper(this).getMobilePrivacyProfilerDBHelper().getDeviceDBMetadata();
        metadata.setLastScanAppUsage(new Date());
        MobilePrivacyProfilerDBHelper.getDBHelper(this).getMobilePrivacyProfilerDB_metadataDao().update(metadata);
        // do the work for both weekly and daily data
        scanAppUsageForLastPeriod(UsageStatsManager.INTERVAL_WEEKLY);
        scanAppUsageForLastPeriod(UsageStatsManager.INTERVAL_DAILY);
    }

    private void scanAppUsageForLastPeriod(int periodType) {
        Log.d(TAG, "Adding new AppUse : preSDK test");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.d(TAG, "Adding new AppUse");
            UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(USAGE_STATS_SERVICE);
            Calendar calendar = Calendar.getInstance();
            //calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            Calendar startDate;
            Calendar endDate;
            switch (periodType) {
                case UsageStatsManager.INTERVAL_WEEKLY:
                    startDate = DateUtils.firstDayOfLastWeek(calendar);
                    endDate = DateUtils.lastDayOfLastWeek(calendar);
                    break;
                default: // default to daily period
                    startDate = DateUtils.yesterdayStart(calendar);
                    endDate = DateUtils.yesterdayEnd(calendar);
                    break;
            }

            // startDate.add(Calendar.MONTH, -1);
            long start = startDate.getTimeInMillis();
            long end = endDate.getTimeInMillis();
            Log.d(TAG, " Stat period required " + DateUtils.printDate(start) +
                    " - " + DateUtils.printDate(end));
            List<UsageStats> stats = usageStatsManager.queryUsageStats(periodType, start, end);
            Log.d(TAG, " usageStatsManager query returned " + stats.size() + " elements; hasPermission= " + PhoneStateUtils.hasPermission(this,Home_CustomViewActivity.PERMISSIONS));
            for (UsageStats appUsageStatsentry : stats) {
                Log.d(TAG, "stats for " + appUsageStatsentry.getPackageName());
                ApplicationHistory applicationHistory = MobilePrivacyProfilerDBHelper.getDBHelper(this).getMobilePrivacyProfilerDBHelper().
                        queryApplicationHistoryByPackageName(appUsageStatsentry.getPackageName());
                if (applicationHistory != null) {
                    // search for equivalent stat to update
                    String statStartDate = DateUtils.printDate(appUsageStatsentry.getFirstTimeStamp());
                    Log.d(TAG, "   stats for " + statStartDate +
                            " - " + DateUtils.printDate(appUsageStatsentry.getLastTimeStamp()));
                    ApplicationUsageStats equivalentExistingStat = null;
                    for (ApplicationUsageStats existingStat : applicationHistory.getUsageStats()) {
                        if (existingStat.getRequestedInterval() == periodType &&
                                existingStat.getFirstTimeStamp().equals(statStartDate)) {
                            equivalentExistingStat = existingStat;
                        }
                    }
                    if (equivalentExistingStat != null) {
                        // update existing
                        Log.d(TAG, "   updating... ");
                        equivalentExistingStat.setLastTimeStamp(DateUtils.printDate(appUsageStatsentry.getLastTimeStamp()));
                        equivalentExistingStat.setLastTimeUsed(DateUtils.printDate(appUsageStatsentry.getLastTimeUsed()));
                        equivalentExistingStat.setTotalTimeInForeground(appUsageStatsentry.getTotalTimeInForeground());
                        MobilePrivacyProfilerDBHelper.getDBHelper(this).getApplicationUsageStatsDao().update(equivalentExistingStat);
                    } else {
                        // create new entry
                        Log.d(TAG, "   creating new entry... ");
                        ApplicationUsageStats appStat = new ApplicationUsageStats();
                        appStat.setFirstTimeStamp(statStartDate);
                        appStat.setLastTimeStamp(DateUtils.printDate(appUsageStatsentry.getLastTimeStamp()));
                        appStat.setLastTimeUsed(DateUtils.printDate(appUsageStatsentry.getLastTimeUsed()));
                        appStat.setTotalTimeInForeground(appUsageStatsentry.getTotalTimeInForeground());
                        appStat.setRequestedInterval(periodType);
                        appStat.setApplication(applicationHistory);
                        appStat.setUserId(MobilePrivacyProfilerDBHelper.getDeviceDBMetadata(this).getUserId());
                        MobilePrivacyProfilerDBHelper.getDBHelper(this).getApplicationUsageStatsDao().create(appStat);
                    }
                }
            }
        }//end ifSdk check
    }//end method

    /**
     * Handle action ScanBatteryUsage in the provided background thread with the provided
     * parameters.
     */

    private void handleActionScanBatteryUsage() {

        //entry date
        Date time = new Date();

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, ifilter);

        //connexion check
        int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
        boolean wireLessCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_WIRELESS;
        String plugType;

        int isPlugged;

        if (usbCharge) {
            isPlugged = 1;
            plugType = "BATTERY_PLUGGED_USB";
        } else if (acCharge) {
            isPlugged = 1;
            plugType = "BATTERY_PLUGGED_AC";
        } else if (wireLessCharge) {
            isPlugged = 1;
            plugType = "BATTERY_PLUGGED_WIRELESS";
        } else {
            isPlugged = 0;
            plugType = "BATTERY_NOT_PLUGGED";
        }

        //get the battery lvl
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        float batteryPct = (level / (float) scale) * 100f;
        Integer batteryLvl = new Integer((int) batteryPct);

        //new entry in DB
        Log.d(TAG, "   creating new Battery entry : time " + time + " , batteryLvl : " + batteryLvl + " ,isPlugged : " + isPlugged + " , pluggedType : " + plugType);

        BatteryUsage batState = new BatteryUsage(time, batteryLvl, isPlugged, plugType, MobilePrivacyProfilerDBHelper.getDeviceDBMetadata(this).getUserId());
        MobilePrivacyProfilerDBHelper.getDBHelper(this).getBatteryUsageDao().create(batState);
    }

    /**
     * Handle action RecordLocation in the provided background thread with the provided
     * parameters.
     * @param intent
     */
    private void handleActionRecordLocation(Intent intent) {


        /**
         * Reference:
         * https://github.com/googlemaps/android-samples/blob/master/tutorials/CurrentPlaceDetailsOnMap
         */


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {

            Location location = null;
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(null!=location) {
                double latitude;
                double longitude;
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                Geolocation lastKnowLocation = MobilePrivacyProfilerDBHelper.getDBHelper(this).getMobilePrivacyProfilerDBHelper().getLastKnowLocation();
                Boolean registerLocation = false;
                //evaluation of registration necessity
                if (null != lastKnowLocation) {
                    double lastLatitude = Double.parseDouble(lastKnowLocation.getLatitude());
                    double lastLongitude = Double.parseDouble(lastKnowLocation.getLongitude());
                    if (300 < distance(latitude, lastLatitude, longitude, lastLongitude)) {
                        registerLocation = true;
                        Log.d(TAG, "New location detected");
                    }//end if supp 300m
                    else {
                        Log.d(TAG, "New location too close to last location");
                    }
                } else {
                    registerLocation = true;
                    Log.d(TAG, "First location recording");
                }
                // info gathering and registration
                if (registerLocation) {
                    Log.d(TAG, "Building a new Geolocation entry");
                    Geolocation geolocation = new Geolocation();
                    geolocation.setLatitude("" + latitude);
                    geolocation.setLongitude("" + longitude);
                    geolocation.setUserId(MobilePrivacyProfilerDBHelper.getDeviceDBMetadata(this).getUserId());
                    geolocation.setDate(new Date(location.getTime()));

                    if (location.hasAccuracy()) {
                        geolocation.setPrecision("" + location.getAccuracy());
                    } else {
                        geolocation.setPrecision("not available");
                    }

                    if (location.hasAltitude()) {
                        geolocation.setAltitude("" + location.getAltitude());
                    } else {
                        geolocation.setAltitude("not available");
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        if (location.hasVerticalAccuracy()) {
                            geolocation.setVerticalPrecision("" + location.getVerticalAccuracyMeters());
                        } else {
                            geolocation.setVerticalPrecision("not available");
                        }
                    } else {
                        geolocation.setVerticalPrecision("not available");
                    }
                    Log.d(TAG, "Geolocation : date " + geolocation.getDate() +
                            ", latitude " + geolocation.getLatitude() +
                            ", longitude " + geolocation.getLongitude() +
                            ", accuracy " + geolocation.getPrecision() +
                            ", altitude " + geolocation.getAltitude() +
                            ", verticalPrecision " + geolocation.getVerticalPrecision() +
                            ", userId " + geolocation.getUserId());
                    MobilePrivacyProfilerDBHelper.getDBHelper(this).getGeolocationDao().create(geolocation);

                }//end register location
            }//end if location not null
            else{Log.d(TAG, "LocationManager returned a null location");}
        }//end if manager
        else{
            Log.d(TAG, "Location Manager was not found");
        }
    }

    /**
     * Handle action ScanAuthenticators in the provided background thread with the provided
     * parameters.
     * @param intent
     */
    private void handleActionScanAuthenticators(Intent intent) {
        //set up the manager
        Log.d(TAG, "Scanning authenticators");
        AccountManager accountManager = (AccountManager) this.getSystemService(Context.ACCOUNT_SERVICE);

        Account[] accounts = accountManager.getAccounts();// get a collection of Accounts and Descriptors
        AuthenticatorDescription[] authDescriptions = accountManager.getAuthenticatorTypes();

        List<String> registredAuthType = MobilePrivacyProfilerDBHelper.getDBHelper(this).getMobilePrivacyProfilerDBHelper().queryAllAuthentificationType();
        boolean noRegistredAuthType = false;
        if(0==registredAuthType.size()){noRegistredAuthType=true;}
        Log.d(TAG,authDescriptions.length+" authDescriptions to record");
        if (noRegistredAuthType||null!=authDescriptions) {
            for (AuthenticatorDescription authDesc : authDescriptions) {
                if (!registredAuthType.contains(authDesc.type)) {//check is the authenticator is already registered
                    //add a new entry

                    //fetching parameters
                    String packageName = authDesc.packageName;
                    String type = authDesc.type;
                    String name = "";
                    Boolean trouve = false;

                    for (int i = 0; !trouve && i < accounts.length; i++) {
                        if (type == accounts[i].type) {
                            trouve = true;
                            name += ", "+accounts[i].name;
                        }
                    }

                    Authentification auth = new Authentification();
                    auth.setPackageName(packageName);
                    auth.setName(name);
                    auth.setType(type);
                    auth.setUserId(MobilePrivacyProfilerDBHelper.getDeviceDBMetadata(this).getUserId());
                    MobilePrivacyProfilerDBHelper.getDBHelper(this).getAuthentificationDao().create(auth);
                    Log.d(TAG, "New Authentification :" + authDesc.type+", names : "+name+", packageName : "+packageName);
                }
            }
        }
    }

    /**
     * Handle action NetActivity in the provided background thread with the provided
     * parameters.
     */
    private void handleActionNetActivity(){
        Intent intent = new Intent(getApplicationContext(), PacketSnifferService.class);
        startService(intent);
    }

    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     *
     * lat1, lon1 Start point lat2, lon2 End point
     * @returns Distance in Meters
     */
    private double distance(double lat1, double lat2, double lon1,
                            double lon2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        return Math.abs(distance);
    }

}
