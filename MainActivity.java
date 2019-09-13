package com.pam.pieter.tgs_plbtw_kos;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btnMasuk;
    Button btnDaftar;
    TextView txtUsername,txtPassword,txtnama;

    private static final String URL_LOGIN="http://infokosjogja.pe.hu/infokosjogja/index.php/api/LogUser/LoginUser";
    private static final String TAG_PESAN="message";
    private static final String TAG_HASIL="result";
    private  static final String API_KEY="a7a1c17b30c9ab9387d1fdea27372caccbda5900";

    JSONParser jParser = new JSONParser();
    JSONArray jsonarray = null;

    private SharedPreferences sharedpreferences;
    private final String name="myAccount";
    private static final int mode = Activity.MODE_PRIVATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedpreferences=getSharedPreferences(name, mode);
        btnMasuk = (Button) findViewById(R.id.btnMasuk);
        btnDaftar=(Button) findViewById(R.id.btndaftar);
        txtUsername=(TextView) findViewById(R.id.edUser);
        txtPassword=(TextView) findViewById(R.id.edPass);
        txtnama=(TextView) findViewById(R.id.edNama);


        if(sharedpreferences.getString("status","").equals("1")){
            finish();
            Intent i = new Intent(getApplicationContext(),ListData.class);
            startActivity(i);
        }

        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txtUsername.getText().toString().isEmpty()&&!txtPassword.getText().toString().isEmpty())
                {
                    new LoginJSON(txtUsername.getText().toString(), txtPassword.getText().toString()).execute();
                }
            }
        });

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent i = new Intent(getApplicationContext(),daftar.class);
                    startActivity(i);
            }
        });
    }


    class LoginJSON extends AsyncTask<String,String,String> {
        String sukses = "";
        String username,password;
        //String apikey;

        public LoginJSON(String username,String password) {
            this.username=username;
            this.password=password;
            //this.apikey=apikey;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("password", password));
            params.add(new BasicNameValuePair("api_key", API_KEY));
            try {
                JSONObject json = jParser.makeHttpRequest(URL_LOGIN, "GET",params);
                Log.d("","--->"+json.toString());

                if (json != null) {
                    sukses = json.getString(TAG_PESAN);
                    if (sukses.equalsIgnoreCase("Login Sukses")) {
                        Log.d("", json.toString());

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if(sukses.equalsIgnoreCase("Login Sukses")){
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("status", "1");
                editor.putString("username",username);
                editor.putString("password",password);
                editor.apply();
                //finish();

                Intent i = new Intent(getApplicationContext(),ListData.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(),"Login Berhasil :D ",Toast.LENGTH_LONG).show();
            }
        }
    }
}
