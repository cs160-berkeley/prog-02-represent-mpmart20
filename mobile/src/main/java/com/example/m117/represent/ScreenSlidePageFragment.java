package com.example.m117.represent;

/**
 * Created by M117 on 3/3/16.
 */

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.RelativeLayout;

public class ScreenSlidePageFragment extends Fragment {


    //thank you developer.android
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rv =  inflater.inflate(R.layout.mainzipcode, container, false);
        View rtn = rv.findViewById(R.id.senatorContent);
//        if(rtn.getParent()!=null) {
//            container.removeView(rtn);
//        }
        return rtn;
    }


}
