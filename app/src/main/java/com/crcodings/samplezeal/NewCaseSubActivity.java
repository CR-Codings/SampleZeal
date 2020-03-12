package com.crcodings.samplezeal;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NewCaseSubActivity extends AppCompatActivity {


    String name = "", age= "", condition= "", woundtype= "", historyone= "",
            historytwo= "", historythree= "", doctorname= "", speciality= "", hospital = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_sub);

        Button btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        if(getIntent() != null) {
            if(getIntent().getStringExtra("name") != null) {
                name = getIntent().getStringExtra("name");
            }
            if(getIntent().getStringExtra("age") != null) {
                age = getIntent().getStringExtra("age");
            }
            if(getIntent().getStringExtra("condition") != null) {
                condition = getIntent().getStringExtra("condition");
            }
            if(getIntent().getStringExtra("woundtype") != null) {
                woundtype = getIntent().getStringExtra("woundtype");
            }
            if(getIntent().getStringExtra("history1") != null) {
                historyone = getIntent().getStringExtra("history1");
            }
            if(getIntent().getStringExtra("history2") != null) {
                historytwo = getIntent().getStringExtra("history2");
            }
            if(getIntent().getStringExtra("history3") != null) {
                historythree = getIntent().getStringExtra("history3");
            }
            if(getIntent().getStringExtra("doctorname") != null) {
                doctorname = getIntent().getStringExtra("doctorname");
            }
            if(getIntent().getStringExtra("speciality") != null) {
                speciality = getIntent().getStringExtra("speciality");
            }
            if(getIntent().getStringExtra("hospital") != null) {
                hospital = getIntent().getStringExtra("hospital");
            }
        }
    }


    @SuppressLint("StaticFieldLeak")
    private class SaveLatLang extends AsyncTask<String,String,String> {
        String message = "", user_id, address_id, access_token;
        String Product_Exception = "false";
        String resServer = "";
        String latitude = "";
        String longitude = "";
        String device_name = "";
        boolean status = false;

        SaveLatLang(String device_name, String latitude, String longitude) {

            this.device_name = device_name;
            this.latitude = latitude;
            this.longitude = longitude;

        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            try{

                URL url = new URL("https://portal.spassh.com/WebApi/Api/Home/StoreValues?deviceId="+device_name
                        +"&lat="+latitude+"&longt="+longitude);

                HttpURLConnection  conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("GET");

                conn.connect();

                int responseCode = conn.getResponseCode();

                BufferedReader in;
                if (responseCode == HttpURLConnection.HTTP_OK
                        || responseCode == HttpURLConnection.HTTP_CREATED) {

                    in = new BufferedReader(
                            new InputStreamReader(conn.getInputStream()));
                    Product_Exception = "true";

                } else {

                    in = new BufferedReader(
                            new InputStreamReader(conn.getErrorStream()));

                    Product_Exception = "false";
                }

                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                resServer = response.toString();


            }catch (Exception e){
                e.printStackTrace();
            }

            return resServer;
        }

        @Override
        protected void onPostExecute(String d) {
            super.onPostExecute(d);

            if(Product_Exception.equals("true")){

            }else{
//                Toast.makeText(LocationService.this,"Please Try Again! Unable to Delete",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
