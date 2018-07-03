/*  */
package fr.inria.diverse.mobileprivacyprofiler.activities;


import fr.inria.diverse.mobileprivacyprofiler.datamodel.OrmLiteDBHelper;
import fr.inria.diverse.mobileprivacyprofiler.R;
import fr.vojtisek.genandroid.genandroidlib.activities.OrmLiteActionBarActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


//Start of user code additional imports Home_CustomViewActivity
import fr.inria.diverse.mobileprivacyprofiler.BroadcastReceiver.WifiScanReceiver;
import fr.inria.diverse.mobileprivacyprofiler.utils.PhoneStateUtils;
import fr.inria.diverse.mobileprivacyprofiler.job.ExportDBJob;
import fr.inria.diverse.mobileprivacyprofiler.job.MobilePrivacyProfilerJobCreator;
import fr.inria.diverse.mobileprivacyprofiler.job.ScanAppUsageJob;
import fr.inria.diverse.mobileprivacyprofiler.job.ScanBatteryJob;
import fr.inria.diverse.mobileprivacyprofiler.job.ScanBluetoothJob;
import fr.inria.diverse.mobileprivacyprofiler.job.ScanCalendarJob;
import fr.inria.diverse.mobileprivacyprofiler.job.ScanCellJob;
import fr.inria.diverse.mobileprivacyprofiler.job.ScanContactJob;
import fr.inria.diverse.mobileprivacyprofiler.job.ScanGeolocationJob;
import fr.inria.diverse.mobileprivacyprofiler.job.ScanPhoneCallLogJob;
import fr.inria.diverse.mobileprivacyprofiler.job.ScanSMSJob;
import android.util.Log;
import android.Manifest;
import android.app.Activity;
import fr.inria.diverse.mobileprivacyprofiler.utils.PhoneStateUtils;
import fr.inria.diverse.mobileprivacyprofiler.rest.MobilePrivacyRestClient;
import fr.inria.diverse.mobileprivacyprofiler.services.OperationDBService;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.MobilePrivacyProfilerDB_metadata;
import fr.inria.diverse.mobileprivacyprofiler.utils.ParametersUtils;
import fr.inria.diverse.mobileprivacyprofiler.rest.MobilePrivacyRestClient;
import fr.inria.diverse.mobileprivacyprofiler.services.OperationDBService;

import android.app.AppOpsManager;
import android.content.Context;

import android.view.View;

import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.security.ProviderInstaller;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

import fr.inria.diverse.mobileprivacyprofiler.rest.MobilePrivacyRestClient;
import fr.inria.diverse.mobileprivacyprofiler.services.OperationDBService;

