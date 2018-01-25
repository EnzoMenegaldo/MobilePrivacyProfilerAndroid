/*  */
package fr.inria.diverse.mobileprivacyprofiler.activities;


import fr.inria.diverse.mobileprivacyprofiler.datamodel.MobilePrivacyProfilerDBHelper;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.OrmLiteDBHelper;
import fr.inria.diverse.mobileprivacyprofiler.R;
import fr.inria.diverse.mobileprivacyprofiler.utils.ParametersUtils;
import fr.vojtisek.genandroid.genandroidlib.activities.OrmLiteActionBarActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import android.preference.PreferenceManager;
//Start of user code additional imports Home_CustomViewActivity

import fr.inria.diverse.mobileprivacyprofiler.BuildConfig;

import android.app.Activity;
import android.content.Context;

import android.util.Log;

import android.view.View;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.dao.RuntimeExceptionDao;
//End of user code
public class Home_CustomViewActivity extends OrmLiteActionBarActivity<OrmLiteDBHelper>
//Start of user code additional implements Home_CustomViewActivity
//End of user code
{
	
	//Start of user code constants Home_CustomViewActivity
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
	public void onClickBtnApplicationHistory(View view){
		showToast( this.getString(R.string.applicationhistorylist_classlistview_launch_toast));
        startActivity(new Intent(this, ApplicationHistoryList_ClassListViewActivity.class));
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

    private void debugText(StringBuilder sb) {
        sb.append("Table "+getHelper().getApplicationHistoryDao().getDataClass().getSimpleName());
        sb.append(" count="+ getHelper().getApplicationHistoryDao().countOf()+"\n");
        sb.append("Table "+getHelper().getApplicationUsageStatsDao().getDataClass().getSimpleName());
        sb.append(" count="+ getHelper().getApplicationUsageStatsDao().countOf()+"\n");

    }

}
