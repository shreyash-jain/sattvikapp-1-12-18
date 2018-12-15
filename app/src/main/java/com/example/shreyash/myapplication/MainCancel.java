package com.example.shreyash.myapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.tomer.fadingtextview.FadingTextView;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static com.example.shreyash.myapplication.RegistrationConfirmation.getContext;

public class MainCancel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cancel);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        String[] meals = new String[]{
                "Breakfast",
                "Lunch",
                "Dinner",

        };
        final boolean[] checkedmeals = new boolean[]{
                false, // Red
                false, // Green
                false, // Blue


        };
        final List<String> mealsList = Arrays.asList(meals);
        com.applandeo.materialcalendarview.CalendarView calendarView = (com.applandeo.materialcalendarview.CalendarView) findViewById(R.id.calendarView);
        Calendar min = Calendar.getInstance();
        min.add(Calendar.DAY_OF_MONTH, 0);

        Calendar max = Calendar.getInstance();
        max.add(Calendar.DAY_OF_MONTH, 15);

        calendarView.setMinimumDate(min);
        calendarView.setMaximumDate(max);

        String[] texts = {"Click a date ","To cancel your meals"};
        FadingTextView FTV = (FadingTextView)findViewById(R.id.fadingTextView);
        FTV.setTexts(texts);

        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar clickedDayCalendar = eventDay.getCalendar();
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Select meals to cancel").setMultiChoiceItems(R.array.mealsCancel,
                        null, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                //TODO:get values and store in a variable
                                String s = "which:"+which+",isChecked:"+isChecked;

                                Toast.makeText(getContext(),s,Toast.LENGTH_SHORT).show();
                                checkedmeals[which] = isChecked;

                                String currentItem = mealsList.get(which);

                                // Notify the current action


                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int sum=0;
                                if (checkedmeals[0]) sum+=1;if (checkedmeals[1]) sum+=3;if (checkedmeals[2]) sum+=5;

                                Toast.makeText(getContext(),"kk"+ sum,Toast.LENGTH_SHORT).show();




                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });




        // Boolean array for initial selected items



        ;




        TextView dietsCancel = (TextView) findViewById(R.id.diets_cancel);
        //TODO: Get number online
        int number =0;
        String s = "Diets remaining to Cancel:"+number;
        dietsCancel.setText(s);

    }
}
