package com.example.m117.represent;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class representActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "pZYPeLqbsW9PxPP34IUmKPAiy";
    private static final String TWITTER_SECRET = "RId2YE2TULpaIAlTTuzW3Si5LSnIDByM6pCfBsb7nr4gQf90Cz ";


    ImageButton button;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Double myLat;
    Double myLong;
    int count;
    String[][][] myReps = new String [10][][];
    String[][][] mySen = new String [2][][];
    String[][][][] itemValue = new String[][][][]

            {
                    {

                            {
                                    {"Winston", "Democrat", "hclinton@hotmail.com", "myweb.com", "rom", "alaska is great", "senator", "December 2016"},
                                    {"bullets", "test", "test1", "test2", "test3"},
                                    {"bill#1", "bill#3", "bill#5", "bill#2"},
                                    {"05 Dec 2016", "11 Nov 2016", "", "12 Nov 2013"}
                            },
                            {
                                    {"Leroy", "Republican", "hclinton@hotmail.com", "myweb.com", "p", "alaska is great", "senator", "January 2015"},
                                    {"best", "test", "test1", "test2", "test3"},
                                    {"bill#1", "bill#3", "bill#5", "bill#2"},
                                    {"05 Dec 2016", "11 Nov 2016", "", "12 Nov 2013"}
                            },
                            {
                                    {"Winston", "Democrat", "hclinton@hotmail.com", "myweb.com", "rom", "alaska is great", "senator", "May 2015"},
                                    {"test", "test1", "test2", "test3"},
                                    {"bill#1", "bill#3", "bill#5", "bill#2"},
                                    {"05 Dec 2016", "11 Nov 2016", "", "12 Nov 2013"}
                            },
                            {
                                    {"Leroy", "Democrat", "hclinton@hotmail.com", "myweb.com", "p", "alaska is great", "senator", "July 2012"},
                                    {"test", "test1", "test2", "test3"},
                                    {"bill#1", "bill#3", "bill#5", "bill#2"},
                                    {"05 Dec 2016", "11 Nov 2016", "", "12 Nov 2013"}
                            }
                    },

                    {
                            {
                                    {"Hillary Clinton", "Democrat", "hclinton@hotmail.com", "myweb.com", "h", "alaska is great", "representative", "May 2015"},
                                    {"test", "test1", "test2", "test3"},
                                    {"bill#1", "bill#3", "bill#5", "bill#2"},
                                    {"05 Dec 2016", "11 Nov 2016", "", "12 Nov 2013"}
                            },
                            {
                                    {"Sarah Palin", "Democrat", "hclinton@hotmail.com", "myweb.com", "bush", "alaska is great", "representative", "June 2016"},
                                    {"qoo", "test", "test1", "test2", "test3"},
                                    {"bill#1", "bill#3", "bill#5", "bill#2"},
                                    {"05 Dec 2016", "11 Nov 2016", "", "12 Nov 2013"}
                            }

                    }
            };




    ////////////////////////
    //code for lat, long////
    ////////////////////////

    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
        Toast.makeText(this, "Failed to connect...", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onConnected(Bundle arg0) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            Double latitude = mLastLocation.getLatitude();
            Double longitude = mLastLocation.getLongitude();
            myLat = latitude;
            myLong = longitude;
        }
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        Toast.makeText(this, "Connection suspended...", Toast.LENGTH_SHORT).show();
    }
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_represent);
        addListenerOnButton();


        //makes the connection
        buildGoogleApiClient();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        } else {
            Toast.makeText(this, "Not connected...", Toast.LENGTH_SHORT).show();
        }

        final Context context = this;
        ((EditText) findViewById(R.id.editText)).setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                            Intent intent = new Intent(context, RepsAndSens.class);
                            intent.putExtra("zip", " Zip Code:" + v.getText().toString());
                            intent.putExtra("zipcode", v.getText().toString());
                            intent.putExtra("lat", new String[]{myLat.toString(),myLong.toString()});


                            startActivity(intent);
                            return true;
                        }
                        return false;
                    }
                });

    }

    public void addListenerOnButton() {

        final Context context = this;
        button = (ImageButton) findViewById(R.id.toMain);

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, RepsAndSens.class);
                intent.putExtra("zip", "" + myLat + "," + myLong);
                intent.putExtra("lat", new String[]{myLat.toString(),myLong.toString()});
                startActivity(intent);
            }
        });

    }



}
