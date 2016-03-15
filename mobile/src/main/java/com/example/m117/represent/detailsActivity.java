package com.example.m117.represent;

/**
 * Created by M117 on 3/3/16.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class detailsActivity extends Activity{
    String[][] values;
    String[] committees;
    String[] bills;
    String[] billsD;
    String myId;
    int commiteeCount = 0;
    int billCount = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //[name, party, email, website, image,tweet, title, term]

            RelativeLayout view = (RelativeLayout) findViewById(R.id.deets);

            values = (String[][]) extras.get("myP");

            Log.d("passed on:", values[0][1]);

            TextView name = (TextView) view.findViewById(R.id.selectedName);
            name.setText(values[0][0]);
            TextView party = (TextView) view.findViewById(R.id.fullTitle);
            String temp = values[0][1] + " "+ values[0][6];
            party.setText(temp);
            TextView term = (TextView) view.findViewById(R.id.term);
            term.setText("End Term: "+values[0][7]);
            myId = values[0][8];
            ImageView iv = (ImageView) view.findViewById(R.id.deetPhoto);
            Picasso.with(getApplicationContext()).load(values[0][4]).into(iv);


        }
        //addListenerOnButton();

        TabHost tabHost = (TabHost)findViewById(R.id.deetsTab);
        tabHost.setup();
        TabHost.TabSpec spec1 = tabHost.newTabSpec("Tab One");
        spec1.setIndicator("Committee(s)");
        spec1.setContent(R.id.myC);
        tabHost.addTab(spec1);
        TabHost.TabSpec spec2 = tabHost.newTabSpec("Tab Two");
        spec2.setIndicator("Bill(s)");
        spec2.setContent(R.id.myB);
        tabHost.addTab(spec2);
        myCommitteeExtractor(myId);


    }

    public void createList(int isC,String[] values, String[] values2) {

        final ListView listView;
        final ListView listView2;

        //[name, party, email, website, image,tweet, title, term]
        if (isC == 1) {
            listView = (ListView) findViewById(R.id.viewC);
            listView.setAdapter(new detailsAdapter(this, values));

        } else {
            listView2 = (ListView) findViewById(R.id.viewB);
            listView2.setAdapter(new billsAdapter(this, values,values2));


        }
    }



    //////////////////////////////
    //code for httpconnections////
    //////////////////////////////

    public void myCommitteeExtractor(String arg) {
        // Gets the URL from the UI's text field.
        String stringUrl;

        stringUrl = "https://congress.api.sunlightfoundation.com/committees?member_ids="+arg+"&apikey=c12953122e564eaf87453d0e9bef3208";

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new CTask().execute(stringUrl);

        } else {
            Log.d("Error:", "No network connection available.");
        }
    }

    public void mybills(String arg) {
        // Gets the URL from the UI's text field.
        String stringUrl;

        stringUrl = "https://congress.api.sunlightfoundation.com/bills/search?sponsor_id="+arg+"&apikey=c12953122e564eaf87453d0e9bef3208";

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new BTask().execute(stringUrl);

        } else {
            Log.d("Error:", "No network connection available.");
        }
    }


    // Uses AsyncTask to create a task away from the main UI thread. This task takes a
    // URL string and uses it to create an HttpUrlConnection. Once the connection
    // has been established, the AsyncTask downloads the contents of the webpage as
    // an InputStream. Finally, the InputStream is converted into a string, which is
    // displayed in the UI by the AsyncTask's onPostExecute method.
    private  class CTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);

            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            Log.d("myc", result);
            try {
                parseC(result);
                mybills(myId);
                createList(1, committees, new String[]{});
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class BTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.

            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Log.d("myc", result);

            try {
                parseBill(result);
               createList(2, bills, billsD);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    // Given a URL, establishes an HttpUrlConnection and retrieves
    // the web page content as a InputStream, which it returns as
    // a string.
    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("downloadUrl", "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }


    // Reads an InputStream and converts it to a String.
    public String readIt(InputStream stream) throws IOException, UnsupportedEncodingException {
        String buffer = new Scanner(stream, "UTF-8").useDelimiter("\\A").next();
        Log.d("read", buffer);
        return new String(buffer);
    }

    public void parseC(String arg) throws JSONException {
        JSONObject myJSON = new JSONObject(arg);
        JSONArray array = myJSON.getJSONArray("results");
        Log.d("ArrayLen", new Integer(array.length()).toString());
        committees = new String[array.length()];
        for(int i = 0 ; i < array.length() ; i++){
            committees[i] = array.getJSONObject(i).getString("name");
        }
       // values = new String[][][][]{mySen, Arrays.copyOfRange(myReps, 0, repCount)};
    }

    public void parseBill(String arg) throws JSONException {
        JSONObject myJSON = new JSONObject(arg);
        JSONArray array = myJSON.getJSONArray("results");
        Log.d("ArrayLen", new Integer(array.length()).toString());
        bills = new String[array.length()];
        billsD = new String[array.length()];
        for(int i = 0 ; i < array.length() ; i++) {
            bills[i] = array.getJSONObject(i).getString("bill_id");
            billsD[i] = array.getJSONObject(i).getString("introduced_on");
            billCount++;
        }

    }

}
