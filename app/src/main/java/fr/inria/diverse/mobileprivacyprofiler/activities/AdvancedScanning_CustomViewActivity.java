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


//Start of user code additional imports AdvancedScanning_CustomViewActivity
import fr.inria.diverse.mobileprivacyprofiler.utils.JobEnum;
import android.widget.ListView;
import android.content.Context;
import android.widget.TextView;

//End of user code
public class AdvancedScanning_CustomViewActivity extends OrmLiteActionBarActivity<OrmLiteDBHelper>
//Start of user code additional implements AdvancedScanning_CustomViewActivity
//End of user code
{
	
	//Start of user code constants AdvancedScanning_CustomViewActivity

	//End of user code

	//Start of user code Static initialization  AdvancedScanning_CustomViewActivity
	private static Context context;
	//End of user code

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		//Start of user code onCreate AdvancedScanning_CustomViewActivity_1
		//End of user code		
        setContentView(R.layout.advancedscanning_customview);
		ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);

        //Start of user code onCreate AdvancedScanning_CustomViewActivity
		TextView textElement = (TextView) findViewById(R.id.advancedscanning_customview_app_state);
		textElement.setText(Starting_CustomViewActivity.app_state);

		JobEnum[] services = JobEnum.values();
		ListView serviceListView = (ListView)findViewById(R.id.serviceListView);
		JobList_Adapter adapter = new JobList_Adapter(getApplicationContext(),services);
		serviceListView.setAdapter(adapter);
		context = getApplicationContext();
		//End of user code
    }
    
    @Override
	protected void onResume() {
		super.onResume();
		refreshScreenData();
		//Start of user code onResume AdvancedScanning_CustomViewActivity
		//End of user code
	}
    //Start of user code additional code AdvancedScanning_CustomViewActivity

	public static Context getContext(){
		return context;
	}

	//End of user code

    /** refresh screen from data 
     */
    public void refreshScreenData() {
    	//Start of user code action when refreshing the screen AdvancedScanning_CustomViewActivity
		//End of user code
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.advancedscanning_customview_actions, menu);
		// add additional programmatic options in the menu
		//Start of user code additional onCreateOptionsMenu AdvancedScanning_CustomViewActivity

		//End of user code
        return super.onCreateOptionsMenu(menu);
    }
    
	// Dealing with Activity results
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//Start of user code onActivityResult AdvancedScanning_CustomViewActivity

		//End of user code
	}
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// behavior of option menu
        switch (item.getItemId()) {
			//Start of user code additional menu action AdvancedScanning_CustomViewActivity
	
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
		//Start of user code getSupportParentActivityIntent AdvancedScanning_CustomViewActivity
		// navigates to the parent activity
		return new Intent(this, Home_CustomViewActivity.class);
		//End of user code
	}
	@Override
	public void onCreateSupportNavigateUpTaskStack(TaskStackBuilder builder) {
		//Start of user code onCreateSupportNavigateUpTaskStack AdvancedScanning_CustomViewActivity
		super.onCreateSupportNavigateUpTaskStack(builder);
		//End of user code
	}
}
