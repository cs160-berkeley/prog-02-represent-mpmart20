package com.example.m117.represent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.support.wearable.activity.WearableActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class representActivity extends WearableActivity {
    View myView;
    View myView2;
    private float x1,x2,y1,y2;
    static final int MIN_DISTANCE = 150;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainwatch);
        addListenerOnView();
    }

    public void addListenerOnView() {
        final Context context = this;

        myView = findViewById(R.id.hillary);
        myView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = MotionEventCompat.getActionMasked(event);
                switch(action) {
                    case (MotionEvent.ACTION_DOWN) :
                        x1 = event.getX();
//                        Intent intent = new Intent(context, repSlides.class);
//                        startActivity(intent);
                        return true;
                    case (MotionEvent.ACTION_MOVE) :

//                        RelativeLayout frame = (RelativeLayout) findViewById(R.id.hillary);
//                        frame.setVisibility(View.INVISIBLE);
//                        RelativeLayout frame2 = (RelativeLayout) findViewById(R.id.palin);
//                        frame2.setVisibility(View.VISIBLE);
                        return true;
                    case (MotionEvent.ACTION_UP) :
                        x2 = event.getX();
                        float deltaX = x2 - x1;

                        if (Math.abs(deltaX) > MIN_DISTANCE)
                        {
                            // Left to Right swipe action
                            if (x2 > x1)
                            {
                                RelativeLayout frame = (RelativeLayout) findViewById(R.id.hillary);
                                frame.setVisibility(View.INVISIBLE);
                                RelativeLayout frame2 = (RelativeLayout) findViewById(R.id.palin);
                                frame2.setVisibility(View.VISIBLE);
                            }

                            // Right to left swipe action
                            else
                            {
                                RelativeLayout frame = (RelativeLayout) findViewById(R.id.hillary);
                                frame.setVisibility(View.INVISIBLE);
                                RelativeLayout frame2 = (RelativeLayout) findViewById(R.id.palin);
                                frame2.setVisibility(View.VISIBLE);
                            }

                        }else
                        {
                            // consider as something else - a screen tap for example
                            Intent intent = new Intent(context, repSlides.class);
                            startActivity(intent);
                        }
                        return true;
                    case (MotionEvent.ACTION_CANCEL) :
//                        RelativeLayout frame3 = (RelativeLayout) findViewById(R.id.hillary);
//                        frame3.setVisibility(View.INVISIBLE);
//                        RelativeLayout frame4 = (RelativeLayout) findViewById(R.id.palin);
//                        frame4.setVisibility(View.VISIBLE);
                        return true;
                    case (MotionEvent.ACTION_OUTSIDE) :
                        return true;
                    default :
                        return true;
                }
            }

        });

        myView2 = findViewById(R.id.palin);
        myView2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = MotionEventCompat.getActionMasked(event);
                switch(action) {
                    case (MotionEvent.ACTION_DOWN) :
                        x1 = event.getX();
                        y1 = event.getY();

//                        Intent intent = new Intent(context, repSlides.class);
//                        startActivity(intent);
                        //return true
                        break;
                    case (MotionEvent.ACTION_MOVE) :
//                        RelativeLayout frame = (RelativeLayout) findViewById(R.id.palin);
//                        frame.setVisibility(View.INVISIBLE);
//                        RelativeLayout frame2 = (RelativeLayout) findViewById(R.id.hillary);
//                        frame2.setVisibility(View.VISIBLE)
//  ;
                    case (MotionEvent.ACTION_UP) :
                        x2 = event.getX();
                        y2 = event.getY();

                        //if left to right sweep event on screen
                        if (x1 < x2)
                        {
                            RelativeLayout frame3 = (RelativeLayout) findViewById(R.id.palin);
                            frame3.setVisibility(View.INVISIBLE);
                            RelativeLayout frame4 = (RelativeLayout) findViewById(R.id.hillary);
                            frame4.setVisibility(View.VISIBLE);                        }

                        // if right to left sweep event on screen
                        if (x1 > x2)
                        {
                            RelativeLayout frame = (RelativeLayout) findViewById(R.id.palin);
                            frame.setVisibility(View.INVISIBLE);
                            RelativeLayout frame2 = (RelativeLayout) findViewById(R.id.hillary);
                            frame2.setVisibility(View.VISIBLE);                        }

                        // if UP to Down sweep event on screen
                        if (y1 < y2)
                        {
                        }

                        //if Down to UP sweep event on screen
                        if (y1 > y2){
                            Intent intent = new Intent(context, repSlides.class);
                            startActivity(intent);
                        }
                        return true;
                    case (MotionEvent.ACTION_CANCEL) :
//                        RelativeLayout frame3 = (RelativeLayout) findViewById(R.id.palin);
//                        frame3.setVisibility(View.INVISIBLE);
//                        RelativeLayout frame4 = (RelativeLayout) findViewById(R.id.hillary);
//                        frame4.setVisibility(View.VISIBLE);
                        return true;
                    case (MotionEvent.ACTION_OUTSIDE) :
                        return true;
                    default :
                        return true;
                }
                return true;

            };

        });

    }
}
