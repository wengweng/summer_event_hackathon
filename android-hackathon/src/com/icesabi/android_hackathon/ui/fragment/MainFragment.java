package com.icesabi.android_hackathon.ui.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.icesabi.android_hackathon.R;
import com.icesabi.android_hackathon.WiFiDirectDiscoveryActivity;
import com.icesabi.android_hackathon.ui.activity.BuddiesActivity;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment {

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
				Intent intent = new Intent(getActivity(),BuddiesActivity.class);
				startActivity(intent);
			}
		});
    	
    	view.findViewById(R.id.button_start_wifi_direct).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), WiFiDirectDiscoveryActivity.class);
				getActivity().startActivity(intent);
			}
		});
    }
}