package com.crcodings.samplezeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = LoginActivity.class.getSimpleName();
    private EditText emailid, password;
    private Button loginButton;
    private TextView forgotPassword, signUp, text_signinasguest;
    private CheckBox show_hide_password;
    private LinearLayout loginLayout;

//    DatabaseHandler dbHandler;
    SharedPreferences sharedPreferences;
    String usernumber = "",Previous_Activity = "";
    ProgressBar progress;

    Toolbar mToolbar;
    TextView toolbarTitle;
    boolean doubleBackToExitPressedOnce = false;
    private long mLastClickTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        dbHandler = new DatabaseHandler(LoginActivity.this);
        sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);

//        if(getIntent() != null){
//            if(getIntent().getStringExtra("usernumber") != null){
//                usernumber = getIntent().getStringExtra("usernumber");
//            }
//            if(getIntent().getStringExtra(Constants.Previous_Activity_Intent) != null){
//                Previous_Activity = getIntent().getStringExtra(Constants.Previous_Activity_Intent);
//            }
//        }
//

        emailid =  findViewById(R.id.login_emailid);
        password =  findViewById(R.id.login_password);
        loginButton =  findViewById(R.id.loginBtn);
        forgotPassword =  findViewById(R.id.forgot_password);
        signUp =  findViewById(R.id.createAccount);
        show_hide_password = findViewById(R.id.show_hide_password);
        loginLayout =  findViewById(R.id.login_layout);
        progress = findViewById(R.id.progress);
        if(usernumber!= null) {
            emailid.setText(usernumber);
        }
        setListeners();


    }

    private void setListeners() {
        loginButton.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        signUp.setOnClickListener(this);
        show_hide_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton button,
                                         boolean isChecked) {

                if (isChecked) {

                    show_hide_password.setText(R.string.hide_pwd);

                    password.setInputType(InputType.TYPE_CLASS_TEXT);
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    show_hide_password.setText(R.string.show_pwd);

                    password.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    password.setTransformationMethod(PasswordTransformationMethod
                            .getInstance());

                }

            }
        });
    }

    private void checkValidation() {

        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            loginButton.setEnabled(false);
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        String getEmailId = emailid.getText().toString();
        String getPassword = password.getText().toString();

        if (getEmailId.equals("") || getEmailId.length() == 0
                || getPassword.equals("") || getPassword.length() == 0) {
            Toast.makeText(getApplicationContext(),"Enter both credentials.",Toast.LENGTH_SHORT).show();
            loginButton.setEnabled(true);
        }
        else if (getPassword.length() < 7) {
            Toast.makeText(getApplicationContext(), "Your password must be more than six letters.", Toast.LENGTH_SHORT).show();
            loginButton.setEnabled(true);
        }
          else {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("useremail",getEmailId);
                jsonObject.put("password",getPassword);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d(TAG,jsonObject.toString());
            new LoginService(jsonObject,getEmailId).execute();

//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(intent);

        }

    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                checkValidation();

                break;

            case R.id.forgot_password:
                Intent forgot_intent = new Intent(LoginActivity.this,ChangePasswordActivity.class);
                startActivity(forgot_intent);
                break;

            case R.id.createAccount:
                Intent intent1 = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent1);
                break;
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
        LoginService(JSONObject userObject,String usernumber) {
            this.jsonObject = userObject;
            this.usernumber = usernumber;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }

        @Override
        protected String doInBackground(String... params) {

            String url = "http://portal.moocow.in:8080/login";

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
            progress.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            loginButton.setEnabled(true);

            if(product_exception.equals("true")){
                sharedPreferences.edit().putBoolean("isLogin",true).apply();
                sharedPreferences.edit().putString("user_id_pref",usernumber).apply();
                try {
                    JSONObject jsonObject = new JSONObject(stream);
                    if(jsonObject.has("username")) {
                        sharedPreferences.edit().putString("user_id_pref",jsonObject.getString("username")).apply();
                    }
                    if(jsonObject.has("accessToken")) {
                        sharedPreferences.edit().putString("access_token_pref",jsonObject.getString("accessToken")).apply();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Toast.makeText(getApplicationContext(),"Login Successfully.",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtra("usernumber",usernumber);
//                intent.putExtra(Constants.Previous_Activity_Intent,Previous_Activity);
                startActivity(intent);
                finish();
            }else if(product_exception.equals("unAuthorized")) {
                Toast.makeText(LoginActivity.this,"Incorrect Username and Password.",Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(LoginActivity.this,"Please try again.",Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onBackPressed() {
        if(!Previous_Activity.equals("ProfileFragment")){
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }else {
            super.onBackPressed();
        }

    }

}
