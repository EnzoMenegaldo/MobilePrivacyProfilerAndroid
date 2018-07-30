package fr.inria.diverse.mobileprivacyprofiler.activities;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.inria.diverse.mobileprivacyprofiler.R;
import fr.inria.diverse.mobileprivacyprofiler.utils.AppStateViewModel;

public class AppStateFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.app_state_fragment, container, false);
        TextView app_state_tw = (TextView)view.findViewById(R.id.app_state_fragment_state);

        // Get the ViewModel.
        AppStateViewModel appStateViewModel = ViewModelProviders.of(AppStateFragment.this).get(AppStateViewModel.class);

        // Create the observer which updates the UI.
        final Observer<CharSequence> app_state_Observer = new Observer<CharSequence>() {
            @Override
            public void onChanged(@Nullable final CharSequence state) {
                // Update the UI, in this case, a TextView.
                app_state_tw.setText(state);
            }
        };

        // Observe the LiveData, passing in this fragment as the LifecycleOwner and the observer.
        appStateViewModel.getCurrentState(getContext()).observe(this, app_state_Observer);

//        app_state = Html.fromHtml(getString(R.string.app_state_fragment_inactive));


        return view;
    }
}
