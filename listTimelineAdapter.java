package com.pam.pieter.tgs_plbtw_kos;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.SimpleAdapter;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by pieter on 11/21/2016.
 */
public class listTimelineAdapter extends SimpleAdapter
{
    List<ModelKos> KosList;
    private Context context;


    private ArrayList<HashMap<String, String>> data;

    private LayoutInflater inflater;
    int i=0;

    private static final String TAG_PESAN="message";
    private static final String TAG_HASIL="result";
    JSONParser jParser = new JSONParser();
    JSONArray jsonarray = null;

    String username,id_timeline,my_username;
    GestureDetector gestureDetector;

    private SharedPreferences sharedpreferences;
    private final String name="myAccount";
    private static final int mode = Activity.MODE_PRIVATE;

    public listTimelineAdapter(Context context, ArrayList<HashMap<String, String>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        Log.d("", "----------->Array " + data);
        this.data = data;
        this.context=context;

        sharedpreferences=context.getSharedPreferences(name, mode);
        my_username = sharedpreferences.getString("username","");

        gestureDetector = new GestureDetector(context,new GestureListener());
    }

    public class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
        // event when double tap occurs

    }
}
