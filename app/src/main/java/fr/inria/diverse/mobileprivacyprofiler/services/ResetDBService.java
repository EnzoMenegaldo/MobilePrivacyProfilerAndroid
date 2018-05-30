package fr.inria.diverse.mobileprivacyprofiler.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.provider.Telephony;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.inria.diverse.mobileprivacyprofiler.datamodel.ApplicationHistory;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.ApplicationUsageStats;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.Authentification;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.BatteryUsage;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.BluetoothDevice;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.BluetoothLog;
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
import fr.inria.diverse.mobileprivacyprofiler.datamodel.DetectedWifi;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.Geolocation;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.KnownWifi;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.MobilePrivacyProfilerDB_metadata;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.NeighboringCellHistory;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.OrmLiteDBHelper;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.PhoneCallLog;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.SMS;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.WebHistory;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.WifiAccessPoint;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 *
 * helper methods.
 */
public class ResetDBService extends IntentService {


    private static final String TAG = ResetDBService.class.getSimpleName();
    private volatile OrmLiteDBHelper dbHelper;


    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_RESET_DB = "fr.inria.diverse.mobileprivacyprofiler.services.action.RESET_DB";
    private static final String ACTION_BAZ = "fr.inria.diverse.mobileprivacyprofiler.services.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "fr.inria.diverse.mobileprivacyprofiler.services.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "fr.inria.diverse.mobileprivacyprofiler.services.extra.PARAM2";

    public ResetDBService() {
        super("ResetDBService");
    }

    /**
     * Starts this service to perform action ResetDB . If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionResetDB(Context context) {
        Intent intent = new Intent(context, ResetDBService.class);
        intent.setAction(ACTION_RESET_DB);
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
        Intent intent = new Intent(context, ResetDBService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_RESET_DB.equals(action)) {
                handleActionResetDB();
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    /**
     * Handle action Resert DB in the provided background thread with the provided
     * parameters.
     */
    private void handleActionResetDB() {
        List<Class> list = new ArrayList<>();
        list.add(ApplicationHistory.class);
        list.add(ApplicationUsageStats.class);
        list.add(Authentification.class);
        list.add(BatteryUsage.class);
        list.add(BluetoothDevice.class);
        list.add(BluetoothLog.class);
        list.add(CalendarEvent.class);
        list.add(CdmaCellData.class);
        list.add(Cell.class);
        list.add(Contact.class);
        list.add(ContactEmail.class);
        list.add(ContactEvent.class);
        list.add(ContactIM.class);
        list.add(ContactOrganisation.class);
        list.add(ContactPhoneNumber.class);
        list.add(ContactPhysicalAddress.class);
        list.add(DetectedWifi.class);
        list.add(Geolocation.class);
        list.add(KnownWifi.class);
        list.add(MobilePrivacyProfilerDB_metadata.class);
        list.add(NeighboringCellHistory.class);
        list.add(PhoneCallLog.class);
        list.add(SMS.class);
        list.add(WebHistory.class);
        list.add(WifiAccessPoint.class);

        for(Class _class : list){
        resetTable(_class);
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

    private OrmLiteDBHelper getDBHelper(){
        if(dbHelper == null){
            dbHelper = OpenHelperManager.getHelper(this, OrmLiteDBHelper.class);
        }
        return dbHelper;
    }

    private void resetTable(Class _class){
        try {
            TableUtils.clearTable(getDBHelper().getConnectionSource(),_class);
        } catch (SQLException e) { e.printStackTrace(); }
    }

}
