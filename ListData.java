package com.pam.pieter.tgs_plbtw_kos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ListData extends AppCompatActivity {

    private SharedPreferences sharedpreferences;
    private final String name="myAccount";
    private static final int mode = Activity.MODE_PRIVATE;
    private String username;

    private static final String URL_DAFTARKOS="http://infokosjogja.pe.hu/infokosjogja/index.php/api/CariKos/searchAll";
    private static final String URL_DAFTARCARI="http://infokosjogja.pe.hu/infokosjogja/index.php/api/CariKos/search";
    private static final String TAG_PESAN="message";
    private static final String TAG_HASIL="result";
    private static final String API_KEY="a7a1c17b30c9ab9387d1fdea27372caccbda5900";

    private  static final String TAG_IDKOS="id_kos";
    private  static final String TAG_namakos="nama_kos";
    private static  final String TAG_pemilik="pemilik";
    private static  final String TAG_alamat="alamat";
    private static  final String TAG_daerah="no_telp";
    private static  final String TAG_harga="harga";
    private static  final String TAG_fasilitas="fasilitas";
    private static  final String TAG_picture="picture";

    JSONParser jParser = new JSONParser();
    JSONArray jsonarray = null;
    ListView listView;
    ArrayList<HashMap<String, String>> arrayListTimeline;
    Button btnLogout,btnSearch;
    TextView lblUsername;
    EditText edCari;
    String api,daerah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);
        sharedpreferences = getSharedPreferences(name, mode);
        username = sharedpreferences.getString("username", "");
        listView = (ListView) findViewById(R.id.lvdata);
        btnLogout = (Button) findViewById(R.id.btnlogout);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        lblUsername = (TextView) findViewById(R.id.lblUsername);
        lblUsername.setText("Logged in as : " + username);
        edCari = (EditText) findViewById(R.id.edCari);


        username=lblUsername.getText().toString();
        api=API_KEY;


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("status", "0");
                editor.apply();
                finish();
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });

        /*btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                username=lblUsername.getText().toString();
                daerah = edCari.getText().toString();
                api=API_KEY;
                new GetTimelineSearchJSON().execute();
            }
        });*/

        new GetTimelineJSON().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w("MainActivity", "onResume");

        listView.setAdapter(null);
        username=lblUsername.getText().toString();
        api=API_KEY;
        new GetTimelineJSON().execute();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w("MainActivity", "onPause");

        listView.setAdapter(null);
        username=lblUsername.getText().toString();
        api=API_KEY;
        new GetTimelineJSON().execute();
    }


    class GetTimelineJSON extends AsyncTask<String,String,String> {
        String sukses = "";
        Context context;
        String username,daerah;
        String api;

        /*public GetTimelineJSON() {

        }
        public GetTimelineJSON(Context context, String username,String api) {
            this.context = context;
            this.username = username;
            this.api = API_KEY;
        }*/

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args)
        {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("api_key", API_KEY));
            try
            {
                JSONObject json= jParser.makeHttpRequest(URL_DAFTARKOS,"POST");
                if(json!=null)
                {
                    sukses=json.getString(TAG_PESAN);

                    if(sukses.equalsIgnoreCase("Data Kos Ditemukan"))
                    {
                        arrayListTimeline = new ArrayList<HashMap<String, String>>();
                        Log.d("","--------->"+json.toString());

                        jsonarray = json.getJSONArray(TAG_HASIL);
                        for(int i = 0;i<jsonarray.length();i++)
                        {
                            JSONObject c = jsonarray.getJSONObject(i);
                            String id = c.getString(TAG_IDKOS);
                            String namakos= c.getString(TAG_namakos);
                            String pemilik = c.getString(TAG_pemilik);
                            String alamat = c.getString(TAG_alamat);
                            String daerah = c.getString(TAG_daerah);
                            //String harga = c.getString(TAG_harga);
                            String fasilitas = c.getString(TAG_fasilitas);
                            String picture = c.getString(TAG_picture);

                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("TAG_IDKOS",id);
                            map.put("TAG_namakos",namakos);
                            map.put("TAG_pemilik",pemilik);
                            map.put("TAG_alamat",alamat);
                            map.put("TAG_daerah",daerah);
                            //map.put("TAG_harga",harga);
                            map.put("TAG_fasilitas",fasilitas);
                            map.put("TAG_picture",picture);
                            arrayListTimeline.add(map);
                        }
                    }
                }

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if (sukses.equalsIgnoreCase("Data kos Ditemukan")) {
                ListAdapter adapter = new listTimelineAdapter(context, arrayListTimeline,
                        R.layout.activity_list_data, new String[]{},
                        new int[]{});
                listView.setAdapter(adapter);
            }
        }
    }


    class GetTimelineSearchJSON extends AsyncTask<String,String,String> {
        String sukses = "";
        Context context;
        String username, daerah;
        String api;

        /*public GetTimelineJSON() {

        }
        public GetTimelineJSON(Context context, String username,String api) {
            this.context = context;
            this.username = username;
            this.api = API_KEY;
        }*/

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("daerah", daerah));
            params.add(new BasicNameValuePair("api_key", API_KEY));
            try {
                JSONObject json = jParser.makeHttpRequest(URL_DAFTARCARI, "GET");
                if (json != null) {
                    sukses = json.getString(TAG_PESAN);

                    if (sukses.equalsIgnoreCase("Data Kos Ditemukan")) {
                        arrayListTimeline = new ArrayList<HashMap<String, String>>();
                        Log.d("", "--------->" + json.toString());

                        jsonarray = json.getJSONArray(TAG_HASIL);
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject c = jsonarray.getJSONObject(i);
                            String id = c.getString(TAG_IDKOS);
                            String namakos = c.getString(TAG_namakos);
                            String pemilik = c.getString(TAG_pemilik);
                            String alamat = c.getString(TAG_alamat);
                            String daerah = c.getString(TAG_daerah);
                           // String harga = c.getString((TAG_harga));
                            String fasilitas = c.getString(TAG_fasilitas);
                            String picture = c.getString(TAG_picture);

                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("TAG_IDKOS", id);
                            map.put("TAG_namakos", namakos);
                            map.put("TAG_pemilik", pemilik);
                            map.put("TAG_alamat", alamat);
                            map.put("TAG_daerah", daerah);
                           // map.put("TAG_harga", harga);
                            map.put("TAG_fasilitas", fasilitas);
                            map.put("TAG_picture", picture);
                            arrayListTimeline.add(map);
                        }
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if (sukses.equalsIgnoreCase("Data kos Ditemukan")) {
                ListAdapter adapter = new listTimelineAdapter(context, arrayListTimeline,
                        R.layout.activity_list_data, new String[]{},
                        new int[]{});
                listView.setAdapter(adapter);
            }
        }
    }
}
