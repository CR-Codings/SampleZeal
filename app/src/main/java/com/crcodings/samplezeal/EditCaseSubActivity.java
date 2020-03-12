package com.crcodings.samplezeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class EditCaseSubActivity extends AppCompatActivity {



    String realPath;
    String name = "", age= "", condition= "", woundtype= "", historyone= "",
            historytwo= "", historythree= "", doctorname= "", speciality= "", hospital = "";

    EditText etComments, etDate, etDressing,etImagePath;
    String Comments ;
    String Date ;
    String Dressingby ;
    String Status ;
    String imagefolder;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_case_sub);

        Button btnSubmit = findViewById(R.id.btnSubmit);
        Button btnUpload = findViewById(R.id.btnUpload);


        etImagePath = findViewById(R.id.etImagePath);
        etComments = findViewById(R.id.etComments);
        etDate = findViewById(R.id.etDate);
        etDressing = findViewById(R.id.etDressing);

        if(getIntent() != null) {
            if(getIntent().getStringExtra("id") != null) {
                id = getIntent().getStringExtra("id");
            }

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
            if(getIntent().getStringExtra("Comments") != null) {
                Comments = getIntent().getStringExtra("Comments");
                etComments.setText(Comments);
            }
            if(getIntent().getStringExtra("Date") != null) {
                Date = getIntent().getStringExtra("Date");
                etDate.setText(Date);
            }
            if(getIntent().getStringExtra("Dressingby") != null) {
                Dressingby = getIntent().getStringExtra("Dressingby");
                etDressing.setText(Dressingby);
            }
            if(getIntent().getStringExtra("Status") != null) {
                Status = getIntent().getStringExtra("Status");
            }
            if(getIntent().getStringExtra("imagefolder") != null) {
                imagefolder = getIntent().getStringExtra("imagefolder");
            }

        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comments = etComments.getText().toString();
                String date = etDate.getText().toString();
                String dressing = etDressing.getText().toString();
                new LoginService(id, name , age, condition, woundtype, historyone,
                        historytwo, historythree, doctorname, speciality, hospital, comments, date, dressing, realPath).execute();
            }
        });
        if(!checkPermissionForReadExtertalStorage()) {
            try {
                requestPermissionForReadExtertalStorage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPermissionForReadExtertalStorage()) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(intent, 0);
                }
            }
        });
    }

    public boolean checkPermissionForReadExtertalStorage() {
        int result = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermissionForReadExtertalStorage() throws Exception {
        try {
            ActivityCompat.requestPermissions((Activity) this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    11);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
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
                comments, date, dressing,filepath, id;




        LoginService( String id,  String name ,String  age, String  condition,String  woundtype, String historyone,
                       String historytwo, String historythree,String  doctorname, String speciality,String  hospital,
                       String  comments, String date,String  dressing, String filepath) {

            this.id = id;
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

            String url = "http://portal.moocow.in:8080/editcase";

            StringBuilder sb;

            try {


                URL Url = new URL(url);
                httpURLConnection = (HttpURLConnection) Url.openConnection();
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                httpURLConnection.setRequestProperty("id", id);
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

                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }


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

                Toast.makeText(getApplicationContext(),"case updated.",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditCaseSubActivity.this,MainActivity.class);
                intent.putExtra("usernumber",usernumber);
//                intent.putExtra(Constants.Previous_Activity_Intent,Previous_Activity);
                startActivity(intent);
                finish();
            }else if(product_exception.equals("unAuthorized")) {
                Toast.makeText(EditCaseSubActivity.this,"Incorrect Username and Password.",Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(EditCaseSubActivity.this,"Please try again.",Toast.LENGTH_LONG).show();
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
