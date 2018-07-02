/*  */
package fr.inria.diverse.mobileprivacyprofiler.rest;

import fr.inria.diverse.mobileprivacyprofiler.datamodel.OrmLiteDBHelper;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import fr.inria.diverse.mobileprivacyprofiler.datamodel.MobilePrivacyProfilerDB_metadata;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.ApplicationHistory;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.ApplicationUsageStats;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.Authentification;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.Contact;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.ContactOrganisation;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.ContactIM;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.ContactEvent;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.ContactPhoneNumber;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.ContactPhysicalAddress;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.ContactEmail;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.KnownWifi;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.LogsWifi;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.Geolocation;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.CalendarEvent;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.PhoneCallLog;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.Cell;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.OtherCellData;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.CdmaCellData;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.NeighboringCellHistory;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.BluetoothDevice;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.BluetoothLog;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.SMS;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.BatteryUsage;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.WebHistory;

// Start of user code additional import for MobilePrivacyRestClient
// End of user code

/** 
  *  
  */ 

public class MobilePrivacyRestClient {

	private static final String TAG = MobilePrivacyRestClient.class.getSimpleName();
	private volatile OrmLiteDBHelper dbHelper;
    
	static MobilePrivacyRestClient mobilePrivacyRestClient = null;
// Start of user code SetUp serverUrl here :
    private String serverUrl = "http://131.254.18.198:4567";
// End of user code
	
	/**
     * Class constructor
     */
    MobilePrivacyRestClient(){}
	
	/**
     * singelton getter
     * @return singelton mobilePrivacyRestClient
     */
    public static MobilePrivacyRestClient getMobilePrivacyRestClient(){
        if(null==mobilePrivacyRestClient){mobilePrivacyRestClient = new MobilePrivacyRestClient();return mobilePrivacyRestClient;}
        return mobilePrivacyRestClient;
	}

