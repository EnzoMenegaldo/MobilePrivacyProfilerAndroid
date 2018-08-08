package fr.inria.diverse.mobileprivacyprofiler.services;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.IntentService;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Intent;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
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
import java.util.Date;
import java.util.List;
import java.util.Set;

import fr.inria.diverse.mobileprivacyprofiler.activities.Home_CustomViewActivity;
import fr.inria.diverse.mobileprivacyprofiler.broadcastReceiver.BluetoothScanReceiver;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.BluetoothLog;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.CdmaCellData;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.Cell;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.KnownWifi;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.LogsWifi;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.MobilePrivacyProfilerDBHelper;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.MobilePrivacyProfilerDB_metadata;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.NeighboringCellHistory;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.OrmLiteDBHelper;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.OtherCellData;
import fr.inria.diverse.mobileprivacyprofiler.utils.PhoneStateUtils;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ScanConnectionIntentService extends IntentService {

    private static final String TAG = ScanConnectionIntentService.class.getSimpleName();
    private volatile OrmLiteDBHelper dbHelper;

    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_SCAN_CELL_INFO = "fr.inria.diverse.mobileprivacyprofiler.services.action.SCAN_NEAR_CELL_INFO";
    private static final String ACTION_SCAN_WIFI = "fr.inria.diverse.mobileprivacyprofiler.services.action.SCAN_WIFI";
    private static final String ACTION_SCAN_BLUETOOTH = "fr.inria.diverse.mobileprivacyprofiler.services.action.SCAN_BLUETOOTH";

    public ScanConnectionIntentService() {
        super("ScanConnectionIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static Void startActionScanCellInfo() {
        Intent intent = new Intent(Home_CustomViewActivity.getContext(), ScanConnectionIntentService.class);
        intent.setAction(ACTION_SCAN_CELL_INFO);
        Home_CustomViewActivity.getContext().startService(intent);
        return null;
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static Void startActionScanWifi() {
        Intent intent = new Intent(Home_CustomViewActivity.getContext(), ScanConnectionIntentService.class);
        intent.setAction(ACTION_SCAN_WIFI);
        Home_CustomViewActivity.getContext().startService(intent);
        return null;
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static Void startActionScanBluetooth() {
        Intent intent = new Intent(Home_CustomViewActivity.getContext(), ScanConnectionIntentService.class);
        intent.setAction(ACTION_SCAN_BLUETOOTH);
        Home_CustomViewActivity.getContext().startService(intent);
        return null;
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SCAN_CELL_INFO.equals(action)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    handleActionScanCellInfo();
                }
            }else if (ACTION_SCAN_WIFI.equals(action)) {
                handleActionScanWifi();
            }else if (ACTION_SCAN_BLUETOOTH.equals(action)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    handleActionScanBluetooth();
                }
            }
        }
    }

    /**
     * Handle action ScanCellInfo in the provided background thread with the provided
     * parameters.
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void handleActionScanCellInfo() {
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
        String mcc = null;
        String mnc = null;
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
            } else if (cellInfo instanceof CellInfoGsm) {
                isCell = true;
                CellInfoGsm cell = (CellInfoGsm) cellInfo;
                cellType = "Gsm";
                cellId = cell.getCellIdentity().getCid();
                lacTac = cell.getCellIdentity().getLac();
                mcc = "" + cell.getCellIdentity().getMcc();
                mnc = "" + cell.getCellIdentity().getMnc();
                strength = cell.getCellSignalStrength().getDbm();
            } else if (cellInfo instanceof CellInfoLte) {
                isCell = true;
                CellInfoLte cell = (CellInfoLte) cellInfo;
                cellType = "Lte";
                cellId = cell.getCellIdentity().getCi();
                lacTac = cell.getCellIdentity().getTac();
                mcc = "" + cell.getCellIdentity().getMcc();
                mnc = "" + cell.getCellIdentity().getMnc();
                strength = cell.getCellSignalStrength().getDbm();
            } else if (cellInfo instanceof CellInfoWcdma) {
                isCell = true;
                CellInfoWcdma cell = (CellInfoWcdma) cellInfo;
                cellType = "Wcdma";
                cellId = cell.getCellIdentity().getCid();
                lacTac = cell.getCellIdentity().getLac();
                mcc = "" + cell.getCellIdentity().getMcc();
                mnc = "" + cell.getCellIdentity().getMnc();
                strength = cell.getCellSignalStrength().getDbm();
                Log.d(TAG, cellType + " : Cid " + cellId + " : Lac " + lacTac + " : Mcc " + cell.getCellIdentity().getMcc() + " : Mnc " + cell.getCellIdentity().getMnc());
            }
            if (2147483647 == cellId) {
                isCell = false;
                Log.d(TAG, "Cell's data not available : Ignoring this row");
            }
            if (isCell) {//isCell true if the cell as been recognised and data are available (!=2147483647)
                //Log.d(TAG,"--------> looking for cell with "+cellId+" as CellId");
                Cell cell = getDBHelper().getMobilePrivacyProfilerDBHelper().queryCellByCellId(cellId);
                if (null == cell) {// new cell if the cell is not in DB (Cell and ( CdmaCellData or OtherCellData) )
                    Log.d(TAG, "Adding a new " + cellType + " Cell");
                    Cell newCell = new Cell(cellId, getDeviceDBMetadata().getUserId());
                    getDBHelper().getCellDao().create(newCell);
                    cell = newCell;
                    if ("Cdma" == cellType) {
                        CdmaCellData cdmaCellData = new CdmaCellData();
                        cdmaCellData.setLongitude(longitude);
                        cdmaCellData.setLatitude(latitude);
                        cdmaCellData.setIdentity(newCell);
                        cdmaCellData.setUserId(getDeviceDBMetadata().getUserId());
                        getDBHelper().getCdmaCellDataDao().create(cdmaCellData);
                    } else {
                        OtherCellData otherCell = new OtherCellData();
                        otherCell.setLacTac(lacTac);
                        otherCell.setType(cellType);
                        otherCell.setIdentity(newCell);
                        otherCell.setMcc(mcc);
                        otherCell.setMnc(mnc);
                        otherCell.setUserId(getDeviceDBMetadata().getUserId());
                        getDBHelper().getOtherCellDataDao().create(otherCell);
                    }
                }
                //then add the history log
                Log.d(TAG, "New Cell history :" + strength + " dBm, " + date.toString() + ", LAC/TAC : " + lacTac);
                NeighboringCellHistory neighboringCellHistory = new NeighboringCellHistory();
                neighboringCellHistory.setStrength(strength);
                neighboringCellHistory.setDate(date);
                neighboringCellHistory.setCells(cell);
                neighboringCellHistory.setUserId(getDeviceDBMetadata().getUserId());
                getDBHelper().getNeighboringCellHistoryDao().create(neighboringCellHistory);
            }
            //reinitializing parameters :
            cellType = "";
            date = new Date();
            cellId = null;
            longitude = null;
            latitude = null;
            lacTac = null;
            strength = 0;

            isCell = false;
        }//end for (processing the cells in neighbouring)
    }

    /**
     * Handle action ScanWifi in the provided background thread with the provided
     * parameters.
     */
    private void handleActionScanWifi() {
        WifiManager wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        MobilePrivacyProfilerDB_metadata metadata = getDeviceDBMetadata();
        Date scanDate = new Date();
        Log.d(TAG,"LastWifiScan set to "+scanDate.toString());
        metadata.setLastWifiScan(scanDate);
        getDBHelper().getMobilePrivacyProfilerDB_metadataDao().update(metadata);

        List<ScanResult> scannedWifis= wifiManager.getScanResults();
        if (null!=scannedWifis&&!scannedWifis.isEmpty()) {
            Log.d(TAG,"Handling scanned Wifi");
            for(ScanResult scannedWifi : scannedWifis){//every scan : record if new wifi point
                String ssid = null;
                String bssid = null;

                ssid = scannedWifi.SSID;
                bssid = scannedWifi.BSSID;

                KnownWifi knownWifi = new KnownWifi();

                if(!getDBHelper().getMobilePrivacyProfilerDBHelper().isKnownWifi(ssid,bssid)){

                    knownWifi.setSsid(ssid);
                    knownWifi.setBssid(bssid);
                    knownWifi.setIsConfiguredWifi(0);
                    knownWifi.setUserId(getDeviceDBMetadata().getUserId());

                    Log.d(TAG," Create a new knownWifi : SSID : "+knownWifi.getSsid()+
                            ", BSSID : "+knownWifi.getBssid()+
                            ", isConfiguredWifi : "+knownWifi.getIsConfiguredWifi()
                    //        +", UserId : "+knownWifi.getUserId()
                    );
                    getDBHelper().getKnownWifiDao().create(knownWifi);
                }
                else{Log.d(TAG,"KnownWifi : SSID : "+ ssid+", BSSID : "+bssid);}
                // get the recorded wifi from the DB
                knownWifi = getDBHelper().getMobilePrivacyProfilerDBHelper().queryKnownWifiFromSsidBssid(ssid,bssid);

                //add a new detection event if not recorded yet
                LogsWifi newLog = new LogsWifi();
                newLog.setKnownWifi(knownWifi);
                Date date = new Date(new Date().getTime()+ (int) (scannedWifi.timestamp*0.001) - SystemClock.uptimeMillis());
                newLog.setTimeStamp(date);
                newLog.setUserId(getDeviceDBMetadata().getUserId());
                if(!getDBHelper().getMobilePrivacyProfilerDBHelper().isRecordedLogWifi(knownWifi,date)) {
                    Log.d(TAG,"Create new LogsWifi : "+knownWifi.toString()+", timeStamp : "+date.toString());
                    getDBHelper().getLogsWifiDao().createOrUpdate(newLog);
                }
                else{Log.d(TAG,"Aborting addition of LogsWifi duplicate");}
            }
        }


        // updating isConfiguredWifi
        List<WifiConfiguration> configuredWifis = wifiManager.getConfiguredNetworks();
        if(null!=configuredWifis&&!configuredWifis.isEmpty()) {
            Log.d(TAG,"Handling configured Wifi : "+configuredWifis.size());
            for (WifiConfiguration wifiConfiguration : configuredWifis) {
                String ssid = null;

                ssid = wifiConfiguration.SSID;
                ssid = ssid.substring(1,ssid.length()-1);
                List<KnownWifi> knownWifis = new ArrayList();
                knownWifis = getDBHelper().getMobilePrivacyProfilerDBHelper().queryKnownWifiFromSsid(ssid);
                if (null!=knownWifis) {//if unrecorded wifi
                    for (KnownWifi knownWifi : knownWifis) {
                        if (1!=knownWifi.getIsConfiguredWifi()) {
                            knownWifi.setIsConfiguredWifi(1);
                            Log.d(TAG, " Editing knownWifi : SSID : " + knownWifi.getSsid() +
                                            ", BSSID : " + knownWifi.getBssid() +
                                            ", isConfiguredWifi : " + knownWifi.getIsConfiguredWifi()
                                    //        +", UserId : "+knownWifi.getUserId()
                            );
                            getDBHelper().getKnownWifiDao().update(knownWifi);
                        }
                        else{Log.d(TAG, "Up to date knownWifi : SSID : " + knownWifi.getSsid() +
                                        ", BSSID : " + knownWifi.getBssid() +
                                        ", isConfiguredWifi : " + knownWifi.getIsConfiguredWifi()
                                //        +", UserId : "+knownWifi.getUserId()
                        );}
                    }
                }
            }
        }
        else{Log.d(TAG,"No WifiConfiguration retrieved");}


    }

    /**
     * Handle action ScanBluetooth in the provided background thread with the provided
     * parameters.
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void handleActionScanBluetooth() {
        if(PhoneStateUtils.isBluetoothActive()) {
            BluetoothManager bluetoothManager = (BluetoothManager) this.getSystemService(Context.BLUETOOTH_SERVICE);
            BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
            Set<BluetoothDevice> bluetoothDevices = bluetoothAdapter.getBondedDevices();

            Log.d(TAG, "Query returned :" + bluetoothDevices.size() + " paired devices");
            if (null != bluetoothDevices) {
                for (BluetoothDevice bluetoothDevice : bluetoothDevices) {
                    String mac = null;
                    String name = null;
                    String userId = getDeviceDBMetadata().getUserId();
                    int type = -1;
                    Date date = new Date();

                    mac = bluetoothDevice.getAddress();
                    name = bluetoothDevice.getName();
                    type = bluetoothDevice.getBluetoothClass().getDeviceClass();

                    if (!getDBHelper().getMobilePrivacyProfilerDBHelper().isRecordedBluetoothDevice(mac)) {
                        fr.inria.diverse.mobileprivacyprofiler.datamodel.BluetoothDevice newBluetoothDevice = new fr.inria.diverse.mobileprivacyprofiler.datamodel.BluetoothDevice();
                        newBluetoothDevice.setMac(mac);
                        newBluetoothDevice.setName(name);
                        newBluetoothDevice.setType(type);
                        newBluetoothDevice.setUserId(userId);
                        Log.d(TAG, "Create new BluetoothDevice : MAC : " + newBluetoothDevice.getMac() +
                                ", Name : " + newBluetoothDevice.getName() +
                                ", type : " + newBluetoothDevice.getType()
                                + ", UserId : " + userId
                        );
                        getDBHelper().getBluetoothDeviceDao().create(newBluetoothDevice);
                    } else {
                        Log.d(TAG, "Recorded BluetoothDevice : MAC : " + mac +
                                        ", Name : " + name +
                                        ", type : " + type
                                //        +", UserId : "+knownWifi.getUserId()
                        );
                    }
                }
            }//end if not null
        }//end if bluetooth active
    }//end method

    private OrmLiteDBHelper getDBHelper(){
        if(dbHelper == null){
            dbHelper = OpenHelperManager.getHelper(this, OrmLiteDBHelper.class);
        }
        return dbHelper;
    }

    private MobilePrivacyProfilerDB_metadata getDeviceDBMetadata(){
        return getDBHelper().getMobilePrivacyProfilerDBHelper().getDeviceDBMetadata();}
}
