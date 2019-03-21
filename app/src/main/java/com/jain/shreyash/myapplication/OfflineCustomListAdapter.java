package com.jain.shreyash.myapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

public class OfflineCustomListAdapter extends ArrayAdapter {
     public ArrayList<Integer> unchecked_bk = new ArrayList<Integer>();
    public ArrayList<Integer> unchecked_ln = new ArrayList<Integer>();
    public ArrayList<Integer> unchecked_dn = new ArrayList<Integer>();


    private final Activity context;

    private final String[] offline_cancel_date;
    private final String[] offline_day_week;
    private final Boolean[] ck_breakfast_offline;
    private final Boolean[] ck_lunch_offline;
    private final Boolean[] ck_dinner_offline;


    public OfflineCustomListAdapter(Activity context, String[] offline_cancel_date, String[] offline_day_week, Boolean[] ck_breakfast_offline, Boolean[] ck_lunch_offline, Boolean[] ck_dinner_offline){

        super(context,R.layout.offline_list_row , offline_cancel_date);
        this.context=context;
        this.offline_cancel_date=offline_cancel_date;
        this.offline_day_week=offline_day_week;
        this.ck_breakfast_offline=ck_breakfast_offline;
        this.ck_lunch_offline=ck_lunch_offline;
        this.ck_dinner_offline=ck_dinner_offline;

    }
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.offline_list_row, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView offline_date = (TextView) rowView.findViewById(R.id.offline_sel_date);
        TextView offline_day_of_week = (TextView) rowView.findViewById(R.id.offline_day_name);
        CheckBox ck_bk=rowView.findViewById(R.id.offline_breakfast_ck);
        CheckBox ck_ln=rowView.findViewById(R.id.offline_lunch_ck);
        CheckBox ck_dn=rowView.findViewById(R.id.offline_dinner_ck);



        //this code sets the values of the objects to values from the arrays
        offline_date.setText(offline_cancel_date[position]);
        offline_day_of_week.setText(offline_day_week[position]);
        ck_bk.setChecked(ck_breakfast_offline[position]);
        ck_dn.setChecked(ck_dinner_offline[position]);
        ck_ln.setChecked(ck_lunch_offline[position]);

        ck_bk.setOnCheckedChangeListener(new  CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if(ck_bk.isChecked())
                unchecked_bk.add(position);
                else{
                    if (unchecked_bk.contains(position))
                    unchecked_bk.remove(new Integer(position));
                }
            }
        });
        ck_ln.setOnCheckedChangeListener(new  CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if(ck_ln.isChecked())
                    unchecked_ln.add(position);
                else{
                    if (unchecked_ln.contains(position))
                    unchecked_ln.remove(new Integer(position));
                }
            }
        });
        ck_dn.setOnCheckedChangeListener(new  CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if(ck_dn.isChecked())
                    unchecked_dn.add(position);
                else{
                    if (unchecked_dn.contains(position))
                    unchecked_dn.remove(new Integer(position));
                }
            }
        });

        return rowView;

    };
}

