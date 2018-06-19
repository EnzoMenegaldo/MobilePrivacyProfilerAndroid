package fr.inria.diverse.mobileprivacyprofiler.services;

import android.Manifest;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.Date;
import java.util.List;

import fr.inria.diverse.mobileprivacyprofiler.datamodel.CdmaCellData;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.Cell;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.MobilePrivacyProfilerDB_metadata;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.NeighboringCellHistory;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.OrmLiteDBHelper;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.OtherCellData;

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
    private static final String ACTION_BAZ = "fr.inria.diverse.mobileprivacyprofiler.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "fr.inria.diverse.mobileprivacyprofiler.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "fr.inria.diverse.mobileprivacyprofiler.extra.PARAM2";

    public ScanConnectionIntentService() {
        super("ScanConnectionIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionScanCellInfo(Context context) {
        Intent intent = new Intent(context, ScanConnectionIntentService.class);
        intent.setAction(ACTION_SCAN_CELL_INFO);
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
        Intent intent = new Intent(context, ScanConnectionIntentService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SCAN_CELL_INFO.equals(action)) {
                handleActionScanCellInfo(intent);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
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

    private MobilePrivacyProfilerDB_metadata getDeviceDBMetadata(){
        return getDBHelper().getMobilePrivacyProfilerDBHelper().getDeviceDBMetadata();}

}
