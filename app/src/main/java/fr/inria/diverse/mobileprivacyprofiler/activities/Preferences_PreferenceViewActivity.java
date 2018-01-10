/*  */
package fr.inria.diverse.mobileprivacyprofiler.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import fr.inria.diverse.mobileprivacyprofiler.R;

//Start of user code Preferences preference activity additional imports
//End of user code

public class Preferences_PreferenceViewActivity  extends android.preference.PreferenceActivity {

	
	//Start of user code Preferences preference activity additional attributes
	//End of user code

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences); 
		//Start of user code Preferences preference activity additional onCreate
		//End of user code
    }

    @Override
	protected void onResume() {
		super.onResume(); 
		//Start of user code Preferences preference activity additional onResume
		//End of user code
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
		//Start of user code preference specific menu definition
        // menu.add(Menu.NONE, 0, 0, "Back to main menu");
		//End of user code
        return super.onCreateOptionsMenu(menu);
    }
 
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

		//Start of user code preference specific menu action
        /* switch (item.getItemId()) {
            case 0:
                startActivity(new Intent(this, AndroidDiveManagerMainActivity.class));
                return true;
        } */
		//End of user code
        return false;
    }

	
	//Start of user code Preferences preference activity additional operations
	//End of user code
}
