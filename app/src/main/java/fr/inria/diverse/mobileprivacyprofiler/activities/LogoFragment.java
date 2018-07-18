package fr.inria.diverse.mobileprivacyprofiler.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.inria.diverse.mobileprivacyprofiler.R;

public class LogoFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.logo_fragment, container, false);
    }

}
