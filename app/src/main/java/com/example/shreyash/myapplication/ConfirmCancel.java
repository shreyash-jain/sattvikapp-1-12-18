package com.example.shreyash.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shreyash.utils.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import in.shadowfax.proswipebutton.ProSwipeButton;

import static com.example.shreyash.myapplication.RegistrationConfirmation.getContext;

public class ConfirmCancel extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_cancel);
       final CheckBox chkIos = (CheckBox) findViewById(R.id.checkbtn);
        TextView dietstext=findViewById(R.id.cancel_diets);
        TextView datetext=findViewById(R.id.cancel_date);
        String toshow="";


        Intent intent=getIntent();
        final int dc = intent.getIntExtra("Cancel_diets", -1);
        final Date d = new Date();
        d.setTime(intent.getLongExtra("date", -1));

        if (dc==1) toshow+="Only Breakfast";
        else if (dc==3) toshow+="Only Lunch";
        else if (dc==5) toshow+="Only Dinner";
        else if (dc==4) toshow+="Breakfast and Lunch";
        else if (dc==6) toshow+="Breakfast and Dinner";
        else if (dc==8) toshow+="Lunch and Dinner";
        else if (dc==9) toshow+="Breakfast, Lunch and Dinner";
        dietstext.setText(toshow);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        String reportDate = df.format(d);
        datetext.setText(reportDate);
        final ProSwipeButton proSwipeBtn = (ProSwipeButton) findViewById(R.id.awesome_btn);
        proSwipeBtn.setOnSwipeListener(new ProSwipeButton.OnSwipeListener() {
            @Override
            public void onSwipeConfirm() {
                // user has swiped the btn. Perform your async operation now
                if (!chkIos.isChecked()){

                    Toast.makeText(getApplicationContext(),"Request cancelled, Accept the condition to proceed",Toast.LENGTH_LONG).show();

                    proSwipeBtn.showResultIcon(false);}
                else
                {
                    uploadCancelDetails(dc, d);
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // task success! show TICK icon in ProSwipeButton
                        proSwipeBtn.showResultIcon(true); // false if task failed
                    }
                }, 2000);
            }
        });
    }

    void uploadCancelDetails(int meals, Date rq_date)
    {

        final String Acceptance = "-1";
        final String b = (meals == 1 || meals == 4 || meals == 6 || meals ==  9)? "1":"0";
        final String d = (meals == 5 || meals == 6 || meals == 8 || meals ==  9)? "1":"0";
        final String l = (meals == 3 || meals == 4 || meals == 8 || meals ==  9)? "1":"0";
        //Todo take diet from shared preference
        final String diet = "1";

        sharedPreferences = this.getSharedPreferences(Constants.MY_PREFERENCE, Context.MODE_PRIVATE);
        final String name = sharedPreferences.getString(Constants.name,"");;

        final DateFormat df = new SimpleDateFormat("yyyy/M/d h:mm:ss a");
        final String request_date = df.format(rq_date);

        final String email = sharedPreferences.getString(Constants.email,"");
        final String email_refined = email.replaceAll("\\W+", "");

        sharedPreferences = getSharedPreferences(Constants.MY_PREFERENCE, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        //Time from server
        final Calendar calendar = Calendar.getInstance();
        DatabaseReference offsetRef = FirebaseDatabase.getInstance().getReference(".info/serverTimeOffset");
        offsetRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                double offset = snapshot.getValue(Double.class);
                double estimatedServerTimeMs = System.currentTimeMillis() + offset;
                calendar.setTimeInMillis(((long) estimatedServerTimeMs));
                calendar.setTimeInMillis(((long) estimatedServerTimeMs));
                Log.d("inter",""+calendar.getTime());
            }
            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Listener was cancelled");
            }
        });

        Toast.makeText(this, ""+request_date, Toast.LENGTH_SHORT).show();
        FirebaseDatabase PostReference = FirebaseDatabase.getInstance();
        final DatabaseReference mPostReference = PostReference.getReference("cancel_sheet");

        final String key = mPostReference.child(email_refined).push().getKey();
        mPostReference.child(email_refined).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        CancelDetails cancelDetails;
                        //Creating record to Firebase
                        SimpleDateFormat format = new SimpleDateFormat("yyyy/M/d h:mm:ss a");

                        cancelDetails = new CancelDetails(
                                Acceptance,
                                b,
                                d,
                                format.format(calendar.getTime()),
                                diet,
                                email,
                                l,
                                name,
                                request_date
                        );
                        mPostReference.child(email_refined).child(key).setValue(cancelDetails);
                        String filename = "CancelData";
                        //Getting number of cancel requests
                        int count = 0;
                        try {
                            FileInputStream inStream = openFileInput(filename);
                            ObjectInputStream objectInStream = new ObjectInputStream(inStream);
                            count = objectInStream.readInt();// Get the number of cancel requests
                            objectInStream.close();
                        }
                        catch (Exception e) {
                            Log.e("reading error",""+e);
                            e.printStackTrace();
                        }
                        //Updating internal storage
                        FileOutputStream outStream;
                        try {
                            outStream = openFileOutput(filename, Context.MODE_PRIVATE);
                            ObjectOutputStream objectOutStream = new ObjectOutputStream(outStream);
                            // Save size first
                            int sz = count +1;
                            objectOutStream.writeInt(sz);
                            objectOutStream.writeObject(cancelDetails);
                            objectOutStream.close();
                            outStream.close();
                        } catch (Exception e) {
                            Log.e("writer error", e+"");
                            e.printStackTrace();
                        }
                        Toast.makeText(ConfirmCancel.this, "requested", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ConfirmCancel.this, Dashboard.class);
                        i.putExtra("EXTRA", "notopenFragment");
                        startActivity(i);
                        finish();

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(ConfirmCancel.this, "Unable to send request", Toast.LENGTH_SHORT).show();
                        Log.w("cancel uplodaed or not", "loadPost:onCancelled", databaseError.toException());
                    }
                });
    }
}
