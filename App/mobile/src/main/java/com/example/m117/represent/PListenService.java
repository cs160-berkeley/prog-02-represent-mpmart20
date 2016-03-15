package com.example.m117.represent;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class PListenService extends WearableListenerService {

    //   WearableListenerServices don't need an iBinder or an onStartCommand: they just need an onMessageReceieved.
    private static final String TOAST = "/send_toast";

    public String[][] getData(String arg) throws JSONException {
        JSONObject myCurr = new JSONObject(arg);
        String [][] temp = new String[][]{};
        String name = myCurr.getString("name");
        String party = myCurr.getString("party");
        String email = myCurr.getString("email");
        String web = myCurr.getString("web");
        String photo = myCurr.getString("photo");
        String tweetId = myCurr.getString("tweet");
        String chamber = myCurr.getString("type");
        String end = myCurr.getString("end");
        String id = myCurr.getString("id");

        return new String[][]{{name, party,email, web, photo, tweetId, chamber, end,id},{},{}};

    };

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("T", "in PhoneListenerService, got: " + messageEvent.getPath());
        Log.d("hacky JSON", new String(messageEvent.getData(), StandardCharsets.UTF_8));

        Intent intent2 = new Intent(getApplicationContext(),detailsActivity.class );
        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            intent2.putExtra("myP", getData(new String(messageEvent.getData(), StandardCharsets.UTF_8)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        startActivity(intent2);

    }
}

