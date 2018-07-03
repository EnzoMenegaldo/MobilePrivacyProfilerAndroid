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
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.VpnService;
import android.util.Log;
import fr.inria.diverse.mobileprivacyprofiler.utils.PhoneStateUtils;
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
	private static final int REQUEST_CODE_VPN = 0;

	//End of user code

	//Start of user code Static initialization  ManualScan_CustomViewActivity
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
		ScanActivityIntentService.startActionScanInstalledApplications();

		showToast(this.getBaseContext().getString(R.string.scandevice_intentservice_start_service)+
				"\n"+
				this.getBaseContext().getString(R.string.scandevice_intentservice_starting_scan_month_app_usage));
		ScanActivityIntentService.startActionScanAppUsage();
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
		ScanActivityIntentService.startActionScanBatteryUsage();
	}

	public void onClickBtnSMS(View view) {
		showToast(this.getBaseContext().getString(R.string.scandevice_intentservice_start_service)+
				"\n"+
				this.getBaseContext().getString(R.string.scandevice_intentservice_starting_scan_sms));
		ScanSocialIntentService.startActionScanSms();
	}

	public void onClickBtnNeihgboringCellHistory(View view) {
		showToast(this.getBaseContext().getString(R.string.scandevice_intentservice_start_service)+
				"\n"+
				this.getBaseContext().getString(R.string.scandevice_intentservice_starting_scan_neighboring_cell_history));
		ScanConnectionIntentService.startActionScanCellInfo();
	}

	public void onClickBtnPhoneCallLog(View view) {
		showToast(this.getBaseContext().getString(R.string.scandevice_intentservice_start_service)+
				"\n"+
				this.getBaseContext().getString(R.string.scandevice_intentservice_starting_scan_phone_call_log));
		ScanSocialIntentService.startActionScanCallHistory();
	}

	public void onClickBtnAuthentification(View view) {
		showToast(this.getBaseContext().getString(R.string.scandevice_intentservice_start_service)+
				"\n"+
				this.getBaseContext().getString(R.string.scandevice_intentservice_starting_scan_authentification));
		ScanActivityIntentService.startActionScanAuthenticators();
	}

	public void onClickBtnCalendarEvent(View view) {
		showToast(this.getBaseContext().getString(R.string.scandevice_intentservice_start_service)+
				"\n"+
				this.getBaseContext().getString(R.string.scandevice_intentservice_starting_scan_calendar_event));
		ScanSocialIntentService.startActionScanCalendarEvent();
	}

	public void onClickBtnAppUsageStat(View view) {
		showToast(this.getBaseContext().getString(R.string.scandevice_intentservice_start_service)+
				"\n"+
				this.getBaseContext().getString(R.string.scandevice_intentservice_starting_scan_app_usage_stat));
		ScanActivityIntentService.startActionScanAppUsage();
	}

	public void onClickBtnContact(View view) {
		showToast(this.getBaseContext().getString(R.string.scandevice_intentservice_start_service)+
				"\n"+
				this.getBaseContext().getString(R.string.scandevice_intentservice_starting_scan_contacts));
		ScanContactIntentService.startActionScanContacts();
	}

	public void onClickBtnGeolocation(View view) {
		showToast(this.getBaseContext().getString(R.string.scandevice_intentservice_start_service)+
				"\n"+
				this.getBaseContext().getString(R.string.scandevice_intentservice_starting_scan_geolocation));
		ScanActivityIntentService.startActionRecordLocation();
	}

	public void onClickBtnScanWifi(View view) {
		showToast(this.getBaseContext().getString(R.string.scandevice_intentservice_start_service)+
				"\n"+
				this.getBaseContext().getString(R.string.scandevice_intentservice_starting_scan_wifi));
		ScanConnectionIntentService.startActionScanWifi();
	}

	public void onClickBtnScanBluetooth(View view) {
		showToast(this.getBaseContext().getString(R.string.scandevice_intentservice_start_service)+
				"\n"+
				this.getBaseContext().getString(R.string.scandevice_intentservice_starting_scan_bluetooth));
		ScanConnectionIntentService.startActionScanBluetooth();
	}

	public void onClickBtnTest(View view) {
		showToast(this.getBaseContext().getString(R.string.beginning_test_service)+
				"\n"+
				this.getBaseContext().getString(R.string.firing_up_test_action));

		Test test = new Test();
		test.mainTest(this);
	}

	public void onClickBtnScanNetActivity(View view){
		// check for VPN already running
		try {
			if (!PhoneStateUtils.checkForActiveInterface(getString(R.string.vpn_interface))) {

				// get user permission for VPN
				Intent intent = VpnService.prepare(this);
				if (intent != null) {
					// ask user for VPN permission
					startActivityForResult(intent, 0);
				} else {
					// already have VPN permission
					onActivityResult(REQUEST_CODE_VPN, RESULT_OK, null);
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "Exception checking network interfaces :" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Show dialog to educate the user about VPN trust
	 * abort app if user chooses to quit
	 * otherwise relaunch the onClickBtnScanNetActivity()
	 */
	private void showVPNRefusedDialog() {
		new AlertDialog.Builder(this)
				.setTitle("Usage Alert")
				.setMessage("You must trust the application in order to run a VPN based trace.")
				.setPositiveButton(getString(R.string.try_again), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						onClickBtnScanNetActivity(null);
					}
				})
				.setNegativeButton(getString(R.string.exit), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						showVPNRefusedDialog();
					}
				})
				.show();

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
    
	// Dealing with Activity results
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//Start of user code onActivityResult ManualScan_CustomViewActivity
		Log.i(TAG, "onActivityResult(resultCode:  " + resultCode + ")");
		switch (requestCode){
			case REQUEST_CODE_VPN :
				if (resultCode == RESULT_OK) {
					ScanActivityIntentService.startActionNetActivity();
				} else if (resultCode == RESULT_CANCELED) {
					showVPNRefusedDialog();
				}
				break;

			default:
				break;
		}
	//End of user code
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
