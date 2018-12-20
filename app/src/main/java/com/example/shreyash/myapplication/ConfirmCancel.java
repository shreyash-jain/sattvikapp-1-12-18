package com.example.shreyash.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import in.shadowfax.proswipebutton.ProSwipeButton;

import static com.example.shreyash.myapplication.RegistrationConfirmation.getContext;

public class ConfirmCancel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_cancel);
       CheckBox chkIos = (CheckBox) findViewById(R.id.checkbtn);
        TextView dietstext=findViewById(R.id.cancel_diets);
        TextView datetext=findViewById(R.id.cancel_date);
        String toshow="";


        Intent intent=getIntent();
        int dc = intent.getIntExtra("Cancel_diets", -1);
        Date d = new Date();
        d.setTime(intent.getLongExtra("date", -1));

        if (dc==1) toshow+="Only Breakfast";
        else if (dc==3) toshow+="Only Lunch";
        else if (dc==5) toshow+="Only Dinner";else if (dc==4) toshow+="Breakfast and Lunch";else if (dc==6) toshow+="Breakfast and Dinner";
        else if (dc==8) toshow+="Lunch and Dinner";
        else if (dc==9) toshow+="Breakfast, Lunch and Dinner";
        dietstext.setText(toshow);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        String reportDate = df.format(d);
        datetext.setText(reportDate);
        ProSwipeButton proSwipeBtn = (ProSwipeButton) findViewById(R.id.awesome_btn);
        proSwipeBtn.setOnSwipeListener(new ProSwipeButton.OnSwipeListener() {
            @Override
            public void onSwipeConfirm() {
                // user has swiped the btn. Perform your async operation now
                if (!chkIos.isChecked()){

                    Toast.makeText(getApplicationContext(),"Request cancelled, Accept the condition to proceed",Toast.LENGTH_LONG).show();
                    
                    proSwipeBtn.showResultIcon(false);}


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

}
