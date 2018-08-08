/*  */
package fr.inria.diverse.mobileprivacyprofiler.rest;

import fr.inria.diverse.mobileprivacyprofiler.activities.Home_CustomViewActivity;
import fr.inria.diverse.mobileprivacyprofiler.activities.Starting_CustomViewActivity;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.OrmLiteDBHelper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.net.InetAddress;
import java.net.UnknownHostException;
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
import fr.inria.diverse.mobileprivacyprofiler.datamodel.NetActivity;
import fr.inria.diverse.mobileprivacyprofiler.exception.NotConnectedToInternetException;
import fr.inria.diverse.mobileprivacyprofiler.utils.PhoneStateUtils;

// Start of user code additional import for MobilePrivacyRestClient
import android.os.Handler;
// End of user code

/** 
  *  
  */ 

public class MobilePrivacyRestClient {

	private static final String TAG = MobilePrivacyRestClient.class.getSimpleName();
	private volatile OrmLiteDBHelper dbHelper;
    
	static MobilePrivacyRestClient mobilePrivacyRestClient = null;
// Start of user code SetUp serverUrl here :
    private static final String serverUrl = "https://userprivacydataserver.diverse-team.fr";
// End of user code
	
	/**
     * Class constructor
     */
    public MobilePrivacyRestClient(){}
	
	/**
     * singelton getter
     * @return singelton mobilePrivacyRestClient
     */
    public static MobilePrivacyRestClient getMobilePrivacyRestClient(){
        if(null==mobilePrivacyRestClient){mobilePrivacyRestClient = new MobilePrivacyRestClient();return mobilePrivacyRestClient;}
        return mobilePrivacyRestClient;
	}

    /**
     * Send an authentication request to the server
     * @param username
     * @param password
     * @param device
     */
	public void authenticate(String username, String password, String device, Handler handler, Context context) throws NotConnectedToInternetException {
	    if(PhoneStateUtils.isConnectedToInternet(context))
            executePostRequest(serverUrl,"/Login","{\"username\":\""+username+"\",\"password\":\""+password+"\",\"device\":\""+device+"\"}",handler);
	    else
	        throw new NotConnectedToInternetException();
    }

