package com.icesabi.android_hackathon.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.icesabi.android_hackathon.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class BuddiesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        return rootView;
    }
    
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
    	view.findViewById(R.id.button_find_buddy).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
    }
}