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
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.TelephonyManager;
import android.util.Log;

import android.content.pm.ResolveInfo;
import android.support.annotation.RequiresApi;

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
import fr.inria.diverse.mobileprivacyprofiler.datamodel.Contact;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.ContactEmail;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.ContactEvent;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.ContactIM;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.ContactOrganisation;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.ContactPhoneNumber;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.ContactPhysicalAddress;
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
    private static final String ACTION_SCAN_CONTACTS = "fr.inria.diverse.mobileprivacyprofiler.services.action.SCAN_CONTACTS";
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
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionScanContacts(Context context) {
        Intent intent = new Intent(context, ScanDeviceIntentService.class);
        intent.setAction(ACTION_SCAN_CONTACTS);
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
            } else if (ACTION_SCAN_CONTACTS.equals(action)) {
                handleActionScanContact();
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
                applicationHistory.setUserMetaData(getDeviceDBMetadata());
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
        batState.setUserMetaData(getDeviceDBMetadata());
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
            Cursor smsQueryOutput = null;
            Uri mSmsQueryUri = Uri.parse(source);
            String columns[] = new String[]{"person", "address", "body", "date", "status"/*,"body"*/};
            //if not 1st scan
            if (null != lastScan) {
                Log.d(TAG, "Fetching messages up to last scan");
                String lastScanString = "" + lastScan.getTime();
                String[] arguments = {lastScanString};
                smsQueryOutput = getContentResolver().query(mSmsQueryUri, columns, "date > ?", arguments, null);
            }
            //if 1st scan
            else {
                Log.d(TAG, "Fetching all messages");
                smsQueryOutput = getContentResolver().query(mSmsQueryUri, columns, null, null, null);
            }
            while (smsQueryOutput.moveToNext()) {
                String date = smsQueryOutput.getString(3);
                String phoneNumber = smsQueryOutput.getString(1);
                String type="";
                if("content://sms/inbox"==source){ type = "inbox";}
                if("content://sms/sent"==source){ type = "sent";}
                SMS sms = new SMS(date, phoneNumber, type);
                sms.setUserMetaData(getDeviceDBMetadata());
                getDBHelper().getSMSDao().create(sms);
                Log.d(TAG,"date : "+date+",phone number : "+phoneNumber+",type :"+type/*+",body : "+smsQueryOutput.getString(5)*/);
            }
            smsQueryOutput.close();
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
                    newCell.setUserMetaData(getDeviceDBMetadata());
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
                    auth.setUserMetaData(getDeviceDBMetadata());
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

                    PhoneCallLog callLog = new PhoneCallLog(phoneNumber, date, duration, callType);
                    callLog.setUserMetaData(getDeviceDBMetadata());
                    getDBHelper().getPhoneCallLogDao().create(callLog);
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
            CalendarEvent registredEvent = getDBHelper().getMobilePrivacyProfilerDBHelper().queryCalendarEvent(eventID);
            if(null!=registredEvent) {   // edit event
                registredEvent.setEventLabel(eventLabel);
                registredEvent.setStartDate(startDate);
                registredEvent.setEndDate(endDate);
                registredEvent.setPlace(place);
                registredEvent.setParticipants(participants);
                getDBHelper().getCalendarEventDao().update(registredEvent);
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
                calendarEvent.setUserMetaData(getDeviceDBMetadata());
                getDBHelper().getCalendarEventDao().create(calendarEvent);
                Log.d(TAG,"New event : \n "+
                        "eventID : "+eventID+", label : "+eventLabel+", startDate : "+startDate+", endDate : "+endDate+", place : "+place+", participants : "+participants);
            }
            }
    }
    /**
     * Handle action ScanContact in the provided background thread with the provided
     * parameters.
     */
    private void handleActionScanContact (){
    // Return all raw_contacts _id in a list: contactIdList
        List<Integer> rawContactIdList = new ArrayList<Integer>();

        ContentResolver contentResolver = getContentResolver();

        //update last ContactScan and flushing previous contact set
        Date scanTimeStamp = new Date();
        getDBHelper().getMobilePrivacyProfilerDBHelper().flushContactDataSet();
        getDeviceDBMetadata().setLastContactScan(scanTimeStamp);

        // Row contacts content uri( access raw_contacts table. ).
        Uri rawContactUri = ContactsContract.RawContacts.CONTENT_URI;
        // Return _id column in contacts raw_contacts table.
        String queryContactColumnArr[] = {ContactsContract.RawContacts._ID};
        // Query raw_contacts table and return raw_contacts table _id.
        Cursor rawContactCursor = contentResolver.query(rawContactUri,queryContactColumnArr, null, null, null);
        if(rawContactCursor!=null)
        {
            rawContactCursor.moveToFirst();
            do{
                int idColumnIndex = rawContactCursor.getColumnIndex(ContactsContract.RawContacts._ID);
                int rawContactsId = rawContactCursor.getInt(idColumnIndex);
                rawContactIdList.add(new Integer(rawContactsId));
            }while(rawContactCursor.moveToNext());
        }

        rawContactCursor.close();
    // end of contactIdList creation

        Log.d(TAG, " Number of contact to process : " + rawContactIdList.size());

        // Loop in the raw contacts list to query contact details
        for(Integer rawContactId :rawContactIdList) {


            Log.d(TAG, "raw contact id : " + rawContactId.intValue());

            // Data content uri (access data table. )
            Uri dataContentUri = ContactsContract.Data.CONTENT_URI;

            // Build query columns name array.
            List<String> queryColumnList = new ArrayList<String>();

            // ContactsContract.Data.CONTACT_ID = "contact_id";
            queryColumnList.add(ContactsContract.Data.CONTACT_ID);

            // ContactsContract.Data.MIMETYPE = "mimetype";
            queryColumnList.add(ContactsContract.Data.MIMETYPE);

            queryColumnList.add(ContactsContract.Data.DATA1);
            queryColumnList.add(ContactsContract.Data.DATA2);
            queryColumnList.add(ContactsContract.Data.DATA3);
            queryColumnList.add(ContactsContract.Data.DATA4);
            queryColumnList.add(ContactsContract.Data.DATA5);
            queryColumnList.add(ContactsContract.Data.DATA6);
            queryColumnList.add(ContactsContract.Data.DATA7);
            queryColumnList.add(ContactsContract.Data.DATA8);
            queryColumnList.add(ContactsContract.Data.DATA9);
            queryColumnList.add(ContactsContract.Data.DATA10);
            queryColumnList.add(ContactsContract.Data.DATA11);
            queryColumnList.add(ContactsContract.Data.DATA12);
            queryColumnList.add(ContactsContract.Data.DATA13);
            queryColumnList.add(ContactsContract.Data.DATA14);
            queryColumnList.add(ContactsContract.Data.DATA15);

            // Translate column name list to array.
            String queryColumnArr[] = queryColumnList.toArray(new String[queryColumnList.size()]);

            // Build query condition string. Query rows by contact id.
            StringBuffer whereClauseBuf = new StringBuffer();
            whereClauseBuf.append(ContactsContract.Data.RAW_CONTACT_ID);
            whereClauseBuf.append("=");
            whereClauseBuf.append(rawContactId);

            // Query data table and return related contact data.
            Cursor contactDetailCursor = contentResolver.query(dataContentUri, queryColumnArr, whereClauseBuf.toString(), null, null);

            /* If this cursor return database table row data
               If do not check cursor.getCount() then it will throw error
               android.database.CursorIndexOutOfBoundsException: Index 0 requested, with a size of 0.
               */
            if(contactDetailCursor!=null && contactDetailCursor.getCount() > 0)
            {
                StringBuffer lineBuf = new StringBuffer();
                contactDetailCursor.moveToFirst();

                lineBuf.append("Raw Contact Id : ");
                lineBuf.append(rawContactId);

                long contactId = contactDetailCursor.getLong(contactDetailCursor.getColumnIndex(ContactsContract.Data.CONTACT_ID));
                lineBuf.append(" , Contact Id : ");
                lineBuf.append(contactId);


                //Creation of data structure to collect details from a contact
                Contact contact = new Contact();
                List<ContactEmail> emailList = new ArrayList();
                ContactOrganisation organisation = new ContactOrganisation();
                List<ContactPhoneNumber> phoneNumberList = new ArrayList();
                List<ContactPhysicalAddress> physicalAddressList = new ArrayList();
                List<ContactIM> imList = new ArrayList();
                List<ContactEvent> eventList = new ArrayList();

                do{
                    // First get mimetype column value.
                    String mimeType = contactDetailCursor.getString(contactDetailCursor.getColumnIndex(ContactsContract.Data.MIMETYPE));
                    lineBuf.append(" \r\n , MimeType : ");
                    lineBuf.append(mimeType);

                    List<String> dataValueList = getColumnValueByMimetype(contactDetailCursor, mimeType);


                    Log.d(TAG, "reformating details to objects");
                    switch (dataValueList.get(0)){
                        //TODO change the List<String> to a Map
                        case "EMAIL":
                            Log.d(TAG, dataValueList.get(0)+" : "+dataValueList.get(1)+" : "+dataValueList.get(2));
                            ContactEmail contactEmail = new ContactEmail(dataValueList.get(1),dataValueList.get(2));
                            emailList.add(contactEmail);
                            break;
                        case "IM":
                            Log.d(TAG, dataValueList.get(0)+" : "+dataValueList.get(1)+" : "+dataValueList.get(2));
                            ContactIM contactIM = new ContactIM(dataValueList.get(1),dataValueList.get(2));
                            imList.add(contactIM);
                            break;
                        case "NICKNAME":
                            Log.d(TAG, dataValueList.get(0)+" : "+dataValueList.get(1));
                            contact.setNickname(dataValueList.get(1));
                            break;
                        case "ORGANISATION":
                            Log.d(TAG, dataValueList.get(0)+" : "+dataValueList.get(1)+" : "+dataValueList.get(2));
                            organisation = new ContactOrganisation(dataValueList.get(1),dataValueList.get(2));
                            break;
                        case "PHONE_NUMBER":
                            Log.d(TAG, dataValueList.get(0)+" : "+dataValueList.get(1)+" : "+dataValueList.get(2));
                            ContactPhoneNumber contactPhoneNumber = new ContactPhoneNumber(dataValueList.get(1),dataValueList.get(2));
                            phoneNumberList.add(contactPhoneNumber);
                            break;
                        case "NAME":
                            Log.d(TAG, dataValueList.get(0)+" : "+dataValueList.get(1)+" : "+dataValueList.get(2)+" : "+dataValueList.get(3)+" : "+dataValueList.get(4)+" : "+dataValueList.get(5)+" : "+dataValueList.get(6));
                            contact.setDisplayName(dataValueList.get(1));
                            contact.setNamePrefix(dataValueList.get(2));
                            contact.setFirstName(dataValueList.get(3));
                            contact.setMiddleName(dataValueList.get(4));
                            contact.setLastName(dataValueList.get(5));
                            contact.setNameSuffix(dataValueList.get(6));
                            contact.setScanTimeStamp(scanTimeStamp);
                            break;
                        case "PHYSICAL_ADDRESS":
                            Log.d(TAG, dataValueList.get(0)+" : "+dataValueList.get(1)+" : "+dataValueList.get(2));
                            ContactPhysicalAddress contactPhysicalAddress = new ContactPhysicalAddress(dataValueList.get(1),dataValueList.get(2));
                            physicalAddressList.add(contactPhysicalAddress);
                            break;
                        case "WEBSITE":
                            Log.d(TAG, dataValueList.get(0)+" : "+dataValueList.get(1));
                            String temp = contact.getWebsite();
                            temp +=", ";
                            temp += dataValueList.get(1);
                            contact.setWebsite(temp);
                            break;
                        case "EVENT":
                            Log.d(TAG, dataValueList.get(0)+" : "+dataValueList.get(1)+" : "+dataValueList.get(2));
                            ContactEvent contactEvent = new ContactEvent(dataValueList.get(1),dataValueList.get(2));
                            eventList.add(contactEvent);
                            break;
                        case "RELATION_TYPE":
                            Log.d(TAG, dataValueList.get(0)+" : "+dataValueList.get(1)+" : "+dataValueList.get(2));
                            String tempo = contact.getRelation();
                            tempo +="; ";
                            tempo += dataValueList.get(1);
                            tempo += " : ";
                            tempo += dataValueList.get(2);
                            contact.setRelation(tempo);
                            break;
                        case "UNPROCESSED_ITEM":
                            Log.d(TAG, dataValueList.get(0));
                            break;
                    }


                }while(contactDetailCursor.moveToNext());
                contactDetailCursor.close();

                // feeding the DB from the collected data
                //TODO check for redundant pre add
                try {
                    //add a new contact
                        contact.setUserMetaData(getDeviceDBMetadata());
                        getDBHelper().getContactDao().create(contact);
                        Log.d(TAG, "Added a new contact : " + contact.getDisplayName());

                    for(ContactEmail contactEmail: emailList){
                        contactEmail.setContact(contact);
                        getDBHelper().getContactEmailDao().create(contactEmail);
                        Log.d(TAG, "Added a new email : " + contactEmail.getEmail());
                        }
                    if(null!=organisation.getCompany()) {
                        organisation.setReferencedContact(contact);
                        getDBHelper().getContactOrganisationDao().create(organisation);
                        Log.d(TAG, "Added a new organisation : " + organisation.getCompany());
                    }

                    for(ContactPhoneNumber contactPhoneNumber : phoneNumberList){
                        contactPhoneNumber.setContact(contact);
                        getDBHelper().getContactPhoneNumberDao().create(contactPhoneNumber);
                        Log.d(TAG, "Added a new phoneNumber : "+contactPhoneNumber.getPhoneNumber());
                    }

                    for(ContactPhysicalAddress contactPhysicalAddress : physicalAddressList){
                        contactPhysicalAddress.setContact(contact);
                        getDBHelper().getContactPhysicalAddressDao().create(contactPhysicalAddress);
                        Log.d(TAG, "Added a new physicalAddress : "+contactPhysicalAddress.getAddress());
                    }

                    for(ContactIM contactIM : imList){
                        contactIM.setContact(contact);
                        getDBHelper().getContactIMDao().create(contactIM);
                        Log.d(TAG, "Added a new instant messenger : "+contactIM.getImId());
                    }

                    for(ContactEvent contactEvent : eventList){
                        contactEvent.setContact(contact);
                        getDBHelper().getContactEventDao().create(contactEvent);
                        Log.d(TAG, "Added a new contactEvent : "+contactEvent.getType()+" : "+contactEvent.getStartDate());
                    }
                } catch (Exception e) { Log.d(TAG, "Error while feeding the DB");e.printStackTrace(); }
            }



        }//end of contact detail processing

    }

        /**
         * Handle action Baz in the provided background thread with the provided
         * parameters.
         */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private OrmLiteDBHelper getDBHelper(){
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
    //TODO ask about the quoting
    /**
     * code used from : www.dev2qa.com/how-to-get-contact-list-in-android-programmatically/
     *
     *  Return data column value by mimetype column value.
     *  Because for each mimetype there has not only one related value,
     *  such as Organization.CONTENT_ITEM_TYPE need return company, department, title, job description etc.
     *  So the return is a list string, each string for one column value.
     * */
    private List<String> getColumnValueByMimetype(Cursor cursor, String mimeType)
    {
        List<String> toReturn = new ArrayList<String>();

        switch (mimeType)
        {
            // Get email data
            case ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE :
                // Email.ADDRESS == data1
                String emailAddress = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
                // Email.TYPE == data2
                int emailType = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
                String emailTypeStr = getEmailTypeString(emailType);


                toReturn.add("EMAIL");
                toReturn.add(emailAddress);

                if("Custom"!=emailTypeStr)  {toReturn.add(emailTypeStr);}
                else                        {
                    // Email.LABEL == data3 (if type = custom then read the label)
                    String emailLabel = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.LABEL));
                    toReturn.add(emailLabel);
                }

                break;

            // Get im data
            case ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE:
                // Im.PROTOCOL == data5
                int imProtocol = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Im.PROTOCOL));
                // Im.DATA == data1
                String imId = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA));

                String imProtocolString = getImProtocolTypeString(cursor,imProtocol);

                toReturn.add("IM");
                toReturn.add(imProtocolString);
                toReturn.add(imId);
                break;

            // Get nickname
            case ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE:
                // Nickname.NAME == data1
                String nickName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Nickname.NAME));

                toReturn.add("NICKNAME");
                toReturn.add(nickName);
                break;

            // Get organization data
            case ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE:
                // Organization.COMPANY == data1
                String company = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.COMPANY));
                // Organization.TITLE == data4
                String title = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE));

                toReturn.add("ORGANISATION");
                toReturn.add(company);
                toReturn.add(title);
                break;

            // Get phone number
            case ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE:
                // Phone.NUMBER == data1
                String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                // Phone.TYPE == data2
                int phoneTypeInt = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                String phoneTypeStr = getPhoneTypeString(cursor,phoneTypeInt);

                toReturn.add("PHONE_NUMBER");
                toReturn.add(phoneNumber);
                toReturn.add(phoneTypeStr);
                break;


            // Get display name
            case ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE:
                // StructuredName.DISPLAY_NAME == data1
                String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME));
                // StructuredName.PREFIX == data4
                String namePrefix = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.PREFIX));
                // StructuredName.GIVEN_NAME == data2
                String givenName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));
                // StructuredName.MIDDLE_NAME == data5
                String middleName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME));
                // StructuredName.FAMILY_NAME == data3
                String familyName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
                // StructuredName.SUFFIX == data6
                String nameSuffix = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.SUFFIX));

                toReturn.add("NAME");
                toReturn.add(displayName);
                toReturn.add(namePrefix);
                toReturn.add(givenName);
                toReturn.add(middleName);
                toReturn.add(familyName);
                toReturn.add(nameSuffix);
                break;

            // Get postal address
            case ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE:
                // StructuredPostal.COUNTRY == data10
                String country = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
                // StructuredPostal.CITY == data7
                String city = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
                // StructuredPostal.REGION == data8
                String region = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
                // StructuredPostal.STREET == data4
                String street = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
                // StructuredPostal.POSTCODE == data9
                String postcode = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
                // StructuredPostal.TYPE == data2
                int postType = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE));
                String postTypeStr = getEmailTypeString(postType); // TYPE_MOBILE isn't displayed other value remain the same than EmailType


                toReturn.add("PHYSICAL_ADDRESS");
                toReturn.add("Country : "+country+", City : "+city+", Region : "+region+",Street : "+street+",Postcode : "+postcode);
                if("Custom"!=postTypeStr)  {toReturn.add(postTypeStr);}
                else                       {
                    // Email.LABEL == data3 (if type = custom then read the label)
                    String addressTypeLabel = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.LABEL));
                    toReturn.add(addressTypeLabel);
                }

                break;


            // Get website
            case ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE:
                // Website.URL == data1
                String websiteUrl = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Website.URL));
                // Website.TYPE == data2
                int websiteTypeInt = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Website.TYPE));
                String webSiteTypeStr = getEmailTypeString(websiteTypeInt);

                toReturn.add("WEBSITE");

                if("Custom"!=webSiteTypeStr)  {toReturn.add(webSiteTypeStr+" : "+websiteUrl);}
                else                          {
                    // Website.LABEL == data3 (if type = custom then read the label)
                    String webSiteTypeLabel = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Website.LABEL));
                    toReturn.add(webSiteTypeLabel+" : "+websiteUrl);
                }
                break;

            // Get Event
            case ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE:
                // Event.START_DATE == data1
                String startDate = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE));
                // Event.TYPE == data2
                int eventType = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.TYPE));
                String eventTypeString = getEventTypeString(cursor,eventType);

                toReturn.add("EVENT");
                toReturn.add(startDate);
                toReturn.add(eventTypeString);
                break;


            // Get Relation
            case ContactsContract.CommonDataKinds.Relation.CONTENT_ITEM_TYPE:
                // Relation.DATA == data1
                String relationName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Relation.DATA));
                // Relation.TYPE == data2
                int relationType = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Relation.TYPE));
                String relationTypeString = getRelationTypeString(cursor,relationType);

                toReturn.add("RELATION_TYPE");
                toReturn.add(relationName);
                toReturn.add(relationTypeString);
                break;

            default:
                toReturn.add("UNPROCESSED_ITEM");
        }

        return toReturn;
    }


    private String getImProtocolTypeString(Cursor cursor,int dataType){
        String toReturn ="";
        if(ContactsContract.CommonDataKinds.Im.PROTOCOL_CUSTOM == dataType)
        {
            toReturn = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Im.CUSTOM_PROTOCOL));
        }
        else if(ContactsContract.CommonDataKinds.Im.PROTOCOL_MSN==dataType)     { toReturn = "MSN"; }
        else if(ContactsContract.CommonDataKinds.Im.PROTOCOL_YAHOO==dataType)   { toReturn = "YAHOO"; }
        else if(ContactsContract.CommonDataKinds.Im.PROTOCOL_SKYPE==dataType)   { toReturn = "SKYPE"; }
        else if(ContactsContract.CommonDataKinds.Im.PROTOCOL_QQ==dataType)      { toReturn = "QQ"; }
        else if(ContactsContract.CommonDataKinds.Im.PROTOCOL_GOOGLE_TALK==dataType) { toReturn = "GOOGLE_TALK"; }
        else if(ContactsContract.CommonDataKinds.Im.PROTOCOL_ICQ==dataType)     { toReturn = "ICQ"; }
        else if(ContactsContract.CommonDataKinds.Im.PROTOCOL_JABBER==dataType)  { toReturn = "JABBER"; }
        else if(ContactsContract.CommonDataKinds.Im.PROTOCOL_NETMEETING==dataType) { toReturn = "NETMEETING"; }

        return toReturn;
    }

    /**
     *  Get email type related string format value.
     * */
    private String getEmailTypeString(int dataType)
    {
        String ret = "";
        if(ContactsContract.CommonDataKinds.Email.TYPE_HOME == dataType)
        { ret = "Home"; }
        else if(ContactsContract.CommonDataKinds.Email.TYPE_WORK==dataType)
        { ret = "Work"; }
        else if(ContactsContract.CommonDataKinds.Email.TYPE_MOBILE==dataType)
        { ret = "Mobile"; }
        else if(ContactsContract.CommonDataKinds.Email.TYPE_OTHER==dataType)
        { ret = "Other"; }
        else if(ContactsContract.CommonDataKinds.Email.TYPE_CUSTOM==dataType)
        { ret = "Custom"; }
        return ret;
    }

    /**
     *  Get phone type related string format value.
     * */
    private String getPhoneTypeString(Cursor cursor, int dataType)
    {
        String toReturn = "";

        switch (dataType){
            case ContactsContract.CommonDataKinds.Phone.TYPE_CUSTOM:
                // Phone.LABEL == data3
                toReturn = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.LABEL));
            break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:          toReturn = "Work";      break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE :       toReturn = "Mobile";    break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_HOME :         toReturn = "Home";      break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_FAX_WORK :     toReturn = "Fax work";  break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_FAX_HOME :     toReturn = "Fax home";  break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_PAGER :        toReturn = "Pager";     break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_OTHER :        toReturn = "Other";     break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_CALLBACK :     toReturn = "Callback";  break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_CAR :          toReturn = "Car";       break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_COMPANY_MAIN : toReturn = "Company main";break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_ISDN :         toReturn = "ISDN";      break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_MAIN :         toReturn = "Main";      break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_OTHER_FAX :    toReturn = "Other fax"; break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_RADIO :        toReturn = "Radio";     break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_TELEX :        toReturn = "Telex";     break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_TTY_TDD :      toReturn = "TTY_TDD";   break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_WORK_MOBILE :  toReturn = "Mobile";    break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_WORK_PAGER :   toReturn = "Pager";     break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_ASSISTANT :    toReturn = "Assistant"; break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_MMS :          toReturn = "MMS";       break;
            }//end switch
        return toReturn;
    }
    /**
     *  Get event type related string format value.
     * */
    private String getEventTypeString(Cursor cursor, int dataType) {
    String toReturn ="";
    switch (dataType){
        case ContactsContract.CommonDataKinds.Event.TYPE_CUSTOM:        toReturn = "Custom";        break;
        case ContactsContract.CommonDataKinds.Event.TYPE_ANNIVERSARY:   toReturn = "Anniversary";   break;
        case ContactsContract.CommonDataKinds.Event.TYPE_OTHER:         toReturn = "Other";         break;
        case ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY:      toReturn = "Birthday";      break;
    }
    if("Custom"==toReturn){ toReturn = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.LABEL)); }

    return toReturn;
    }

    /**
     *  Translate the Relation  int type to related string value.
     * */
    private String getRelationTypeString(Cursor cursor, int relationType) {
        String toReturn = "";

        switch (relationType){
            case ContactsContract.CommonDataKinds.Relation.TYPE_CUSTOM:
                // Relation.LABEL == data3
                toReturn = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Relation.LABEL));
                break;
            case ContactsContract.CommonDataKinds.Relation.TYPE_ASSISTANT:          toReturn = "Assistant";     break;
            case ContactsContract.CommonDataKinds.Relation.TYPE_BROTHER :           toReturn = "Brother";       break;
            case ContactsContract.CommonDataKinds.Relation.TYPE_CHILD :             toReturn = "Child";         break;
            case ContactsContract.CommonDataKinds.Relation.TYPE_DOMESTIC_PARTNER :  toReturn = "Domestic_Partner";  break;
            case ContactsContract.CommonDataKinds.Relation.TYPE_FATHER :            toReturn = "Father";        break;
            case ContactsContract.CommonDataKinds.Relation.TYPE_FRIEND :            toReturn = "Friend";        break;
            case ContactsContract.CommonDataKinds.Relation.TYPE_MANAGER :           toReturn = "Manager";       break;
            case ContactsContract.CommonDataKinds.Relation.TYPE_MOTHER :            toReturn = "Mother";        break;
            case ContactsContract.CommonDataKinds.Relation.TYPE_PARENT :            toReturn = "Parent";        break;
            case ContactsContract.CommonDataKinds.Relation.TYPE_PARTNER :           toReturn = "Partner";       break;
            case ContactsContract.CommonDataKinds.Relation.TYPE_REFERRED_BY :       toReturn = "Referred_by";   break;
            case ContactsContract.CommonDataKinds.Relation.TYPE_RELATIVE :          toReturn = "Relative";      break;
            case ContactsContract.CommonDataKinds.Relation.TYPE_SISTER :            toReturn = "Sister";        break;
            case ContactsContract.CommonDataKinds.Relation.TYPE_SPOUSE :            toReturn = "Spouse";        break;
        }//end switch
        return toReturn;
    }

    private MobilePrivacyProfilerDB_metadata getDeviceDBMetadata(){
    return getDBHelper().getMobilePrivacyProfilerDBHelper().getDeviceDBMetadata();}

}//end class
