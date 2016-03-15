package com.example.m117.represent;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.support.wearable.view.GridPagerAdapter;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by M117 on 3/11/16.
 */
public class myGridAdapter extends FragmentGridPagerAdapter {
    private static final int TRANSITION_DURATION_MILLIS = 100;
    String county = "default";
    private Context mContext;
    String oCount= "75%";
    String rCount = "25%";
    private List<Row> mRows;
    private ColorDrawable mDefaultBg;
    private ColorDrawable mClearBg;
    String state ="";

    static final int[] BG_IMAGES = new int[] {
            R.drawable.backgroundw,
            R.drawable.backgroundw,
            R.drawable.whitew,
            R.drawable.mapw
    };


    public myGridAdapter(Context ctx, FragmentManager fm, String data) throws JSONException {
        super(fm);
        mContext = ctx;

        JSONObject obj = new JSONObject(data);
        JSONArray mySen = obj.getJSONArray("sen");
        JSONArray myReps =  obj.getJSONArray("rep");
        JSONObject myVote = obj.getJSONObject("votes");
        oCount = myVote.getString("obama")+"%";
        rCount = myVote.getString("rom")+"%";
        county = myVote.getString("county");
        state = myVote.getString("state");
        //creation of data
        mRows = new ArrayList<>();
        Fragment[] myCards = new Fragment[2];
        for(int i = 0 ; i < mySen.length() ; i++) {
            String name = mySen.getJSONObject(i).getJSONObject("details").getString("name");
            String party = mySen.getJSONObject(i).getJSONObject("details").getString("party");
            String title = "Senator (" + Character.toUpperCase(party.charAt(0)) + ")";
            //Fragment myC =  cardFragment(name,title);

            Bundle bundle = new Bundle();
            bundle.putString("header", name);
            bundle.putString("content", title);
            bundle.putString("data", mySen.getJSONObject(i).getJSONObject("details").toString());
            customFragment fragobj = new customFragment();
            fragobj.setArguments(bundle);
            myCards[i] = fragobj;

        }
        mRows.add(new Row(myCards));
        myCards = new Fragment[15];
        int counter = 0;
        for(int i = 0 ; i < myReps.length() ; i++) {
            String name = myReps.getJSONObject(i).getJSONObject("details").getString("name");
            String party = myReps.getJSONObject(i).getJSONObject("details").getString("party");
            Fragment myC = cardFragment(name, "Representative (" + Character.toUpperCase(party.charAt(0)) + ")");
            String title = "Representative (" + Character.toUpperCase(party.charAt(0)) + ")";
            //myCards[i] = myC;
            Bundle bundle = new Bundle();
            bundle.putString("header", name);
            bundle.putString("content", title);
            bundle.putString("data", myReps.getJSONObject(i).getJSONObject("details").toString());

            customFragment fragobj = new customFragment();
            fragobj.setArguments(bundle);
            myCards[i] = fragobj;
            counter++;
        }
        mRows.add(new Row(Arrays.copyOfRange(myCards, 0, counter)));

        Bundle bundle = new Bundle();
        bundle.putString("header", "2012 U.S Election");
        bundle.putString("content", "Obama vs Romney\n\nresults for\n\n"+ county+ " County, " + state);
        bundle.putString("data", data);

        electionFragment fragobj = new electionFragment();
        fragobj.setArguments(bundle);

        Bundle bundle1 = new Bundle();
        bundle1.putString("header", "Results:");
        bundle1.putString("content", "\nObama\n\n" + oCount +"\n\nRomney\n\n" + rCount);
        //bundle1.putString("data", data);

        electionFragment fragobj1 = new electionFragment();
        fragobj1.setArguments(bundle1);
        mRows.add(new Row(
                fragobj,
                fragobj1

        ));

        Bundle bundle2 = new Bundle();
        bundle2.putString("header", "Shake Your Watch!");
        bundle2.putString("content", "Explore the United States by randomly loading a new location");
        //bundle2.putString("data", data);

        mapFragment fragobj2 = new mapFragment();
        fragobj2.setArguments(bundle2);
        mRows.add(new Row(
                fragobj2

        ));

//        mRows.add(new Row(
//                cardFragment("Shake Your Watch!", "Explore the United States by randomly loading a new location"),
//                cardFragment("", "Please wait while we load your results"),
//                cardFragment("", "Updating Results")
//        ));
        mDefaultBg = new ColorDrawable();
        mClearBg = new ColorDrawable();
    }

    LruCache<Integer, Drawable> mRowBackgrounds = new LruCache<Integer, Drawable>(3) {
        @Override
        protected Drawable create(final Integer row) {
            int resid = BG_IMAGES[row % BG_IMAGES.length];
            new DrawableLoadingTask(mContext) {
                @Override
                protected void onPostExecute(Drawable result) {
                    TransitionDrawable background = new TransitionDrawable(new Drawable[] {
                            mDefaultBg,
                            result
                    });
                    mRowBackgrounds.put(row, background);
                    notifyRowBackgroundChanged(row);
                    background.startTransition(TRANSITION_DURATION_MILLIS);
                }
            }.execute(resid);
            return mDefaultBg;
        }
    };

    LruCache<Point, Drawable> mPageBackgrounds = new LruCache<Point, Drawable>(3) {
        @Override
        protected Drawable create(final Point page) {

            return GridPagerAdapter.BACKGROUND_NONE;
        }
    };

    private Fragment cardFragment(String titleRes, String textRes) {
        Resources res = mContext.getResources();
        CardFragment fragment = CardFragment.create(titleRes, textRes);
        // Add some extra bottom margin to leave room for the page indicator
        fragment.setCardMarginBottom(res.getDimensionPixelSize(R.dimen.card_margin_bottom));
        return fragment;
    }
//
//    private Fragment sFrag(String titleRes, String textRes) {
//        Resources res = mContext.getResources();
//        customFragment fragment = new customFragment().create(titleRes, textRes);
//        // Add some extra bottom margin to leave room for the page indicator
//        fragment.setCardMarginBottom(res.getDimensionPixelSize(R.dimen.card_margin_bottom));
//        return fragment;
//    }



    /** A convenient container for a row of fragments. */
    private class Row {
        final List<Fragment> columns = new ArrayList<Fragment>();

        public Row(Fragment... fragments) {
            for (Fragment f : fragments) {
                add(f);
            }
        }

        public void add(Fragment f) {
            columns.add(f);
        }
        Fragment getColumn(int i) {
            return columns.get(i);
        }
        public int getColumnCount() {
            return columns.size();
        }
    }

    @Override
    public Fragment getFragment(int row, int col) {
        Row adapterRow = mRows.get(row);
        return adapterRow.getColumn(col);
    }

    @Override
    public Drawable getBackgroundForRow(final int row) {
        return mRowBackgrounds.get(row);
    }

    @Override
    public Drawable getBackgroundForPage(final int row, final int column) {
        return mPageBackgrounds.get(new Point(column, row));
    }

    @Override
    public int getRowCount() {
        return mRows.size();
    }

    @Override
    public int getColumnCount(int rowNum) {
        return mRows.get(rowNum).getColumnCount();
    }

    class DrawableLoadingTask extends AsyncTask<Integer, Void, Drawable> {
        private static final String TAG = "Loader";
        private Context context;
        DrawableLoadingTask(Context context) {
            this.context = context;
        }
        @Override
        protected Drawable doInBackground(Integer... params) {
            Log.d(TAG, "Loading asset 0x" + Integer.toHexString(params[0]));
            return context.getResources().getDrawable(params[0]);
        }
    }
}
