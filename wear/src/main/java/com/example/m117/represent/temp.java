package com.example.m117.represent;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;

/**
 * Created by M117 on 3/4/16.
 */
public class temp extends WearableActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainwatch);
       // setAmbientEnabled();

//        mContainerView = (BoxInsetLayout) findViewById(R.id.container);
//        mTextView = (TextView) findViewById(R.id.text);
//        mClockView = (TextView) findViewById(R.id.clock);
    }
}
