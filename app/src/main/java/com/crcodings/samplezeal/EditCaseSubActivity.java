package com.crcodings.samplezeal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EditCaseSubActivity extends AppCompatActivity {

    String name = "", age= "", condition= "", woundtype= "", historyone= "",
            historytwo= "", historythree= "", doctorname= "", speciality= "", hospital = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_case_sub);

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
}
