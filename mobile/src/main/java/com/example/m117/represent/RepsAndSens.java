package com.example.m117.represent;

/**
 * Created by M117 on 3/3/16.
 */

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

import io.fabric.sdk.android.Fabric;

public class RepsAndSens extends FragmentActivity {
    private static final String TWITTER_KEY = "pZYPeLqbsW9PxPP34IUmKPAiy";
    private static final String TWITTER_SECRET = "RId2YE2TULpaIAlTTuzW3Si5LSnIDByM6pCfBsb7nr4gQf90Cz ";


    String[][][] myReps = new String [10][][];
    String[][][] mySen = new String [2][][];
    String[][][][]  values;
    String [] myLoc= new String[]{};
    Boolean isZip = false;
    String z = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainzipcode);
        //twitter auth
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        TabHost tabHost = (TabHost)findViewById(R.id.getTabsHere);
        tabHost.setup();
        TabHost.TabSpec spec1 = tabHost.newTabSpec("Tab One");
        spec1.setIndicator("Senators");
        spec1.setContent(R.id.linearLayout2);
        tabHost.addTab(spec1);
        TabHost.TabSpec spec2 = tabHost.newTabSpec("Tab Two");
        spec2.setIndicator("Representatives");
        spec2.setContent(R.id.linearLayout3);
        tabHost.addTab(spec2);

        //get my values set from representMainPage
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String myStr = (String) extras.get("zip");
            TextView myV = (TextView) findViewById(R.id.headerMain);
            myV.setText(myStr);
            if (extras.get("zipcode") != null){
                String[] arg = (String[])extras.get("lat");
                myLoc = arg;
                isZip = true;
                z = (String) extras.get("zipcode");
                myZipHandler((String) extras.get("zipcode"), true, false, 0.0, 0.0);
            }else{
                String[] arg = (String[])extras.get("lat");
                myLoc = arg;
                myLatLngHandler("", false, false, arg[0], arg[1]);

            }
        }
    }


    public void createList(int isSen, String[][][] values){
        final ListView listView;
        final ListView listView2;

        if (isSen == 1) {
            listView = (ListView) findViewById(R.id.repList);
            listView.setAdapter(new listAdapter(this, values));
            listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            Log.d("List", "loading");


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("List", "click click");
                    String[][] itemValue = (String[][]) listView.getItemAtPosition(position);
                    Intent i = new Intent(getApplicationContext(), detailsActivity.class);
                    i.putExtra("myP", itemValue);
                    startActivity(i);

                }
            });
        }else{
            listView2 = (ListView) findViewById(R.id.senList);
            listView2.setAdapter(new listAdapter(this, values));
            listView2.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            Log.d("List", "Loading");

            listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("List", "click click");

                    String[][] itemValue = (String[][]) listView2.getItemAtPosition(position);
                    Intent i = new Intent(getApplicationContext(), detailsActivity.class);
                    i.putExtra("myP", itemValue);
                    startActivity(i);

                }

            });
        }
    }

    public String parseDataSunlight(String arg) throws JSONException {
        final BlockingQueue<String> asyncResult = new SynchronousQueue<String>();

        JSONObject myJSON = new JSONObject(arg);
        JSONArray array = myJSON.getJSONArray("results");
        myReps = new String[10][][];
        mySen = new String[2][][];
        int senCount = 0;
        int repCount = 0;
        for(int i = 0 ; i < array.length() ; i++){
            String name = array.getJSONObject(i).getString("first_name") + " " + array.getJSONObject(i).getString("last_name");
            String party;
            if (array.getJSONObject(i).getString("party").equals("D")){
                party = "Democrat";
            }else{
                party = "Republican";
            }
            String web = array.getJSONObject(i).getString("website");
            String chamber;
            if(array.getJSONObject(i).getString("chamber").equals("senate")){
                chamber = "Senator";
            }else{
                chamber = "Representative";
            }
            String end = array.getJSONObject(i).getString("term_end");
            String email = array.getJSONObject(i).getString("oc_email");
            String id = array.getJSONObject(i).getString("bioguide_id");
            String photo = "https://theunitedstates.io/images/congress/225x275/"+id+".jpg";
            //twitter_id
            final String tweetId =  array.getJSONObject(i).getString("twitter_id");

            String[] deets = new String[]{name, party,email, web, photo, tweetId, chamber, end,id};
            String[][] obj = new String[][]{deets, {}, {}, {}};
            if (chamber == "Senator"){
                mySen[senCount] = obj;
                senCount++;
            }else{
                myReps[repCount]= obj;
                repCount++;
            }

        }
        values = new String[][][][]{mySen, Arrays.copyOfRange(myReps, 0, repCount)};
        return "";
    }



    public String[] getCountyInfo(String countyName,String state) throws IOException, JSONException {
        InputStream is = getResources().openRawResource(R.raw.electioncounty);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
            is.close();
        }

        String jsonString = writer.toString();
        JSONArray myFile = new JSONArray(jsonString);
        String obamaP = "";
        String romP = "";
        for(int i = 0; i < myFile.length(); i++){
            String county = myFile.getJSONObject(i).getString("county-name");
            if (county.equals(countyName)){
                obamaP = myFile.getJSONObject(i).getString("obama-percentage");
                romP = myFile.getJSONObject(i).getString("romney-percentage");
            }

        }
        return new String[]{obamaP,romP,countyName,state};
    }

    public String[] parseLocationData(String arg) throws JSONException {
        JSONObject myJSON = new JSONObject(arg);
        JSONArray array = myJSON.getJSONArray("results");
        JSONArray address = array.getJSONObject(0).getJSONArray("address_components");
        String myName ="";
        String state = "";
        Log.d("Address Size ", ""+address.length());
        for (int i  = 0; i < address.length();i++){
            String countyName = address.getJSONObject(i).getString("long_name");
            Log.d("long_name", countyName);
            String stateN = address.getJSONObject(i).getString("short_name");
            JSONArray myTypes = address.getJSONObject(i).getJSONArray("types");
            Log.d("Types Size ", ""+myTypes.length());

            for (int j = 0; j < myTypes.length(); j++){
                if(myTypes.getString(j).equals("administrative_area_level_2")){
                    Log.d("Get Type", ""+myTypes.getString(j));

                    myName = countyName;
                }
                if(myTypes.getString(j).equals("administrative_area_level_1")){
                    Log.d("Get Type", ""+myTypes.getString(j));

                    state = stateN;
                }
            }
        }
        Log.d("myName", myName);



        try {
            return getCountyInfo(nameParse(myName),state);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String[]{" "," "};
    }

    public String nameParse(String arg){
        String[] bits  =arg.split(" ");
        String word = "";
        for (int i = 0; i < bits.length-1; i++){
            if (i == bits.length-2){
                word = word + bits[i];
            }else {
                word = word + bits[i]+ " ";
            }
        }
        return word;
    }
    //////////////////////////////
    //code for httpconnections////
    //////////////////////////////

    // When user clicks button, calls AsyncTask.
    // Before attempting to fetch the URL, makes sure that there is a network connection.
    public void myZipHandler(String arg, boolean isZip, boolean isCounty, double latitude, double longitude) {
        // Gets the URL from the UI's text field.
        String stringUrl;

        stringUrl = "https://congress.api.sunlightfoundation.com/legislators/locate?zip="+arg+"&apikey=c12953122e564eaf87453d0e9bef3208";

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new LegislatorTask().execute(stringUrl);

        } else {
            Log.d("Error:", "No network connection available.");
        }
    }

    public void myLatLngHandler(String arg, boolean isZip, boolean isCounty, String latitude, String longitude) {
        // Gets the URL from the UI's text field.
        String stringUrl;
        stringUrl = "https://congress.api.sunlightfoundation.com/legislators/locate?latitude="+latitude+"&longitude="+longitude+"&apikey=c12953122e564eaf87453d0e9bef3208\n";
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new LegislatorTask().execute(stringUrl);

        } else {
            Log.d("Error:", "No network connection available.");
        }
    }

    public void myCountyHandler( String latitude, String longitude) {
        // Gets the URL from the UI's text field.
        String stringUrl;
        stringUrl = "https://maps.googleapis.com/maps/api/geocode/json?latlng="+latitude+","+longitude+"&result_type=administrative_area_level_2&key=AIzaSyBZZsVV9tKGtW_L5yey7C7M6h_8xL25-IY";
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new CountyTask().execute(stringUrl);

        } else {
            Log.d("Error:", "No network connection available.");
        }
    }

    public void forZip(String zip) {
        // Gets the URL from the UI's text field.
        String stringUrl;
        stringUrl = "https://maps.googleapis.com/maps/api/geocode/json?address="+zip+"&region=us&result_type=administrative_area_level_2&key=AIzaSyBZZsVV9tKGtW_L5yey7C7M6h_8xL25-IY";
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new CountyTask().execute(stringUrl);

        } else {
            Log.d("Error:", "No network connection available.");
        }
    }





    // Uses AsyncTask to create a task away from the main UI thread. This task takes a
    // URL string and uses it to create an HttpUrlConnection. Once the connection
    // has been established, the AsyncTask downloads the contents of the webpage as
    // an InputStream. Finally, the InputStream is converted into a string, which is
    // displayed in the UI by the AsyncTask's onPostExecute method.
    private  class CountyTask extends AsyncTask<String, Void, String> {
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

            try {
                Log.d("myResults", result);
                String[] myD= parseLocationData(result);
                Log.d("County", myD[0]);
                Intent sendIntent = new Intent(getApplicationContext(), PtoWService.class);
                sendIntent.putExtra("watchScreen", values);
                sendIntent.putExtra("countyData", myD);
                startService(sendIntent);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class LegislatorTask extends AsyncTask<String, Void, String> {
        Context myC;

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
            try {
                parseDataSunlight(result);
                createList(0, values[0]);
                createList(1, values[1]);
                if (isZip){
                    forZip(z);
                }else {
                    myCountyHandler(myLoc[0], myLoc[1]);
                }
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

        }finally {
            if (is != null) {
                is.close();
            }
        }
    }

    // Reads an InputStream and converts it to a String.
    public String readIt(InputStream stream) throws IOException, UnsupportedEncodingException {
        String buffer = new Scanner(stream, "UTF-8").useDelimiter("\\A").next();
        return new String(buffer);
    }


}
