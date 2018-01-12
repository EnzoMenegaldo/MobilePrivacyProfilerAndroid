/*  */
package fr.inria.diverse.mobileprivacyprofiler.activities;


import fr.inria.diverse.mobileprivacyprofiler.datamodel.ApplicationHistory;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.OrmLiteDBHelper;
import fr.inria.diverse.mobileprivacyprofiler.R;
import fr.vojtisek.genandroid.genandroidlib.activities.OrmLiteActionBarActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import com.j256.ormlite.dao.RuntimeExceptionDao;
// Start of user code protectedApplicationHistoryDetail_ElementViewActivity_additional_import
//import fr.inria.diverse.mobileprivacyprofiler.tools.ThemeUtil;
// End of user code

public class ApplicationHistoryDetail_ElementViewActivity extends OrmLiteActionBarActivity<OrmLiteDBHelper>
// Start of user code protectedApplicationHistoryDetail_ElementViewActivity_additional_implements
// End of user code
{
	
	protected int applicationHistoryId;
	
	private static final String LOG_TAG = ApplicationHistoryDetail_ElementViewActivity.class.getCanonicalName();

// Start of user code protectedApplicationHistoryDetail_ElementViewActivity_additional_attributes
// End of user code
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		// Start of user code protectedApplicationHistoryDetail_ElementViewActivity_onCreate_1
		//ThemeUtil.onActivityCreateSetTheme(this);
		// End of user code
        setContentView(R.layout.applicationhistorydetail_elementview);

		ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);

        applicationHistoryId = getIntent().getExtras().getInt("applicationHistoryId");
        
		// Start of user code protectedApplicationHistoryDetail_ElementViewActivity_onCreate
		// End of user code
    }
    
    @Override
	protected void onResume() {
		super.onResume();
		refreshScreenData();
	}
    
    
    private void refreshScreenData() {
    	// get our dao
    	RuntimeExceptionDao<ApplicationHistory, Integer> entriesDao = getHelper().getApplicationHistoryDao();
		// Start of user code protectedApplicationHistoryDetail_ElementViewActivity.refreshScreenData
    	ApplicationHistory entry = entriesDao.queryForId(applicationHistoryId);
    	entry.setContextDB(getHelper().getMobilePrivacyProfilerDBHelper());

		((TextView) findViewById(R.id.applicationhistorydetail_elementview_appname)).setText(entry.getAppName());
		((TextView) findViewById(R.id.applicationhistorydetail_elementview_packagename)).setText(entry.getPackageName());
		
		
		/*SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");
    	((TextView) findViewById(R.id.detail_divedate)).setText(dateFormatter.format(entry.getDate()));
		
    	((TextView) findViewById(R.id.detail_divelocation)).setText(entry.getLocation());
    	
    	((TextView) findViewById(R.id.detail_divedepth)).setText(entry.getMaxdepth().toString());
    	
    	((TextView) findViewById(R.id.detail_diveduration)).setText(entry.getDuration().toString());
    	*/	
		// End of user code
    	
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		// add options in the menu
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.applicationhistorydetail_elementview_actions, menu);
		// add additional programmatic options in the menu
		//Start of user code additional onCreateOptionsMenu ApplicationHistoryDetail_EditableElementViewActivity

		//End of user code
        return super.onCreateOptionsMenu(menu);
    }
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
		// behavior of option menu
        switch (item.getItemId()) {
			case R.id.applicationhistorydetail_elementview_action_preference:
	        	startActivity(new Intent(this, Preferences_PreferenceViewActivity.class));
	            return true;
			//Start of user code additional menu action ApplicationHistoryDetail_ElementViewActivity
	
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
		//Start of user code getSupportParentActivityIntent ApplicationHistoryDetail_ClassListViewActivity
		// navigates to the parent activity
		return new Intent(this, ApplicationHistoryList_ClassListViewActivity.class);
		//End of user code
	}
	@Override
	public void onCreateSupportNavigateUpTaskStack(TaskStackBuilder builder) {
		//Start of user code onCreateSupportNavigateUpTaskStack ApplicationHistoryDetail_ClassListViewActivity
		super.onCreateSupportNavigateUpTaskStack(builder);
		//End of user code
	}

	// Start of user code protectedApplicationHistoryDetail_ElementViewActivity_additional_operations
	// End of user code

}
