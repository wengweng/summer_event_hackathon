package com.icesabi.android_hackathon.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import com.icesabi.android_hackathon.R;
import com.icesabi.android_hackathon.ui.fragment.MainFragment;
//http://www.bignerdranch.com/blog/solving-the-android-image-loading-problem-volley-vs-picasso/
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment())
                    .commit();
        }
    }

}
