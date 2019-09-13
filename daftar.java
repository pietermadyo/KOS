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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class daftar extends AppCompatActivity {

    Button btnMasuk;
    Button btnDaftar;
    EditText txtUsername,txtPassword,txtnama,txtAPIKEY;
    private static final String URL_SIGNUP="http://infokosjogja.pe.hu/infokosjogja/index.php/api/LogUser/SignUp";
    private static final String TAG_PESAN="message";
    private  static final String API_KEY="a7a1c17b30c9ab9387d1fdea27372caccbda5900";

    JSONParser jParser = new JSONParser();
    JSONArray jsonarray = null;
    private SharedPreferences sharedpreferences;
    private final String name="myAccount";
    private static final int mode = Activity.MODE_PRIVATE;
    String username,password,nama,api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        sharedpreferences=getSharedPreferences(name, mode);

        btnMasuk = (Button) findViewById(R.id.btnMasuk);
        btnDaftar=(Button) findViewById(R.id.btndaftar);
        txtUsername=(EditText) findViewById(R.id.edUser);
        txtPassword=(EditText) findViewById(R.id.edPass);
        txtnama=(EditText) findViewById(R.id.edNama);



        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                username=txtUsername.getText().toString();
                password=txtPassword.getText().toString();
                nama=txtnama.getText().toString();
                api=API_KEY;
                new SignUpJSON().execute();
            }
        });
    }

    class SignUpJSON extends AsyncTask<String,String,String> {
        String sukses = "";
        String username,password,nama;
        ProgressDialog dialog;

       /*public SignUpJSON(String username,String password,String name) {
            this.username=username;
            this.password=password;
            this.name=name;

       }*/

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(daftar.this);
            dialog.setMessage("Mohon Tunggu...");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("password", password));
            params.add(new BasicNameValuePair("nama", nama));
            params.add(new BasicNameValuePair("api_key", API_KEY));
            try {
                JSONObject json = jParser.makeHttpRequest(URL_SIGNUP, "GET",params);
                Log.d("","--->"+json.toString());

                if (json != null) {
                    sukses = json.getString(TAG_PESAN);
                    if (sukses.equalsIgnoreCase("Daftar Pengguna Berhasil")) {
                        Log.d("", json.toString());
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s)
        {
            dialog.dismiss();

            if(sukses.equalsIgnoreCase("Daftar Pengguna Berhasil")){
                Toast.makeText(getApplicationContext(),"Daftar berhasil :D ",Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
            else
                Toast.makeText(getApplicationContext(),"Daftar Gagal :(",Toast.LENGTH_LONG).show();
        }
    }

}
