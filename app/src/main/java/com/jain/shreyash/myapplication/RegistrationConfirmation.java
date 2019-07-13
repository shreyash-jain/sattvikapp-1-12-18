package com.jain.shreyash.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jain.shreyash.myapplication.R;
import com.jain.shreyash.utils.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.jain.shreyash.utils.Constants.mess;

public class RegistrationConfirmation extends AppCompatActivity implements View.OnClickListener {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("student_sheet");
    SharedPreferences sharedPreferences;
    TextView textName,textEmail,textPhone,textAddress,textYear,textDepartment,textDU;
    String email,name,password,phone,room,hostel,year,dept,du,mess,roll_no;
    Button confirm;
    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_confirmation);

        textName = findViewById(R.id.text_view_name);
        textEmail = findViewById(R.id.text_view_email);
        textPhone = findViewById(R.id.text_view_phone);
        textAddress = findViewById(R.id.text_view_address);
        textYear = findViewById(R.id.text_view_year);
        textDepartment = findViewById(R.id.text_view_department);
        textDU = findViewById(R.id.text_view_du);
        confirm = findViewById(R.id.confirm);
        Intent i = getIntent();
        name = i.getStringExtra("name");
        email = i.getStringExtra("email");
        password = i.getStringExtra("password");
        phone = i.getStringExtra("Phone");
        room = i.getStringExtra("Room");
        hostel = i.getStringExtra("Hostel");
        year = i.getStringExtra("Year");
        dept = i.getStringExtra("Department");
        du = i.getStringExtra("DU");
        roll_no = i.getStringExtra("roll_no");

        textName.setText("name: "+name);
        textEmail.setText("email: "+email);
        textPhone.setText("Phone No.: "+phone);
        textAddress.setText("Address: "+room+", "+hostel);
        textYear.setText("Current Year: " +year);
        textDepartment.setText("Department: "+dept);
        textDU.setText("DU Number: "+du);
        //textRollNumber.setText();
        confirm.setOnClickListener(this);

    }
    private void submitForm() {
        Log.d("A","In submitForm");
        //TODO: Check for internet connectivity
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else
            connected = false;
        //If no error continue else prompt user to start internet
        if (connected==false){
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(mContext, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(mContext);
            }
            builder.setTitle("Delete entry")
                    .setMessage("Are you sure you want to delete this entry?")
            .show();
        }
        else {
            final ProgressDialog progressDialog = new ProgressDialog(RegistrationConfirmation.this, R.style.MyAlertDialogStyle);
            progressDialog.setIndeterminate(true);
            progressDialog.setTitle("Authenticating...");
            progressDialog.setMessage("loading");
            progressDialog.show();

            confirm.setEnabled(false);
            final Calendar calendar = Calendar.getInstance();
            DatabaseReference offsetRef = FirebaseDatabase.getInstance().getReference(".info/serverTimeOffset");
            offsetRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    double offset = snapshot.getValue(Double.class);
                    double estimatedServerTimeMs = System.currentTimeMillis() + offset;
                    calendar.setTimeInMillis(((long) estimatedServerTimeMs));
                    Log.d("inter", "" + calendar.getTime());
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    System.err.println("Listener was cancelled");
                }
            });

            //Fetch email
            final String email_refined = email.replaceAll("\\W+", "");
            FirebaseDatabase PostReference = FirebaseDatabase.getInstance();
            DatabaseReference mPostReference = PostReference.getReference("student_sheet");
            mPostReference.child("students").child(email_refined).
                    addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            PersonDetails personDetails2 = dataSnapshot.getValue(PersonDetails.class);
                            try {
                                //checking if already registered or not
                                Toast.makeText(RegistrationConfirmation.this, "You are already registered " + personDetails2.name, Toast.LENGTH_SHORT).show();
                                Log.d("registered already", personDetails2.name);
                                progressDialog.dismiss();
                                confirm.setEnabled(true);
                            } catch (Exception e) {
                                //Creating record to Firebase
                                SimpleDateFormat format = new SimpleDateFormat("yyyy/M/d h:mm:ss a");
                                //Toast.makeText(Registration.this, ""+format.format(calendar.getTime()), Toast.LENGTH_SHORT).show();

                                PersonDetails personDetails = new PersonDetails(
                                        mess,
                                        roll_no,
                                        format.format(calendar.getTime()),
                                        name,
                                       // dept,
                                       //  year,
                                        room,
                                        hostel,
                                        phone,
                                        email,
                                        du,
                                        password,
                                        0+"");
                                myRef.child("students").child(email_refined).setValue(personDetails);
                                //Log.d("flag in registering",flag)
                                sharedPreferences = getSharedPreferences(Constants.MY_PREFERENCE, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(Constants.name, name);
                                editor.putString(Constants.email, email);
                                editor.putString(Constants.pin, password);
                                editor.putString(Constants.isactive, "0");
                                editor.putString(Constants.mess, mess);
                                editor.putString(Constants.rollNumber, mess);


                                editor.apply();
                                progressDialog.dismiss();
                                Intent i = new Intent(RegistrationConfirmation.this, Offline.class);
                                startActivity(i);
                                finishAffinity();
                                Toast.makeText(RegistrationConfirmation.this, "Welcome " + name, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            progressDialog.dismiss();
                            Log.w("registered or not", "loadPost:onCancelled", databaseError.toException());
                        }
                    });
        }
        //Todo Detach listener (very important)
        //Todo
        //Todo
        //Todo
        //Todo
    }

    @Override
    public void onClick(View view) {
        if(view == confirm) {

            submitForm();
        }
    }
}
