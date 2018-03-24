package com.example.shreyash.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
//TODO: Correct scrolling in this activity
public class ActivityAbout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        final ScrollView scroll = (ScrollView)findViewById(R.id.scroll_about);

        final TextView text_vision = (TextView)findViewById(R.id.text_vision_content);
        TextView title_vision = (TextView) findViewById(R.id.text_vision_title);
        title_vision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(text_vision.isShown()){
                    text_vision.setVisibility(View.GONE);
                }
                else{
                    text_vision.setVisibility(View.VISIBLE);
                }
            }
        });
        final TextView text_history = (TextView)findViewById(R.id.text_history_content);
        final TextView title_history = (TextView) findViewById(R.id.text_history_title);
        text_history.setVisibility(View.GONE);
        title_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(text_history.isShown()){
                    text_history.setVisibility(View.GONE);
                }
                else{
                    text_history.setVisibility(View.VISIBLE);
                    int[] location = new int[2];
                    title_history.getLocationOnScreen(location);
                    scroll.smoothScrollTo(location[0],location[1]);
                }
            }
        });
        final TextView text_registration = (TextView)findViewById(R.id.text_registration_procedure_content);
        final TextView title_registration = (TextView) findViewById(R.id.text_registration_procedure_title);
        text_registration.setVisibility(View.GONE);
        title_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(text_registration.isShown()){
                    text_registration.setVisibility(View.GONE);
                }
                else{
                    text_registration.setVisibility(View.VISIBLE);
                    int[] location = new int[2];
                    title_history.getLocationOnScreen(location);
                    scroll.smoothScrollTo(location[0],location[1]);
                }
            }
        });
        final TextView text_general = (TextView)findViewById(R.id.text_general_rules_content);
        final TextView title_general = (TextView) findViewById(R.id.text_general_rules_title);
        text_general.setVisibility(View.GONE);
        title_general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(text_general.isShown()){
                    text_general.setVisibility(View.GONE);
                }
                else{
                    text_general.setVisibility(View.VISIBLE);
                    int[] location = new int[2];
                    title_history.getLocationOnScreen(location);
                    scroll.smoothScrollTo(location[0],location[1]);
                }
            }
        });
    }
}
