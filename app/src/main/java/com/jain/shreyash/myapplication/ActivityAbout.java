package com.jain.shreyash.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jain.shreyash.myapplication.R;

//TODO: Correct scrolling in this activity
public class ActivityAbout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        final ScrollView scroller=findViewById(R.id.scroll_about);

        final TextView textVision = findViewById(R.id.text_vision_content);
        final TextView titleVision =  findViewById(R.id.text_vision_title);
        expandCollapseText(titleVision,textVision);
        final TextView textHistory = findViewById(R.id.text_history_content);

        final TextView titleHistory = findViewById(R.id.text_history_title);

        textHistory.setVisibility(View.GONE);
        expandCollapseText(titleHistory,textHistory);
        final TextView textRegistration = findViewById(R.id.text_registration_procedure_content);
        final TextView titleRegistration =  findViewById(R.id.text_registration_procedure_title);
        textRegistration.setVisibility(View.GONE);
        expandCollapseText(titleRegistration,textRegistration);
        final TextView textGeneral = findViewById(R.id.text_general_rules_content);
        final TextView titleGeneral =  findViewById(R.id.text_general_rules_title);
        textGeneral.setVisibility(View.GONE);
        expandCollapseText(titleGeneral,textGeneral);
        titleHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              {
                    Log.d("shreyash","scroll end");
                    if(textHistory.isShown()){
                        textHistory.setVisibility(View.GONE);
                    }
                    else{
                        textHistory.setVisibility(View.VISIBLE);

                    }

                    //How to get the scrollview move to the end of the page here??
                }

            }
        });

    }

    void expandCollapseText(final TextView title,final TextView text){
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(text.isShown()){
                    text.setVisibility(View.GONE);
                }
                else{
                    text.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
