/*  */
package fr.inria.diverse.mobileprivacyprofiler.activities;


import fr.inria.diverse.mobileprivacyprofiler.datamodel.OrmLiteDBHelper;
import fr.inria.diverse.mobileprivacyprofiler.R;
import fr.vojtisek.genandroid.genandroidlib.activities.OrmLiteActionBarActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


//Start of user code additional imports Contact_CustomViewActivity

import fr.inria.diverse.mobileprivacyprofiler.BuildConfig;

import android.app.Activity;
import android.content.Context;

import android.util.Log;

import android.view.View;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
//End of user code
public class Contact_CustomViewActivity extends OrmLiteActionBarActivity<OrmLiteDBHelper>
//Start of user code additional implements Contact_CustomViewActivity
//End of user code
{
	
	//Start of user code constants Contact_CustomViewActivity
    public static final String Contact_EMAIl = "mobileprofiler_ur1@gmail.com";
	//End of user code

	//Start of user code Static initialization  Contact_CustomViewActivity
	//End of user code

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		//Start of user code onCreate Contact_CustomViewActivity_1
		//End of user code		
        setContentView(R.layout.contact_customview);
        //Start of user code onCreate Contact_CustomViewActivity
        TextView textElement = (TextView) findViewById(R.id.contact_customview_app_state);
        textElement.setText(Starting_CustomViewActivity.app_state);
		//End of user code
    }
    
    @Override
	protected void onResume() {
		super.onResume();
		refreshScreenData();
		//Start of user code onResume Contact_CustomViewActivity
        TextView textElement = (TextView) findViewById(R.id.contact_customview_app_state);
        textElement.setText(Starting_CustomViewActivity.app_state);
		//End of user code
	}
    //Start of user code additional code Contact_CustomViewActivity
	public void onClickSendMailBtn(View view){
        final EditText sendMailInput = (EditText) findViewById(R.id.sendMailInput);
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{Contact_EMAIl});
        i.putExtra(Intent.EXTRA_SUBJECT, "[Participant Etude Profile]");
        i.putExtra(Intent.EXTRA_TEXT   , sendMailInput.getText());
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(Contact_CustomViewActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
	//End of user code

    /** refresh screen from data 
     */
    public void refreshScreenData() {
    	//Start of user code action when refreshing the screen Contact_CustomViewActivity
		//End of user code
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.contact_customview_actions, menu);
		// add additional programmatic options in the menu
		//Start of user code additional onCreateOptionsMenu Contact_CustomViewActivity

		//End of user code
        return super.onCreateOptionsMenu(menu);
    }
    
	// Dealing with Activity results
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//Start of user code onActivityResult Contact_CustomViewActivity

		//End of user code
	}
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// behavior of option menu
        switch (item.getItemId()) {
			//Start of user code additional menu action Contact_CustomViewActivity
	
			//End of user code
			default:
                return super.onOptionsItemSelected(item);
        }
    }

}