	/**
     * Export the local DB to the server
     * @return
     */
    public void exportDB(Context context) throws SQLException, NotConnectedToInternetException {

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
		exportNetActivity(context);

	}//endexportDB


	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportMetadata(Context context) throws NotConnectedToInternetException {
        if(PhoneStateUtils.isConnectedToInternet(context)) {
            //query all entries
            List<MobilePrivacyProfilerDB_metadata> toExport = getDBHelper(context).getMobilePrivacyProfilerDB_metadataDao().queryForAll();

            if (null != toExport && !toExport.isEmpty()) {
                //translation of the collection into Json
                String postData = serialize(toExport);
                //execute the export to the server
                executePostRequest(this.serverUrl, "/Metadata", postData, null);
            }
        }
        else
            throw new NotConnectedToInternetException();
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportApplicationHistory(Context context) throws NotConnectedToInternetException {
        if(PhoneStateUtils.isConnectedToInternet(context)) {
            //query all entries
            List<ApplicationHistory> toExport = getDBHelper(context).getApplicationHistoryDao().queryForAll();
            if (null != toExport && !toExport.isEmpty()) {
                //translation of the collection into Json
                String postData = serialize(toExport);
                Log.d(TAG, postData);
                //execute the export to the server
                executePostRequest(this.serverUrl, "/ApplicationHistory", postData, null);
            }
        }else
            throw new NotConnectedToInternetException();
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportApplicationUsageStats(Context context) throws NotConnectedToInternetException {
        if(PhoneStateUtils.isConnectedToInternet(context)) {
            //query all entries
            List<ApplicationUsageStats> toExport = getDBHelper(context).getApplicationUsageStatsDao().queryForAll();
            if (null != toExport && !toExport.isEmpty()) {
                //translation of the collection into Json
                String postData = serialize(toExport);
                Log.d(TAG, postData);
                //execute the export to the server
                executePostRequest(this.serverUrl, "/ApplicationUsageStats", postData, null);
            }
        }else
            throw new NotConnectedToInternetException();
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportAuthentification(Context context) throws NotConnectedToInternetException {
        if (PhoneStateUtils.isConnectedToInternet(context)) {

            //query all entries
            List<Authentification> toExport = getDBHelper(context).getAuthentificationDao().queryForAll();

            if (null != toExport && !toExport.isEmpty()) {
                //translation of the collection into Json
                String postData = serialize(toExport);
                Log.d(TAG, postData);
                //execute the export to the server
                executePostRequest(this.serverUrl, "/Authentification", postData, null);
            }
        }else
            throw new NotConnectedToInternetException();
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportContact(Context context) throws NotConnectedToInternetException {
        if(PhoneStateUtils.isConnectedToInternet(context)) {
            //query all entries
            List<Contact> toExport = getDBHelper(context).getContactDao().queryForAll();
            if (null != toExport && !toExport.isEmpty()) {
                //translation of the collection into Json
                String postData = serialize(toExport);
                Log.d(TAG, postData);
                //execute the export to the server
                executePostRequest(this.serverUrl, "/Contact", postData, null);
            }
        }else
            throw new NotConnectedToInternetException();
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportContactOrganisation(Context context) throws NotConnectedToInternetException {
        if(PhoneStateUtils.isConnectedToInternet(context)) {
            //query all entries
            List<ContactOrganisation> toExport = getDBHelper(context).getContactOrganisationDao().queryForAll();
            if (null != toExport && !toExport.isEmpty()) {
                //translation of the collection into Json
                String postData = serialize(toExport);
                Log.d(TAG, postData);
                //execute the export to the server
                executePostRequest(this.serverUrl, "/ContactOrganisation", postData, null);
            }
        }else
            throw new NotConnectedToInternetException();
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportContactIM(Context context) throws NotConnectedToInternetException {
        if(PhoneStateUtils.isConnectedToInternet(context)) {
            //query all entries
            List<ContactIM> toExport = getDBHelper(context).getContactIMDao().queryForAll();
            if (null != toExport && !toExport.isEmpty()) {
                //translation of the collection into Json
                String postData = serialize(toExport);
                Log.d(TAG, postData);
                //execute the export to the server
                executePostRequest(this.serverUrl, "/ContactIM", postData, null);
            }
        }else
            throw new NotConnectedToInternetException();
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportContactEvent(Context context) throws NotConnectedToInternetException {
        if(PhoneStateUtils.isConnectedToInternet(context)) {
            //query all entries
            List<ContactEvent> toExport = getDBHelper(context).getContactEventDao().queryForAll();
            if (null != toExport && !toExport.isEmpty()) {
                //translation of the collection into Json
                String postData = serialize(toExport);
                Log.d(TAG, postData);
                //execute the export to the server
                executePostRequest(this.serverUrl, "/ContactEvent", postData, null);
            }
        }else
            throw new NotConnectedToInternetException();
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportContactPhoneNumber(Context context) throws NotConnectedToInternetException {
        if(PhoneStateUtils.isConnectedToInternet(context)) {
            //query all entries
            List<ContactPhoneNumber> toExport = getDBHelper(context).getContactPhoneNumberDao().queryForAll();
            if (null != toExport && !toExport.isEmpty()) {
                //translation of the collection into Json
                String postData = serialize(toExport);
                Log.d(TAG, postData);
                //execute the export to the server
                executePostRequest(this.serverUrl, "/ContactPhoneNumber", postData, null);
            }
        }else
            throw new NotConnectedToInternetException();
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportContactPhysicalAddress(Context context) throws NotConnectedToInternetException {
        if(PhoneStateUtils.isConnectedToInternet(context)) {
            //query all entries
            List<ContactPhysicalAddress> toExport = getDBHelper(context).getContactPhysicalAddressDao().queryForAll();
            if (null != toExport && !toExport.isEmpty()) {
                //translation of the collection into Json
                String postData = serialize(toExport);
                Log.d(TAG, postData);
                //execute the export to the server
                executePostRequest(this.serverUrl, "/ContactPhysicalAddress", postData, null);
            }
        }else
            throw new NotConnectedToInternetException();
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportContactEmail(Context context) throws NotConnectedToInternetException {
        if(PhoneStateUtils.isConnectedToInternet(context)) {
            //query all entries
            List<ContactEmail> toExport = getDBHelper(context).getContactEmailDao().queryForAll();
            if (null != toExport && !toExport.isEmpty()) {
                //translation of the collection into Json
                String postData = serialize(toExport);
                Log.d(TAG, postData);
                //execute the export to the server
                executePostRequest(this.serverUrl, "/ContactEmail", postData, null);
            }
        }else
            throw new NotConnectedToInternetException();
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportKnownWifi(Context context) throws NotConnectedToInternetException {
        if (PhoneStateUtils.isConnectedToInternet(context)) {
            //query all entries
            List<KnownWifi> toExport= getDBHelper(context).getKnownWifiDao().queryForAll();
            if(null != toExport && !toExport.isEmpty()) {
                //translation of the collection into Json
                String postData = serialize(toExport);
                Log.d(TAG,postData);
                //execute the export to the server
                executePostRequest(this.serverUrl,"/KnownWifi",postData, null);
            }
        }else
            throw new NotConnectedToInternetException();

    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportLogsWifi(Context context) throws NotConnectedToInternetException {
        if (PhoneStateUtils.isConnectedToInternet(context)) {
            //query all entries
            List<LogsWifi> toExport= getDBHelper(context).getLogsWifiDao().queryForAll();
            if(null != toExport && !toExport.isEmpty()) {
                //translation of the collection into Json
                String postData = serialize(toExport);
                Log.d(TAG,postData);
                //execute the export to the server
                executePostRequest(this.serverUrl,"/LogsWifi",postData, null);
            }
        }else
                throw new NotConnectedToInternetException();
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportGeolocation(Context context) throws NotConnectedToInternetException {
        if (PhoneStateUtils.isConnectedToInternet(context)) {
            //query all entries
            List<Geolocation> toExport= getDBHelper(context).getGeolocationDao().queryForAll();
            if(null != toExport && !toExport.isEmpty()) {
                //translation of the collection into Json
                String postData = serialize(toExport);
                Log.d(TAG,postData);
                //execute the export to the server
                executePostRequest(this.serverUrl,"/Geolocation",postData, null);
            }
        }else
            throw new NotConnectedToInternetException();
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportCalendarEvent(Context context) throws NotConnectedToInternetException {
        if (PhoneStateUtils.isConnectedToInternet(context)) {
            //query all entries
            List<CalendarEvent> toExport= getDBHelper(context).getCalendarEventDao().queryForAll();
            if(null != toExport && !toExport.isEmpty()) {
                //translation of the collection into Json
                String postData = serialize(toExport);
                Log.d(TAG,postData);
                //execute the export to the server
                executePostRequest(this.serverUrl,"/CalendarEvent",postData, null);
            }
        }else
            throw new NotConnectedToInternetException();
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportPhoneCallLog(Context context) throws NotConnectedToInternetException {
        if (PhoneStateUtils.isConnectedToInternet(context)) {
            //query all entries
            List<PhoneCallLog> toExport = getDBHelper(context).getPhoneCallLogDao().queryForAll();
            if (null != toExport && !toExport.isEmpty()) {
                //translation of the collection into Json
                String postData = serialize(toExport);
                Log.d(TAG, postData);
                //execute the export to the server
                executePostRequest(this.serverUrl, "/PhoneCallLog", postData, null);
            }
        }else
            throw new NotConnectedToInternetException();
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportCell(Context context) throws NotConnectedToInternetException {
        if(PhoneStateUtils.isConnectedToInternet(context)){
            //query all entries
            List<Cell> toExport= getDBHelper(context).getCellDao().queryForAll();
            if(null != toExport && !toExport.isEmpty()) {
                //translation of the collection into Json
                String postData = serialize(toExport);
                Log.d(TAG,postData);
                //execute the export to the server
                executePostRequest(this.serverUrl,"/Cell",postData, null);
            }
        }else
                throw new NotConnectedToInternetException();
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportOtherCellData(Context context) throws NotConnectedToInternetException {
        if(PhoneStateUtils.isConnectedToInternet(context)){
            //query all entries
            List<OtherCellData> toExport= getDBHelper(context).getOtherCellDataDao().queryForAll();
            if(null != toExport && !toExport.isEmpty()) {
                //translation of the collection into Json
                String postData = serialize(toExport);
                Log.d(TAG,postData);
                //execute the export to the server
                executePostRequest(this.serverUrl,"/OtherCellData",postData, null);
            }
        }else
                throw new NotConnectedToInternetException();
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportCdmaCellData(Context context) throws NotConnectedToInternetException {
        if(PhoneStateUtils.isConnectedToInternet(context)) {
            //query all entries
            List<CdmaCellData> toExport = getDBHelper(context).getCdmaCellDataDao().queryForAll();
            if (null != toExport && !toExport.isEmpty()) {
                //translation of the collection into Json
                String postData = serialize(toExport);
                Log.d(TAG, postData);
                //execute the export to the server
                executePostRequest(this.serverUrl, "/CdmaCellData", postData, null);
            }
        }else
            throw new NotConnectedToInternetException();
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportNeighboringCellHistory(Context context) throws NotConnectedToInternetException {
        if(PhoneStateUtils.isConnectedToInternet(context)) {
            //query all entries
            List<NeighboringCellHistory> toExport = getDBHelper(context).getNeighboringCellHistoryDao().queryForAll();
            if (null != toExport && !toExport.isEmpty()) {
                //translation of the collection into Json
                String postData = serialize(toExport);
                Log.d(TAG, postData);
                //execute the export to the server
                executePostRequest(this.serverUrl, "/NeighboringCellHistory", postData, null);
            }
        }else
            throw new NotConnectedToInternetException();
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportBluetoothDevice(Context context) throws NotConnectedToInternetException {
        if(PhoneStateUtils.isConnectedToInternet(context)) {
            //query all entries
            List<BluetoothDevice> toExport = getDBHelper(context).getBluetoothDeviceDao().queryForAll();
            if (null != toExport && !toExport.isEmpty()) {
                //translation of the collection into Json
                String postData = serialize(toExport);
                Log.d(TAG, postData);
                //execute the export to the server
                executePostRequest(this.serverUrl, "/BluetoothDevice", postData, null);
            }
        }else
            throw new NotConnectedToInternetException();
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportBluetoothLog(Context context) throws NotConnectedToInternetException {
        if(PhoneStateUtils.isConnectedToInternet(context)) {
            //query all entries
            List<BluetoothLog> toExport = getDBHelper(context).getBluetoothLogDao().queryForAll();
            if (null != toExport && !toExport.isEmpty()) {
                //translation of the collection into Json
                String postData = serialize(toExport);
                Log.d(TAG, postData);
                //execute the export to the server
                executePostRequest(this.serverUrl, "/BluetoothLog", postData, null);
            }
        }else
            throw new NotConnectedToInternetException();
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportSMS(Context context) throws NotConnectedToInternetException {
        if(PhoneStateUtils.isConnectedToInternet(context)) {
            //query all entries
            List<SMS> toExport = getDBHelper(context).getSMSDao().queryForAll();
            if (null != toExport && !toExport.isEmpty()) {
                //translation of the collection into Json
                String postData = serialize(toExport);
                Log.d(TAG, postData);
                //execute the export to the server
                executePostRequest(this.serverUrl, "/SMS", postData, null);
            }
        }else
            throw new NotConnectedToInternetException();
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportBatteryUsage(Context context) throws NotConnectedToInternetException {
        if(PhoneStateUtils.isConnectedToInternet(context)) {
            //query all entries
            List<BatteryUsage> toExport = getDBHelper(context).getBatteryUsageDao().queryForAll();
            if (null != toExport && !toExport.isEmpty()) {
                //translation of the collection into Json
                String postData = serialize(toExport);
                Log.d(TAG, postData);
                //execute the export to the server
                executePostRequest(this.serverUrl, "/BatteryUsage", postData, null);
            }
        }else
            throw new NotConnectedToInternetException();
    }

	/**
     * query all entries from a table to serialize it into a list and sending it to the server part
     * @param context
     * @throws SQLException
     */
    private void exportNetActivity(Context context) {
        //query all entries
        List<NetActivity> toExport= getDBHelper(context).getNetActivityDao().queryForAll();
        if(null != toExport && !toExport.isEmpty()) {
            //translation of the collection into Json
            String postData = serialize(toExport);
            Log.d(TAG,postData);
            //execute the export to the server
            executePostRequest(this.serverUrl,"/NetActivity",postData,null);
        }
    }

	/**
     * Execute the web request prepared in the export method
     * @param serverUrl
     * @param apiPath
     * @param postData
     */
    private void executePostRequest(String serverUrl,String apiPath,String postData, Handler handler){
        try {
            HttpPostAsyncTask task = new HttpPostAsyncTask(postData,handler);
            task.execute( serverUrl+ apiPath);
        } catch (Exception e) { e.printStackTrace(); }
    }

	/**
     * @param object
     * @return a json String containing a token field and a field from your object
     */
    private String serialize (Object object) {
        //translation of object into Json
        ObjectMapper mapper = new ObjectMapper();
        String jsonObjectOutput = "";
        try {
            jsonObjectOutput = mapper.writeValueAsString(object);
            jsonObjectOutput=jsonObjectOutput.replace("[{\"_id","[{\"android_id");
            jsonObjectOutput=jsonObjectOutput.replace(",{\"_id",",{\"android_id");
            Log.d(TAG, "serialized : "+jsonObjectOutput);

        } catch (JsonProcessingException e) {e.printStackTrace();}
        return addTokenToPostData(jsonObjectOutput);
    }
    
    private String addTokenToPostData(String data){
        String token = Starting_CustomViewActivity.getContext().getSharedPreferences(Starting_CustomViewActivity.Login_Information,Context.MODE_PRIVATE).getString(Starting_CustomViewActivity.SHARED_PREF_TOKEN_TAG,"");
        return "{\"token\":\""+token+"\",\"data\":"+data+"}";
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
