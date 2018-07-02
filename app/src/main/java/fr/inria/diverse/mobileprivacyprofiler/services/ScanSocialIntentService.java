package fr.inria.diverse.mobileprivacyprofiler.services;

import android.Manifest;
import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.Date;

import fr.inria.diverse.mobileprivacyprofiler.activities.Home_CustomViewActivity;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.CalendarEvent;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.MobilePrivacyProfilerDBHelper;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.MobilePrivacyProfilerDB_metadata;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.OrmLiteDBHelper;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.PhoneCallLog;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.SMS;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ScanSocialIntentService extends IntentService {

    private static final String TAG = ScanSocialIntentService.class.getSimpleName();
    private volatile OrmLiteDBHelper dbHelper;

    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_SCAN_SMS = "fr.inria.diverse.mobileprivacyprofiler.services.action.SMS_SCAN";
    private static final String ACTION_SCAN_CALL_HISTORY = "fr.inria.diverse.mobileprivacyprofiler.services.action.ACTION_SCAN_CALL_HISTORY";
    private static final String ACTION_SCAN_CALENDAR_EVENT = "fr.inria.diverse.mobileprivacyprofiler.services.action.ACTION_SCAN_CALENDAR_EVENT";

    public ScanSocialIntentService() {
        super("ScanSocialIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static Void startActionScanSms() {
        Intent intent = new Intent(Home_CustomViewActivity.getContext(), ScanSocialIntentService.class);
        intent.setAction(ACTION_SCAN_SMS);
        Home_CustomViewActivity.getContext().startService(intent);
        return null;
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static Void startActionScanCallHistory() {
        Intent intent = new Intent(Home_CustomViewActivity.getContext(), ScanSocialIntentService.class);
        intent.setAction(ACTION_SCAN_CALL_HISTORY);
        Home_CustomViewActivity.getContext().startService(intent);
        return null;
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static Void startActionScanCalendarEvent() {
        Intent intent = new Intent(Home_CustomViewActivity.getContext(), ScanSocialIntentService.class);
        intent.setAction(ACTION_SCAN_CALENDAR_EVENT);
        Home_CustomViewActivity.getContext().startService(intent);
        return null;
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SCAN_SMS.equals(action)) {
                handleActionScanSms();
            } else if (ACTION_SCAN_CALL_HISTORY.equals(action)) {
                handleActionScanCallHistory(intent);
            } else if (ACTION_SCAN_CALENDAR_EVENT.equals(action)) {
                handleActionScanCalendarEvent(intent);
            }
        }
    }

    /**
     * Handle action ScanSms in the provided background thread with the provided
     * parameters.
     */

    private void handleActionScanSms() {
        //list sms sources
        String[] sources = {"content://sms/inbox", "content://sms/sent"};
        //get the last update if it exist(else expecting = null)
        MobilePrivacyProfilerDB_metadata metadata = MobilePrivacyProfilerDBHelper.getDBHelper(this).getMobilePrivacyProfilerDBHelper().getDeviceDBMetadata();
        Date lastScan = metadata.getLastSmsScan();

        String displayLastScan;
        if (null == lastScan) {
            displayLastScan = "none";
        } else {
            displayLastScan = lastScan.toString();
        }
        Log.d(TAG, "   Starting new SmsScan : time of last scan " + displayLastScan);

        //fetch sms from each sources
        for (String source : sources) {
            Cursor smsQueryOutput = null;
            Uri mSmsQueryUri = Uri.parse(source);
            String columns[] = new String[]{"person", "address", "body", "date", "status"/*,"body"*/};
            //if not 1st scan
            if (null != lastScan) {
                Log.d(TAG, "Fetching messages up to last scan");
                String lastScanString = "" + lastScan.getTime();
                String[] arguments = {lastScanString};
                smsQueryOutput = getContentResolver().query(mSmsQueryUri, columns, "date > ?", arguments, null);
            }
            //if 1st scan
            else {
                Log.d(TAG, "Fetching all messages");
                smsQueryOutput = getContentResolver().query(mSmsQueryUri, columns, null, null, null);
            }
            while (smsQueryOutput.moveToNext()) {
                String date = smsQueryOutput.getString(3);
                String phoneNumber = smsQueryOutput.getString(1);
                String type = "";
                if ("content://sms/inbox" == source) {
                    type = "inbox";
                }
                if ("content://sms/sent" == source) {
                    type = "sent";
                }
                SMS sms = new SMS(date, phoneNumber, type, MobilePrivacyProfilerDBHelper.getDeviceDBMetadata(this).getUserId());
                MobilePrivacyProfilerDBHelper.getDBHelper(this).getSMSDao().create(sms);
                Log.d(TAG, "date : " + date + ",phone number : " + phoneNumber + ",type :" + type/*+",body : "+smsQueryOutput.getString(5)*/);
            }
            smsQueryOutput.close();
        }
        Log.d(TAG, "Updating last SMS scan");
        metadata.setLastSmsScan(new Date());
        MobilePrivacyProfilerDBHelper.getDBHelper(this).getMobilePrivacyProfilerDB_metadataDao().update(metadata);
    }

    /**
     * Handle action ScanCallHistory in the provided background thread with the provided
     * parameters.
     */

    private void handleActionScanCallHistory(Intent intent) {
        //making a cursor out of the CallLog's data
        ContentResolver contentResolver = getContentResolver();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Cursor callLogCursor = contentResolver.query(android.provider.CallLog.Calls.CONTENT_URI, /*uri*/
                null,
                null,
                null,
                android.provider.CallLog.Calls.DEFAULT_SORT_ORDER /*sort by*/);

        //get the last update if it exist(else expecting = null)
        MobilePrivacyProfilerDB_metadata metadata = MobilePrivacyProfilerDBHelper.getDBHelper(this).getMobilePrivacyProfilerDBHelper().getDeviceDBMetadata();
        Date lastScan = metadata.getLastCallScan();

        String displayLastScan;
        if (null == lastScan) {
            displayLastScan = "none";
            lastScan = new Date(0);
        } else {
            displayLastScan = lastScan.toString();
        }
        Log.d(TAG, " Starting new Call History Scan : time of last scan " + displayLastScan);


        if (null != callLogCursor) {
            while (callLogCursor.moveToNext()) {//processing call log entries

                Date date = new Date(callLogCursor.getLong(callLogCursor.getColumnIndex(CallLog.Calls.DATE)));

                if (date.after(lastScan)) {//if the log has been registered since last scan

                    String phoneNumber = callLogCursor.getString(callLogCursor.getColumnIndex(CallLog.Calls.NUMBER));

                    long local = callLogCursor.getLong(callLogCursor.getColumnIndex(CallLog.Calls.DURATION));
                    long duration = new Float( local).longValue();

                    int callTypeCode = callLogCursor.getInt(callLogCursor.getColumnIndex(CallLog.Calls.TYPE));
                    String[] typeArray = {"INCOMING", "OUTGOING", "MISSED", "VOICEMAIL", "REJECTED", "BLOCKED", "ANSWERED_EXTERNALLY"};
                    String callType = typeArray[callTypeCode - 1];

                    PhoneCallLog callLog = new PhoneCallLog(phoneNumber, date, duration, callType,MobilePrivacyProfilerDBHelper.getDeviceDBMetadata(this).getUserId());
                    MobilePrivacyProfilerDBHelper.getDBHelper(this).getPhoneCallLogDao().create(callLog);
                    Log.d(TAG,"phoneNumber : "+phoneNumber+", date : "+date+", duration : "+duration+", callType : "+callType);
                }
            }
        }
        callLogCursor.close();

        Log.d(TAG, " Updating last Call History Scan");
        metadata.setLastCallScan(new Date());
        MobilePrivacyProfilerDBHelper.getDBHelper(this).getMobilePrivacyProfilerDB_metadataDao().update(metadata);

    }
    /**
     * Handle action ScanCalendarEvent in the provided background thread with the provided
     * parameters.
     */

    private void
    handleActionScanCalendarEvent(Intent intent) {
        // Projection array. Creating indices for this array
        String[] EVENT_PROJECTION = new String[]{
                CalendarContract.Events._ID,                        // 0
                CalendarContract.Events.ORGANIZER,                  // 1
                CalendarContract.Events.TITLE,                      // 2
                CalendarContract.Events.EVENT_LOCATION,             // 3
                CalendarContract.Events.DTSTART,                    // 4
                CalendarContract.Events.DTEND                       // 5
        };

        // The indices for the projection array above.
        int PROJECTION_ID_INDEX = 0;
        int PROJECTION_OrganizerMail_INDEX = 1;
        int PROJECTION_TITLE_INDEX = 2;
        int PROJECTION_EVENT_LOCATION_INDEX = 3;
        int PROJECTION_DTSTART_INDEX = 4;
        int PROJECTION_DTEND_INDEX = 5;

        Cursor queryEventOutput = null;

        ContentResolver cr = getContentResolver();
        Uri uri = CalendarContract.Events.CONTENT_URI;
        // Submit the query and get a Cursor object back.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        queryEventOutput = cr.query(uri, EVENT_PROJECTION, null, null, null);
        Log.d(TAG,"Number of event to record : "+queryEventOutput.getCount());
        while (queryEventOutput.moveToNext()) {
            long eventID = 0;
            String eventLabel = null;
            String place = null;
            String startDate = null;
            String endDate = null;

            // Get the field values
            eventID = queryEventOutput.getLong(PROJECTION_ID_INDEX);
            eventLabel = queryEventOutput.getString(PROJECTION_TITLE_INDEX);
            place = queryEventOutput.getString(PROJECTION_EVENT_LOCATION_INDEX);
            startDate = queryEventOutput.getString(PROJECTION_DTSTART_INDEX);
            endDate = queryEventOutput.getString(PROJECTION_DTEND_INDEX);

            // Preparing the gathering on participant's information
            String participants = "";

            String[] ATTENDEE_PROJECTION = new String[] {CalendarContract.Attendees.ATTENDEE_NAME};

            Cursor queryAttendeeOuput = null;

            uri = CalendarContract.Attendees.CONTENT_URI;
            String[] arg ={""+eventID};
            //queryAttendeeOuput = cr.query(uri,ATTENDEE_PROJECTION,CalendarContract.Attendees.android_id+" = ?",arg,null );
            final String query = "(" + CalendarContract.Attendees.EVENT_ID + " = ?)";
            final String[] args = new String[]{""+eventID};
            queryAttendeeOuput = cr.query(uri,ATTENDEE_PROJECTION,query,args,null );

            while (queryAttendeeOuput.moveToNext()){
                participants+= queryAttendeeOuput.getString(0);
            }
            //Stocking data
            CalendarEvent registredEvent = MobilePrivacyProfilerDBHelper.getDBHelper(this).getMobilePrivacyProfilerDBHelper().queryCalendarEvent(eventID);
            if(null!=registredEvent) {   // edit event
                registredEvent.setEventLabel(eventLabel);
                registredEvent.setStartDate(startDate);
                registredEvent.setEndDate(endDate);
                registredEvent.setPlace(place);
                registredEvent.setParticipants(participants);
                MobilePrivacyProfilerDBHelper.getDBHelper(this).getCalendarEventDao().update(registredEvent);
                Log.d(TAG,"Event edited : \n "+
                        "eventID : "+eventID+", label : "+eventLabel+", startDate : "+startDate+", endDate : "+endDate+", place : "+place+", participants : "+participants);

            }
            else {   // add new event
                CalendarEvent calendarEvent = new CalendarEvent();
                calendarEvent.setEventId(eventID);
                calendarEvent.setEventLabel(eventLabel);
                calendarEvent.setStartDate(startDate);
                calendarEvent.setEndDate(endDate);
                calendarEvent.setPlace(place);
                calendarEvent.setParticipants(participants);
                calendarEvent.setUserId(MobilePrivacyProfilerDBHelper.getDeviceDBMetadata(this).getUserId());
                MobilePrivacyProfilerDBHelper.getDBHelper(this).getCalendarEventDao().create(calendarEvent);
                Log.d(TAG,"New event : \n "+
                        "eventID : "+eventID+", label : "+eventLabel+", startDate : "+startDate+", endDate : "+endDate+", place : "+place+", participants : "+participants);
            }
        }
    }


}
