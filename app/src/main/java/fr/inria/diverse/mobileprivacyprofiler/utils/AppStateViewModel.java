package fr.inria.diverse.mobileprivacyprofiler.utils;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.text.Html;

import fr.inria.diverse.mobileprivacyprofiler.R;

public class AppStateViewModel extends ViewModel{

    private static MutableLiveData<CharSequence> currentState ;

    public static MutableLiveData<CharSequence> getCurrentState(Context context) {
        if (currentState == null) {
            currentState = new MutableLiveData<>();
            currentState.setValue(Html.fromHtml(context.getString(R.string.app_state_fragment_inactive)));
        }
        return currentState;
    }

    public static boolean isCollectionRunning(Context context){
        if(currentState.getValue() == null)
            return false;
        if(getCurrentState(context).getValue().toString().equals((Html.fromHtml(context.getString(R.string.app_state_fragment_inactive))).toString()))
            return false;
        return true;
    }

}
