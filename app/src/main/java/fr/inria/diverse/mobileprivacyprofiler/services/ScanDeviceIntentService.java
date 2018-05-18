package fr.inria.diverse.mobileprivacyprofiler.services;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorDescription;
import android.annotation.SuppressLint;
import android.app.AppOpsManager;
import android.app.IntentService;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.provider.CalendarContract;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.inria.diverse.mobileprivacyprofiler.datamodel.ApplicationHistory;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.ApplicationUsageStats;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.Authentification;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.BatteryUsage;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.CalendarEvent;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.CdmaCellData;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.Cell;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.MobilePrivacyProfilerDB_metadata;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.NeighboringCellHistory;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.OrmLiteDBHelper;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.OtherCellData;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.PhoneCallLog;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.SMS;
import fr.inria.diverse.mobileprivacyprofiler.utils.DateUtils;

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
    private static final String ACTION_SCAN_APP_USAGE = "fr.inria.diverse.mobileprivacyprofiler.services.action.SCAN_APP_USAGE";
    private static final String ACTION_SCAN_BATTERY_USAGE = "fr.inria.diverse.mobileprivacyprofiler.services.action.SCAN_BATTERY_USAGE";
    private static final String ACTION_SCAN_CELL_INFO = "fr.inria.diverse.mobileprivacyprofiler.services.action.SCAN_NEAR_CELL_INFO";
    private static final String ACTION_SCAN_SMS = "fr.inria.diverse.mobileprivacyprofiler.services.action.SMS_SCAN";
    private static final String ACTION_RECORD_LOCATION = "fr.inria.diverse.mobileprivacyprofiler.services.action.RECORD_LOCATION";
    private static final String ACTION_SCAN_AUTHENTICATORS = "fr.inria.diverse.mobileprivacyprofiler.services.action.ACTION_SCAN_AUTHENTICATORS";
    private static final String ACTION_SCAN_CALL_HISTORY = "fr.inria.diverse.mobileprivacyprofiler.services.action.ACTION_SCAN_CALL_HISTORY";
    private static final String ACTION_SCAN_CALENDAR_EVENT = "fr.inria.diverse.mobileprivacyprofiler.services.action.ACTION_SCAN_CALENDAR_EVENT";
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
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionScanBatteryUsage(Context context) {
        Intent intent = new Intent(context, ScanDeviceIntentService.class);
        intent.setAction(ACTION_SCAN_BATTERY_USAGE);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionScanSms(Context context) {
        Intent intent = new Intent(context, ScanDeviceIntentService.class);
        intent.setAction(ACTION_SCAN_SMS);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionScanCellInfo(Context context) {
        Intent intent = new Intent(context, ScanDeviceIntentService.class);
        intent.setAction(ACTION_SCAN_CELL_INFO);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionRecordLocation(Context context) {
        Intent intent = new Intent(context, ScanDeviceIntentService.class);
        intent.setAction(ACTION_RECORD_LOCATION);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionScanAuthenticators(Context context) {
        Intent intent = new Intent(context, ScanDeviceIntentService.class);
        intent.setAction(ACTION_SCAN_AUTHENTICATORS);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionScanCallHistory(Context context) {
        Intent intent = new Intent(context, ScanDeviceIntentService.class);
        intent.setAction(ACTION_SCAN_CALL_HISTORY);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionScanCalendarEvent(Context context) {
        Intent intent = new Intent(context, ScanDeviceIntentService.class);
        intent.setAction(ACTION_SCAN_CALENDAR_EVENT);
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
            } else if (ACTION_SCAN_BATTERY_USAGE.equals(action)) {
                handleActionScanBatteryUsage();
            } else if (ACTION_SCAN_SMS.equals(action)) {
                handleActionScanSms();
            } else if (ACTION_SCAN_CELL_INFO.equals(action)) {
                handleActionScanCellInfo(intent);
            } else if (ACTION_RECORD_LOCATION.equals(action)) {
                handleActionRecordLocation(intent);
            } else if (ACTION_SCAN_AUTHENTICATORS.equals(action)) {
                handleActionScanAuthenticators(intent);
            } else if (ACTION_SCAN_CALL_HISTORY.equals(action)) {
                handleActionScanCallHistory(intent);
            } else if (ACTION_SCAN_CALENDAR_EVENT.equals(action)) {
                handleActionScanCalendarEvent(intent);
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
        // store in DB the last scan date
        MobilePrivacyProfilerDB_metadata metadata = getDBHelper().getMobilePrivacyProfilerDBHelper().getDeviceDBMetadata();
        metadata.setLastScanInstalledApplications(new Date());
        getDBHelper().getMobilePrivacyProfilerDB_metadataDao().update(metadata);
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
            if (applicationHistory == null) {
                // create
                applicationHistory = new ApplicationHistory(appName, packageInfo.packageName);
                getDBHelper().getApplicationHistoryDao().create(applicationHistory);
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
        MobilePrivacyProfilerDB_metadata metadata = getDBHelper().getMobilePrivacyProfilerDBHelper().getDeviceDBMetadata();
        metadata.setLastScanAppUsage(new Date());
        getDBHelper().getMobilePrivacyProfilerDB_metadataDao().update(metadata);
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
            //TODO query doesn't bring anything
            List<UsageStats> stats = usageStatsManager.queryUsageStats(periodType, start, end);
            Log.d(TAG, " usageStatsManager query returned " + stats.size() + " elements; hasPermission= " + hasPermission());
            for (UsageStats appUsageStatsentry : stats) {
                Log.d(TAG, "stats for " + appUsageStatsentry.getPackageName());
                ApplicationHistory applicationHistory = getDBHelper().getMobilePrivacyProfilerDBHelper().
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
                        getDBHelper().getApplicationUsageStatsDao().update(equivalentExistingStat);
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
                        getDBHelper().getApplicationUsageStatsDao().create(appStat);
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

        BatteryUsage batState = new BatteryUsage(time, batteryLvl, isPlugged, plugType);
        getDBHelper().getBatteryUsageDao().create(batState);
    }


    /**
     * Handle action ScanSms in the provided background thread with the provided
     * parameters.
     */

    private void handleActionScanSms() {
        //list sms sources
        String[] sources = {"content://sms/inbox", "content://sms/sent"};
        //get the last update if it exist(else expecting = null)
        MobilePrivacyProfilerDB_metadata metadata = getDBHelper().getMobilePrivacyProfilerDBHelper().getDeviceDBMetadata();
        Date lastScan = metadata.getLastSmsScan();

        String displayLastScan;
        if (null == lastScan) {
            displayLastScan = "none";
        } else {
            displayLastScan = lastScan.toString();
        }
        Log.d(TAG, "   Starting new SmsScan : time of last scan " + displayLastScan);

        //fetch sms from each sources
        for (String source : sources) {
            Cursor cursor = null;
            Uri mSmsQueryUri = Uri.parse(source);
            String columns[] = new String[]{"person", "address", "body", "date", "status"/*,"body"*/};
            //if not 1st scan
            if (null != lastScan) {
                Log.d(TAG, "Fetching messages up to last scan");
                String lastScanString = "" + lastScan.getTime();
                String[] arguments = {lastScanString};
                cursor = getContentResolver().query(mSmsQueryUri, columns, "date > ?", arguments, null);
            }
            //if 1st scan
            else {
                Log.d(TAG, "Fetching all messages");
                cursor = getContentResolver().query(mSmsQueryUri, columns, null, null, null);
            }
            while (cursor.moveToNext()) {
                String date = cursor.getString(3);
                String phoneNumber = cursor.getString(1);
                String type="";
                if("content://sms/inbox"==source){ type = "inbox";}
                if("content://sms/sent"==source){ type = "sent";}
                SMS sms = new SMS(date, phoneNumber, type);
                getDBHelper().getSMSDao().create(sms);
                Log.d(TAG,"date : "+date+",phone number : "+phoneNumber+",type :"+type/*+",body : "+cursor.getString(5)*/);
            }
            cursor.close();
        }
        Log.d(TAG, "Updating last SMS scan");
        metadata.setLastSmsScan(new Date());
        getDBHelper().getMobilePrivacyProfilerDB_metadataDao().update(metadata);
    }

    /**
     * Handle action ScanCellInfo in the provided background thread with the provided
     * parameters.
     */

    private void handleActionScanCellInfo(Intent intent) {
        //set up the manager
        Log.d(TAG, "Requesting CellInfo");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        List<CellInfo> cellInfos = telephonyManager.getAllCellInfo();// get a collection of the cells of the neighborhood
        Log.d(TAG, "Finding " + cellInfos.size() + " cells");
        //setting up args to fill DAOs
        String cellType = "";
        Date date = new Date();
        Integer cellId = null;
        Integer longitude = null;
        Integer latitude = null;
        Integer lacTac = null;
        Integer strength = 0;

        boolean isCell = false;
        //dealing with datas to add
        for (CellInfo cellInfo : cellInfos) { // fetching info regarding of the cell type
            if (cellInfo instanceof CellInfoCdma) {
                isCell = true;
                CellInfoCdma cell = (CellInfoCdma) cellInfo;
                cellType = "Cdma";
                cellId = cell.getCellIdentity().getBasestationId();
                longitude = cell.getCellIdentity().getLongitude();
                latitude = cell.getCellIdentity().getLatitude();
                strength = cell.getCellSignalStrength().getDbm();
            }
            if (cellInfo instanceof CellInfoGsm) {
                isCell = true;
                CellInfoGsm cell = (CellInfoGsm) cellInfo;
                cellType = "Gsm";
                cellId = cell.getCellIdentity().getCid();
                lacTac = cell.getCellIdentity().getLac();
                strength = cell.getCellSignalStrength().getDbm();
            }
            if (cellInfo instanceof CellInfoLte) {
                isCell = true;
                CellInfoLte cell = (CellInfoLte) cellInfo;
                cellType = "Lte";
                cellId = cell.getCellIdentity().getCi();
                lacTac = cell.getCellIdentity().getTac();
                strength = cell.getCellSignalStrength().getDbm();
            }
            if (cellInfo instanceof CellInfoWcdma) {
                isCell = true;
                CellInfoWcdma cell = (CellInfoWcdma) cellInfo;
                cellType = "Wcdma";
                cellId = cell.getCellIdentity().getCid();
                lacTac = cell.getCellIdentity().getLac();
                strength = cell.getCellSignalStrength().getDbm();
            }
            if (isCell) {//isCell true if the cell as been recognised
                Cell cell = getDBHelper().getMobilePrivacyProfilerDBHelper().queryCellByCellId(cellId);
                if (null == cell) {// new cell if the cell is not in DB (Cell and ( CdmaCellData or OtherCellData) )
                    Log.d(TAG, "Adding a new " + cellType + " Cell");
                    Cell newCell = new Cell();
                    newCell.setCellId(cellId);
                    getDBHelper().getCellDao().create(newCell);
                    cell = newCell;
                    if ("Cdma" == cellType) {
                        CdmaCellData cdmaCellData = new CdmaCellData();
                        cdmaCellData.setLongitude(longitude);
                        cdmaCellData.setLatitude(latitude);
                        cdmaCellData.setIdentity(newCell);
                        getDBHelper().getCdmaCellDataDao().create(cdmaCellData);
                    } else {
                        OtherCellData otherCell = new OtherCellData();
                        otherCell.setLacTac(lacTac);
                        otherCell.setType(cellType);
                        otherCell.setIdentity(newCell);
                        getDBHelper().getOtherCellDataDao().create(otherCell);
                    }
                }
                //then add the history log
                Log.d(TAG, "New Cell history :" + strength + " dBm, " + date.toString()+", LAC/TAC : "+lacTac);
                NeighboringCellHistory neighboringCellHistory = new NeighboringCellHistory();
                neighboringCellHistory.setStrength(strength);
                neighboringCellHistory.setDate(date);
                neighboringCellHistory.setCells(cell);
                getDBHelper().getNeighboringCellHistoryDao().create(neighboringCellHistory);
            }
        }//end for (processing the cells in neighbouring)
    }

    /**
     * Handle action RecordLocation in the provided background thread with the provided
     * parameters.
     * @param intent
     */
    private void handleActionRecordLocation(Intent intent) {
        // TODO: Handle action RecordLocation
        throw new UnsupportedOperationException("Not yet implemented");
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

        List<String> registredAuthType = getDBHelper().getMobilePrivacyProfilerDBHelper().queryAllAuthentificationType();
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
                    getDBHelper().getAuthentificationDao().create(auth);
                    Log.d(TAG, "New Authentification :" + authDesc.type+", names : "+name+", packageName : "+packageName);
                }
            }
        }
    }

    /**
     * Handle action ScanCallHistory in the provided background thread with the provided
     * parameters.
     */

    private void handleActionScanCallHistory(Intent intent) {
        //making a cursor out of the CallLog's data
        ContentResolver contentResolver = getContentResolver();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Cursor callLogCursor = contentResolver.query(android.provider.CallLog.Calls.CONTENT_URI, /*uri*/
                null,
                null,
                null,
                android.provider.CallLog.Calls.DEFAULT_SORT_ORDER /*sort by*/);

        //get the last update if it exist(else expecting = null)
        MobilePrivacyProfilerDB_metadata metadata = getDBHelper().getMobilePrivacyProfilerDBHelper().getDeviceDBMetadata();
        Date lastScan = metadata.getLastCallScan();

        String displayLastScan;
        if (null == lastScan) {
            displayLastScan = "none";
            lastScan = new Date(0);
        } else {
            displayLastScan = lastScan.toString();
        }
        Log.d(TAG, " Starting new Call History Scan : time of last scan " + displayLastScan);


        if (null != callLogCursor) {
            while (callLogCursor.moveToNext()) {//processing call log entries

                Date date = new Date(callLogCursor.getLong(callLogCursor.getColumnIndex(CallLog.Calls.DATE)));

                if (date.after(lastScan)) {//if the log has been registered since last scan

                    String phoneNumber = callLogCursor.getString(callLogCursor.getColumnIndex(CallLog.Calls.NUMBER));

                    long local = callLogCursor.getLong(callLogCursor.getColumnIndex(CallLog.Calls.DURATION));
                    long duration = new Float( local).longValue();

                    int callTypeCode = callLogCursor.getInt(callLogCursor.getColumnIndex(CallLog.Calls.TYPE));
                    String[] typeArray = {"INCOMING", "OUTGOING", "MISSED", "VOICEMAIL", "REJECTED", "BLOCKED", "ANSWERED_EXTERNALLY"};
                    String callType = typeArray[callTypeCode - 1];

                    PhoneCallLog CallLog = new PhoneCallLog(phoneNumber, date, duration, callType);
                    getDBHelper().getPhoneCallLogDao().create(CallLog);
                    Log.d(TAG,"phoneNumber : "+phoneNumber+", date : "+date+", duration : "+duration+", callType : "+callType);
                }
            }
        }
        callLogCursor.close();

        Log.d(TAG, " Updating last Call History Scan");
        metadata.setLastCallScan(new Date());
        getDBHelper().getMobilePrivacyProfilerDB_metadataDao().update(metadata);

    }
    /**
     * Handle action ScanCalendarEvent in the provided background thread with the provided
     * parameters.
     */

    private void
    handleActionScanCalendarEvent(Intent intent) {
        // Projection array. Creating indices for this array
        String[] EVENT_PROJECTION = new String[]{
                CalendarContract.Events._ID,                        // 0
                CalendarContract.Events.ORGANIZER,                  // 1
                CalendarContract.Events.TITLE,                      // 2
                CalendarContract.Events.EVENT_LOCATION,             // 3
                CalendarContract.Events.DTSTART,                    // 4
                CalendarContract.Events.DTEND                       // 5
        };

        // The indices for the projection array above.
        int PROJECTION_ID_INDEX = 0;
        int PROJECTION_OrganizerMail_INDEX = 1;
        int PROJECTION_TITLE_INDEX = 2;
        int PROJECTION_EVENT_LOCATION_INDEX = 3;
        int PROJECTION_DTSTART_INDEX = 4;
        int PROJECTION_DTEND_INDEX = 5;

        Cursor queryEventOutput = null;

        ContentResolver cr = getContentResolver();
        Uri uri = CalendarContract.Events.CONTENT_URI;
        // Submit the query and get a Cursor object back.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        queryEventOutput = cr.query(uri, EVENT_PROJECTION, null, null, null);
        Log.d(TAG,"Number of event to record : "+queryEventOutput.getCount());
        while (queryEventOutput.moveToNext()) {
            long eventID = 0;
            String eventLabel = null;
            String place = null;
            String startDate = null;
            String endDate = null;

            // Get the field values
            eventID = queryEventOutput.getLong(PROJECTION_ID_INDEX);
            eventLabel = queryEventOutput.getString(PROJECTION_TITLE_INDEX);
            place = queryEventOutput.getString(PROJECTION_EVENT_LOCATION_INDEX);
            startDate = queryEventOutput.getString(PROJECTION_DTSTART_INDEX);
            endDate = queryEventOutput.getString(PROJECTION_DTEND_INDEX);

            // Preparing the gathering on participant's information
            String participants = null;

            String[] ATTENDEE_PROJECTION = new String[] {CalendarContract.Attendees.ATTENDEE_NAME};

            Cursor queryAttendeeOuput = null;

            uri = CalendarContract.Attendees.CONTENT_URI;
            String[] arg ={""+eventID};
            //queryAttendeeOuput = cr.query(uri,ATTENDEE_PROJECTION,CalendarContract.Attendees._ID+" = ?",arg,null );
            queryAttendeeOuput = cr.query(uri,ATTENDEE_PROJECTION,null,null,null );

            while (queryAttendeeOuput.moveToNext()){
                participants+= queryAttendeeOuput.getString(0);
            }
            //Stocking data
            CalendarEvent RegistredEvent = getDBHelper().getMobilePrivacyProfilerDBHelper().queryCalendarEvent(eventID);
            if(null!=RegistredEvent) {   // edit event
                RegistredEvent.setEventLabel(eventLabel);
                RegistredEvent.setStartDate(startDate);
                RegistredEvent.setEndDate(endDate);
                RegistredEvent.setPlace(place);
                RegistredEvent.setParticipants(participants);
                getDBHelper().getCalendarEventDao().update(RegistredEvent);
                Log.d(TAG,"Event edited : \n "+
                        "eventID : "+eventID+", label : "+eventLabel+", startDate : "+startDate+", endDate : "+endDate+", place : "+place+", participants : "+participants);

            }
            else {   // add new event
                CalendarEvent calendarEvent = new CalendarEvent();
                calendarEvent.setEventId(eventID);
                calendarEvent.setEventLabel(eventLabel);
                calendarEvent.setStartDate(startDate);
                calendarEvent.setEndDate(endDate);
                calendarEvent.setPlace(place);
                calendarEvent.setParticipants(participants);
                getDBHelper().getCalendarEventDao().create(calendarEvent);
                Log.d(TAG,"New event : \n "+
                        "eventID : "+eventID+", label : "+eventLabel+", startDate : "+startDate+", endDate : "+endDate+", place : "+place+", participants : "+participants);
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
