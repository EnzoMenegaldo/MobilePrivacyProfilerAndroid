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


import android.preference.PreferenceManager;
//Start of user code additional imports Home_CustomViewActivity
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

import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;

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
    private static final String TAG = Home_CustomViewActivity.class.getSimpleName();
    private static final int MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS = 100;
    private static final String native_lib = "native_lib";
    private static Context context;
	//End of user code

    //Start of user code Static Initialization
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
			PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        setContentView(R.layout.home_customview);
        //Start of user code onCreate Home_CustomViewActivity

        // Display Debug info
        ParametersUtils paramUtil = new ParametersUtils(this);
        if (paramUtil.getParamBoolean(R.string.pref_key_affichage_debug, false)){
            ((ScrollView) findViewById(R.id.home_debug)).setVisibility(View.VISIBLE);
        }
        if (!hasPermission()){
            requestPermission();
        }
        context = getApplicationContext();
        JobManager.create(this.getApplicationContext());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS:
               if (!hasPermission()){
                    requestPermission();
                }
                break;
        }
    }

    private void requestPermission() {
        Toast.makeText(this, "Please grant App usage stat permission to Mobiel Privacy Profiler", Toast.LENGTH_SHORT).show();
        startActivityForResult(new Intent(android.provider.Settings.ACTION_USAGE_ACCESS_SETTINGS), MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS);
    }

    private boolean hasPermission() {

       /* AppOpsManager appOps = (AppOpsManager)
                getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(), getPackageName());
        return mode == AppOpsManager.MODE_ALLOWED;*/
       return true  ;

//        return ContextCompat.checkSelfPermission(this,
//                Manifest.permission.PACKAGE_USAGE_STATS) == PackageManager.PERMISSION_GRANTED;*/
    }

    private void debugText(StringBuilder sb) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
	    sb.append("Has usage access permission = "+hasPermission()+"\n");

        sb.append(" - - Running Job status: - -\n");
        Set<JobRequest> jobRequests = JobManager.instance().getAllJobRequests();
        for (JobRequest jr : jobRequests ) {
            sb.append("  "+jr.getTag()+"("+jr.getJobId()+") IntervalMs="+jr.getIntervalMs()+
                    ",  ScheduledAt="+ dateFormat.format(new Date(jr.getScheduledAt()))+"\n");
        }

        sb.append(" - - last bg task run - -\n");
        MobilePrivacyProfilerDB_metadata metadata = getHelper().getMobilePrivacyProfilerDBHelper().getDeviceDBMetadata();
        sb.append("ScanInstalledApp: "+(metadata.getLastScanInstalledApplications()!=null ? dateFormat.format(metadata.getLastScanInstalledApplications()):"never")+"\n");
        sb.append("ScanAppUsage: "+(metadata.getLastScanAppUsage()!=null ? dateFormat.format(metadata.getLastScanAppUsage()):"never")+"\n");

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
	//End of user code

    /** refresh screen from data 
     */
    public void refreshScreenData() {
    	//Start of user code action when refreshing the screen Home_CustomViewActivity

        ParametersUtils paramUtil = new ParametersUtils(this);
        if(paramUtil.getParamBoolean(R.string.pref_key_affichage_debug, false)){
            // debug is set to true we can show some stuff here
            StringBuilder sb = new StringBuilder();
            sb.append("- - Debug - -\n");
            debugText(sb);
            ((TextView) findViewById(R.id.home_debug_text)).setText(sb.toString());
        }
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
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// behavior of option menu
        switch (item.getItemId()) {
			case R.id.home_customview_action_preference:
	        	startActivity(new Intent(this, Preferences_PreferenceViewActivity.class));
	            return true;
			//Start of user code additional menu action Home_CustomViewActivity
            case R.id.home_customview_action_manualscan:
                startActivity(new Intent(this, ManualScan_CustomViewActivity.class));
                return true;
			//End of user code
			default:
                return super.onOptionsItemSelected(item);
        }
    }

}
