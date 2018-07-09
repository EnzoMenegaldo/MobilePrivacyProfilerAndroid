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
//Start of user code additional imports Starting_CustomViewActivity

import fr.inria.diverse.mobileprivacyprofiler.BuildConfig;

import android.app.Activity;
import android.content.Context;

import android.util.Log;

import android.view.View;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.dao.RuntimeExceptionDao;
//End of user code
public class Starting_CustomViewActivity extends OrmLiteActionBarActivity<OrmLiteDBHelper>
//Start of user code additional implements Starting_CustomViewActivity
//End of user code
{
	
	//Start of user code constants Starting_CustomViewActivity
	//End of user code

	//Start of user code Static initialization  Starting_CustomViewActivity
	public static Context context;
	public static String app_state;
	//End of user code

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		//Start of user code onCreate Starting_CustomViewActivity_1

		//End of user code		
			PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        setContentView(R.layout.starting_customview);
        //Start of user code onCreate Starting_CustomViewActivity
		context = getApplicationContext();
		app_state = getApplicationContext().getString(R.string.home_customview_app_state_inactive);
		//End of user code
    }
    
    @Override
	protected void onResume() {
		super.onResume();
		refreshScreenData();
		//Start of user code onResume Starting_CustomViewActivity
		context = getApplicationContext();
		//End of user code
	}
    //Start of user code additional code Starting_CustomViewActivity
	public void onClickBtnSample(View view){
		Intent intent = new Intent(this,Home_CustomViewActivity.class);
		startActivity(intent);
    }
    public void onClickHelpBtn(View view){
        Intent intent = new Intent(this,Help_CustomViewActivity.class);
        startActivity(intent);
    }
    public static Context getContext(){
    	return context;
	}

	public static boolean isCollectionRunning(){
    	Context context=getContext();
		if(null!=context&&app_state.equals(context.getString(R.string.home_customview_app_state_inactive)))
			return false;
		return true;
	}
	//End of user code

    /** refresh screen from data 
     */
    public void refreshScreenData() {
    	//Start of user code action when refreshing the screen Starting_CustomViewActivity
		//End of user code
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.starting_customview_actions, menu);
		// add additional programmatic options in the menu
		//Start of user code additional onCreateOptionsMenu Starting_CustomViewActivity

		//End of user code
        return super.onCreateOptionsMenu(menu);
    }
    
	// Dealing with Activity results
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//Start of user code onActivityResult Starting_CustomViewActivity

		//End of user code
	}
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// behavior of option menu
        switch (item.getItemId()) {
			case R.id.starting_customview_action_preference:
	        	startActivity(new Intent(this, Preferences_PreferenceViewActivity.class));
	            return true;
			//Start of user code additional menu action Starting_CustomViewActivity
	
			//End of user code
			default:
                return super.onOptionsItemSelected(item);
        }
    }

}
