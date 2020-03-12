package com.crcodings.samplezeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewCaseSubActivity extends AppCompatActivity {

    String realPath;
    String name = "", age= "", condition= "", woundtype= "", historyone= "",
            historytwo= "", historythree= "", doctorname= "", speciality= "", hospital = "";

    EditText etComments, etDate, etDressing,etImagePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_sub);

        Button btnSubmit = findViewById(R.id.btnSubmit);
        Button btnUpload = findViewById(R.id.btnUpload);


        etImagePath = findViewById(R.id.etImagePath);
        etComments = findViewById(R.id.etComments);
        etDate = findViewById(R.id.etDate);
        etDressing = findViewById(R.id.etDressing);

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

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comments = etComments.getText().toString();
                String date = etDate.getText().toString();
                String dressing = etDressing.getText().toString();
                new LoginService(name , age, condition, woundtype, historyone,
                        historytwo, historythree, doctorname, speciality, hospital, comments, date, dressing, realPath).execute();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 0);
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
        String name , age, condition, woundtype, historyone,
                historytwo, historythree, doctorname, speciality, hospital,
                comments, date, dressing,filepath;




        LoginService(  String name ,String  age, String  condition,String  woundtype, String historyone,
                       String historytwo, String historythree,String  doctorname, String speciality,String  hospital,
                       String  comments, String date,String  dressing, String filepath) {

            this.name = name;
            this.age = age;
            this.condition = condition;
            this.woundtype = woundtype;
            this.historyone = historyone;
            this.historytwo = historytwo;
            this.historythree = historythree;
            this.doctorname = doctorname;
            this.speciality = speciality;
            this.hospital = hospital;
            this.comments = comments;
            this.date = date;
            this.dressing = dressing;
            this.filepath = filepath;

        }



        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {

            String url = "http://portal.moocow.in:8080/newcase";

            StringBuilder sb;
            DataOutputStream outputStream = null;
            InputStream inputStream = null;
            String boundary =  "*****"+Long.toString(System.currentTimeMillis())+"*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            String[] q = filepath.split("/");
            int idx = q.length - 1;

            try {

                File file = new File(filepath);
                FileInputStream fileInputStream = new FileInputStream(file);

                URL Url = new URL(url);
                httpURLConnection = (HttpURLConnection) Url.openConnection();
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary="+boundary);
                httpURLConnection.setRequestProperty("name", name);
                httpURLConnection.setRequestProperty("age", age);
                httpURLConnection.setRequestProperty("condition", condition);
                httpURLConnection.setRequestProperty("woundtype", woundtype);
                httpURLConnection.setRequestProperty("history1", historyone);
                httpURLConnection.setRequestProperty("history2", historytwo);
                httpURLConnection.setRequestProperty("history3", historythree);
                httpURLConnection.setRequestProperty("doctorname", doctorname);
                httpURLConnection.setRequestProperty("speciality", speciality);
                httpURLConnection.setRequestProperty("hospital", hospital);
                httpURLConnection.setRequestProperty("comments", comments);
                httpURLConnection.setRequestProperty("date", date);
                httpURLConnection.setRequestProperty("dressingby", dressing);
                httpURLConnection.setRequestProperty("status", "open");

//                OutputStreamWriter wr = new OutputStreamWriter(httpURLConnection.getOutputStream());
//                Log.d("TAG"+"_AC","ACB" + jsonObject.toString());
//                wr.write(jsonObject.toString());
//                wr.close();

                outputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                outputStream.writeBytes("--" + boundary + "\r\n");
                outputStream.writeBytes("Content-Disposition: form-data; name=\"" + "img_upload" + "\"; filename=\"" + q[idx] +"\"" + "\r\n");
                outputStream.writeBytes("Content-Type: image/jpeg" + "\r\n");
                outputStream.writeBytes("Content-Transfer-Encoding: binary" + "\r\n");
                outputStream.writeBytes("\r\n");
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, 1048576);
                buffer = new byte[bufferSize];
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                while(bytesRead > 0) {
                    outputStream.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, 1048576);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }
                outputStream.writeBytes("\r\n");
                outputStream.writeBytes("--" + boundary + "--" + "\r\n");
                inputStream = httpURLConnection.getInputStream();
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

                inputStream.close();
                httpURLConnection.disconnect();
                fileInputStream.close();
                outputStream.flush();
                outputStream.close();



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

                Toast.makeText(getApplicationContext(),"Login Successfully.",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NewCaseSubActivity.this,MainActivity.class);
                intent.putExtra("usernumber",usernumber);
//                intent.putExtra(Constants.Previous_Activity_Intent,Previous_Activity);
                startActivity(intent);
                finish();
            }else if(product_exception.equals("unAuthorized")) {
                Toast.makeText(NewCaseSubActivity.this,"Incorrect Username and Password.",Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(NewCaseSubActivity.this,"Please try again.",Toast.LENGTH_LONG).show();
            }
        }
    }



    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent data) {
        if(resCode == Activity.RESULT_OK && data != null){
            // Check the SDK Version
            if (Build.VERSION.SDK_INT < 11)
                realPath = PathOfImage.PathAPI11(this, data.getData());
            else if (Build.VERSION.SDK_INT < 19)
                realPath = PathOfImage.Path_API18(this, data.getData());
            else
                realPath = PathOfImage.Path_API19(this, data.getData());
            etImagePath.setText( realPath);

        }
    }
}
