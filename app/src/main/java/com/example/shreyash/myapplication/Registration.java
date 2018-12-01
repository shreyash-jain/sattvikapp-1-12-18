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
    String name;
    String password;
    String email;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("student_sheet");
    SharedPreferences sharedpreferences;
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
            submitForm();
        }
    }


    private void submitForm() {
            //TODO: Check for internet connectivity
            //If no error continue else prompt user to start internet
            final ProgressDialog progressDialog = new ProgressDialog(Registration.this, R.style.MyAlertDialogStyle);
            progressDialog.setIndeterminate(true);
            progressDialog.setTitle("Authenticating...");
            progressDialog.setMessage("loading");
            progressDialog.show();
            /*new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                        }
                    }, 2000);*/

            buttonSubmit.setEnabled(false);
            final Calendar calendar = Calendar.getInstance();
            DatabaseReference offsetRef = FirebaseDatabase.getInstance().getReference(".info/serverTimeOffset");
            offsetRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    double offset = snapshot.getValue(Double.class);
                    double estimatedServerTimeMs = System.currentTimeMillis() + offset;
                    calendar.setTimeInMillis(((long) estimatedServerTimeMs));
                    Log.d("inter",""+calendar.getTime());
                }
                @Override
                public void onCancelled(DatabaseError error) {
                    System.err.println("Listener was cancelled");
                }
            });

            //Fetch email
            email = editTextEmail.getText().toString();
            name = editTextName.getText().toString();
            password = editTextPass.getText().toString();
            final String email_refined = email.replaceAll("\\W+","");
            FirebaseDatabase PostReference = FirebaseDatabase.getInstance();
            DatabaseReference mPostReference = PostReference.getReference("student_sheet");
            mPostReference.child("students").child(email_refined).
                    addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Person_Details personDetails2 = dataSnapshot.getValue(Person_Details.class);
                            try {
                                //checking if already registered or not
                                Toast.makeText(Registration.this, "You are already registered "+ personDetails2.name , Toast.LENGTH_SHORT).show();
                                Log.d("registered already", personDetails2.name);
                                progressDialog.dismiss();
                                buttonSubmit.setEnabled(true);
                            }
                            catch (Exception e){
                                //Creating record to Firebase
                                SimpleDateFormat format = new SimpleDateFormat("yyyy/M/d h:mm:ss a");
                                //Toast.makeText(Registration.this, ""+format.format(calendar.getTime()), Toast.LENGTH_SHORT).show();

                                Person_Details personDetails = new Person_Details(
                                        format.format(calendar.getTime()),
                                        editTextName.getText().toString(),
                                        editTextBranch.getText().toString(),
                                        editTextyear.getText().toString(),
                                        editTextRoom.getText().toString(),
                                        editTextHostel.getText().toString(),
                                        editTextMobile.getText().toString(),
                                        editTextEmail.getText().toString(),
                                        editTextdu.getText().toString(),
                                        editTextPass.getText().toString());
                                myRef.child("students").child(email_refined).setValue(personDetails);
                                //Log.d("flag in registering",flag)
                                sharedpreferences = getSharedPreferences(Constants.myPreference, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(Constants.Name, name);
                                editor.putString(Constants.Email, email);
                                editor.putString(Constants.Password,password);
                                editor.apply();
                                progressDialog.dismiss();
                                Intent i = new Intent(Registration.this, Offline.class);
                                startActivity(i);
                                finishAffinity();
                                Toast.makeText(Registration.this,  "Welcome "+name, Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            progressDialog.dismiss();
                            Log.w("registered or not", "loadPost:onCancelled", databaseError.toException());
                        }
                    });
            //Todo Detach listener (very important)
            //Todo
            //Todo
            //Todo
            //Todo
    }
}


