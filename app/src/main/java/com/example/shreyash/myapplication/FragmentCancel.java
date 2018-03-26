package com.example.shreyash.myapplication;

/**
 * Created by Shreyash on 17-02-2018.
 */

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
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

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

        displayDate = rootview.findViewById(R.id.text_datepicker);
        displayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getContext(),mDateSetListener,year,month,day);
                dialog.show();
            }
        });
        mDateSetListener= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month = month+1;
                String dateString = dayOfMonth+"/"+month+"/"+year;
                displayDate.setText(dateString);
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date dateSelected = format.parse(dateString);
                    Date dateCurrent = Calendar.getInstance().getTime();
                    //TODO: get current date from backend
                    if(dateSelected.after(dateCurrent)){
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Select meals to cancel").setMultiChoiceItems(R.array.mealsCancel,
                                null, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                //TODO:get values and store in a variable
                                String s = "which:"+which+",isChecked:"+isChecked;
                                Toast.makeText(getContext(),s,Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //TODO: use the stored value to send in database
                                Toast.makeText(getContext(),"Clicked OK",Toast.LENGTH_SHORT).show();
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
                    else{
                        //TODO: Get data from backend and display it here by toast
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();
                }
            }
        };
        DatePickerDialog dialog1 = new DatePickerDialog(getContext(),mDateSetListener,year,month,day);
        dialog1.show();
        TextView diets_cancel = (TextView) rootview.findViewById(R.id.diets_cancel);
        //TODO: Get number online
        int number =0;
        String s = "Diets remaining to Cancel:"+number;
        diets_cancel.setText(s);
        return rootview;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Meals Cancellation");
    }
}