//End of user code
public class Home_CustomViewActivity extends OrmLiteActionBarActivity<OrmLiteDBHelper>
//Start of user code additional implements Home_CustomViewActivity
//End of user code
{
	
	//Start of user code constants Home_CustomViewActivity
    public static WifiScanReceiver wifiScanReceiver;
    private static final String TAG = Home_CustomViewActivity.class.getSimpleName();
    public static final String[] PERMISSIONS = { Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.BLUETOOTH,
                                                    Manifest.permission.GET_ACCOUNTS, Manifest.permission.INTERNET,
                                                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_CALENDAR,
                                                            Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_SMS,
                                                                Manifest.permission.READ_CONTACTS, Manifest.permission.READ_EXTERNAL_STORAGE,
                                                                    Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,
                                                                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_WIFI_STATE };
    private static final String native_lib = "native_lib";
    private static Context context;
	//End of user code

	//Start of user code Static initialization  Home_CustomViewActivity
        static {
            System.loadLibrary(native_lib);
        }
	//End of user code

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		//Start of user code onCreate Home_CustomViewActivity_1
		//ThemeUtil.onActivityCreateSetTheme(this);
		//End of user code		
        setContentView(R.layout.home_customview);
        //Start of user code onCreate Home_CustomViewActivity

        if (!PhoneStateUtils.hasPermission(this,PERMISSIONS)){
            PhoneStateUtils.requestPermissions(this,PERMISSIONS);
        }
        context = getApplicationContext();

        //The Android API docs correctly state that TLSv1.2 is only supported for SSLEngine in API Level 20 or later (Lollipop) while SSLSocket supports it since level 16.
        //If the user use a device whose api is older than 20, he won't be able to use SSLSocket
        updateAndroidSecurityProvider(this);


        JobManager.create(this).addJobCreator(new MobilePrivacyProfilerJobCreator());


        ToggleButton toggleCollection = (ToggleButton)findViewById(R.id.home_customview_toggle_collection);
        TextView app_state = (TextView)findViewById(R.id.home_customview_app_state);
        TextView screen_explanation = (TextView)findViewById(R.id.home_customview_screen_explanation);

        toggleCollection.setOnClickListener(view -> {
            if(((ToggleButton)view).isChecked()){
                scheduleAllJobs();
                app_state.setText(R.string.home_customview_app_state_active);
                screen_explanation.setText(R.string.home_customview_stop_collection);
            }else{
                cancelAllJobs();
                app_state.setText(R.string.home_customview_app_state_inactive);
                screen_explanation.setText(R.string.home_customview_run_collection);
            }
        });




/*
        this.wifiScanReceiver = new WifiScanReceiver();
        unregisterReceiver(wifiScanReceiver);
        registerReceiver(
                wifiScanReceiver,
                new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        );*/

		//End of user code
    }
    
    @Override
	protected void onResume() {
		super.onResume();
		refreshScreenData();
		//Start of user code onResume Home_CustomViewActivity
		//End of user code
	}
    //Start of user code additional code Home_CustomViewActivity

    public static Context getContext(){return context;}

	public void onClickBtnApplicationHistory(View view){
		showToast( this.getString(R.string.applicationhistorylist_classlistview_launch_toast));
        startActivity(new Intent(this, ApplicationHistoryList_ClassListViewActivity.class));
    }

    public void onClickBtnExportDB (View view) throws SQLException {
        showToast( this.getString(R.string.export_db_launch_toast));
        MobilePrivacyRestClient.getMobilePrivacyRestClient().exportDB(this);
    }

    public void onClickBtnResetDB (View view){
        showToast( this.getString(R.string.reset_db_launch_toast));
        OperationDBService.startActionResetDB(this);
    }

    private void debugText(StringBuilder sb) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
	    sb.append("Has usage access permission = "+ PhoneStateUtils.hasPermission(this,PERMISSIONS)+"\n");

        sb.append(" - - Running Job status: - -\n");
        Set<JobRequest> jobRequests = JobManager.instance().getAllJobRequests();
        for (JobRequest jr : jobRequests ) {
            sb.append("  "+jr.getTag()+"("+jr.getJobId()+") IntervalMs="+jr.getIntervalMs()+
                    ",  ScheduledAt="+ dateFormat.format(new Date(jr.getScheduledAt()))+"\n");
        }

        sb.append(" - - last bg task run - -\n");
        MobilePrivacyProfilerDB_metadata metadata = getHelper().getMobilePrivacyProfilerDBHelper().getDeviceDBMetadata();
        sb.append("Last transmission date: "+(metadata.getLastTransmissionDate()!=null ? dateFormat.format(metadata.getLastTransmissionDate()):"never")+"\n");
        sb.append("ScanInstalledApp: "+(metadata.getLastScanInstalledApplications()!=null ? dateFormat.format(metadata.getLastScanInstalledApplications()):"never")+"\n");
        sb.append("ScanAppUsage: "+(metadata.getLastScanAppUsage()!=null ? dateFormat.format(metadata.getLastScanAppUsage()):"never")+"\n");
        sb.append("ScanSMS: "+(metadata.getLastSmsScan()!=null ? dateFormat.format(metadata.getLastSmsScan()):"never")+"\n");
        sb.append("ScanCallLog: "+(metadata.getLastCallScan()!=null ? dateFormat.format(metadata.getLastCallScan()):"never")+"\n");
        sb.append("ContactScan: "+(metadata.getLastContactScan()!=null ? dateFormat.format(metadata.getLastContactScan()):"never")+"\n");


        sb.append(" - - - -\n");
        sb.append("Table "+getHelper().getApplicationHistoryDao().getDataClass().getSimpleName());
        sb.append(" count="+ getHelper().getApplicationHistoryDao().countOf()+"\n");

        sb.append("Table "+getHelper().getApplicationUsageStatsDao().getDataClass().getSimpleName());
        sb.append(" count="+ getHelper().getApplicationUsageStatsDao().countOf()+"\n");

        sb.append("Table "+getHelper().getBatteryUsageDao().getDataClass().getSimpleName());
        sb.append(" count="+ getHelper().getBatteryUsageDao().countOf()+"\n");

        sb.append("Table "+getHelper().getBluetoothDeviceDao().getDataClass().getSimpleName());
        sb.append(" count="+ getHelper().getBluetoothDeviceDao().countOf()+"\n");

        sb.append("Table "+getHelper().getBluetoothLogDao().getDataClass().getSimpleName());
        sb.append(" count="+ getHelper().getBluetoothLogDao().countOf()+"\n");

        sb.append("Table "+getHelper().getCalendarEventDao().getDataClass().getSimpleName());
        sb.append(" count="+ getHelper().getCalendarEventDao().countOf()+"\n");

        sb.append("Table "+getHelper().getContactDao().getDataClass().getSimpleName());
        sb.append(" count="+ getHelper().getContactDao().countOf()+"\n");

        sb.append("Table "+getHelper().getContactEmailDao().getDataClass().getSimpleName());
        sb.append(" count="+ getHelper().getContactEmailDao().countOf()+"\n");

        sb.append("Table "+getHelper().getContactPhoneNumberDao().getDataClass().getSimpleName());
        sb.append(" count="+ getHelper().getContactPhoneNumberDao().countOf()+"\n");

        sb.append("Table "+getHelper().getContactPhysicalAddressDao().getDataClass().getSimpleName());
        sb.append(" count="+ getHelper().getContactPhysicalAddressDao().countOf()+"\n");

        sb.append("Table "+getHelper().getLogsWifiDao().getDataClass().getSimpleName());
        sb.append(" count="+ getHelper().getLogsWifiDao().countOf()+"\n");

        /*
        sb.append("Table "+getHelper().getDetectedWifi_AccessPointDao().getDataClass().getSimpleName());
        sb.append(" count="+ getHelper().getDetectedWifi_AccessPointDao().countOf()+"\n");
        */

        sb.append("Table "+getHelper().getGeolocationDao().getDataClass().getSimpleName());
        sb.append(" count="+ getHelper().getGeolocationDao().countOf()+"\n");
