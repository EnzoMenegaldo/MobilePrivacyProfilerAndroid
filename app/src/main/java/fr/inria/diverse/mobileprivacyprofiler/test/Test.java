package fr.inria.diverse.mobileprivacyprofiler.test;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import fr.inria.diverse.mobileprivacyprofiler.datamodel.ApplicationHistory;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.ApplicationUsageStats;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.MobilePrivacyProfilerDBHelper;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.MobilePrivacyProfilerDB_metadata;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.OrmLiteDBHelper;


import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Test {

    private volatile OrmLiteDBHelper dbHelper;

    // Initialisation de la Gestion des Log
    private static final String TAG = Test.class.getSimpleName();

    public static void mainTest(Context context) {

        /*MobilePrivacyProfilerDB_metadata metadata = new MobilePrivacyProfilerDB_metadata();
        Date fake = new Date();
        metadata.setLastCallScan(fake);
        metadata.setLastContactScan(fake);
        metadata.setLastScanAppUsage(fake);
        metadata.setId(1);
        metadata.setLastScanInstalledApplications(fake);
        metadata.setLastSmsScan(fake);
        metadata.setLastTransmissionDate(fake);

        ApplicationHistory a = new ApplicationHistory();
        a.setId(1);
        a.setAppName("appName (a)");
        a.setPackageName("packageName (a)");
        a.setUserMetaData(metadata);

        ApplicationUsageStats appUseStat = new ApplicationUsageStats();
        appUseStat.setApplication(a);
        appUseStat.setFirstTimeStamp("firstTimeStamp");
        appUseStat.setLastTimeStamp("lastTimeStamp");
        appUseStat.setId(1);
        appUseStat.setLastTimeUsed("lastTimeUsed");
        appUseStat.setRequestedInterval(0);
        appUseStat.setTotalTimeInForeground(111111);

        List<ApplicationUsageStats>list = new ArrayList<>();
        list.add(appUseStat);
        a.setUsageStats(list);*/

        Log.d(TAG,"Let's go testing!!!!");
        /*ApplicationHistory b = new ApplicationHistory();
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
    private OrmLiteDBHelper getDBHelper(Context context){
        if(dbHelper == null){
            dbHelper = OpenHelperManager.getHelper(context, OrmLiteDBHelper.class);
        }
        return dbHelper;
    }
}//end class
