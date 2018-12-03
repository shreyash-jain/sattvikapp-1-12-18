package com.example.shreyash.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.shreyash.utils.Constants;
import com.google.common.collect.Range;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Registration extends AppCompatActivity  implements View.OnClickListener   {
    private EditText editTextName, editTextEmail, editTextMobile,
            editTextyear, editTextdu,editTextPass,editTextRoom;
    private AutoCompleteTextView editTextHostel,editTextBranch;
    private Button buttonSubmit;
    private AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        awesomeValidation = new AwesomeValidation(ValidationStyle.UNDERLABEL);
        awesomeValidation.setContext(this);
        editTextName =  findViewById(R.id.input_name);
        editTextEmail =  findViewById(R.id.input_email);
        editTextMobile =  findViewById(R.id.input_phone);
        editTextRoom = findViewById(R.id.input_room);
        editTextdu =  findViewById(R.id.input_du_no);
        editTextyear =  findViewById(R.id.input_year);
        editTextPass = findViewById(R.id.input_password);
        buttonSubmit = findViewById(R.id.button);

        awesomeValidation.addValidation(this, R.id.input_name, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.input_email, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.input_phone, "^[2-9]{2}[0-9]{8}$", R.string.mobileerror);
        awesomeValidation.addValidation(this, R.id.input_year, Range.closed(1, 6), R.string.ageerror);
        String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";
        awesomeValidation.addValidation(this, R.id.input_password, regexPassword, R.string.err_password);
        //awesomeValidation.addValidation(this, R.id.input_confirm_password,editTextPass.getText().toString(), R.string.err_password_confirmation);

        String[] clg_branches = getResources().getStringArray(R.array.branches);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, clg_branches);
        editTextBranch = findViewById(R.id.input_department);
        editTextBranch.setThreshold(1);
        editTextBranch.setAdapter(adapter);

        editTextHostel = findViewById(R.id.input_hostel);
        String[] clg_hostels = getResources().getStringArray(R.array.hostel_array);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, clg_hostels);
        editTextHostel.setAdapter(adapter2);

        awesomeValidation.addValidation(this, R.id.input_hostel, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.input_department, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        //TODO: awesome validation for confirm password and du number
        buttonSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == buttonSubmit) {
            Intent i = new Intent(Registration.this, RegistrationConfirmation.class);
            i.putExtra("name",editTextName.getText().toString());
            i.putExtra("email",editTextEmail.getText().toString());
            i.putExtra("Phone",editTextMobile.getText().toString());
            i.putExtra("Room",editTextRoom.getText().toString());
            i.putExtra("Hostel",editTextHostel.getText().toString());
            i.putExtra("Year",editTextyear.getText().toString());
            i.putExtra("Department",editTextBranch.getText().toString());
            i.putExtra("DU",editTextdu.getText().toString());
            i.putExtra("password",editTextPass.getText().toString());
            startActivity(i);
        }
    }
}


