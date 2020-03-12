package com.crcodings.samplezeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.crcodings.samplezeal.adapter.ReportsAdapter;
import com.crcodings.samplezeal.model.ReportsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ReportsActivity extends AppCompatActivity {

    RecyclerView rvExistingcases;
    private ArrayList<ReportsModel> reportsModelArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        rvExistingcases = findViewById(R.id.rvExistingcases);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvExistingcases.setLayoutManager(linearLayoutManager);

        new ManageAddressAsyncTask().execute();

    }

    @SuppressLint("StaticFieldLeak")
    class ManageAddressAsyncTask extends AsyncTask<String, Void, String> {

        String Category,  otp;
        String Product_Exception = "false";
        String resServer = "";
        String user_id = "";
        String access_token = "";
        String activity = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                URL url = new URL("http://portal.moocow.in:8080/allcases");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
//                conn.setRequestProperty ("Authorization", access_token);
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


            } catch (Exception e) {
                e.printStackTrace();
                Product_Exception = "false";
            }

            return resServer;
        }

        @Override
        protected void onPostExecute(String resServer) {
            super.onPostExecute(resServer);

//            progress.setVisibility(View.GONE);
            if(Product_Exception.equals("true")) {
                try {
                    reportsModelArrayList = new ArrayList<>();
                    JSONObject vJsonObject = new JSONObject(resServer);
                    JSONObject dataJsonObject = vJsonObject.getJSONObject("data");
                    JSONArray dataJsonArray = dataJsonObject.getJSONArray("data");
                    for (int i = 0; i < dataJsonArray.length(); i++) {
                        JSONObject jsonObject = dataJsonArray.getJSONObject(i);
                        ReportsModel reportsModel = new ReportsModel();

                        if (jsonObject.has("id")) {
                            reportsModel.setId(jsonObject.getString("id"));
                        }

                        if (jsonObject.has("Name")) {
                            reportsModel.setName(jsonObject.getString("Name"));
                        }

                        if (jsonObject.has("Age")) {
                            reportsModel.setAge(jsonObject.getString("Age"));
                        }

                        if (jsonObject.has("Conditionpatient")) {
                            reportsModel.setConditionpatient(jsonObject.getString("Conditionpatient"));
                        }

                        if (jsonObject.has("Woundtype")) {
                            reportsModel.setWoundtype(jsonObject.getString("Woundtype"));
                        }

                        if (jsonObject.has("History1")) {
                            reportsModel.setHistory1(jsonObject.getString("History1"));
                        }

                        if (jsonObject.has("History2")) {
                            reportsModel.setHistory2(jsonObject.getString("History2"));
                        }

                        if (jsonObject.has("History3")) {
                            reportsModel.setHistory3(jsonObject.getString("History3"));
                        }

                        if (jsonObject.has("Doctorname")) {
                            reportsModel.setDoctorname(jsonObject.getString("Doctorname"));
                        }

                        if (jsonObject.has("Speciality")) {
                            reportsModel.setSpeciality(jsonObject.getString("Speciality"));
                        }

                        if (jsonObject.has("Hospital")) {
                            reportsModel.setHospital(jsonObject.getString("Hospital"));
                        }

                        if (jsonObject.has("Comments")) {
                            reportsModel.setComments(jsonObject.getString("Comments"));
                        }

                        if (jsonObject.has("Date")) {
                            reportsModel.setDate(jsonObject.getString("Date"));
                        }

                        if (jsonObject.has("Dressingby")) {
                            reportsModel.setDressingby(jsonObject.getString("Dressingby"));
                        }

                        if (jsonObject.has("Status")) {
                            reportsModel.setStatus(jsonObject.getString("Status"));
                        }

                        if (jsonObject.has("imagefolder")) {
                            reportsModel.setImagefolder(jsonObject.getString("imagefolder"));
                        }

                        reportsModelArrayList.add(reportsModel);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(reportsModelArrayList.size() > 0) {
                    ReportsAdapter reportsAdapter = new ReportsAdapter(ReportsActivity.this,reportsModelArrayList);
                    rvExistingcases.setAdapter(reportsAdapter);

                }
            }else {
                Toast.makeText(getApplicationContext(),"Network error",Toast.LENGTH_LONG).show();
            }

        }
    }
}
