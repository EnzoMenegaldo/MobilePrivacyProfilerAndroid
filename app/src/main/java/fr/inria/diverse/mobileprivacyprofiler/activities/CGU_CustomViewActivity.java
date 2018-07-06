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


//Start of user code additional imports CGU_CustomViewActivity

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
public class CGU_CustomViewActivity extends OrmLiteActionBarActivity<OrmLiteDBHelper>
//Start of user code additional implements CGU_CustomViewActivity
//End of user code
{
	
	//Start of user code constants CGU_CustomViewActivity
	//End of user code

	//Start of user code Static initialization  CGU_CustomViewActivity
	//End of user code

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		//Start of user code onCreate CGU_CustomViewActivity_1
		//End of user code		
        setContentView(R.layout.cgu_customview);
        //Start of user code onCreate CGU_CustomViewActivity
		//End of user code
    }
    
    @Override
	protected void onResume() {
		super.onResume();
		refreshScreenData();
		//Start of user code onResume CGU_CustomViewActivity
		//End of user code
	}
    //Start of user code additional code CGU_CustomViewActivity
	public void onClickBtnSample(View view){
		showToast("sample button pressed. \nPlease customize ;-)");
    }
	//End of user code

    /** refresh screen from data 
     */
    public void refreshScreenData() {
    	//Start of user code action when refreshing the screen CGU_CustomViewActivity
		//End of user code
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.cgu_customview_actions, menu);
		// add additional programmatic options in the menu
		//Start of user code additional onCreateOptionsMenu CGU_CustomViewActivity

		//End of user code
        return super.onCreateOptionsMenu(menu);
    }
    
	// Dealing with Activity results
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//Start of user code onActivityResult CGU_CustomViewActivity

		//End of user code
	}
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// behavior of option menu
        switch (item.getItemId()) {
			case R.id.cgu_customview_action_preference:
	        	startActivity(new Intent(this, Preferences_PreferenceViewActivity.class));
	            return true;
			//Start of user code additional menu action CGU_CustomViewActivity
	
			//End of user code
			default:
                return super.onOptionsItemSelected(item);
        }
    }

}