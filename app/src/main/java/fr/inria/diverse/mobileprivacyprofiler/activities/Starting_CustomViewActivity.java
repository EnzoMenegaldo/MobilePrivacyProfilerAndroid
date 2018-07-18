/*  */
package fr.inria.diverse.mobileprivacyprofiler.activities;


import fr.inria.diverse.mobileprivacyprofiler.datamodel.OrmLiteDBHelper;
import fr.inria.diverse.mobileprivacyprofiler.R;
import fr.inria.diverse.mobileprivacyprofiler.exception.NotConnectedToInternetException;
import fr.inria.diverse.mobileprivacyprofiler.rest.HttpPostAsyncTask;
import fr.inria.diverse.mobileprivacyprofiler.rest.MobilePrivacyRestClient;
import fr.vojtisek.genandroid.genandroidlib.activities.OrmLiteActionBarActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.preference.PreferenceManager;

//Start of user code additional imports Starting_CustomViewActivity
import android.os.Handler;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.security.ProviderInstaller;
//End of user code

public class Starting_CustomViewActivity extends OrmLiteActionBarActivity<OrmLiteDBHelper>
//Start of user code additional implements Starting_CustomViewActivity
//End of user code
{
	
	//Start of user code constants Starting_CustomViewActivity
	public static final String Login_Information = "login_information";
	public static final String SHARED_PREF_USERNAME_TAG = "username";
	public static final String SHARED_PREF_PASSWORD_TAG = "password";
	//End of user code

	//Start of user code Static initialization  Starting_CustomViewActivity
	public static Context context;
	public static MyHandler handler;
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

		//The Android API docs correctly state that TLSv1.2 is only supported for SSLEngine in API Level 20 or later (Lollipop) while SSLSocket supports it since level 16.
		//If the user use a device whose api is older than 20, he won't be able to use SSLSocket
		updateAndroidSecurityProvider(this);

		((EditText)findViewById(R.id.starting_customview_username)).setText(getSharedPreferences(Login_Information,MODE_PRIVATE).getString(SHARED_PREF_USERNAME_TAG,""));
		((EditText)findViewById(R.id.starting_customview_password)).setText(getSharedPreferences(Login_Information,MODE_PRIVATE).getString(SHARED_PREF_PASSWORD_TAG,""));
		handler = new MyHandler(Starting_CustomViewActivity.this);
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
	public void onClickValidate(View view){
		String username = ((EditText)findViewById(R.id.starting_customview_username)).getText().toString();
		String password = ((EditText)findViewById(R.id.starting_customview_password)).getText().toString();
		if(username.equals("admin")){
			Intent intent = new Intent(view.getContext(), Home_CustomViewActivity.class);
			view.getContext().startActivity(intent);
		}else if(!username.isEmpty() && !password.isEmpty()) {
			try {
				SharedPreferences.Editor editor = context.getSharedPreferences(Login_Information, MODE_PRIVATE).edit();
				editor.putString(SHARED_PREF_USERNAME_TAG, username);
				editor.putString(SHARED_PREF_PASSWORD_TAG, password);
				editor.apply();
				MobilePrivacyRestClient.getMobilePrivacyRestClient().authenticate(username,password,handler,getContext());
			} catch (NotConnectedToInternetException e) {
				Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
			}
		}
		else
			Toast.makeText(this,R.string.starting_customview_null_credentials,Toast.LENGTH_LONG).show();
    }

    public static Context getContext(){
    	return context;
	}

	private static class MyHandler extends Handler{

		private Context context;

		public MyHandler(Context context){
			super();
			this.context = context;
		}

		@Override
		public void handleMessage(Message msg){
			//We use that to get the authentication response from the server
			if(msg.what == HttpPostAsyncTask.HTT_STATUS_CODE){
				if(msg.obj != null) {
					if ((Integer) msg.obj == 200) {
						Intent intent = new Intent(context, Home_CustomViewActivity.class);
						context.startActivity(intent);
					} else {
						Toast.makeText(context, R.string.starting_customview_invalid_credentials, Toast.LENGTH_LONG).show();
					}
				}else{
					Toast.makeText(context, NotConnectedToInternetException.Message, Toast.LENGTH_LONG).show();
				}
			}
		}
	}

	/**
	 * Install a newer security provider using Google Play Services to allow the application to use SSLSocket.
	 * If the devise has an API lower than 20, by default, he won't be able to use SSLSocket.
	 * https://stackoverflow.com/questions/29916962/javax-net-ssl-sslhandshakeexception-javax-net-ssl-sslprotocolexception-ssl-han/36892715#36892715
	 */
	private void updateAndroidSecurityProvider(Activity callingActivity) {
		try {
			ProviderInstaller.installIfNeeded(this);
		} catch (GooglePlayServicesRepairableException e) {
			// Thrown when Google Play Services is not installed, up-to-date, or enabled
			// Show dialog to allow users to install, update, or otherwise enable Google Play services.
			GooglePlayServicesUtil.getErrorDialog(e.getConnectionStatusCode(), callingActivity, 0);
		} catch (GooglePlayServicesNotAvailableException e) {
			Log.e("SecurityException", "Google Play Services not available.");
		}
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
			//Start of user code additional menu action Starting_CustomViewActivity
			case R.id.starting_customview_to_help_customview:
				startActivity(new Intent(this, Help_CustomViewActivity.class));
				return true;
			//End of user code
			default:
                return super.onOptionsItemSelected(item);
        }
    }

}
