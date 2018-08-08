/*  */
package fr.inria.diverse.mobileprivacyprofiler.activities;


import fr.inria.diverse.mobileprivacyprofiler.datamodel.OrmLiteDBHelper;
import fr.inria.diverse.mobileprivacyprofiler.R;
import fr.inria.diverse.mobileprivacyprofiler.utils.AppStateViewModel;
import fr.vojtisek.genandroid.genandroidlib.activities.OrmLiteActionBarActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


//Start of user code additional imports Home_CustomViewActivity
import fr.inria.diverse.mobileprivacyprofiler.utils.JobEnum;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.VpnService;

import fr.inria.diverse.mobileprivacyprofiler.utils.PhoneStateUtils;
import fr.inria.diverse.mobileprivacyprofiler.job.MobilePrivacyProfilerJobCreator;

import android.util.Log;
import android.Manifest;

import android.content.Context;

import android.view.View;

import android.widget.TextView;
import android.widget.ToggleButton;

import com.evernote.android.job.JobManager;

import java.io.IOException;
import java.security.Permission;
import java.util.Arrays;
import java.util.List;

//End of user code
public class Home_CustomViewActivity extends OrmLiteActionBarActivity<OrmLiteDBHelper> implements ActivityCompat.OnRequestPermissionsResultCallback
//Start of user code additional implements Home_CustomViewActivity
//End of user code
{
	
	//Start of user code constants Home_CustomViewActivity
    private static final String TAG = Home_CustomViewActivity.class.getSimpleName();
    public static List<String> PERMISSIONS = Arrays.asList(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.BLUETOOTH,
                                                    Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE,
                                                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_CALENDAR,
                                                            Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_SMS,
                                                                Manifest.permission.READ_CONTACTS, Manifest.permission.READ_EXTERNAL_STORAGE,
                                                                    Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,
                                                                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_WIFI_STATE);
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

        //With an api level lower than 23, we have to ask for this permission
        // https://developer.android.com/reference/android/Manifest.permission#GET_ACCOUNTS
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1)
            PERMISSIONS.add(Manifest.permission.GET_ACCOUNTS);

        PhoneStateUtils.checkAllPermissions(PERMISSIONS,this);

        JobManager.create(this).addJobCreator(new MobilePrivacyProfilerJobCreator());


        ToggleButton toggleCollection = (ToggleButton)findViewById(R.id.home_customview_toggle_collection);
        TextView screen_explanation = (TextView)findViewById(R.id.home_customview_screen_explanation);

        toggleCollection.setOnClickListener(view -> {
            if(((ToggleButton)view).isChecked()){
                runSelectedJob();
                AppStateViewModel.getCurrentState(getContext()).setValue(Html.fromHtml(getString(R.string.app_state_fragment_active)));
                screen_explanation.setText(R.string.home_customview_stop_collection);
            }else{
                cancelSelectedJob();
                AppStateViewModel.getCurrentState(getContext()).setValue(Html.fromHtml(getString(R.string.app_state_fragment_inactive)));
                screen_explanation.setText(R.string.home_customview_run_collection);
            }
        });

        toggleCollection.setChecked(AppStateViewModel.isCollectionRunning(getContext()));

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
    public static Context getContext(){return Starting_CustomViewActivity.getContext();}

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
                if (!PhoneStateUtils.hasAppOpPermissions(this)){
                    PhoneStateUtils.requestAppOpsPermissions(this);
                }else{
                    PhoneStateUtils.setupVpn(this);
                }
                break;

            case PhoneStateUtils.REQUEST_CODE_VPN:
                if (resultCode == RESULT_CANCELED) {
                    PhoneStateUtils.setupVpn(this);
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
			//Start of user code additional menu action Home_CustomViewActivity
            case R.id.home_customview_action_advancedscanning:
                startActivity(new Intent(this, AdvancedScanning_CustomViewActivity.class));
                return true;
            case R.id.home_customview_action_manualscan:
                startActivity(new Intent(this, ManualScan_CustomViewActivity.class));
                return true;
            case R.id.home_customview_to_help_customview:
                startActivity(new Intent(this, Help_CustomViewActivity.class));
                return true;
			//End of user code
			default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Get results of ActivityCompat.requestPermissions
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        String[] array = PERMISSIONS.toArray(new String[PERMISSIONS.size()]);
        if(!PhoneStateUtils.hasBasicPermissions(this,array)){
            PhoneStateUtils.requestBasicPermissions(this,array);
        }else if(!PhoneStateUtils.hasAppOpPermissions(this)){
                PhoneStateUtils.requestAppOpsPermissions(this);
        }else{
            PhoneStateUtils.setupVpn(this);
        }
    }

}
