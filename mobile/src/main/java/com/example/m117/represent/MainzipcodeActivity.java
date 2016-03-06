package com.example.m117.represent;

/**
 * Created by M117 on 3/3/16.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainzipcodeActivity extends FragmentActivity {

    Button button;
    Button button2;
    Button details;
    Button details2;
    Button details3;
    ImageButton backbtn;
    ImageButton nxtSen;
    ImageButton bckSen;
    ImageButton nxtRep;
    ImageButton nxtRep2;
    ImageButton bckRep;
    ImageButton bckRep2;

    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 5;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;
    private ViewPager mPager2;


    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;
    private PagerAdapter mPagerAdapter2;




    public void addListenerOnButton() {

        final Context context = this;

        //////////////////////////////////
        //////////////////////////////////
        //////////////navigation//////////
        //////////////////////////////////
        //////////////////////////////////

        //brings you to details screen
        backbtn = (ImageButton) findViewById(R.id.back);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, representActivity.class);
                startActivity(intent);
            }
        });



        //////////////////////////////////
        //////////////////////////////////
        //////////////Senators////////////
        //////////////////////////////////
        //////////////////////////////////

        //brings you to details screen
        button = (Button) findViewById(R.id.toClinton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, detailsActivity.class);
                startActivity(intent);
            }
        });

        button2 = (Button) findViewById(R.id.toPalin);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, detailsActivity.class);
                startActivity(intent);
            }
        });

        //will hopefully switch screens
        nxtSen = (ImageButton) findViewById(R.id.nextSen);
        nxtSen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                RelativeLayout frame = (RelativeLayout) findViewById(R.id.sarah);
                frame.setVisibility(View.INVISIBLE);
                RelativeLayout frame2 = (RelativeLayout) findViewById(R.id.hillary);
                frame2.setVisibility(View.VISIBLE);
            }
        });

        bckSen = (ImageButton) findViewById(R.id.bckSen);
        bckSen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                RelativeLayout frame = (RelativeLayout) findViewById(R.id.hillary);
                frame.setVisibility(View.INVISIBLE);
                RelativeLayout frame2 = (RelativeLayout) findViewById(R.id.sarah);
                frame2.setVisibility(View.VISIBLE);
            }
        });

        //////////////////////////////////
        //////////////////////////////////
        ///////Representatives////////////
        //////////////////////////////////
        //////////////////////////////////

        //brings you to details screen
        button = (Button) findViewById(R.id.toClinton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, detailsActivity.class);
                startActivity(intent);
            }
        });

        button2 = (Button) findViewById(R.id.toPalin);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, detailsActivity.class);
                startActivity(intent);
            }
        });

        //will hopefully switch screens
        nxtRep = (ImageButton) findViewById(R.id.nxtRep);
        nxtRep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                RelativeLayout frame = (RelativeLayout) findViewById(R.id.initialRep);
                frame.setVisibility(View.INVISIBLE);
                RelativeLayout frame2 = (RelativeLayout) findViewById(R.id.midRep);
                frame2.setVisibility(View.VISIBLE);
            }
        });

        nxtRep2 = (ImageButton) findViewById(R.id.nxtRep2);
        nxtRep2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                RelativeLayout frame = (RelativeLayout) findViewById(R.id.midRep);
                frame.setVisibility(View.INVISIBLE);
                RelativeLayout frame2 = (RelativeLayout) findViewById(R.id.endReps);
                frame2.setVisibility(View.VISIBLE);
            }
        });

        bckRep = (ImageButton) findViewById(R.id.bckRep);
        bckRep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                RelativeLayout frame = (RelativeLayout) findViewById(R.id.midRep);
                frame.setVisibility(View.INVISIBLE);
                RelativeLayout frame2 = (RelativeLayout) findViewById(R.id.initialRep);
                frame2.setVisibility(View.VISIBLE);
            }
        });

        bckRep2 = (ImageButton) findViewById(R.id.bckRep2);
        bckRep2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                RelativeLayout frame = (RelativeLayout) findViewById(R.id.endReps);
                frame.setVisibility(View.INVISIBLE);
                RelativeLayout frame2 = (RelativeLayout) findViewById(R.id.midRep);
                frame2.setVisibility(View.VISIBLE);
            }
        });

        //brings you to details screen
        details = (Button) findViewById(R.id.toJenkins);
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, detailsActivity.class);
                startActivity(intent);
            }
        });

        details2 = (Button) findViewById(R.id.toLong);
        details2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, detailsActivity.class);
                startActivity(intent);
            }
        });

        details3 = (Button) findViewById(R.id.toJackson);
        details3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, detailsActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainzipcode);
        addListenerOnButton();


//
//        mPager = (ViewPager) findViewById(R.id.senPager);
//        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
//        mPager.setAdapter(mPagerAdapter);

//        mPager2 = (ViewPager) findViewById(R.id.repPager);
//        mPagerAdapter2 = new ScreenSlidePagerAdapter(getSupportFragmentManager());
//        mPager2.setAdapter(mPagerAdapter2);

    }

    public boolean onEditorAction(TextView exampleView, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_DOWN) {
            ;//match this behavior to your 'Send' (or Confirm) button
        }
        return true;
    }



    public void onBackPressed() {
//        if (mPager.getCurrentItem() == 0) {
//            // If the user is currently looking at the first step, allow the system to handle the
//            // Back button. This calls finish() on this activity an  d pops the back stack.
//            super.onBackPressed();
//        } else {
//            // Otherwise, select the previous step.
//            if(mPager.getParent()!=null)
//                ((ViewGroup)mPager.getParent()).removeView(mPager);
//            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
//        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new ScreenSlidePageFragment();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