/*
        sb.append("Table "+getHelper().getGSMCellDao().getDataClass().getSimpleName());
        sb.append(" count="+ getHelper().getGSMCellDao().countOf()+"\n");

        sb.append("Table "+getHelper().getIdentityDao().getDataClass().getSimpleName());
        sb.append(" count="+ getHelper().getIdentityDao().countOf()+"\n");
*/
        sb.append("Table "+getHelper().getKnownWifiDao().getDataClass().getSimpleName());
        sb.append(" count="+ getHelper().getKnownWifiDao().countOf()+"\n");

        sb.append("Table "+getHelper().getNeighboringCellHistoryDao().getDataClass().getSimpleName());
        sb.append(" count="+ getHelper().getNeighboringCellHistoryDao().countOf()+"\n");

        sb.append("Table "+getHelper().getPhoneCallLogDao().getDataClass().getSimpleName());
        sb.append(" count="+ getHelper().getPhoneCallLogDao().countOf()+"\n");

        sb.append("Table "+getHelper().getSMSDao().getDataClass().getSimpleName());
        sb.append(" count="+ getHelper().getSMSDao().countOf()+"\n");

        sb.append("Table "+getHelper().getNetActivityDao().getDataClass().getSimpleName());
        sb.append(" count="+ getHelper().getNetActivityDao().countOf()+"\n");
