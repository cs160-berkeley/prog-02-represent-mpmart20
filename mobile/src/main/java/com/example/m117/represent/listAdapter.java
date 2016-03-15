package com.example.m117.represent;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;

import com.twitter.sdk.android.core.AppSession;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;

import java.util.List;


/**
 * Created by M117 on 3/9/16.
 */
public class listAdapter extends BaseAdapter {

    Context context;
    String[][][] data;
    private static LayoutInflater inflater = null;

    public listAdapter(Context context, String[][][] data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }


    @Override
    //[name, party, email, website, image,tweet]

    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.row, null);

        TextView name = (TextView) vi.findViewById(R.id.name);
        name.setText(data[position][0][0]);
        TextView party = (TextView) vi.findViewById(R.id.party);
        party.setText(data[position][0][1]);
        TextView email = (TextView) vi.findViewById(R.id.email);
        email.setText(data[position][0][2]);
        TextView web = (TextView) vi.findViewById(R.id.website);
        web.setText(data[position][0][3]);
        web.setMovementMethod(LinkMovementMethod.getInstance());
        ImageView iv = (ImageView) vi.findViewById(R.id.myPhoto);
        Picasso.with(context).load(data[position][0][4]).into(iv);

        final String tweetId = data[position][0][5];
        final View finalVi = vi;
        TwitterCore.getInstance().logInGuest(new Callback<AppSession>() {
            @Override
            public void success(Result<AppSession> appSessionResult) {
                AppSession session = appSessionResult.data;
                TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient(session);
                twitterApiClient.getStatusesService().userTimeline(null, tweetId, 1, null, null, false, false, false, true, new Callback<List<Tweet>>() {
                    String handle = "";

                    @Override
                    public void success(Result<List<Tweet>> listResult) {
                        Tweet tweet = listResult.data.get(0);
                        Log.d("fabricstuff", "result: " + tweet.text + "  " + tweet.createdAt + "  " + tweetId);
                        handle = tweet.text;
                        TextView my = (TextView) finalVi.findViewById(R.id.tweet);
                        my.setText(tweet.text);


                    }

                    @Override
                    public void failure(TwitterException e) {
                        Log.d("tweet", e.getMessage());
                        TextView my = (TextView) finalVi.findViewById(R.id.tweet);
                        my.setText("Oh no! Twitter did not find your Legislator :(");
                    }

                });
            }

            @Override
            public void failure(TwitterException e) {
                e.printStackTrace();
            }


        });

        return vi;

    }


}
