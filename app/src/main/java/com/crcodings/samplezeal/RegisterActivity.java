package com.crcodings.samplezeal;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.crcodings.samplezeal.adapter.WoundTypeSpinnerAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    EditText etName, etAddress, etCity, etCountry, etEmail, etPincode, etNumber;
    String designation = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.designation_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setPrompt("Select Designation");

        spinner.setAdapter(
                new WoundTypeSpinnerAdapter(
                        adapter,
                        R.layout.adapter_woundtype_spinner,
                        this));
         designation = spinner.getSelectedItem().toString();
        Button btnSubmit = findViewById(R.id.btnSubmit);
        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        etCity = findViewById(R.id.etCity);
        etCountry = findViewById(R.id.etCountry);
        etEmail = findViewById(R.id.etEmail);
        etPincode = findViewById(R.id.etPincode);
        etNumber = findViewById(R.id.etNumber);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String address = etAddress.getText().toString();
                String city = etCity.getText().toString();
                String country = etCountry.getText().toString();
                String email = etEmail.getText().toString();
                String pincode = etPincode.getText().toString();
                String number = etNumber.getText().toString();


                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("custname",name);
                    jsonObject.put("designationtype",designation);
                    jsonObject.put("emailid",email);
                    jsonObject.put("phoneno",number);
                    jsonObject.put("address",address);
                    jsonObject.put("country",country);
                    jsonObject.put("state",city);
                    jsonObject.put("pincode",pincode);

                    new LoginService(jsonObject, "").execute();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    @SuppressLint("StaticFieldLeak")
    class  LoginService extends AsyncTask<String, Void, String> {

        private InputStream in;
        private HttpURLConnection httpURLConnection;
        String stream = null;
        JSONObject jsonObject;
        String product_exception;
        String usernumber;
        String message;
        LoginService(JSONObject userObject,String usernumber) {
            this.jsonObject = userObject;
            this.usernumber = usernumber;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progress.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }

        @Override
        protected String doInBackground(String... params) {

            String url = "http://portal.moocow.in:8080/addProfile";

            StringBuilder sb;

            try {
                URL Url = new URL(url);
                httpURLConnection = (HttpURLConnection) Url.openConnection();
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", "application/json");

                OutputStreamWriter wr = new OutputStreamWriter(httpURLConnection.getOutputStream());
                Log.d(TAG+"_AC","ACB" + jsonObject.toString());
                wr.write(jsonObject.toString());
                wr.close();

                int repCode = httpURLConnection.getResponseCode();

                if (repCode == HttpURLConnection.HTTP_CREATED || repCode == HttpURLConnection.HTTP_OK) {

                    in = new BufferedInputStream(httpURLConnection.getInputStream());
                    product_exception = "true";
                } else if(repCode == HttpURLConnection.HTTP_UNAUTHORIZED) {

                    in = new BufferedInputStream(httpURLConnection.getErrorStream());
                    product_exception = "unAuthorized";
                }else {
                    in = new BufferedInputStream(httpURLConnection.getErrorStream());
                    product_exception = "false";
                }

                // Read the BufferedInputStream
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                sb = new StringBuilder();

                String line;
                while ((line = r.readLine()) != null) {
                    sb.append(line);
                }

                stream = sb.toString();
                Log.d(TAG+"_AC","ACR : " + stream);



            } catch (Exception e) {
                e.printStackTrace();
                product_exception = String.valueOf(false);
            } finally {
                // Disconnect the HttpURLConnection & Close InputStream
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }
            return stream;
        }

        @Override
        protected void onPostExecute(String stream) {

            if(product_exception.equals("true")){
                try {
                    JSONObject jsonObject = new JSONObject(stream);
                    if(jsonObject.has("data")) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        if(jsonObject1.has("msg")) {
                            String message = jsonObject1.getString("msg");
                            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                            if(message.contains("added")) {
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Toast.makeText(getApplicationContext(),"Login Successfully.",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                intent.putExtra("usernumber",usernumber);
//                intent.putExtra(Constants.Previous_Activity_Intent,Previous_Activity);
                startActivity(intent);
                finish();
            }else if(product_exception.equals("unAuthorized")) {
                Toast.makeText(RegisterActivity.this,"Incorrect Username and Password.",Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(RegisterActivity.this,"Please try again.",Toast.LENGTH_LONG).show();
            }
        }
    }

}
