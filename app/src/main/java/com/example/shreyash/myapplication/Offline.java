package com.example.shreyash.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Offline extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    String abhinav = "mypref";
    String Name = "nameKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);
        Button b1=(Button)findViewById(R.id.continued);
        sharedPreferences = getSharedPreferences(abhinav, Context.MODE_PRIVATE);
        sharedPreferences.getString(Name,"");
        String s = "Name:"+sharedPreferences.getString(Name,"")+",Email:"+",Password";
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        //TODO: Check online if form filled. Enable button
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Offline.this,Dashboard.class);
                startActivity(i);
                finish();
            }
        });
    }
}
