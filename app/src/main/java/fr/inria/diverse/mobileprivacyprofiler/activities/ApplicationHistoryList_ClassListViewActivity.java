/*  */
package fr.inria.diverse.mobileprivacyprofiler.activities;


import fr.inria.diverse.mobileprivacyprofiler.datamodel.*;
import fr.inria.diverse.mobileprivacyprofiler.R;
import fr.vojtisek.genandroid.genandroidlib.activities.OrmLiteActionBarActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
// Start of user code protectedApplicationHistoryList_ClassListViewActivity_additionalimports
//import fr.inria.diverse.mobileprivacyprofiler.tools.ThemeUtil;
// End of user code

public class ApplicationHistoryList_ClassListViewActivity extends OrmLiteActionBarActivity<OrmLiteDBHelper> implements OnItemClickListener {
	
	private static final String LOG_TAG = ApplicationHistoryList_ClassListViewActivity.class.getSimpleName();

	//Start of user code constants ApplicationHistoryList_ClassListViewActivity
	//End of user code
	
    ApplicationHistoryList_Adapter adapter;


	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		//Start of user code onCreate ApplicationHistoryList_ClassListViewActivity_1
		//ThemeUtil.onActivityCreateSetTheme(this);
		//End of user code
		setContentView(R.layout.applicationhistorylist_listview);

		ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);

		ListView list = (ListView) findViewById(R.id.applicationhistorylist_listview);
        list.setClickable(false);
		//Start of user code onCreate ApplicationHistoryList_ClassListViewActivity adapter creation
        adapter = new ApplicationHistoryList_Adapter(this, getHelper().getMobilePrivacyProfilerDBHelper());		
		//End of user code
		// avoid opening the keyboard on view opening
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        list.setOnItemClickListener(this);

        list.setAdapter(adapter);

		// Get the intent, verify the action and get the query
        handleIntent(getIntent());

		//Start of user code onCreate additions ApplicationHistoryList_ClassListViewActivity
		//End of user code
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		//Start of user code onResume additions ApplicationHistoryList_ClassListViewActivity
		//End of user code
	}

	@Override
    protected void onNewIntent(Intent intent) {
        // Because this activity has set launchMode="singleTop", the system calls this method
        // to deliver the intent if this activity is currently the foreground activity when
        // invoked again (when the user executes a search from this activity, we don't create
        // a new instance of this activity, so the system delivers the search intent here)
        handleIntent(intent);
    }
	
	private void handleIntent(Intent intent) {
		//Log.d(LOG_TAG,"Intent received");
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
           // handles a click on a search suggestion; launches activity to show word
           //  Intent wordIntent = new Intent(this, WordActivity.class);
           // wordIntent.setData(intent.getData());
           // startActivity(wordIntent);
        } else if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            // handles a search query
            String query = intent.getStringExtra(SearchManager.QUERY);
    		Log.d(LOG_TAG,"ACTION_SEARCH Intent received for "+query);
            ApplicationHistoryList_ClassListViewActivity.this.adapter.getFilter().filter(query);
        }
    }	

	@Override
	public boolean onSearchRequested() {
		Log.d(LOG_TAG,"onSearchRequested received");
	    return super.onSearchRequested();
	}

	public void onItemClick(AdapterView<?> arg0, View view, int position, long index) {
			//Start of user code onItemClick additions ApplicationHistoryList_ClassListViewActivity
			showToast(view.toString() + ", "+ view.getId());
			//End of user code		
    }

	//Start of user code additional  ApplicationHistoryList_ClassListViewActivity methods

	//End of user code

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		// add options in the menu
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.applicationhistorylist_classlistview_actions, menu);
		// Associate searchable configuration with the SearchView
		// deal with compat
		MenuItem  menuItem = (MenuItem ) menu.findItem(R.id.applicationhistorylist_classlistview_action_search);
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
		searchView.setSearchableInfo( searchManager.getSearchableInfo(getComponentName()));
	    searchView.setIconifiedByDefault(false);
    	searchView.setOnQueryTextListener( new SearchView.OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String arg0) {
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
					// already done by normal
				}
				else{
					ApplicationHistoryList_ClassListViewActivity.this.adapter.getFilter().filter(arg0);
				}
				return false;
			}
			
			@Override
			public boolean onQueryTextChange(String arg0) {
				// TODO must be careful if the request might be long
				// action on text change
				ApplicationHistoryList_ClassListViewActivity.this.adapter.getFilter().filter(arg0);
				return false;
			}
		});
	    
		// add additional programmatic options in the menu
		//Start of user code additional onCreateOptionsMenu ApplicationHistoryList_ClassListViewActivity

		//End of user code
        return super.onCreateOptionsMenu(menu);
    }
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
		// behavior of option menu
        switch (item.getItemId()) {
			case R.id.applicationhistorylist_classlistview_action_preference:
	        	startActivity(new Intent(this, Preferences_PreferenceViewActivity.class));
	            return true;
			//Start of user code additional menu action ApplicationHistoryList_ClassListViewActivity
	
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
		//Start of user code getSupportParentActivityIntent ApplicationHistoryList_ClassListViewActivity
		// navigates to the parent activity
		return new Intent(this, Home_CustomViewActivity.class);
		//End of user code
	}
	@Override
	public void onCreateSupportNavigateUpTaskStack(TaskStackBuilder builder) {
		//Start of user code onCreateSupportNavigateUpTaskStack ApplicationHistoryList_ClassListViewActivity
		super.onCreateSupportNavigateUpTaskStack(builder);
		//End of user code
	}

	// Start of user code protectedApplicationHistoryList_ClassListViewActivity
	public void onClickFilterBtn(View view){
		showToast("filter button pressed. \nPlease customize ;-)");
    }
	// End of user code

	
}
