package com.example.shreyash.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.shreyash.utils.Constants;

public class Offline extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);
        Button b1=findViewById(R.id.continued);
        sharedPreferences = getSharedPreferences(Constants.MY_PREFERENCE, Context.MODE_PRIVATE);
        String s = "name:"+sharedPreferences.getString(Constants.name,"")+",email:"+",password";
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


