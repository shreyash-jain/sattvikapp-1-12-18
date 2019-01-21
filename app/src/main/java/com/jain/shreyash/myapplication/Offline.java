package com.jain.shreyash.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jain.shreyash.myapplication.R;
import com.jain.shreyash.utils.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Offline extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Boolean isInternetPresent = false;

    // Connection detector class
    ConnectionDetector cd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);
        final Button b1=findViewById(R.id.continued);
        sharedPreferences = getSharedPreferences(Constants.MY_PREFERENCE, Context.MODE_PRIVATE);
        String s = "name:"+sharedPreferences.getString(Constants.name,"")+",email:"+",password";
        //Toast.makeText(this, s, Toast.LENGTH_SHORT).show();

        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        if(!isInternetPresent){
            showAlertDialog(Offline.this, "We are nothing with out Internet",
                    "Please connect to net", false);
        }


        //TODO: Check online if form filled. Enable button

        b1.setEnabled(false);

        sharedPreferences = getSharedPreferences(Constants.MY_PREFERENCE, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        final String email = sharedPreferences.getString(Constants.email,"");
        final String email_refined = email.replaceAll("\\W+", "");

        FirebaseDatabase PostReference = FirebaseDatabase.getInstance();
        DatabaseReference mPostReference = PostReference.getReference("student_sheet");

        mPostReference.child("students").child(email_refined).
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        PersonDetails personDetails = dataSnapshot.getValue(PersonDetails.class);

                        editor.putString(Constants.isactive, personDetails.isactive);
                        editor.apply();
                        if(personDetails.isactive.equals("1"))
                        {
                            b1.setEnabled(true);
                            Intent i=new Intent(Offline.this,Dashboard.class);
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

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Offline.this,Dashboard.class);
                i.putExtra("EXTRA", "notopenFragment");
                startActivity(i);
                finish();
            }
        });
    }

    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting alert dialog icon
        alertDialog.setIcon((status) ? R.drawable.ic_signal_wifi_off_black_24dp : R.drawable.ic_signal_wifi_off_black_24dp);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
}


