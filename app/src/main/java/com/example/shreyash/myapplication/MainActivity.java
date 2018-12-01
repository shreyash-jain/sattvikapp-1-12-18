package com.example.shreyash.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import utils.Constants;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sharedpreferences = getSharedPreferences(Constants.MYPREFERENCE, Context.MODE_PRIVATE);
                String s =sharedpreferences.getString(Constants.NAME,"");
                if(s.equals("")) {
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
                else{
                    Intent i = new Intent(MainActivity.this, Dashboard.class);
                    startActivity(i);
                    finish();
                }
            }
        }, 3000);
    }
}
