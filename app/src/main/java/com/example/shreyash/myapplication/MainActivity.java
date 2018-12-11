package com.example.shreyash.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.shreyash.utils.Constants;

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
                if(s.equals("")) {

                    Intent i = new Intent(MainActivity.this, IntroActivity.class);
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
        }, 3000);


    }
}
