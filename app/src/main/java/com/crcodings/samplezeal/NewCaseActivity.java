package com.crcodings.samplezeal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import com.crcodings.samplezeal.adapter.WoundTypeSpinnerAdapter;

public class NewCaseActivity extends AppCompatActivity {

    EditText etName, etAge, etCondition, etHistoryOne, etHistoryTwo, etHistoryThree,
            etDoctorName, etSpeciality, etHospital;

    String woundType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case);

        final Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.woundtype_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setPrompt("Select Wound Type");

        spinner.setAdapter(
                new WoundTypeSpinnerAdapter(
                        adapter,
                        R.layout.adapter_woundtype_spinner,
                        this));

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


        Button btnSubmit = findViewById(R.id.btnSubmit);
         etName = findViewById(R.id.etName);
         etAge = findViewById(R.id.etAge);
         etCondition = findViewById(R.id.etCondition);
         etDoctorName = findViewById(R.id.etDoctorName);
         etHistoryOne = findViewById(R.id.etHistoryOne);
         etHistoryTwo = findViewById(R.id.etHistoryTwo);
         etHistoryThree = findViewById(R.id.etHistoryThree);
         etSpeciality = findViewById(R.id.etSpeciality);
         etHospital = findViewById(R.id.etHospital);

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

                Intent intent = new Intent(getApplicationContext(), NewCaseSubActivity.class);
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
                startActivity(intent);
            }
        });

    }

}