/*
        sb.append("Table "+getHelper().getWifiAccessPointDao().getDataClass().getSimpleName());
        sb.append(" count="+ getHelper().getWifiAccessPointDao().countOf()+"\n");
*/
    }

    /**
     * Install a newer security provider using Google Play Services to allow the application to use SSLSocket.
     * If the devise has an API lower than 20, by default, he won't be able to use SSLSocket.
     * https://stackoverflow.com/questions/29916962/javax-net-ssl-sslhandshakeexception-javax-net-ssl-sslprotocolexception-ssl-han/36892715#36892715
     */
    private void updateAndroidSecurityProvider(Activity callingActivity) {
        try {
            ProviderInstaller.installIfNeeded(this);
        } catch (GooglePlayServicesRepairableException e) {
            // Thrown when Google Play Services is not installed, up-to-date, or enabled
            // Show dialog to allow users to install, update, or otherwise enable Google Play services.
            GooglePlayServicesUtil.getErrorDialog(e.getConnectionStatusCode(), callingActivity, 0);
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.e("SecurityException", "Google Play Services not available.");
        }
    }

    /**
     * Schedule all jobs
     */
    public void scheduleAllJobs(){
        ScanAppUsageJob.schedule();
        ScanBatteryJob.schedule();
        ScanBluetoothJob.schedule();
        ScanCalendarJob.schedule();
        ScanCellJob.schedule();
        ScanContactJob.schedule();
        ScanGeolocationJob.schedule();
        ScanPhoneCallLogJob.schedule();
        ScanSMSJob.schedule();
        ExportDBJob.schedule();
    }

    /**
     * Cancel all jobs
     */
    public void cancelAllJobs(){
        ScanAppUsageJob.cancelRequest();
        ScanBatteryJob.cancelRequest();
        ScanBluetoothJob.cancelRequest();
        ScanCalendarJob.cancelRequest();
        ScanCellJob.cancelRequest();
        ScanContactJob.cancelRequest();
        ScanGeolocationJob.cancelRequest();
        ScanPhoneCallLogJob.cancelRequest();
        ScanSMSJob.cancelRequest();
        ExportDBJob.cancelRequest();
    }
    //End of user code

    /** refresh screen from data 
     */
    public void refreshScreenData() {
    	//Start of user code action when refreshing the screen Home_CustomViewActivity

      /*  ParametersUtils paramUtil = new ParametersUtils(this);
        if(paramUtil.getParamBoolean(R.string.pref_key_affichage_debug, false)){
            // debug is set to true we can show some stuff here
            StringBuilder sb = new StringBuilder();
            sb.append("- - Debug - -\n");
            debugText(sb);
            ((TextView) findViewById(R.id.home_debug_text)).setText(sb.toString());
        }*/
		//End of user code
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.home_customview_actions, menu);
		// add additional programmatic options in the menu
		//Start of user code additional onCreateOptionsMenu Home_CustomViewActivity

		//End of user code
        return super.onCreateOptionsMenu(menu);
    }
    
	// Dealing with Activity results
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//Start of user code onActivityResult Home_CustomViewActivity
        switch (requestCode){
            case PhoneStateUtils.MY_PERMISSIONS_REQUEST:
                if (!PhoneStateUtils.hasPermission(this,PERMISSIONS)){
                    PhoneStateUtils.requestPermissions(this,PERMISSIONS);
                }
                break;
        }
		//End of user code
	}
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// behavior of option menu
        switch (item.getItemId()) {
			case R.id.home_customview_action_preference:
	        	startActivity(new Intent(this, Preferences_PreferenceViewActivity.class));
	            return true;
			//Start of user code additional menu action Home_CustomViewActivity
            case R.id.home_customview_action_advancedscanning:
                startActivity(new Intent(this, AdvancedScanning_CustomViewActivity.class));
                return true;
            case R.id.home_customview_action_manualscan:
                startActivity(new Intent(this, ManualScan_CustomViewActivity.class));
                return true;
			//End of user code
			default:
                return super.onOptionsItemSelected(item);
        }
    }

}
