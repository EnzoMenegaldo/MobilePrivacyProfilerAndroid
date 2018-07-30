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
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

    private volatile OrmLiteDBHelper dbHelper;

    // Initialisation de la Gestion des Log
    private final String TAG = Test.class.getSimpleName();

    public Test(){}

    
    public void mainTest(Context context) {
        Log.d(TAG,"-----> Let's go testing!  :  <-----");

        String parseDummy = "[{Object A{,}}, {[{,}],ObjectB et b}, { hébhéObj@ctC}, {ObjectD}, {ObjectE}]";
        List<String> parseOutput = parser(parseDummy);
        Log.d(TAG,"+++++++++\n"+parseOutput.toString()+" same as input? : "+parseDummy.equals(parseOutput.toString()) +"\n"
                +parseOutput.get(0)+ "\n"
                +parseOutput.get(1)+"\n"
                +parseOutput.get(2)+"\n"
                +parseOutput.get(3)+"\n"
                +parseOutput.get(4)+"\n");

        MobilePrivacyProfilerDB_metadata metadata = getDeviceDBMetadata(context);

        //creating an ApplicationHistory
        ApplicationHistory a = new ApplicationHistory();
        a.setAppName("appName (a)");
        a.setPackageName("packageName (a)");
        a.setUserId(metadata.getUserId());
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
            /*appUseStatsInitial.setFirstTimeStamp("firstTimeStamp");
            appUseStatsInitial.setLastTimeStamp("lastTimeStamp");
            appUseStatsInitial.setLastTimeUsed("lastTimeUsed");*/
            appUseStatsInitial.setRequestedInterval(0);
            appUseStatsInitial.setTotalTimeInForeground(111111);
            appUseStatsInitial.setUserId(metadata.getUserId());
            //adding it to the db
            getDBHelper(context).getApplicationUsageStatsDao().create(appUseStatsInitial);

            //querying it from db
            List<ApplicationUsageStats> appUseStatFromBase = null;
            appUseStatFromBase = getDBHelper(context).getMobilePrivacyProfilerDBHelper().queryApplicationUsageStatsByApplicationHistory(appHist);
            Log.d(TAG, "appUseStat query successful : " + (null != appUseStatFromBase)
                        + "\n Returned : " + appUseStatFromBase.size() + " results");

            /**
             *Testing objects :
             * List<ApplicationUsageStats>      appUseStatFromBase
             * ApplicationHistory               appHist
             * MobilePrivacyProfilerDB_metadata metadata
             **/


            /*******************************************************************************
            *Convert AppUseStats to Json deserialize then reserialize before comparing Json*
            ********************************************************************************
            */

            String jsonFunctionOutPut = serialize("=",appUseStatFromBase.get(0));
            ApplicationUsageStats appUseOutput =(ApplicationUsageStats) deserialize(jsonFunctionOutPut,ApplicationUsageStats.class);
            Log.d(TAG,"Deserialized json: "+appUseOutput.toString());
            String jsonFunctionSecondOutput = serialize("=",appUseOutput);
            Log.d(TAG,"\n"
                    +"Json is stable : "+jsonFunctionOutPut.equals(jsonFunctionSecondOutput)
                    +"\nObjects are equals : "+appUseStatFromBase.get(0).equals(appUseOutput));

            /***************************************************************************************
             *Convert ApplicationHistory to Json deserialize then reserialize before comparing Json*
             ***************************************************************************************
             */

            String jsonOutPutFunction = serialize("*",appHist);
            ApplicationHistory appHistOutput =(ApplicationHistory) deserialize(jsonOutPutFunction,ApplicationHistory.class);
            Log.d(TAG,"Deserialized json: "+appHistOutput.toString());
            String jsonSecondOutputFunction = serialize("*",appHistOutput);
            Log.d(TAG,"\n"
                    +"Json is stable : "+jsonOutPutFunction.equals(jsonSecondOutputFunction)
                    +"\nObjects are equals : "+appHist.equals(appHistOutput));
        }//end if appList!=null

            /*****************************************************************************
             *Convert metadata to Json deserialize then reserialize before comparing Json*
             *****************************************************************************
             */

            String jsonMetadataOutPut = serialize("~",metadata);
            MobilePrivacyProfilerDB_metadata metadataOutput =(MobilePrivacyProfilerDB_metadata) deserialize(jsonMetadataOutPut,MobilePrivacyProfilerDB_metadata.class);
            Log.d(TAG,"Deserialized json: "+metadata.toString());
            String jsonSecondMetadataOutput = serialize("~",metadataOutput);
            Log.d(TAG,"\n"
                    +"Json is stable : "+jsonMetadataOutPut.equals(jsonSecondMetadataOutput)
                    +"\nObjects are equals : "+metadata.equals(metadataOutput));


        /*********************************************************
         *Test of de/serialisation of an List<ApplicationHistory>*
         *********************************************************
         */
        //querying it from db
        List<ApplicationHistory> appHistList = null;
        appHistList = getDBHelper(context).getApplicationHistoryDao().queryForAll();
        Log.d(TAG,"Number of ApplicationHistory to serialize : "+appHistList.size());

        //serialize the list
        String outPut = serialize("-",appHistList);
        //deserialize the list
        List<String> AppHistJsonList = parser(outPut);
        List<ApplicationHistory> appHistListOutPut = new ArrayList<>();
        for(String json : AppHistJsonList) {
            ApplicationHistory app =(ApplicationHistory) deserialize(json, ApplicationHistory.class);
            appHistListOutPut.add(app);
        }

        for(ApplicationHistory app :appHistListOutPut) {
            Log.d(TAG, app.toString()+"");
        }
        Log.d(TAG, ""+appHistListOutPut.size());
            /*
            //translation of appUsageStats into Json
            ObjectMapper mapperMetadata = new ObjectMapper();
            String jsonMetadata = "";
            try {
                jsonMetadata = mapperAppStats.writeValueAsString(metadata);
                Log.d(TAG, "~~~~~>" + jsonMetadata + "<~~~~~");
            } catch (JsonProcessingException e) { e.printStackTrace(); }
            */


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
        String jsonInString = "{\"android_id\":1,\"appName\":\"appName : a\",\"packageName\":\"packageName : a\",\"usageStats\":null,\"contextDB\":null}";
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
        String jsonStringSample = "[{\"android_id\":1,\"appName\":\"appName : a\",\"packageName\":\"packageName : a\",\"usageStats\":null,\"contextDB\":null},{\"android_id\":2,\"appName\":\"appName : b\",\"packageName\":\"packageName : b\",\"usageStats\":null,\"contextDB\":null},{\"android_id\":3,\"appName\":\"appName : c\",\"packageName\":\"packageName : c\",\"usageStats\":null,\"contextDB\":null}]";
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

    /**
     * @param aroS (parameter used for logCat tag
     * @param object
     * @return a json String from your object
     */
    private String serialize (String aroS,Object object) {
        //translation of object into Json
        ObjectMapper mapper = new ObjectMapper();
        String jsonObjectOutput = "";
        try {
            jsonObjectOutput = mapper.writeValueAsString(object);
            Log.d(TAG, ". \n"+aroS + aroS + aroS + aroS + ">" + jsonObjectOutput + "<" + aroS + aroS + aroS + aroS);

        } catch (JsonProcessingException e) {e.printStackTrace();}
    return jsonObjectOutput;
    }

    /**
     * Use Jackson to deserialize a single object of deserialisationClass Type
     * @param jsonArg
     * @param deserialisationClass
     * @return a single Object you need to cast to deserialisationClass Type
     */

    private Object deserialize (String jsonArg,Class deserialisationClass) {
        // Convert JSON string to single Object
        ObjectMapper mapper = new ObjectMapper();
        Object deserializedObject = null;
        try {
            deserializedObject = mapper.readValue(jsonArg, deserialisationClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Deserialized json: " + deserializedObject.toString());

    return deserializedObject;
    }

    // unsupported operation from jackson-databind
   /* private List deserializeList (String jsonArg,Class deserialisationClass) {
        // Convert JSON string to single Object
        ObjectMapper mapper = new ObjectMapper();
        ArrayList list = new ArrayList();
        try {
            list = mapper.readValue(jsonArg, mapper.getTypeFactory().constructCollectionType(List.class, deserialisationClass));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Deserialized json: " + list.toString());

        return list;
    }*/

    /**
     * take in a single json String to parse it into a List<String>
     * @param json
     * @return a List<String>
     */
    public List<String> parser(String json){

        int length;
        int parseCounter;
        int hookCount=0;
        int parenthesisCount=0;

        length = json.length();
        List<String> result = new ArrayList<>();

        StringBuffer stringBuffer = new StringBuffer();
        for(parseCounter = 0;parseCounter<length;parseCounter++) {
            char testedCar = json.charAt(parseCounter);

            switch(testedCar) {
                case '['    :
                    if(0!=hookCount){
                        stringBuffer.append(testedCar);
                    }
                    hookCount++;
                break;

                case '{'    :
                    parenthesisCount++;
                    stringBuffer.append(testedCar);
                break;

                case '}'    :
                    parenthesisCount--;
                    stringBuffer.append(testedCar);
                break;

                case ']'    :
                    hookCount--;
                    if(0==hookCount){
                        result.add(stringBuffer.toString());
                        stringBuffer= new StringBuffer();
                    }
                    else{
                        stringBuffer.append(testedCar);
                    }
                break;

                case ','    :
                    if(0==parenthesisCount){
                        result.add(stringBuffer.toString());
                        stringBuffer= new StringBuffer();
                    }else{ stringBuffer.append(testedCar);}
                break;

                case ' '    :
                    if(0!=parenthesisCount){
                        stringBuffer.append(testedCar);
                    }
                break;

                default     :       stringBuffer.append(testedCar);
            }//end switch

        }//end for

        return result;
    }

    private OrmLiteDBHelper getDBHelper(Context context){
        if(dbHelper == null){
            dbHelper = OpenHelperManager.getHelper(context, OrmLiteDBHelper.class);
        }
        return dbHelper;
    }

    private MobilePrivacyProfilerDB_metadata getDeviceDBMetadata(Context context){
        return getDBHelper(context).getMobilePrivacyProfilerDBHelper().getDeviceDBMetadata();}

}//end class
