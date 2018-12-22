package com.example.shreyash.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.shreyash.utils.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //TODO: check if already login from local database. If data present go to Dashboard Else go to LoginActivity
                sharedPreferences = getSharedPreferences(Constants.MY_PREFERENCE, Context.MODE_PRIVATE);
                String mail = sharedPreferences.getString(Constants.email,"");
                //String active = sharedPreferences.getString(Constants.isactive,"0");
                if(mail.equals("")) {

                    Intent i = new Intent(MainActivity.this, IntroActivity.class);
                    startActivity(i);
                    finish();
                }
                else {
                    final boolean[] isupdated = {false};
                    final String email = sharedPreferences.getString(Constants.email,"");
                    final String email_refined = email.replaceAll("\\W+", "");

                    sharedPreferences = getSharedPreferences(Constants.MY_PREFERENCE, Context.MODE_PRIVATE);
                    final SharedPreferences.Editor editor = sharedPreferences.edit();

                    FirebaseDatabase PostReference = FirebaseDatabase.getInstance();
                    DatabaseReference mPostReference = PostReference.getReference("student_sheet");

                    mPostReference.child("students").child(email_refined).
                            addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    PersonDetails personDetails = dataSnapshot.getValue(PersonDetails.class);

                                    editor.putString(Constants.isactive, personDetails.isactive);
                                    editor.apply();
                                    isupdated[0] = true;
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Log.w("activated or not", "loadPost:onCancelled", databaseError.toException());
                                }
                            });

                    DatabaseReference cPostReference = PostReference.getReference("cancel_sheet");
                    cPostReference.child(email_refined).
                            addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    List<CancelDetails> cancelDetailsArray = new ArrayList<>();
                                    for (DataSnapshot child: dataSnapshot.getChildren()) {
                                        cancelDetailsArray.add(child.getValue(CancelDetails.class));
                                    }
                                    //Saving to internal storage
                                    String filename = "CancelData";
                                    FileOutputStream outStream;
                                    try {
                                        outStream = openFileOutput(filename, Context.MODE_PRIVATE);
                                        ObjectOutputStream objectOutStream = new ObjectOutputStream(outStream);
                                        // Save size first
                                        objectOutStream.writeInt(cancelDetailsArray.size());
                                        for(CancelDetails var:cancelDetailsArray)
                                            objectOutStream.writeObject(var);
                                        objectOutStream.close();
                                        outStream.close();
                                    } catch (Exception e) {
                                        Toast.makeText(MainActivity.this, "oho", Toast.LENGTH_SHORT).show();
                                        Log.e("writer error", e+"");
                                        e.printStackTrace();
                                    }


                                    /*
                                    // Reading from internal storage
                                    List<CancelDetails> cancelDetailsArrayTemp = new ArrayList<>();
                                    String filename = "CancelData";
                                    try {
                                        Toast.makeText(CancelList.this, "reading", Toast.LENGTH_SHORT).show();
                                        //FileInputStream inStream = new FileInputStream(filename);
                                        FileInputStream inStream = openFileInput(filename);
                                        ObjectInputStream objectInStream = new ObjectInputStream(inStream);
                                        int count = objectInStream.readInt();// Get the number of cancel requests
                                        for (int c=0; c < count; c++)
                                            cancelDetailsArrayTemp.add((CancelDetails) objectInStream.readObject());
                                        objectInStream.close();
                                    }
                                    catch (Exception e) {
                                        Toast.makeText(CancelList.this, "ohhh", Toast.LENGTH_SHORT).show();
                                        Log.e("reading error",""+e);
                                        e.printStackTrace();
                                    }

                                    cancelList=new ArrayList<>();
                                    for(int i = 0; i< cancelDetailsArrayTemp.size();i++)
                                    {
                                        int acep = Integer.parseInt(cancelDetailsArrayTemp.get(i).Acceptance);
                                        /*cancelList.add(
                                                new CancelListItem(acep,"Lunch and Dinner",cancelDetailsArrayTemp.get(i).request_date)
                                        );
                                     Toast.makeText(CancelList.this, ""+cancelDetailsArrayTemp.get(i).request_date, Toast.LENGTH_SHORT).show();
                                     }
                                     */
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Log.w("activated or not", "loadPost:onCancelled", databaseError.toException());
                                }
                            });

                    String active = sharedPreferences.getString(Constants.isactive,"0");
                    //while(!isupdated[0]);
                    nextPage(isupdated[0], active);
                }

            }
        }, 500);


    }

    void nextPage(boolean isupdated, String active)
    {
        if(active.equals("0") || !isupdated)
        {
            Intent i = new Intent(MainActivity.this, Offline.class);
            i.putExtra("EXTRA", "notopenFragment");
            startActivity(i);
            finish();
        }
        else{
            Intent i = new Intent(MainActivity.this, Dashboard.class);
            i.putExtra("EXTRA", "notopenFragment");
            startActivity(i);
            finish();
        }
    }
}
