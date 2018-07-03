package fr.inria.diverse.mobileprivacyprofiler.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
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
import fr.inria.diverse.mobileprivacyprofiler.datamodel.Geolocation;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.KnownWifi;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.LogsWifi;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.MobilePrivacyProfilerDBHelper;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.MobilePrivacyProfilerDB_metadata;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.NeighboringCellHistory;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.OrmLiteDBHelper;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.PhoneCallLog;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.SMS;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.NetActivity;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 *
 * helper methods.
 */
public class OperationDBService extends IntentService {


    private static final String TAG = OperationDBService.class.getSimpleName();
    private volatile OrmLiteDBHelper dbHelper;


    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_RESET_DB = "fr.inria.diverse.mobileprivacyprofiler.services.action.RESET_DB";

    public OperationDBService() {
        super("OperationDBService");
    }

    /**
     * Starts this service to perform action ResetDB . If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionResetDB(Context context) {
        Intent intent = new Intent(context, OperationDBService.class);
        intent.setAction(ACTION_RESET_DB);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_RESET_DB.equals(action)) {
                handleActionResetDB();
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
        list.add(LogsWifi.class);
        list.add(Geolocation.class);
        list.add(KnownWifi.class);
        list.add(MobilePrivacyProfilerDB_metadata.class);
        list.add(NeighboringCellHistory.class);
        list.add(PhoneCallLog.class);
        list.add(SMS.class);
        list.add(NetActivity.class);

        for(Class _class : list){
            resetTable(_class);
        }
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
