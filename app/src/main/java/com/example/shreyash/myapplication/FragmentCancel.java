package com.example.shreyash.myapplication;

/**
 * Created by Shreyash on 17-02-2018.
 */

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import java.net.InetAddress;
import java.net.URL;
import java.util.Date;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.NtpV3Packet;
import org.apache.commons.net.ntp.TimeInfo;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.instacart.library.truetime.TrueTime;
import com.tomer.fadingtextview.FadingTextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class FragmentCancel extends Fragment {
    private TextView displayDate;
    private  TextView viewallcancel;
    long tdiff;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootview = inflater.inflate(R.layout.fragment_cancel, container, false);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        viewallcancel=rootview.findViewById(R.id.view_more);
        viewallcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CancelList.class);
                startActivity(i);
            }
        });


       // checkTimeServer();



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
        com.applandeo.materialcalendarview.CalendarView calendarView = (com.applandeo.materialcalendarview.CalendarView) rootview.findViewById(R.id.calendarView);
        Calendar min = Calendar.getInstance();
        min.add(Calendar.DAY_OF_MONTH, 0);

        Calendar max = Calendar.getInstance();
        max.add(Calendar.DAY_OF_MONTH, 15);

        calendarView.setMinimumDate(min);
        calendarView.setMaximumDate(max);

        String[] texts = {"Click a date ","To cancel your meals"};
        FadingTextView FTV = (FadingTextView) rootview.findViewById(R.id.fadingTextView);
        FTV.setTexts(texts);
        final boolean[] correct_date = new boolean[1];

        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar clickedDayCalendar = eventDay.getCalendar();
                Date clickeddate=clickedDayCalendar.getTime();
                Date today = new Date();
                Date fdate = new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(fdate);
                c.add(Calendar.DATE, 15);
                fdate = c.getTime();
                correct_date[0] =isTimeAutomatic(getContext());
                if(!correct_date[0]){
                    Toast.makeText(getActivity(), "Please set time to automatic", Toast.LENGTH_LONG).show();
                }

                else if (!today.after(clickeddate) && !fdate.before(clickeddate) ){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select meals to cancel").setMultiChoiceItems(R.array.mealsCancel,
                        null, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                //TODO:get values and store in a variable


                                checkedmeals[which] = isChecked;

                                String currentItem = mealsList.get(which);

                                // Notify the current action


                            }
                        })
                        .setPositiveButton("PROCEED", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int sum=0;
                                if (checkedmeals[0]) sum+=1;if (checkedmeals[1]) sum+=3;if (checkedmeals[2]) sum+=5;
                                checkedmeals[0]=false;
                                checkedmeals[1]=false;
                                checkedmeals[2]=false;


                                    Intent intent = new Intent(getActivity(), ConfirmCancel.class);
                                    intent.putExtra("Cancel_diets", sum);
                                    intent.putExtra("date", clickeddate.getTime());
                                    sum=0;

                                    startActivity(intent);





                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();

            }}
        });





        // Boolean array for initial selected items



        ;



        //TODO: Get number online

        return rootview;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Meals Cancellation");

    }
    String TAG = "YOUR_APP_TAG";
    String TIME_SERVER = "0.europe.pool.ntp.org";
    public void checkTimeServer() {
        try {
            NTPUDPClient timeClient = new NTPUDPClient();
            InetAddress inetAddress = InetAddress.getByName(TIME_SERVER);
            TimeInfo timeInfo = timeClient.getTime(inetAddress);
            long setverTime = timeInfo.getMessage().getTransmitTimeStamp().getTime();

            // store this somewhere and use to correct system time
            long timeCorrection = System.currentTimeMillis()-setverTime;
            tdiff=timeCorrection;
        } catch (Exception e) {
            Log.v(TAG,"Time server error - "+e.getLocalizedMessage());
        }
    }
    public static boolean isTimeAutomatic(Context c) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global.getInt(c.getContentResolver(), Settings.Global.AUTO_TIME, 0) == 1;
        } else {
            return android.provider.Settings.System.getInt(c.getContentResolver(), android.provider.Settings.System.AUTO_TIME, 0) == 1;
        }
    }
}

