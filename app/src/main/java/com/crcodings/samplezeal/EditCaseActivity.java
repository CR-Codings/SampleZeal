package com.crcodings.samplezeal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.crcodings.samplezeal.adapter.WoundTypeSpinnerAdapter;

public class EditCaseActivity extends AppCompatActivity {

    String id;
    String Name ;
    String Age ;
    String Conditionpatient ;
    String Woundtype ;
    String History1 ;
    String History2 ;
    String History3 ;
    String Doctorname ;
    String Speciality ;
    String Hospital ;
    String Comments ;
    String Date ;
    String Dressingby ;
    String Status ;
    String imagefolder;

    String woundType = "";


    EditText etName, etAge, etCondition, etHistoryOne, etHistoryTwo, etHistoryThree,
            etDoctorName, etSpeciality, etHospital;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_case);

        final Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.woundtype_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setPrompt("Select Wound Type");

        spinner.setAdapter(
                new WoundTypeSpinnerAdapter(
                        adapter,
                        R.layout.adapter_woundtype_spinner,
                        this));


        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etCondition = findViewById(R.id.etCondition);
        etDoctorName = findViewById(R.id.etDoctorName);
        etHistoryOne = findViewById(R.id.etHistoryOne);
        etHistoryTwo = findViewById(R.id.etHistoryTwo);
        etHistoryThree = findViewById(R.id.etHistoryThree);
        etSpeciality = findViewById(R.id.etSpeciality);
        etHospital = findViewById(R.id.etHospital);

        if(getIntent() != null) {
            if(getIntent().getStringExtra("id") != null) {
                id = getIntent().getStringExtra("id");
            }
            if(getIntent().getStringExtra("Name") != null) {
                Name = getIntent().getStringExtra("Name");
                etName.setText(Name);
            }
            if(getIntent().getStringExtra("Age") != null) {
                Age = getIntent().getStringExtra("Age");
                etAge.setText(Age);

            }
            if(getIntent().getStringExtra("Conditionpatient") != null) {
                Conditionpatient = getIntent().getStringExtra("Conditionpatient");
                etCondition.setText(Conditionpatient);
            }
            if(getIntent().getStringExtra("Woundtype") != null) {
                Woundtype = getIntent().getStringExtra("Woundtype");
                spinner.setSelection(1);
            }
            if(getIntent().getStringExtra("History1") != null) {
                History1 = getIntent().getStringExtra("History1");
                etHistoryOne.setText(History1);
            }
            if(getIntent().getStringExtra("History2") != null) {
                History2 = getIntent().getStringExtra("History2");
                etHistoryTwo.setText(History2);
            }
            if(getIntent().getStringExtra("History3") != null) {
                History3 = getIntent().getStringExtra("History3");
                etHistoryThree.setText(History3);
            }
            if(getIntent().getStringExtra("Doctorname") != null) {
                Doctorname = getIntent().getStringExtra("Doctorname");
                etDoctorName.setText(Doctorname);
            }
            if(getIntent().getStringExtra("Speciality") != null) {
                Speciality = getIntent().getStringExtra("Speciality");
                etSpeciality.setText(Speciality);
            }
            if(getIntent().getStringExtra("Hospital") != null) {
                Hospital = getIntent().getStringExtra("Hospital");
                etHospital.setText(Hospital);
            }
            if(getIntent().getStringExtra("Comments") != null) {
                Comments = getIntent().getStringExtra("Comments");
            }
            if(getIntent().getStringExtra("Date") != null) {
                Date = getIntent().getStringExtra("Date");
            }
            if(getIntent().getStringExtra("Dressingby") != null) {
                Dressingby = getIntent().getStringExtra("Dressingby");
            }
            if(getIntent().getStringExtra("Status") != null) {
                Status = getIntent().getStringExtra("Status");
            }
            if(getIntent().getStringExtra("imagefolder") != null) {
                imagefolder = getIntent().getStringExtra("imagefolder");
            }



            Button btnSubmit = findViewById(R.id.btnSubmit);


            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    if(position != 1){
                        woundType = "VPU";
                    } else {
                        woundType = "DPU";
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = etName.getText().toString();
                    String age = etAge.getText().toString();
                    String condition = etCondition.getText().toString();
                    String doctorname = etDoctorName.getText().toString();
                    String historyone = etHistoryOne.getText().toString();
                    String historytwo = etHistoryTwo.getText().toString();
                    String historythree = etHistoryThree.getText().toString();
                    String speciality = etSpeciality.getText().toString();
                    String hospital = etHospital.getText().toString();

                    Intent intent = new Intent(getApplicationContext(), EditCaseSubActivity.class);
                    intent.putExtra("id",id);
                    intent.putExtra("name",name);
                    intent.putExtra("age",age);
                    intent.putExtra("condition",condition);
                    intent.putExtra("woundtype",woundType);
                    intent.putExtra("history1",historyone);
                    intent.putExtra("history2",historytwo);
                    intent.putExtra("history3",historythree);
                    intent.putExtra("doctorname",doctorname);
                    intent.putExtra("speciality",speciality);
                    intent.putExtra("hospital",hospital);
                    intent.putExtra("comments",Comments);
                    intent.putExtra("date",Date);
                    intent.putExtra("dressingby",Dressingby);
                    intent.putExtra("status",Status);
                    startActivity(intent);
                }
            });

        }

    }
}
