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
import fr.inria.diverse.mobileprivacyprofiler.utils.JobEnum;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.VpnService;
import fr.inria.diverse.mobileprivacyprofiler.broadcastReceiver.WifiScanReceiver;
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

//End of user code
public class Home_CustomViewActivity extends OrmLiteActionBarActivity<OrmLiteDBHelper>
//Start of user code additional implements Home_CustomViewActivity
//End of user code
{
	
	//Start of user code constants Home_CustomViewActivity
    public static final String BUNDLE_IS_RUNNING_TAG = "isCollectionRunning";
    private static final int REQUEST_CODE_VPN = 0;
    private static final String TAG = Home_CustomViewActivity.class.getSimpleName();
    public static final String[] PERMISSIONS = { Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.BLUETOOTH,
                                                    Manifest.permission.GET_ACCOUNTS, Manifest.permission.INTERNET,
                                                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_CALENDAR,
                                                            Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_SMS,
                                                                Manifest.permission.READ_CONTACTS, Manifest.permission.READ_EXTERNAL_STORAGE,
                                                                    Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,
                                                                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_WIFI_STATE,
                                                                            Manifest.permission.ACCESS_NETWORK_STATE};
    private static final String native_lib = "native_lib";
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
        if(savedInstanceState != null)
            Starting_CustomViewActivity.app_state = savedInstanceState.getString(BUNDLE_IS_RUNNING_TAG);

        if (!PhoneStateUtils.hasPermission(this,PERMISSIONS)){
            PhoneStateUtils.requestPermissions(this,PERMISSIONS);
        }

        JobManager.create(this).addJobCreator(new MobilePrivacyProfilerJobCreator());


        ToggleButton toggleCollection = (ToggleButton)findViewById(R.id.home_customview_toggle_collection);
        TextView app_state_text_view = (TextView)findViewById(R.id.home_customview_app_state);
        app_state_text_view.setText(Starting_CustomViewActivity.app_state);
        TextView screen_explanation = (TextView)findViewById(R.id.home_customview_screen_explanation);

        toggleCollection.setChecked(Starting_CustomViewActivity.isCollectionRunning());

        toggleCollection.setOnClickListener(view -> {
            if(((ToggleButton)view).isChecked()){
                setupVpn();
                runSelectedJob();
                Starting_CustomViewActivity.app_state = getString(R.string.home_customview_app_state_active);
                app_state_text_view.setText(Starting_CustomViewActivity.app_state);
                screen_explanation.setText(R.string.home_customview_stop_collection);
            }else{
                cancelSelectedJob();
                Starting_CustomViewActivity.app_state =getString(R.string.home_customview_app_state_inactive);
                app_state_text_view.setText(Starting_CustomViewActivity.app_state);
                screen_explanation.setText(R.string.home_customview_run_collection);
            }
        });
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
    public void onClickHelpBtn(View view){
        Intent intent = new Intent(this,Help_CustomViewActivity.class);
        startActivity(intent);
    }

    public static Context getContext(){return Starting_CustomViewActivity.getContext();}


    /**
     * Save the activity state when it stops.
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(BUNDLE_IS_RUNNING_TAG, Starting_CustomViewActivity.app_state);
    }



    /**
     * Schedule all selected jobs
     */
    public void runSelectedJob(){
        for(JobEnum job : JobEnum.values())
            if(job.isSelected())
                job.run();
    }

    /**
     * Cancel all selected jobs
     */
    public void cancelSelectedJob(){
        for(JobEnum job : JobEnum.values())
            if(job.isSelected())
                job.cancel();
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
                        setupVpn();
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

    private void setupVpn() {
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

    //End of user code

    /** refresh screen from data 
     */
    public void refreshScreenData() {
    	//Start of user code action when refreshing the screen Home_CustomViewActivity

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

            case REQUEST_CODE_VPN :
                if (resultCode == RESULT_OK) {

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
