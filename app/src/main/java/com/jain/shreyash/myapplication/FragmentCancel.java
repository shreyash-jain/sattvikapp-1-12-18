package com.jain.shreyash.myapplication;

/**
 * Created by Shreyash on 17-02-2018.
 */

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.DatePicker;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.builders.DatePickerBuilder;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.google.firebase.database.ValueEventListener;
import com.jain.shreyash.myapplication.R;
import com.tomer.fadingtextview.FadingTextView;

import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class FragmentCancel extends Fragment {
    private TextView displayDate;

    List<CancelListItem> cancelList;

    //the recyclerview
    RecyclerView recyclerView;
    public  static String[] off_dates;
    List<CancelDetails> cancelDetailsArrayTemper;
    LinearLayout layout;
    ListView listView;
    TextView cancel_text;
    public  static String[] off_day;
    public  static int num_dates;
    long tdiff;
    Button btnTag;
    // flag for Internet connection status
    Boolean isInternetPresent = false;
    public  static ArrayList<Integer> uncheck_bk = new ArrayList<Integer>();
    public  static ArrayList<Integer> uncheck_ln = new ArrayList<Integer>();
    public  static ArrayList<Integer> uncheck_dn = new ArrayList<Integer>();

    // Connection detector class
    ConnectionDetector cd;
    int cancel_active;
    ArrayList<Integer> offline_coloumn_list = new ArrayList<Integer>();
    List<CheckCancelDate> cancel_dates_checker;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootview = inflater.inflate(R.layout.fragment_cancel, container, false);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        recyclerView = (RecyclerView) rootview.findViewById(R.id.recyclerViewcCancel);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

         layout = (LinearLayout) rootview.findViewById(R.id.add_button);
        listView = (ListView) rootview.findViewById(R.id.listview_on_offline);

        cancel_text=rootview.findViewById(R.id.cancel_btn_id);
        List<CancelDetails> cancelDetailsArrayTemp = new ArrayList<>();
        String filename = "CancelData";
        try {

            //FileInputStream inStream = new FileInputStream(filename);
            FileInputStream inStream = getActivity().openFileInput(filename);
            ObjectInputStream objectInStream = new ObjectInputStream(inStream);
            int count = objectInStream.readInt();// Get the number of cancel requests
            for (int c=0; c < count; c++)
                cancelDetailsArrayTemp.add((CancelDetails) objectInStream.readObject());
            objectInStream.close();
        }
        catch (Exception e) {
            Toast.makeText(getContext(), "ohhh", Toast.LENGTH_SHORT).show();
            Log.e("reading error",""+e);
            e.printStackTrace();
        }

        cancelList=new ArrayList<>();
        Collections.reverse(cancelDetailsArrayTemp);
        for(int i = 0; i< cancelDetailsArrayTemp.size();i++) {


            String status = cancelDetailsArrayTemp.get(i).Acceptance;
            if(status.equals("-1")) status = "pending";
            else if(status.equals("1")) status = "accepted";
            else if(status.equals("0")) status = "rejected";
            boolean b = cancelDetailsArrayTemp.get(i).b.equals("1");
            boolean l = cancelDetailsArrayTemp.get(i).l.equals("1");
            boolean d = cancelDetailsArrayTemp.get(i).d.equals("1");

            Calendar calc = Calendar.getInstance();

            calc.add(Calendar.DATE, -2);

            Date today = calc.getTime();





            String sdate= cancelDetailsArrayTemp.get(i).request_date.substring(0, cancelDetailsArrayTemp.get(i).request_date.length()-11);

            if (status=="pending"){
                try {
                    Date predate=new SimpleDateFormat("yyyy/MM/dd").parse(sdate);
                    if (predate.before(today)){
                        cancelList.add(

                                new CancelListItem("Pending...",cancelDetailsArrayTemp.get(i).date , sdate, b, l, d, R.color.thirdpage,false)
                        );

                    }
                    else {
                        cancelList.add(

                                new CancelListItem(status,cancelDetailsArrayTemp.get(i).date , sdate, b, l, d, R.color.bg_screen1,false)
                        );
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

           }
            else if (status=="accepted")
                cancelList.add(

                        new CancelListItem(status,cancelDetailsArrayTemp.get(i).date , sdate, b, l, d, R.color.thirdpage,false)
                );
            else
                cancelList.add(

                        new CancelListItem(status,cancelDetailsArrayTemp.get(i).date , sdate, b, l, d, R.color.thirdpage,false)
                );
        }
        CancellationAdapter adapter = new CancellationAdapter(getContext(), cancelList);
        recyclerView.setAdapter(adapter);













        // checkTimeServer();



        String[] meals = new String[]{
                "Breakfast",
                "Lunch",
                "Dinner",

        };


        final List<String> mealsList = Arrays.asList(meals);
        com.applandeo.materialcalendarview.CalendarView calendarView = (com.applandeo.materialcalendarview.CalendarView) rootview.findViewById(R.id.calendarView);


        Calendar max = Calendar.getInstance();
        max.add(Calendar.DAY_OF_MONTH, 5);


        calendarView.setMaximumDate(max);



        String[] texts = {"Click a date ","To cancel your meals"};
        FadingTextView FTV = (FadingTextView) rootview.findViewById(R.id.fadingTextView);
        FTV.setTexts(texts);
        final boolean[] correct_date = new boolean[1];


        FirebaseDatabase BoardReference = FirebaseDatabase.getInstance();

        DatabaseReference mBoardReference = BoardReference.getReference("cancel_sheet");
        cancel_active=1;
        mBoardReference.child("cancel_active").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CheckCancelActive message = dataSnapshot.getValue(CheckCancelActive.class);

                cancel_active=message.current;
                Log.e("current cancel stats",cancel_active+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        List<EventDay> cancel_events = new ArrayList<>();



       cancelDetailsArrayTemper = new ArrayList<>();

        try {

            //FileInputStream inStream = new FileInputStream(filename);
            FileInputStream inStream = getActivity().openFileInput(filename);
            ObjectInputStream objectInStream = new ObjectInputStream(inStream);
            int count = objectInStream.readInt();// Get the number of cancel requests
            for (int c=0; c < count; c++)
                cancelDetailsArrayTemper.add((CancelDetails) objectInStream.readObject());
            objectInStream.close();
        }
        catch (Exception e) {

            Log.e("reading error",""+e);
            e.printStackTrace();
        }
       cancel_dates_checker= new ArrayList<>();


        for(int i = 0; i< cancelDetailsArrayTemper.size();i++) {


            String sdate= cancelDetailsArrayTemper.get(i).request_date.substring(0, cancelDetailsArrayTemper.get(i).request_date.length()-12);
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            try {
                Date pdate = format.parse(sdate);
                Calendar event_day=toCalendar(pdate);
                String status = cancelDetailsArrayTemper.get(i).Acceptance;


                boolean b = cancelDetailsArrayTemper.get(i).b.equals("1");
                boolean l = cancelDetailsArrayTemper.get(i).l.equals("1");
                boolean d = cancelDetailsArrayTemper.get(i).d.equals("1");
                if(status.equals("1")) {
                    if (b && !l && !d) {
                        cancel_events.add(new EventDay(event_day, R.drawable.b));

                    } else if (!b && l && !d) {
                        cancel_events.add(new EventDay(event_day, R.drawable.l));
                    } else if (!b && !l && d) {
                        cancel_events.add(new EventDay(event_day, R.drawable.d));
                    } else if (b && l && !d) {
                        cancel_events.add(new EventDay(event_day, R.drawable.bl));
                    } else if (b && !l && d) {
                        cancel_events.add(new EventDay(event_day, R.drawable.bd));
                    } else if (!b && l && d) {
                        cancel_events.add(new EventDay(event_day, R.drawable.ld));
                    } else if (b && l && d) {
                        cancel_events.add(new EventDay(event_day, R.drawable.bld));
                    }
                }
                if(status.equals("-1")) {
                    if (b && !l && !d) {
                        cancel_events.add(new EventDay(event_day, R.drawable.pen_b));

                    } else if (!b && l && !d) {
                        cancel_events.add(new EventDay(event_day, R.drawable.pen_l));
                    } else if (!b && !l && d) {
                        cancel_events.add(new EventDay(event_day, R.drawable.pen_d));
                    } else if (b && l && !d) {
                        cancel_events.add(new EventDay(event_day, R.drawable.pen_bl));
                    } else if (b && !l && d) {
                        cancel_events.add(new EventDay(event_day, R.drawable.pen_bd));
                    } else if (!b && l && d) {
                        cancel_events.add(new EventDay(event_day, R.drawable.pen_ld));
                    } else if (b && l && d) {
                        cancel_events.add(new EventDay(event_day, R.drawable.pen_bld));
                    }
                }


                int acceptance=Integer.valueOf(cancelDetailsArrayTemper.get(i).Acceptance);
                cancel_dates_checker.add(new CheckCancelDate(pdate,acceptance,b,l,d));
            } catch (ParseException e) {
                e.printStackTrace();
            }





        }
        calendarView.setEvents(cancel_events);

        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar clickedDayCalendar = eventDay.getCalendar();
                Date clickeddate=clickedDayCalendar.getTime();
                Date today = new Date();
                Date fdate = new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(fdate);
                c.add(Calendar.DATE, 5);
                fdate = c.getTime();
                correct_date[0] =isTimeAutomatic(getContext());
                cd = new ConnectionDetector(getActivity().getApplicationContext());
                isInternetPresent = cd.isConnectingToInternet();
                if(!isInternetPresent){
                    Toast.makeText(getActivity(), "Please connect to internet", Toast.LENGTH_LONG).show();
                }
                else if(!correct_date[0]){
                    Toast.makeText(getActivity(), "Your time is not correct, Please set time to automatic", Toast.LENGTH_LONG).show();
                }
                else if(cancel_active==0){
                    createAlertDialogCancel();
                    //Toast.makeText(getActivity(), "Cancel service is currently unavailable", Toast.LENGTH_LONG).show();

                }

                else if (!today.after(clickeddate) && !fdate.before(clickeddate) ){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    Calendar maxc = Calendar.getInstance();
                    maxc.add(clickedDayCalendar.DAY_OF_MONTH, 5);
                    Calendar minc = Calendar.getInstance();
                    minc.add(Calendar.DAY_OF_MONTH, -1);
                    DatePickerBuilder builder2 = new DatePickerBuilder(getContext(), listener)

                            .pickerType(CalendarView.MANY_DAYS_PICKER)
                            .minimumDate(clickedDayCalendar) // Minimum available date
                            .maximumDate(maxc)
                            .headerColor(R.color.background);


                    DatePicker datePicker = builder2.build();
                    datePicker.show();

                /*
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
                                int coinside=0;
                                for(int i = 0; i< cancel_dates_checker.size();i++){
                                    if ((clickeddate.compareTo(cancel_dates_checker.get(i).for_date)==0)){
                                        if(checkedmeals[0]==true && cancel_dates_checker.get(i).ck_breakfast==true){
                                            coinside=1;
                                            break;
                                        }
                                        if(checkedmeals[1]==true && cancel_dates_checker.get(i).ck_lunch==true){
                                            coinside=1;
                                            break;
                                        }
                                        if(checkedmeals[2]==true && cancel_dates_checker.get(i).ck_dinner==true){
                                            coinside=1;
                                            break;
                                        }
                                    }

                                }
                                checkedmeals[0]=false;
                                checkedmeals[1]=false;
                                checkedmeals[2]=false;
                                if (sum>0 && coinside==0){


                                    Intent intent = new Intent(getActivity(), ConfirmCancel.class);
                                    intent.putExtra("Cancel_diets", sum);
                                    intent.putExtra("date", clickeddate.getTime());
                                    sum=0;coinside=0;
                                    startActivity(intent);
                                }

                                else {
                                    if (sum==0)
                                    Toast.makeText(getActivity(), "Please select any meal", Toast.LENGTH_LONG).show();
                                    else   Toast.makeText(getActivity(), "You are selecting already requested meal", Toast.LENGTH_LONG).show();
                                }







                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();

            */}}
        });





        // Boolean array for initial selected items



        ;



        //TODO: Get number online

        return rootview;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createAlertDialogOnCreate();
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Meals Cancellation");

    }


    public static boolean isTimeAutomatic(Context c) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global.getInt(c.getContentResolver(), Settings.Global.AUTO_TIME, 0) == 1;
        } else {
            return android.provider.Settings.System.getInt(c.getContentResolver(), android.provider.Settings.System.AUTO_TIME, 0) == 1;
        }
    }


    public static Calendar toCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
    private OnSelectDateListener listener = new OnSelectDateListener() {
        @Override
        public void onSelect(List<Calendar> calendars) {
            offline_coloumn_list.clear();

            num_dates=calendars.size();
            off_dates = new String[num_dates];
            off_day = new String[num_dates];
            Boolean[] make_ck_set =new Boolean[num_dates];
            Toast.makeText(getActivity(), String.valueOf(num_dates), Toast.LENGTH_SHORT).show();
            for(int i = 0; i<num_dates; i++){
                Calendar thiscal= calendars.get(i);
                Date thisdate=thiscal.getTime();
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                DateFormat df_day = new SimpleDateFormat("dd");
                String get_only_day_string = df_day.format(thisdate);

                int this_day_of_month = Integer.valueOf(get_only_day_string);
                DateFormat df_month = new SimpleDateFormat("MM");
                String get_only_month_string=df_month.format(thisdate);
                int this_month = Integer.valueOf(get_only_month_string);
                String reportDate = df.format(thisdate);
                SimpleDateFormat formatter = new SimpleDateFormat("EEE");
                String this_day = formatter.format(thiscal.getTime());
                off_dates[i]=reportDate;
                off_day[i]=this_day;
                make_ck_set[i]=false;
                offline_coloumn_list.add(7+this_day_of_month+(this_month-1)*31);


                cancel_text.setText("Select meals to cancel");


            }

            btnTag = new Button(getContext());

            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params1.weight=1.0f;
            params1.gravity= Gravity.RIGHT;
            layout.removeAllViews();
            listView.setAdapter(null);
            btnTag.setLayoutParams(params1);
            btnTag.setText("Continue");
            btnTag.setId(0);
            layout.addView(btnTag);
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = (168)*num_dates;
            OfflineCustomListAdapter whatever = new OfflineCustomListAdapter(getActivity(),off_dates,off_day, make_ck_set,make_ck_set,make_ck_set);
            listView.setLayoutParams(params);
            listView.requestLayout();
            listView.setAdapter(whatever);
            btnTag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ListAdapter adapter = listView.getAdapter();
                    uncheck_bk =((OfflineCustomListAdapter) adapter).unchecked_bk;
                    uncheck_ln =((OfflineCustomListAdapter) adapter).unchecked_ln;
                    uncheck_dn =((OfflineCustomListAdapter) adapter).unchecked_dn;



                    Log.i("BK: ",uncheck_bk.toString());
                    Log.i("LN: ",uncheck_ln.toString());
                    Log.i("DN: ",uncheck_dn.toString());
                    int every_day=0;
                    for(int ind=0;ind<num_dates;ind++){
                        if (!uncheck_bk.contains(ind) && !uncheck_ln.contains(ind) && !uncheck_dn.contains(ind))
                        {
                            every_day=1;
                            break;
                        }
                        for(int i = 0; i< cancelDetailsArrayTemper.size();i++) {


                            String sdate= cancelDetailsArrayTemper.get(i).request_date.substring(0, cancelDetailsArrayTemper.get(i).request_date.length()-12);
                            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                            try {
                                Date pdate = format.parse(sdate);
                                Calendar event_day=toCalendar(pdate);
                                String status = cancelDetailsArrayTemper.get(i).Acceptance;


                                boolean b = cancelDetailsArrayTemper.get(i).b.equals("1");
                                boolean l = cancelDetailsArrayTemper.get(i).l.equals("1");
                                boolean d = cancelDetailsArrayTemper.get(i).d.equals("1");





                            } catch (ParseException e) {
                                e.printStackTrace();
                            }





                        }
                    }
                    if (every_day==1){
                        Toast.makeText(getActivity(), "Select meals to proceed", Toast.LENGTH_SHORT).show();


                    }
                    else
                        {
                            Intent intent = new Intent(getActivity(), ConfirmCancel.class);
                           // intent.putExtra("Cancel_diets", 1);


                            startActivity(intent);
                    }

                }
            });

        }

    };


    public void createAlertDialogCancel() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        // Setting Dialog Title
        alertDialog.setTitle("Oops!");

        // Setting Dialog Message
        alertDialog.setMessage("Cancellation service temporarily unavailable");

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.ic_cloud_off_black_24dp);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // User pressed YES button. Write Logic Here



                Toast.makeText(getContext(), "Please check Noticeboard for further update",Toast.LENGTH_SHORT).show();
            }
        });

        // Setting Negative "NO" Button





        // Showing Alert Message
        alertDialog.show();

    }

    public void createAlertDialogOnCreate() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        // Setting Dialog Title
        alertDialog.setTitle("Do Online Cancellation only at Emergency");

        // Setting Dialog Message
        alertDialog.setMessage("We are counting your requests, if exceeded over an limit your requests will be automatically cancelled");

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.ic_warning_black_24dp);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // User pressed YES button. Write Logic Here

                // Toast.makeText(getContext(), "Please check Noticeboard for further update",Toast.LENGTH_SHORT).show();
            }
        });

        // Setting Negative "NO" Button





        // Showing Alert Message
        alertDialog.show();

    }






}

