package com.example.shreyash.myapplication;

/**
 * Created by Shreyash on 17-02-2018.
 */

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.tomer.fadingtextview.FadingTextView;

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

public class FragmentCancel extends Fragment {
    private TextView displayDate;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootview = inflater.inflate(R.layout.fragment_cancel, container, false);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
       /* String TIME_SERVER = "time-a.nist.gov";
        NTPUDPClient timeClient = new NTPUDPClient();
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getByName(TIME_SERVER);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        TimeInfo timeInfo = null;
        try {
            timeInfo = timeClient.getTime(inetAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long returnTime = timeInfo.getMessage().getTransmitTimeStamp().getTime();
        Date time = new Date(returnTime);
       DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String reportDate = df.format(time);*/


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
              // Toast.makeText(getContext(),reportDate,Toast.LENGTH_LONG).show();
                if (!today.after(clickeddate) && !fdate.before(clickeddate) ){
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


        TextView dietsCancel = (TextView) rootview.findViewById(R.id.diets_cancel);
        //TODO: Get number online
        int number =0;
        String s = "Diets remaining to Cancel:  "+number;
        dietsCancel.setText(s);
        return rootview;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Meals Cancellation");
    }
}

