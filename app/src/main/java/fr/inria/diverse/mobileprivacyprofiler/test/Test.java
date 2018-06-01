package fr.inria.diverse.mobileprivacyprofiler.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import fr.inria.diverse.mobileprivacyprofiler.datamodel.ApplicationHistory;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.ApplicationUsageStats;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.MobilePrivacyProfilerDBHelper;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.MobilePrivacyProfilerDB_metadata;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.OrmLiteDBHelper;


import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Test {

    private static volatile OrmLiteDBHelper dbHelper;

    // Initialisation de la Gestion des Log
    private static final String TAG = Test.class.getSimpleName();

    public static void mainTest(Context context) {
        Log.d(TAG,"-----> Let's go testing!  :  <-----");

        MobilePrivacyProfilerDB_metadata metadata = getDBHelper(context).getMobilePrivacyProfilerDBHelper().getDeviceDBMetadata();

        //creating an ApplicationHistory
        ApplicationHistory a = new ApplicationHistory();
        a.setAppName("appName (a)");
        a.setPackageName("packageName (a)");
        a.setUserMetaData(metadata);
        //adding it to the db
        getDBHelper(context).getApplicationHistoryDao().create(a);

        //querying it from db
        ApplicationHistory appHist = null;
        appHist = getDBHelper(context).getMobilePrivacyProfilerDBHelper().queryApplicationHistoryByAppName("appName (a)");
        Log.d(TAG,"appHistList query successful : "+(null!=appHist));

        if(null!=appHist) {
            //creating an ApplicationUsageHistory related to appHist
            ApplicationUsageStats appUseStatsInitial = new ApplicationUsageStats();
            appUseStatsInitial.setApplication(appHist);
            appUseStatsInitial.setFirstTimeStamp("firstTimeStamp");
            appUseStatsInitial.setLastTimeStamp("lastTimeStamp");
            appUseStatsInitial.setLastTimeUsed("lastTimeUsed");
            appUseStatsInitial.setRequestedInterval(0);
            appUseStatsInitial.setTotalTimeInForeground(111111);
            //adding it to the db
            getDBHelper(context).getApplicationUsageStatsDao().create(appUseStatsInitial);

            //querying it from db
            List<ApplicationUsageStats> appUseStatFromBase = null;
            appUseStatFromBase = getDBHelper(context).getMobilePrivacyProfilerDBHelper().queryApplicationUsageStatsByApplicationHistory(appHist);
            Log.d(TAG, "appUseStat query successful : " + (null != appUseStatFromBase) + "\n Returned : " + appUseStatFromBase.size() + " results");

            //translation of appUsageStats into Json
            ObjectMapper mapperAppStats = new ObjectMapper();
            String jsonAppStatsOutput = "";
            try {
                for (ApplicationUsageStats appUse : appUseStatFromBase) {
                    jsonAppStatsOutput = mapperAppStats.writeValueAsString(appUse);
                    Log.d(TAG, "=====>" + jsonAppStatsOutput + "<======");
                }
            } catch (JsonProcessingException e) { e.printStackTrace(); }


            // Convert JSON string to single Object
            String jsonInString = jsonAppStatsOutput;
            ApplicationUsageStats appUseStatsDeserilized = null;
            try {
                appUseStatsDeserilized = mapperAppStats.readValue(jsonInString, ApplicationUsageStats.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d(TAG,"Deserialized json: "+appUseStatsDeserilized.toString());

            String json="";
            try {
                 json = mapperAppStats.writeValueAsString(appUseStatsDeserilized);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            Log.d(TAG,json);


            //translation of appHist into Json
            ObjectMapper mapperAppHist = new ObjectMapper();
            String jsonAppHistOutput = "";
            try {
                jsonAppHistOutput = mapperAppHist.writeValueAsString(appHist);
                Log.d(TAG, "*****>" + jsonAppHistOutput + "<*****");
            } catch (JsonProcessingException e) { e.printStackTrace(); }

            //translation of appUsageStats into Json
            ObjectMapper mapperMetadata = new ObjectMapper();
            String jsonMetadata = "";
            try {
                jsonMetadata = mapperAppStats.writeValueAsString(metadata);
                Log.d(TAG, "~~~~~>" + jsonMetadata + "<~~~~~");
            } catch (JsonProcessingException e) { e.printStackTrace(); }


        }//end if appList!=null
        /*
        ApplicationHistory b = new ApplicationHistory();
        b.setAppName("appName : b");
        b.setPackageName("packageName : b");
        ApplicationHistory c = new ApplicationHistory();
        c.setAppName("appName : c");
        c.setPackageName("packageName : c");

        List<ApplicationHistory> appHistToExport = new ArrayList<ApplicationHistory>();
        appHistToExport.add(a);
        appHistToExport.add(b);
        appHistToExport.add(c);

        //translation of the collection into Json
        ObjectMapper mapper = new ObjectMapper();
        String jsonList ="";
        try {
            jsonList = mapper.writeValueAsString(appHistToExport);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        log.info(jsonList);


        // Convert JSON string to single Object
        String jsonInString = "{\"_id\":1,\"appName\":\"appName : a\",\"packageName\":\"packageName : a\",\"usageStats\":null,\"contextDB\":null}";
        ApplicationHistory appHist = null;
        try {
            appHist = mapper.readValue(jsonInString, ApplicationHistory.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info(appHist.toString());
        */
/*
        try {
            jsonList = mapper.writeValueAsString(appHist);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        log.info(jsonList);

        // Convert JSON string to a List of Object
        String jsonStringSample = "[{\"_id\":1,\"appName\":\"appName : a\",\"packageName\":\"packageName : a\",\"usageStats\":null,\"contextDB\":null},{\"_id\":2,\"appName\":\"appName : b\",\"packageName\":\"packageName : b\",\"usageStats\":null,\"contextDB\":null},{\"_id\":3,\"appName\":\"appName : c\",\"packageName\":\"packageName : c\",\"usageStats\":null,\"contextDB\":null}]";
        List<ApplicationHistory> appHistList = null;
        try {
            appHistList = mapper.readValue(jsonStringSample, mapper.getTypeFactory().constructCollectionType(List.class, ApplicationHistory.class));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            jsonList = mapper.writeValueAsString(appHistList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        log.info(jsonList);
*/


    }//end main
    private static OrmLiteDBHelper getDBHelper(Context context){
        if(dbHelper == null){
            dbHelper = OpenHelperManager.getHelper(context, OrmLiteDBHelper.class);
        }
        return dbHelper;
    }
}//end class