	/**
     * Export the local DB to the server
     * @return
     */
    public void exportDB(Context context) throws SQLException {

        MobilePrivacyProfilerDB_metadata metadata = getDBHelper(context).getMobilePrivacyProfilerDBHelper().getDeviceDBMetadata();
        metadata.setLastTransmissionDate(new Date());
        getDBHelper(context).getMobilePrivacyProfilerDB_metadataDao().update(metadata);

					
		exportMetadata(context);

		exportApplicationHistory(context);
		exportApplicationUsageStats(context);
		exportAuthentification(context);
		exportContact(context);
		exportContactOrganisation(context);
		exportContactIM(context);
		exportContactEvent(context);
		exportContactPhoneNumber(context);
		exportContactPhysicalAddress(context);
		exportContactEmail(context);
		exportKnownWifi(context);
		exportLogsWifi(context);
		exportGeolocation(context);
		exportCalendarEvent(context);
		exportPhoneCallLog(context);
		exportCell(context);
		exportOtherCellData(context);
		exportCdmaCellData(context);
		exportNeighboringCellHistory(context);
		exportBluetoothDevice(context);
		exportBluetoothLog(context);
		exportSMS(context);
		exportBatteryUsage(context);
		exportWebHistory(context);

	}//endexportDB


	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportMetadata(Context context) {
        //query all entries
        List<MobilePrivacyProfilerDB_metadata> toExport= getDBHelper(context).getMobilePrivacyProfilerDB_metadataDao().queryForAll();

        if(null!=toExport && !toExport.isEmpty()) {
            //translation of the collection into Json
            String postData = serialize(toExport);
            //execute the export to the server
            executePostRequest(this.serverUrl, "/Metadata", postData);
        }
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportApplicationHistory(Context context) {
        //query all entries
        List<ApplicationHistory> toExport= getDBHelper(context).getApplicationHistoryDao().queryForAll();
        if(null != toExport && !toExport.isEmpty()) {
            //translation of the collection into Json
            String postData = serialize(toExport);
            Log.d(TAG,postData);
            //execute the export to the server
            executePostRequest(this.serverUrl,"/ApplicationHistory",postData);
        }
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportApplicationUsageStats(Context context) {
        //query all entries
        List<ApplicationUsageStats> toExport= getDBHelper(context).getApplicationUsageStatsDao().queryForAll();
        if(null != toExport && !toExport.isEmpty()) {
            //translation of the collection into Json
            String postData = serialize(toExport);
            Log.d(TAG,postData);
            //execute the export to the server
            executePostRequest(this.serverUrl,"/ApplicationUsageStats",postData);
        }
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportAuthentification(Context context) {
        //query all entries
        List<Authentification> toExport= getDBHelper(context).getAuthentificationDao().queryForAll();
        if(null != toExport && !toExport.isEmpty()) {
            //translation of the collection into Json
            String postData = serialize(toExport);
            Log.d(TAG,postData);
            //execute the export to the server
            executePostRequest(this.serverUrl,"/Authentification",postData);
        }
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportContact(Context context) {
        //query all entries
        List<Contact> toExport= getDBHelper(context).getContactDao().queryForAll();
        if(null != toExport && !toExport.isEmpty()) {
            //translation of the collection into Json
            String postData = serialize(toExport);
            Log.d(TAG,postData);
            //execute the export to the server
            executePostRequest(this.serverUrl,"/Contact",postData);
        }
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportContactOrganisation(Context context) {
        //query all entries
        List<ContactOrganisation> toExport= getDBHelper(context).getContactOrganisationDao().queryForAll();
        if(null != toExport && !toExport.isEmpty()) {
            //translation of the collection into Json
            String postData = serialize(toExport);
            Log.d(TAG,postData);
            //execute the export to the server
            executePostRequest(this.serverUrl,"/ContactOrganisation",postData);
        }
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportContactIM(Context context) {
        //query all entries
        List<ContactIM> toExport= getDBHelper(context).getContactIMDao().queryForAll();
        if(null != toExport && !toExport.isEmpty()) {
            //translation of the collection into Json
            String postData = serialize(toExport);
            Log.d(TAG,postData);
            //execute the export to the server
            executePostRequest(this.serverUrl,"/ContactIM",postData);
        }
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportContactEvent(Context context) {
        //query all entries
        List<ContactEvent> toExport= getDBHelper(context).getContactEventDao().queryForAll();
        if(null != toExport && !toExport.isEmpty()) {
            //translation of the collection into Json
            String postData = serialize(toExport);
            Log.d(TAG,postData);
            //execute the export to the server
            executePostRequest(this.serverUrl,"/ContactEvent",postData);
        }
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportContactPhoneNumber(Context context) {
        //query all entries
        List<ContactPhoneNumber> toExport= getDBHelper(context).getContactPhoneNumberDao().queryForAll();
        if(null != toExport && !toExport.isEmpty()) {
            //translation of the collection into Json
            String postData = serialize(toExport);
            Log.d(TAG,postData);
            //execute the export to the server
            executePostRequest(this.serverUrl,"/ContactPhoneNumber",postData);
        }
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportContactPhysicalAddress(Context context) {
        //query all entries
        List<ContactPhysicalAddress> toExport= getDBHelper(context).getContactPhysicalAddressDao().queryForAll();
        if(null != toExport && !toExport.isEmpty()) {
            //translation of the collection into Json
            String postData = serialize(toExport);
            Log.d(TAG,postData);
            //execute the export to the server
            executePostRequest(this.serverUrl,"/ContactPhysicalAddress",postData);
        }
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportContactEmail(Context context) {
        //query all entries
        List<ContactEmail> toExport= getDBHelper(context).getContactEmailDao().queryForAll();
        if(null != toExport && !toExport.isEmpty()) {
            //translation of the collection into Json
            String postData = serialize(toExport);
            Log.d(TAG,postData);
            //execute the export to the server
            executePostRequest(this.serverUrl,"/ContactEmail",postData);
        }
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportKnownWifi(Context context) {
        //query all entries
        List<KnownWifi> toExport= getDBHelper(context).getKnownWifiDao().queryForAll();
        if(null != toExport && !toExport.isEmpty()) {
            //translation of the collection into Json
            String postData = serialize(toExport);
            Log.d(TAG,postData);
            //execute the export to the server
            executePostRequest(this.serverUrl,"/KnownWifi",postData);
        }
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportLogsWifi(Context context) {
        //query all entries
        List<LogsWifi> toExport= getDBHelper(context).getLogsWifiDao().queryForAll();
        if(null != toExport && !toExport.isEmpty()) {
            //translation of the collection into Json
            String postData = serialize(toExport);
            Log.d(TAG,postData);
            //execute the export to the server
            executePostRequest(this.serverUrl,"/LogsWifi",postData);
        }
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportGeolocation(Context context) {
        //query all entries
        List<Geolocation> toExport= getDBHelper(context).getGeolocationDao().queryForAll();
        if(null != toExport && !toExport.isEmpty()) {
            //translation of the collection into Json
            String postData = serialize(toExport);
            Log.d(TAG,postData);
            //execute the export to the server
            executePostRequest(this.serverUrl,"/Geolocation",postData);
        }
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportCalendarEvent(Context context) {
        //query all entries
        List<CalendarEvent> toExport= getDBHelper(context).getCalendarEventDao().queryForAll();
        if(null != toExport && !toExport.isEmpty()) {
            //translation of the collection into Json
            String postData = serialize(toExport);
            Log.d(TAG,postData);
            //execute the export to the server
            executePostRequest(this.serverUrl,"/CalendarEvent",postData);
        }
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportPhoneCallLog(Context context) {
        //query all entries
        List<PhoneCallLog> toExport= getDBHelper(context).getPhoneCallLogDao().queryForAll();
        if(null != toExport && !toExport.isEmpty()) {
            //translation of the collection into Json
            String postData = serialize(toExport);
            Log.d(TAG,postData);
            //execute the export to the server
            executePostRequest(this.serverUrl,"/PhoneCallLog",postData);
        }
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportCell(Context context) {
        //query all entries
        List<Cell> toExport= getDBHelper(context).getCellDao().queryForAll();
        if(null != toExport && !toExport.isEmpty()) {
            //translation of the collection into Json
            String postData = serialize(toExport);
            Log.d(TAG,postData);
            //execute the export to the server
            executePostRequest(this.serverUrl,"/Cell",postData);
        }
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportOtherCellData(Context context) {
        //query all entries
        List<OtherCellData> toExport= getDBHelper(context).getOtherCellDataDao().queryForAll();
        if(null != toExport && !toExport.isEmpty()) {
            //translation of the collection into Json
            String postData = serialize(toExport);
            Log.d(TAG,postData);
            //execute the export to the server
            executePostRequest(this.serverUrl,"/OtherCellData",postData);
        }
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportCdmaCellData(Context context) {
        //query all entries
        List<CdmaCellData> toExport= getDBHelper(context).getCdmaCellDataDao().queryForAll();
        if(null != toExport && !toExport.isEmpty()) {
            //translation of the collection into Json
            String postData = serialize(toExport);
            Log.d(TAG,postData);
            //execute the export to the server
            executePostRequest(this.serverUrl,"/CdmaCellData",postData);
        }
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportNeighboringCellHistory(Context context) {
        //query all entries
        List<NeighboringCellHistory> toExport= getDBHelper(context).getNeighboringCellHistoryDao().queryForAll();
        if(null != toExport && !toExport.isEmpty()) {
            //translation of the collection into Json
            String postData = serialize(toExport);
            Log.d(TAG,postData);
            //execute the export to the server
            executePostRequest(this.serverUrl,"/NeighboringCellHistory",postData);
        }
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportBluetoothDevice(Context context) {
        //query all entries
        List<BluetoothDevice> toExport= getDBHelper(context).getBluetoothDeviceDao().queryForAll();
        if(null != toExport && !toExport.isEmpty()) {
            //translation of the collection into Json
            String postData = serialize(toExport);
            Log.d(TAG,postData);
            //execute the export to the server
            executePostRequest(this.serverUrl,"/BluetoothDevice",postData);
        }
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportBluetoothLog(Context context) {
        //query all entries
        List<BluetoothLog> toExport= getDBHelper(context).getBluetoothLogDao().queryForAll();
        if(null != toExport && !toExport.isEmpty()) {
            //translation of the collection into Json
            String postData = serialize(toExport);
            Log.d(TAG,postData);
            //execute the export to the server
            executePostRequest(this.serverUrl,"/BluetoothLog",postData);
        }
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportSMS(Context context) {
        //query all entries
        List<SMS> toExport= getDBHelper(context).getSMSDao().queryForAll();
        if(null != toExport && !toExport.isEmpty()) {
            //translation of the collection into Json
            String postData = serialize(toExport);
            Log.d(TAG,postData);
            //execute the export to the server
            executePostRequest(this.serverUrl,"/SMS",postData);
        }
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportBatteryUsage(Context context) {
        //query all entries
        List<BatteryUsage> toExport= getDBHelper(context).getBatteryUsageDao().queryForAll();
        if(null != toExport && !toExport.isEmpty()) {
            //translation of the collection into Json
            String postData = serialize(toExport);
            Log.d(TAG,postData);
            //execute the export to the server
            executePostRequest(this.serverUrl,"/BatteryUsage",postData);
        }
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportWebHistory(Context context) {
        //query all entries
        List<WebHistory> toExport= getDBHelper(context).getWebHistoryDao().queryForAll();
        if(null != toExport && !toExport.isEmpty()) {
            //translation of the collection into Json
            String postData = serialize(toExport);
            Log.d(TAG,postData);
            //execute the export to the server
            executePostRequest(this.serverUrl,"/WebHistory",postData);
        }
    }

	/**
     * Execute the web request prepared in the export method
     * @param serverUrl
     * @param apiPath
     * @param postData
     */
    private void executePostRequest(String serverUrl,String apiPath,String postData){
        try {
            HttpPostAsyncTask task = new HttpPostAsyncTask(postData);
            task.execute( serverUrl+ apiPath);
        } catch (Exception e) { e.printStackTrace(); }
    }

	/**
     * @param object
     * @return a json String from your object
     */
    private String serialize (Object object) {
        //translation of object into Json
        ObjectMapper mapper = new ObjectMapper();
        String jsonObjectOutput = "";
        try {
            jsonObjectOutput = mapper.writeValueAsString(object);
            Log.d(TAG, "serialized : "+object.getClass().getSimpleName());

        } catch (JsonProcessingException e) {e.printStackTrace();}
        return jsonObjectOutput;
    }

    /**
     *
     * @param context
     * @return the dbHelper to query the DB
     */
    public OrmLiteDBHelper getDBHelper(Context context){
        if(dbHelper == null){
            dbHelper = OpenHelperManager.getHelper(context, OrmLiteDBHelper.class);
        }
        return dbHelper;
    }

}//end class
