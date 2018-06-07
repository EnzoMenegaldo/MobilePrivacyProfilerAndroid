package fr.inria.diverse.mobileprivacyprofiler.rest;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.inria.diverse.mobileprivacyprofiler.datamodel.ApplicationHistory;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.OrmLiteDBHelper;

public class MobilePrivacyRestClient {

    static MobilePrivacyRestClient mobilePrivacyRestClient = null;

    private static final String TAG = MobilePrivacyRestClient.class.getSimpleName();
    private volatile OrmLiteDBHelper dbHelper;
    private String serverUrl = "http://131.254.18.198:4567";

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

        exportApplicationHistory(context);

        //TODO
        //other tables export

    }

    /**
     *
     */
    private void exportApplicationHistory(Context context) throws SQLException {//TODO
        //query all ApplicationHistory entries
        List<ApplicationHistory> appHistToExport= getDBHelper(context).getApplicationHistoryDao().queryForAll();
        //translation of the collection into Json
        String postData = serialize(appHistToExport);
        //execute the export to the server
        executePostRequest(this.serverUrl,"/ApplicationHistory",postData);


    }


    private void executePostRequest(String serverUrl,String apiPath,String postData){
        try {
            HttpPostAsyncTask task = new HttpPostAsyncTask(postData);
            task.execute( serverUrl+ apiPath);
        } catch (Exception e) { e.printStackTrace(); }
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

}


