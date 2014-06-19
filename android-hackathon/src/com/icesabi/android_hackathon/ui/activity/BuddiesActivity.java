package com.icesabi.android_hackathon.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import com.icesabi.android_hackathon.R;
import com.icesabi.android_hackathon.ui.fragment.BuddiesFragment;

public class BuddiesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new BuddiesFragment())
                    .commit();
        }
    }

}