package com.example.m117.represent;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by M117 on 3/10/16.
 */
public class PtoWService extends Service {

    private GoogleApiClient mApiClient;
    String mymessage = "";

    @Override
    public void onCreate() {
        Log.d("Made phone to watch? ", "true");

        super.onCreate();
        //initialize the googleAPIClient for message passing
        mApiClient = new GoogleApiClient.Builder( this )
                .addApi( Wearable.API )
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle connectionHint) {
                    }

                    @Override
                    public void onConnectionSuspended(int cause) {
                    }
                })
                .build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mApiClient.disconnect();
    }

    public String convertValues(String[][][][] arg1, String[] cData) throws JSONException {
        JSONObject data = new JSONObject();
        for(int i = 0; i< arg1.length; i++){
            JSONArray mylist = new JSONArray();

            for (int j = 0; j < arg1[i].length; j ++){
                JSONObject person = new JSONObject();
                for (int x = 0; x< arg1[i][j].length; x++){
                    if(x == 0){
                        JSONObject deets = new JSONObject();
                        deets.put("name",arg1[i][j][x][0]);
                        deets.put("party",arg1[i][j][x][1]);
                        deets.put("email",arg1[i][j][x][2]);
                        deets.put("web",arg1[i][j][x][3]);
                        deets.put("photo",arg1[i][j][x][4]);
                        deets.put("tweet",arg1[i][j][x][5]);
                        deets.put("type",arg1[i][j][x][6]);
                        deets.put("end",arg1[i][j][x][7]);
                        deets.put("id",arg1[i][j][x][8]);
                        person.put("details", deets);
                        continue;
                    }else if (x == 1){
                        JSONArray list = new JSONArray();
                        for(int y = 0; y< arg1[i][j][x].length; y++){
                        list.put(arg1[i][j][x][i]);
                        }
                        person.put("committees", list);
                    }else if( x ==2){
                        JSONArray list = new JSONArray();

                        for(int y = 0; y< arg1[i][j][x].length; y++){
                            JSONObject bill = new JSONObject();
                            bill.put("name",arg1[i][j][2][i]);
                            bill.put("date",arg1[i][j][3][i]);
                            list.put(bill);
                        }
                        person.put("bills", list);
                    }
                }
                mylist.put( person);
            }
            if (i ==0){
                data.put("sen", mylist);
            }else{
                data.put("rep",mylist);
            }

        }

        JSONObject c = new JSONObject();
        c.put("state", cData[3]);
        c.put("county", cData[2]);
        c.put("rom", cData[1]);
        c.put("obama",cData[0]);
        data.put("votes",c);
        return data.toString();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // which was passed over when we called startService
        Bundle extras = intent.getExtras();
        final String[][][][] myInfo = (String[][][][])extras.get("watchScreen");
        final String[] county = (String[])extras.get("countyData");

        try {
             mymessage = convertValues(myInfo,county);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Send the message with the cat name
        new Thread(new Runnable() {
            @Override
            public void run() {
                //first, connect to the apiclient
                mApiClient.connect();
                //now that you're connected, send a massage with the cat name
                sendMessage("/" + myInfo[0][0][0][1],mymessage);
                Log.d("sending shit", "hopefully");

            }
        }).start();

        return START_STICKY;
    }

    @Override //remember, all services need to implement an IBiner
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void sendMessage( final String path, final String text ) {
        //one way to send message: start a new thread and call .await()
        new Thread( new Runnable() {
            @Override
            public void run() {
                NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes( mApiClient ).await();
                for(Node node : nodes.getNodes()) {
                    //we find 'nodes', which are nearby bluetooth devices (aka emulators)
                    //send a message for each of these nodes (just one, for an emulator)
                    MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(
                            mApiClient, node.getId(), path, text.getBytes() ).await();
                    //4 arguments: api client, the node ID, the path (for the listener to parse),
                    //and the message itself (you need to convert it to bytes.)
                }
            }
        }).start();
    }

}
