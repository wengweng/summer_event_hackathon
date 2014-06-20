package com.icesabi.android_hackathon.ui.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;

import com.icesabi.android_hackathon.R;
import com.icesabi.android_hackathon.ui.fragment.BuddiesFragment;
import com.icesabi.android_hackathon.ui.fragment.BuddiesFragment.IBuddiesFragment;
import com.icesabi.android_hackathon.ui.fragment.QuestionFragment;

import domain.Buddy;

public class BuddiesActivity extends Activity implements IBuddiesFragment {

	List<Buddy> clicked = new ArrayList<Buddy>();
	
	 public enum STEP {
		BUDDIES, QUESTION
	}
	 
	 STEP mStep;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStep = STEP.BUDDIES;
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            BuddiesFragment fragment = new BuddiesFragment();
			getFragmentManager().beginTransaction()
                    .add(R.id.container, fragment, "buddies_fragment")
                    .commit();
        }
    }

	@Override
	public void triggerQuestion() {
		mStep = STEP.QUESTION;
		QuestionFragment fragment = new QuestionFragment();
		getFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, "question_fragment").setTransition(android.R.anim.fade_in)
                .commit();
	}

	@Override
	public void onBuddySelected(Buddy buddy, boolean selected) {
		int pos = 0;
		for (Buddy myBuddy:clicked) {
			if (myBuddy.getName().equalsIgnoreCase(buddy.getName())) {
				break;
			}
			pos++;
		}
		if (pos <= (clicked.size() - 1) ) {
			if (selected) {
				// Do nothing
			}
			else {
				clicked.remove(pos);
			}
		}
		else {
			if (selected) {
				clicked.add(buddy);
			}
		}
	}
	
	@Override
	public void onBackPressed(){
		switch (mStep) {
		case BUDDIES:
			finish();
			break;
		case QUESTION:
			mStep = STEP.BUDDIES;
			BuddiesFragment fragment = new BuddiesFragment();
			getFragmentManager().beginTransaction()
			.replace(R.id.container, fragment, "buddies_fragment").setTransition(android.R.anim.fade_in)
			.commit();
			break;
			default:
				break;
		}
	}

	@Override
	public List<Buddy> getCheckedBuddies() {
		return clicked;
	}

}
