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


//Start of user code additional imports FAQ_CustomViewActivity

import android.view.View;

import android.widget.ListView;
import android.widget.TextView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//End of user code
public class FAQ_CustomViewActivity extends OrmLiteActionBarActivity<OrmLiteDBHelper>
//Start of user code additional implements FAQ_CustomViewActivity
//End of user code
{
	
	//Start of user code constants FAQ_CustomViewActivity
	//End of user code

	//Start of user code Static initialization  FAQ_CustomViewActivity
	//End of user code

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		//Start of user code onCreate FAQ_CustomViewActivity_1
		//End of user code		
        setContentView(R.layout.faq_customview);
        //Start of user code onCreate FAQ_CustomViewActivity
        ListView serviceListView = (ListView)findViewById(R.id.faq_customview_questions_list);
        FAQList_Adapter adapter = new FAQList_Adapter(getApplicationContext(),parseFAQFile());
        serviceListView.setAdapter(adapter);
		//End of user code
    }
    
    @Override
	protected void onResume() {
		super.onResume();
		refreshScreenData();
		//Start of user code onResume FAQ_CustomViewActivity
		//End of user code
	}
    //Start of user code additional code FAQ_CustomViewActivity
	public void onClickBtnSample(View view){
		showToast("sample button pressed. \nPlease customize ;-)");
    }

    /**
     * Parse the faq file to return a JsonNode[] whose each JsonNode contains a question and its answer.
     * @return
     */
    public JsonNode[] parseFAQFile(){
        try {
            JsonNode root = new ObjectMapper().readTree(new BufferedInputStream(getResources().openRawResource(R.raw.faq_profile)));
            List<JsonNode> faq_list = new ArrayList<>();
            Iterator<JsonNode> iterator = root.elements();
            while(iterator.hasNext())
                faq_list.add(iterator.next());
            return faq_list.toArray(new JsonNode[faq_list.size()]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
	//End of user code

    /** refresh screen from data 
     */
    public void refreshScreenData() {
    	//Start of user code action when refreshing the screen FAQ_CustomViewActivity
		//End of user code
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.faq_customview_actions, menu);
		// add additional programmatic options in the menu
		//Start of user code additional onCreateOptionsMenu FAQ_CustomViewActivity

		//End of user code
        return super.onCreateOptionsMenu(menu);
    }
    
	// Dealing with Activity results
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//Start of user code onActivityResult FAQ_CustomViewActivity

		//End of user code
	}
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// behavior of option menu
        switch (item.getItemId()) {
			//Start of user code additional menu action FAQ_CustomViewActivity
	
			//End of user code
			default:
                return super.onOptionsItemSelected(item);
        }
    }

}
