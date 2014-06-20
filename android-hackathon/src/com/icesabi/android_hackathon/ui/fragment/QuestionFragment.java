package com.icesabi.android_hackathon.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;

import com.icesabi.android_hackathon.MainApplication;
import com.icesabi.android_hackathon.R;
import com.icesabi.android_hackathon.ui.fragment.BuddiesFragment.IBuddiesFragment;

public class QuestionFragment extends Fragment implements OnClickListener {
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_question, container, false);
        return rootView;
    }
    
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
    	view.findViewById(R.id.button1).setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		final MainApplication app = (MainApplication) getActivity().getApplication();
		app.sendQuestion(((EditText)getView().findViewById(R.id.question_input)).getText().toString());
		
		((IBuddiesFragment)getActivity()).triggerList();
	}
}
