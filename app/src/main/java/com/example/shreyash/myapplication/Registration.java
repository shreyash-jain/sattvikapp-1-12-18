package com.example.shreyash.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.CheckBox;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.common.collect.Range;

import java.util.List;
import java.util.regex.Matcher;

public class Registration extends AppCompatActivity  implements View.OnClickListener   {
    private EditText editTextName, editTextEmail, editTextMobile,
            editTextyear, editTextdu;
    private AutoCompleteTextView editTextHostel,editTextBranch;
    private Button buttonSubmit;
    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        TextView t1=(TextView) findViewById(R.id.secret);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Registration.this,Offline.class);
                startActivity(i);
            }
        });

        awesomeValidation = new AwesomeValidation(ValidationStyle.UNDERLABEL);
        awesomeValidation.setContext(this);
        editTextName = (EditText) findViewById(R.id.input_name);
        editTextEmail = (EditText) findViewById(R.id.input_email);
        editTextMobile = (EditText) findViewById(R.id.input_phone);
        editTextdu = (EditText) findViewById(R.id.input_du_no);
        editTextyear = (EditText) findViewById(R.id.input_year);

        buttonSubmit = (Button) findViewById(R.id.button);
        editTextHostel =(AutoCompleteTextView) findViewById(R.id.input_hostel);
        editTextBranch=(AutoCompleteTextView) findViewById(R.id.input_department);

        awesomeValidation.addValidation(this, R.id.input_name, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.input_email, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.input_phone, "^[2-9]{2}[0-9]{8}$", R.string.mobileerror);
        awesomeValidation.addValidation(this, R.id.input_year, Range.closed(1, 6), R.string.ageerror);

        String[] clg_branches = getResources().getStringArray(R.array.branches);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, clg_branches);
        //Find TextView control
        AutoCompleteTextView acTextView = (AutoCompleteTextView) findViewById(R.id.input_department);
        //Set the number of characters the user must type before the drop down list is shown
        acTextView.setThreshold(1);
        //Set the adapter
        acTextView.setAdapter(adapter);
        final AutoCompleteTextView textView = findViewById(R.id.input_hostel);
        // Get the string array
        String[] clg_hostels = getResources().getStringArray(R.array.hostel_array);
        // Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, clg_hostels);
        textView.setAdapter(adapter2);
        awesomeValidation.addValidation(this, R.id.input_hostel, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.input_department, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        buttonSubmit.setOnClickListener(this);
    }

    private void submitForm() {
        if (awesomeValidation.validate()) {
            Toast.makeText(this, "Validation Successful", Toast.LENGTH_LONG).show();
            //TODO: Send all data to database and store in local database if possible
            Intent i = new Intent(Registration.this, Offline.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == buttonSubmit) {
            submitForm();
        }

    }
}


