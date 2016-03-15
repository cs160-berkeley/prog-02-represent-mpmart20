package com.example.m117.represent;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by M117 on 3/14/16.
 */
public class mapFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map, container, false);
        String strtext = getArguments().getString("header");
        TextView name = (TextView) view.findViewById(R.id.header);
        name.setText(strtext);
        strtext = getArguments().getString("content");
        TextView content = (TextView) view.findViewById(R.id.content);
        content.setText(strtext);
        view.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Log.d("woo", "press my shit");
            }
        });
        return view;
    }






}
