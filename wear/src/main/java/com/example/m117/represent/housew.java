package com.example.m117.represent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.support.wearable.activity.WearableActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class housew extends WearableActivity {
    View myView;
    View myView2;

    private float x1,x2,y1,y2;
    static final int MIN_DISTANCE = 150;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.whitehouse);
        addListenerOnView();
    }

    public void addListenerOnView() {
        final Context context = this;


        myView = findViewById(R.id.mainwhite);
        myView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = MotionEventCompat.getActionMasked(event);
                switch(action) {
                    case (MotionEvent.ACTION_DOWN) :
                        x1 = event.getX();
                        y1 = event.getY();
                        return true;
                    case (MotionEvent.ACTION_MOVE) :

                        return true;
                    case (MotionEvent.ACTION_UP) :
                        x2 = event.getX();
                        y2 = event.getY();

                        //if left to right sweep event on screen
                        if (x1 < x2)
                        {
//                            RelativeLayout frame3 = (RelativeLayout) findViewById(R.id.first);
//                            frame3.setVisibility(View.INVISIBLE);
//                            RelativeLayout frame4 = (RelativeLayout) findViewById(R.id.end);
//                            frame4.setVisibility(View.VISIBLE);
                        }

                        // if right to left sweep event on screen
                        if (x1 > x2)
                        {
//                            RelativeLayout frame = (RelativeLayout) findViewById(R.id.first);
//                            frame.setVisibility(View.INVISIBLE);
//                            RelativeLayout frame2 = (RelativeLayout) findViewById(R.id.mid);
//                            frame2.setVisibility(View.VISIBLE);
                        }

                        // if UP to Down sweep event on screen
                        if (y1 < y2)
                        {
                            Intent intent = new Intent(context, repSlides.class);
                            startActivity(intent);
                        }

                        //if Down to UP sweep event on screen //add going to map
                        if (y1 > y2){
                            Intent intent = new Intent(context, repSlides.class);
                            startActivity(intent);

                        }
                        return true;
                    case (MotionEvent.ACTION_CANCEL) :
                        return true;
                    case (MotionEvent.ACTION_OUTSIDE) :
                        return true;
                    default :
                        return true;
                }
            }

        });

//        myView2 = findViewById(R.id.mid);
//        myView2.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int action = MotionEventCompat.getActionMasked(event);
//                switch(action) {
//                    case (MotionEvent.ACTION_DOWN) :
//                        x1 = event.getX();
//                        y1 = event.getY();
//                        return true;
//                    case (MotionEvent.ACTION_MOVE) :
////
//                        return true;
//                    case (MotionEvent.ACTION_UP) :
//                        x2 = event.getX();
//                        y2 = event.getY();
//
//                        //if left to right sweep event on screen
//                        if (x1 < x2)
//                        {
//                            RelativeLayout frame3 = (RelativeLayout) findViewById(R.id.mid);
//                            frame3.setVisibility(View.INVISIBLE);
//                            RelativeLayout frame4 = (RelativeLayout) findViewById(R.id.first);
//                            frame4.setVisibility(View.VISIBLE);
//                        }
//
//                        // if right to left sweep event on screen
//                        if (x1 > x2)
//                        {
//                            RelativeLayout frame = (RelativeLayout) findViewById(R.id.mid);
//                            frame.setVisibility(View.INVISIBLE);
//                            RelativeLayout frame2 = (RelativeLayout) findViewById(R.id.end);
//                            frame2.setVisibility(View.VISIBLE);
//                        }
//
//                        // if UP to Down sweep event on screen
//                        if (y1 < y2)
//                        {
//                            Intent intent = new Intent(context, representActivity.class);
//                            startActivity(intent);
//                        }
//
//                        //if Down to UP sweep event on screen //add going to house
//                        if (y1 > y2){
//
//                        }
//                        return true;
//                    case (MotionEvent.ACTION_CANCEL) :
////
//                        return true;
//                    case (MotionEvent.ACTION_OUTSIDE) :
//                        return true;
//                    default :
//                        return true;
//                }
//            }
//
//        });


    }
}