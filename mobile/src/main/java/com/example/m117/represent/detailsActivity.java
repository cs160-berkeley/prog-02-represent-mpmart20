package com.example.m117.represent;

/**
 * Created by M117 on 3/3/16.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class detailsActivity extends Activity{
    ImageButton backbtn2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        addListenerOnButton();

    }

    public void addListenerOnButton() {

        final Context context = this;

        //////////////////////////////////
        //////////////////////////////////
        //////////////navigation//////////
        //////////////////////////////////
        //////////////////////////////////

        //brings you to details screen
        backbtn2 = (ImageButton) findViewById(R.id.back2);
        backbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, MainzipcodeActivity.class);
                startActivity(intent);
            }
        });
    }

}
