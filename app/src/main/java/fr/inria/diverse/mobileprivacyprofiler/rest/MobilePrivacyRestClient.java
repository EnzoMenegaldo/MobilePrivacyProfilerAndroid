package fr.inria.diverse.mobileprivacyprofiler.rest;

import android.content.Context;
import android.util.Log;

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
    private String serverUrl = "server/app/URL";

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
     * @return true if no problems occurred during operation
     */
    public boolean exportDB(Context context) throws SQLException {
        boolean noExecProblem = true;
        boolean tempResponse = false;

        tempResponse=exportApplicationHistory(context);
        noExecProblem=noExecProblemChecker(noExecProblem,tempResponse);

        //TODO
        //other tables export

        if(noExecProblem){ Log.d(TAG, "exportDB: export ended without problem");}
        else{Log.d(TAG, "exportDB: export encountered a problem");}
        return noExecProblem;
    }

    /**
     *
     * @return true if the request answer is conform
     */
    private boolean exportApplicationHistory(Context context) throws SQLException {//TODO
        //query all ApplicationHistory entries
        List<ApplicationHistory> appHistToExport= getDBHelper(context).getMobilePrivacyProfilerDBHelper().getAllApplicationHistory();
        //translation of the collection into Json
        ObjectMapper mapper = new ObjectMapper();
        String jsonList = null;
        try {
            jsonList = mapper.writeValueAsString(appHistToExport);
        } catch (Exception jsonProcessingException) {
            jsonProcessingException.printStackTrace();
        }

        try {
            //Map<String, List<ApplicationHistory>> postData = new HashMap<>();
            //postData.put("appHistList", appHistToExport);
            HttpPostAsyncTask task = new HttpPostAsyncTask(jsonList);
            task.execute(serverUrl + "/ApplicationHistory");
        } catch (Exception e) {
            e.printStackTrace();return false;
        }

        return  true;
    }



    /**
     * get de general token and the last operation token to update the general token
     * @param general
     * @param income
     * @return true if general&income
     */
    private boolean noExecProblemChecker(boolean general,boolean income){
        return general&&income;
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

}


