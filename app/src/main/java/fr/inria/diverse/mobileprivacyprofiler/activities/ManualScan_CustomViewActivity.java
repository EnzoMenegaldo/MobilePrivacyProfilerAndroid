/*  */
package fr.inria.diverse.mobileprivacyprofiler.activities;


import fr.inria.diverse.mobileprivacyprofiler.datamodel.OrmLiteDBHelper;
import fr.inria.diverse.mobileprivacyprofiler.R;
import fr.vojtisek.genandroid.genandroidlib.activities.OrmLiteActionBarActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


//Start of user code additional imports ManualScan_CustomViewActivity
import fr.inria.diverse.mobileprivacyprofiler.services.ScanActivityIntentService;
import fr.inria.diverse.mobileprivacyprofiler.services.ScanConnectionIntentService;
import fr.inria.diverse.mobileprivacyprofiler.services.ScanContactIntentService;
import fr.inria.diverse.mobileprivacyprofiler.services.ScanSocialIntentService;
import fr.inria.diverse.mobileprivacyprofiler.test.Test;

import android.view.View;

//End of user code
public class ManualScan_CustomViewActivity extends OrmLiteActionBarActivity<OrmLiteDBHelper>
//Start of user code additional implements ManualScan_CustomViewActivity
//End of user code
{
	
	//Start of user code constants ManualScan_CustomViewActivity
	private static final String TAG = ManualScan_CustomViewActivity.class.getSimpleName();

	//End of user code

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		//Start of user code onCreate ManualScan_CustomViewActivity_1
		//End of user code		
        setContentView(R.layout.manualscan_customview);
		ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);

        //Start of user code onCreate ManualScan_CustomViewActivity
		//End of user code
    }
    
    @Override
	protected void onResume() {
		super.onResume();
		refreshScreenData();
		//Start of user code onResume ManualScan_CustomViewActivity
		//End of user code
	}
    //Start of user code additional code ManualScan_CustomViewActivity
	public void onClickBtnApplicationHistoryManualScan(View view){
		showToast(this.getBaseContext().getString(R.string.scandevice_intentservice_start_service)+
				"\n"+
				this.getBaseContext().getString(R.string.scandevice_intentservice_starting_scan_installed_applications));
		ScanActivityIntentService.startActionScanInstalledApplications(this);

		showToast(this.getBaseContext().getString(R.string.scandevice_intentservice_start_service)+
				"\n"+
				this.getBaseContext().getString(R.string.scandevice_intentservice_starting_scan_month_app_usage));
		ScanActivityIntentService.startActionScanAppUsage(this);
/*
		new JobRequest.Builder(ScanAppUsageJob.TAG)
				.startNow()
				//.setRequiresBatteryNotLow(true)
				//.setRequiresStorageNotLow(true)
				//.setRequiredNetworkType(JobRequest.NetworkType.UNMETERED)
				//.setRequiresCharging(true)
				//.setRequirementsEnforced(true)
				.build()
				.schedule();*/
    }
    //-------> methods to trigger actions from button click <-------
	public void onClickBtnBatteryUsage(View view) {
		showToast(this.getBaseContext().getString(R.string.scandevice_intentservice_start_service)+
				"\n"+
				this.getBaseContext().getString(R.string.scandevice_intentservice_starting_scan_battery));
		ScanActivityIntentService.startActionScanBatteryUsage(this);
	}
	public void onClickBtnSMS(View view) {
		showToast(this.getBaseContext().getString(R.string.scandevice_intentservice_start_service)+
				"\n"+
				this.getBaseContext().getString(R.string.scandevice_intentservice_starting_scan_sms));
		ScanSocialIntentService.startActionScanSms(this);
	}
	public void onClickBtnNeihgboringCellHistory(View view) {
		showToast(this.getBaseContext().getString(R.string.scandevice_intentservice_start_service)+
				"\n"+
				this.getBaseContext().getString(R.string.scandevice_intentservice_starting_scan_neighboring_cell_history));
		ScanConnectionIntentService.startActionScanCellInfo(this);
	}
	public void onClickBtnPhoneCallLog(View view) {
		showToast(this.getBaseContext().getString(R.string.scandevice_intentservice_start_service)+
				"\n"+
				this.getBaseContext().getString(R.string.scandevice_intentservice_starting_scan_phone_call_log));
		ScanSocialIntentService.startActionScanCallHistory(this);
	}
	public void onClickBtnAuthentification(View view) {
		showToast(this.getBaseContext().getString(R.string.scandevice_intentservice_start_service)+
				"\n"+
				this.getBaseContext().getString(R.string.scandevice_intentservice_starting_scan_authentification));
		ScanActivityIntentService.startActionScanAuthenticators(this);
	}
	public void onClickBtnCalendarEvent(View view) {
		showToast(this.getBaseContext().getString(R.string.scandevice_intentservice_start_service)+
				"\n"+
				this.getBaseContext().getString(R.string.scandevice_intentservice_starting_scan_calendar_event));
		ScanSocialIntentService.startActionScanCalendarEvent(this);
	}
	public void onClickBtnAppUsageStat(View view) {
		showToast(this.getBaseContext().getString(R.string.scandevice_intentservice_start_service)+
				"\n"+
				this.getBaseContext().getString(R.string.scandevice_intentservice_starting_scan_app_usage_stat));
		ScanActivityIntentService.startActionScanAppUsage(this);
	}
	public void onClickBtnContact(View view) {
		showToast(this.getBaseContext().getString(R.string.scandevice_intentservice_start_service)+
				"\n"+
				this.getBaseContext().getString(R.string.scandevice_intentservice_starting_scan_contacts));
		ScanContactIntentService.startActionScanContacts(this);
	}
	public void onClickBtnGeolocation(View view) {
		showToast(this.getBaseContext().getString(R.string.scandevice_intentservice_start_service)+
				"\n"+
				this.getBaseContext().getString(R.string.scandevice_intentservice_starting_scan_geolocation));
		ScanActivityIntentService.startActionRecordLocation(this);
	}
	public void onClickBtnScanWifi(View view) {
		showToast(this.getBaseContext().getString(R.string.scandevice_intentservice_start_service)+
				"\n"+
				this.getBaseContext().getString(R.string.scandevice_intentservice_starting_scan_wifi));
		ScanConnectionIntentService.startActionScanWifi(this);
	}
	public void onClickBtnScanBluetooth(View view) {
		showToast(this.getBaseContext().getString(R.string.scandevice_intentservice_start_service)+
				"\n"+
				this.getBaseContext().getString(R.string.scandevice_intentservice_starting_scan_bluetooth));
		ScanConnectionIntentService.startActionScanBluetooth(this);
	}
	public void onClickBtnTest(View view) {
		showToast(this.getBaseContext().getString(R.string.beginning_test_service)+
				"\n"+
				this.getBaseContext().getString(R.string.firing_up_test_action));

		Test test = new Test();
		test.mainTest(this);
	}

	//End of user code

    /** refresh screen from data 
     */
    public void refreshScreenData() {
    	//Start of user code action when refreshing the screen ManualScan_CustomViewActivity
		//End of user code
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.manualscan_customview_actions, menu);
		// add additional programmatic options in the menu
		//Start of user code additional onCreateOptionsMenu ManualScan_CustomViewActivity
		//End of user code
        return super.onCreateOptionsMenu(menu);
    }
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// behavior of option menu
        switch (item.getItemId()) {
			case R.id.manualscan_customview_action_preference:
	        	startActivity(new Intent(this, Preferences_PreferenceViewActivity.class));
	            return true;
			//Start of user code additional menu action ManualScan_CustomViewActivity
	
			//End of user code
			// Respond to the action bar's Up/Home button
			case android.R.id.home:
	        	TaskStackBuilder.create(this)
	                // Add all of this activity's parents to the back stack
	                .addNextIntentWithParentStack(getSupportParentActivityIntent())
	                // Navigate up to the closest parent
	                .startActivities();
	            return true;
			default:
                return super.onOptionsItemSelected(item);
        }
    }

	//  ------------ dealing with Up button
	@Override
	public Intent getSupportParentActivityIntent() {
		//Start of user code getSupportParentActivityIntent ManualScan_CustomViewActivity
		// navigates to the parent activity
		return new Intent(this, Home_CustomViewActivity.class);
		//End of user code
	}
	@Override
	public void onCreateSupportNavigateUpTaskStack(TaskStackBuilder builder) {
		//Start of user code onCreateSupportNavigateUpTaskStack ManualScan_CustomViewActivity
		super.onCreateSupportNavigateUpTaskStack(builder);
		//End of user code
	}
}
