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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;

public class Registration extends AppCompatActivity  implements View.OnClickListener   {
    private EditText editTextName, editTextEmail, editTextMobile,
            editTextyear, editTextdu,editTextPass,editTextRoom;
    private AutoCompleteTextView editTextHostel,editTextBranch;
    private Button buttonSubmit;
    private AwesomeValidation awesomeValidation;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("student_sheet");
    SharedPreferences sharedpreferences;
    public static final String myPreference = "mypref";
    public static final String Name = "nameKey";
    public static final String Email = "emailKey";
    public static final String Password = "passwordKey";

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
        editTextRoom = findViewById(R.id.input_room);
        editTextdu = (EditText) findViewById(R.id.input_du_no);
        editTextyear = (EditText) findViewById(R.id.input_year);
        editTextPass = findViewById(R.id.input_password);
        buttonSubmit = (Button) findViewById(R.id.button);
        editTextHostel =(AutoCompleteTextView) findViewById(R.id.input_hostel);
        editTextBranch=(AutoCompleteTextView) findViewById(R.id.input_department);

        awesomeValidation.addValidation(this, R.id.input_name, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.input_email, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.input_phone, "^[2-9]{2}[0-9]{8}$", R.string.mobileerror);
        awesomeValidation.addValidation(this, R.id.input_year, Range.closed(1, 6), R.string.ageerror);
        String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";
        awesomeValidation.addValidation(this, R.id.input_password, regexPassword, R.string.err_password);
        //awesomeValidation.addValidation(this, R.id.input_confirm_password,editTextPass.getText().toString(), R.string.err_password_confirmation);
        String[] clg_branches = getResources().getStringArray(R.array.branches);
        //TODO: awesome validation for confirm password and du number
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, clg_branches);
        //Find TextView control
        AutoCompleteTextView acTextView = (AutoCompleteTextView) findViewById(R.id.input_department);
        //Set the number of characters the user must type before the drop down list is shown
        acTextView.setThreshold(1);
        //Set the card_adapter_menulbd
        acTextView.setAdapter(adapter);
        final AutoCompleteTextView textView = findViewById(R.id.input_hostel);
        // Get the string array
        String[] clg_hostels = getResources().getStringArray(R.array.hostel_array);
        // Create the card_adapter_menulbd and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, clg_hostels);
        textView.setAdapter(adapter2);
        awesomeValidation.addValidation(this, R.id.input_hostel, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.input_department, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        buttonSubmit.setOnClickListener(this);
    }

    private void submitForm() {
       // if (awesomeValidation.validate()) {
//            Toast.makeText(this, "Validation Successful", Toast.LENGTH_LONG).show();


            //TODO: Check for internet connectivity
            //If no error continue else prompt user to start internet

            //Starting Progress Dialog
            final ProgressDialog progressDialog = new ProgressDialog(Registration.this, R.style.MyAlertDialogStyle);
            progressDialog.setIndeterminate(true);
            progressDialog.setTitle("Authenticating...");
            progressDialog.setMessage("loading");
            progressDialog.show();


            //Fetching Internet time and local time
//            Date currentTime = Calendar.getInstance().getTime();
//            Log.d("local",""+currentTime);

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
            final String email = editTextEmail.getText().toString();
            final String email_refined = email.replaceAll("\\W+","");
            //Toast.makeText(this, ""+email, Toast.LENGTH_SHORT).show();
            FirebaseDatabase PostReference = FirebaseDatabase.getInstance();
            DatabaseReference mPostReference = PostReference.getReference("student_sheet");
            mPostReference.child("students").child(email_refined).
                    addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Create_record create_record2 = dataSnapshot.getValue(Create_record.class);
                            try {
                                //checking if already registred or not
                                Toast.makeText(Registration.this, "You are already registered "+create_record2.name , Toast.LENGTH_SHORT).show();
                            }
                            catch (Exception e){
                                //Creating record to Firebase
                                SimpleDateFormat format = new SimpleDateFormat("yyyy/M/d h:mm:ss a");
                                //Toast.makeText(Registration.this, ""+format.format(calendar.getTime()), Toast.LENGTH_SHORT).show();

                                String name = editTextName.getText().toString();
                                String email = editTextEmail.getText().toString();
                                String password = editTextPass.getText().toString();
                                Create_record create_record = new Create_record(
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
                                myRef.child("students").child(email_refined).setValue(create_record);
                                Intent i = new Intent(Registration.this, Offline.class);
                                sharedpreferences = getSharedPreferences(myPreference, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(Name, name);
                                editor.putString(Email, email);
                                editor.putString(Password,password);
                                editor.apply();
                                startActivity(i);
                                finish();
                                Toast.makeText(Registration.this,  "Welcome "+editTextName.getText(), Toast.LENGTH_SHORT).show();
                                //Toast.makeText(Registration.this, ""+e, Toast.LENGTH_SHORT).show();
                                //Todo Detach listener (very important)
                                //Todo
                                //Todo
                                //Todo
                                //Todo
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w("registered or not", "loadPost:onCancelled", databaseError.toException());
                        }
                    });

            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                        }
                    }, 2000);
        }
  //  }

    @Override
    public void onClick(View v) {
        if (v == buttonSubmit) {
            submitForm();
        }

    }
}


