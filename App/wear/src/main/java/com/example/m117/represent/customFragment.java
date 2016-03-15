package com.example.m117.represent;

import android.app.Fragment;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by M117 on 3/11/16.
 */
public class customFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.custom_fragment, container, false);
        Button b  = (Button)view.findViewById(R.id.button);
        String strtext = getArguments().getString("header");
        TextView name = (TextView) view.findViewById(R.id.header);
        name.setText(strtext);
        strtext = getArguments().getString("content");
        final TextView content = (TextView) view.findViewById(R.id.content);
        content.setText(strtext);
        b.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Log.d("woo", "press me");
                Intent sendIntent = new Intent(getActivity(), WatchToPhoneService.class);
                sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                sendIntent.putExtra("toPhone", getArguments().getString("data"));
                getActivity().startService(sendIntent);
            }
        });
        return view;
    }






}
