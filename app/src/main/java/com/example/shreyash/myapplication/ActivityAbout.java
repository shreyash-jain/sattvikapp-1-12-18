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

        final TextView text_vision = findViewById(R.id.text_vision_content);
        final TextView title_vision =  findViewById(R.id.text_vision_title);
        expandCollapseText(title_vision,text_vision);
        final TextView text_history = findViewById(R.id.text_history_content);
        final TextView title_history =  findViewById(R.id.text_history_title);
        text_history.setVisibility(View.GONE);
        expandCollapseText(title_history,text_history);
        final TextView text_registration = findViewById(R.id.text_registration_procedure_content);
        final TextView title_registration =  findViewById(R.id.text_registration_procedure_title);
        text_registration.setVisibility(View.GONE);
        expandCollapseText(title_registration,text_registration);
        final TextView text_general = findViewById(R.id.text_general_rules_content);
        final TextView title_general =  findViewById(R.id.text_general_rules_title);
        text_general.setVisibility(View.GONE);
        expandCollapseText(title_general,text_general);
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
