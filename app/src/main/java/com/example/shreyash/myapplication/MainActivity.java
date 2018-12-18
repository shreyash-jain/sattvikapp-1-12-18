package com.example.shreyash.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.shreyash.utils.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
                String s = sharedPreferences.getString(Constants.email,"");
                //String active = sharedPreferences.getString(Constants.isactive,"0");
                if(s.equals("")) {

                    Intent i = new Intent(MainActivity.this, IntroActivity.class);
                    startActivity(i);
                    finish();
                }
                else {
                    final String email = sharedPreferences.getString(Constants.email,"");
                    final String email_refined = email.replaceAll("\\W+", "");

                    FirebaseDatabase PostReference = FirebaseDatabase.getInstance();
                    DatabaseReference mPostReference = PostReference.getReference("student_sheet");

                    mPostReference.child("students").child(email_refined).
                            addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    PersonDetails personDetails = dataSnapshot.getValue(PersonDetails.class);
                                    sharedPreferences = getSharedPreferences(Constants.MY_PREFERENCE, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(Constants.isactive, personDetails.isactive);
                                    editor.apply();
                                    if(personDetails.isactive.equals("0"))
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
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Log.w("activated or not", "loadPost:onCancelled", databaseError.toException());
                                }
                            });
                }

            }
        }, 3000);


    }
}
