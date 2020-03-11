package com.crcodings.samplezeal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import com.crcodings.samplezeal.adapter.WoundTypeSpinnerAdapter;

public class NewCaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case);


        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.woundtype_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setPrompt("Select Wound Type");

        spinner.setAdapter(
                new WoundTypeSpinnerAdapter(
                        adapter,
                        R.layout.adapter_woundtype_spinner,
                        this));

        Button btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewCaseSubActivity.class);
                intent.putExtra("name","");
                intent.putExtra("name","");
                intent.putExtra("name","");
                intent.putExtra("name","");
                intent.putExtra("name","");
                intent.putExtra("name","");
                intent.putExtra("name","");
                intent.putExtra("name","");
                intent.putExtra("name","");
                intent.putExtra("name","");
                intent.putExtra("name","");
                startActivity(intent);
            }
        });

    }
}
