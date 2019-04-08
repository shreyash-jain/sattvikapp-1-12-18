package com.jain.shreyash.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.CardView;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.text.DateFormatSymbols;

import com.applandeo.materialcalendarview.EventDay;
import com.jain.shreyash.myapplication.R;
import com.jain.shreyash.utils.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import static com.jain.shreyash.myapplication.FragmentCancel.toCalendar;

public class FragmentBoard extends Fragment  {
    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    List<CancelDetails> cancelDetailsArrayTemper;
    int count;
    int other;
    int limiter;
    Boolean poll_active;
    private Date date;
    private Date dateCompareOne;
    private Date dateCompareTwo;
    private Date dateCompareThree;
    private Date dateCompareFour;



    private String compareStringOne = "20:00";
    private String compareStringTwo = "23:59";
    private String compareStringThree = "00:01";
    private String compareStringFour = "11:59";

    public static final String inputFormat = "HH:mm";
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;

    SimpleDateFormat inputParser = new SimpleDateFormat(inputFormat, Locale.US);

    private ViewPager viewPager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View rootview= inflater.inflate(R.layout.fragment_board, container, false);

        tabLayout=rootview.findViewById(R.id.tab_id);
        appBarLayout=rootview.findViewById(R.id.appboard);
        viewPager=rootview.findViewById(R.id.view_pager);
        ViewPagerAdapter adapter= new ViewPagerAdapter(getChildFragmentManager());
        adapter.AddFragment(new FragmentToday(),"Today");
        adapter.AddFragment(new FragmentTomorrow(),"Tomorrow");
        adapter.AddFragment(new FragmentDAT(),"Next Day");

        viewPager.setAdapter(adapter);

        FirebaseDatabase BoardReference_poll = FirebaseDatabase.getInstance();


        DatabaseReference myRef = BoardReference_poll.getReference("feedback_sheet");

        String get_poll;

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String str = sdf.format(new Date());
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/YYYY");
        String str_tod = sdf2.format(new Date());
        String str_yes= sdf2.format(yesterday());

        materialDesignFAM = (FloatingActionMenu) rootview.findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (FloatingActionButton) rootview.findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = (FloatingActionButton) rootview.findViewById(R.id.material_design_floating_action_menu_item2);
        floatingActionButton3 = (FloatingActionButton) rootview.findViewById(R.id.material_design_floating_action_menu_item3);

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                View targetView = rootview.findViewById(R.id.notice_ll);
                targetView.getParent().requestChildFocus(targetView,targetView);
                materialDesignFAM.close(true);
                //TODO something when floating action menu first item clicked

            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                View targetView = rootview.findViewById(R.id.menu_ll);
                targetView.getParent().requestChildFocus(targetView,targetView);
                materialDesignFAM.close(true);
                //TODO something when floating action menu second item clicked

            }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                View targetView = rootview.findViewById(R.id.expense_ll);
                targetView.getParent().requestChildFocus(targetView,targetView);
                materialDesignFAM.close(true);
                //TODO something when floating action menu third item clicked

            }
        });
        date = parseDate(str);

        dateCompareOne = parseDate(compareStringOne);
        dateCompareTwo = parseDate(compareStringTwo);
        dateCompareThree = parseDate(compareStringThree);
        dateCompareFour = parseDate(compareStringFour);

        String add_date;
        if ( dateCompareOne.before( date ) && dateCompareTwo.after(date)) {
           get_poll="poll2";
           add_date=str_tod;
        }
        else if ( dateCompareThree.before( date ) && dateCompareFour.after(date)) {
            get_poll="poll2";
            add_date=str_yes;
        }
        else {
            get_poll = "poll3";

        add_date=str_tod;}

        SharedPreferences sp = getContext().getSharedPreferences("poll_compare_board", Activity.MODE_PRIVATE);
        String get_date= sp.getString("date_saved_poll", "2010-04-12-04-55-11");

        SimpleDateFormat formatter1=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        limiter=0;
        Date date1=new Date();
        try {
            date1=formatter1.parse(get_date);
            Date date2=new Date();

            long duration  = date2.getTime() - date1.getTime();
            long diffInDays = TimeUnit.MILLISECONDS.toHours(duration);
            Log.d("hours",date1.toString()+"first"+date2.toString()+"second"+diffInDays+"");
            if (diffInDays>4) limiter=1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DatabaseReference mBoardReference_poll = BoardReference_poll.getReference("poll_sheet");

        other=0;
        count=1;


        mBoardReference_poll.child(get_poll).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                other++;
                getPollDetails message = dataSnapshot.getValue(getPollDetails.class);
                Log.e("current cancel stats",dataSnapshot.getKey());
                poll_active=message.poll_bool;
                Log.e("current cancel stats",poll_active+"");


                if (poll_active && count==1 && other==1 ){



                    LayoutInflater inflater = LayoutInflater.from(getContext());

                    LinearLayout options_layout = (LinearLayout) rootview.findViewById(R.id.poll_layout_feedback);
                    View to_add = inflater.inflate(R.layout.poll_layout, options_layout,false);

                    TextView poll_head=to_add.findViewById(R.id.poll_title);
                    poll_head.setText(message.poll_name+"\non "+add_date);
                    RadioButton rg1=to_add.findViewById(R.id.choice_1);
                    RadioButton rg2=to_add.findViewById(R.id.choice_2);
                    RadioButton rg3=to_add.findViewById(R.id.choice_3);
                    TextView per1=to_add.findViewById(R.id.per1);
                    TextView per2=to_add.findViewById(R.id.per2);
                    TextView per3=to_add.findViewById(R.id.per3);
                    rg1.setText(message.poll_item1);
                    rg2.setText(message.poll_item2);
                    rg3.setText(message.poll_item3);


                    ContentLoadingProgressBar pb1=to_add.findViewById(R.id.progress_choice_1);
                    ContentLoadingProgressBar pb2=to_add.findViewById(R.id.progress_choice_2);
                    ContentLoadingProgressBar pb3=to_add.findViewById(R.id.progress_choice_3);



                    Button btn=to_add.findViewById(R.id.vote);
                    options_layout.addView(to_add);

                    if(limiter==0){

                        float a1=message.poll_count1;
                        float a2=message.poll_count2;
                        float a3=message.poll_count3;
                        float total;
                        total=(a1+a2+a3);
                        a1=((float)a1/(float)total)*100;
                        a2=((float)a2/(float)total)*100;
                        a3=((float)a3/(float)total)*100;


                        pb1.setProgress((int) a1);
                        pb2.setProgress((int) a2);
                        pb3.setProgress((int) a3);
                        per1.setText(a1+"%");
                        per2.setText(a2+"%");
                        per3.setText(a3+"%");

                        btn.setEnabled(false);
                    }

                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FirebaseDatabase BoardReference = FirebaseDatabase.getInstance();

                            DatabaseReference mBoardReference_poll = BoardReference.getReference("poll_sheet");
                            DatabaseReference hopperRef = mBoardReference_poll.child(get_poll);

                            Map<String, Object> hopperUpdates = new HashMap<>();
                            int v1=0,v2=0,v3=0;
                            if(rg1.isChecked()){



                                v1++;
                                count++;
                                hopperUpdates.put("poll_count1", message.poll_count1+1);

                                hopperRef.updateChildren(hopperUpdates);

                                btn.setEnabled(false);
                            }
                            else if(rg2.isChecked()){
                                count++;

                                v2++;
                                hopperUpdates.put("poll_count2", message.poll_count2+1);

                                hopperRef.updateChildren(hopperUpdates);

                                btn.setEnabled(false);
                            }
                            else if(rg3.isChecked())
                            { count++;
                                hopperUpdates.put("poll_count3", message.poll_count3+1);

                                v3++;
                                hopperRef.updateChildren(hopperUpdates);

                                btn.setEnabled(false);}
                            else {
                                Toast.makeText(getContext(),"select an option",Toast.LENGTH_LONG).show();


                            }
                            float a1=message.poll_count1+v1;
                            float a2=message.poll_count2+v2;
                            float a3=message.poll_count3+v3;
                            float total;
                            total=(a1+a2+a3);
                            a1=((float)a1/(float)total)*100;
                            a2=((float)a2/(float)total)*100;
                            a3=((float)a3/(float)total)*100;


                            pb1.setProgress((int) a1);
                            pb2.setProgress((int) a2);
                            pb3.setProgress((int) a3);
                            per1.setText(a1+"%");
                            per2.setText(a2+"%");
                            per3.setText(a3+"%");

                            SharedPreferences sp = getContext().getSharedPreferences("poll_compare_board", Activity.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            String date = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.getDefault()).format(new Date());
                            editor.putString("date_saved_poll",date);
                            editor.commit();
                            if(v3==1){


                                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

                                alert.setTitle("What went wrong ?");
                                alert.setMessage("Please elaborate");

// Set an EditText view to get user input
                                final EditText input = new EditText(getContext());
                                alert.setView(input);

                                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                        // Do something with value!
                                        final Calendar calendar = Calendar.getInstance();
                                        DatabaseReference offsetRef = FirebaseDatabase.getInstance().getReference(".info/serverTimeOffset");
                                        offsetRef.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot snapshot) {
                                                double offset = snapshot.getValue(Double.class);
                                                double estimatedServerTimeMs = System.currentTimeMillis() + offset;
                                                calendar.setTimeInMillis(((long) estimatedServerTimeMs));
                                                calendar.setTimeInMillis(((long) estimatedServerTimeMs));
                                                Log.d("inter",""+calendar.getTime());
                                            }
                                            @Override
                                            public void onCancelled(DatabaseError error) {
                                                System.err.println("Listener was cancelled");
                                            }
                                        });
                                        SharedPreferences sharedpreferences;
                                        sharedpreferences = getActivity().getSharedPreferences(Constants.MY_PREFERENCE, Context.MODE_PRIVATE);
                                        final String name = sharedpreferences.getString(Constants.name,"");
                                        final String email = sharedpreferences.getString(Constants.email,"");



                                        FirebaseDatabase PostReference = FirebaseDatabase.getInstance();
                                        DatabaseReference mPostReference = PostReference.getReference("feedback_sheet");

                                        final String key = myRef.child("feedbacks").push().getKey();
                                        mPostReference.child("feedbacks").
                                                addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        Feedback_Details feedbackDetails;
                                                        //Creating record to Firebase
                                                        SimpleDateFormat format = new SimpleDateFormat("yyyy/M/d h:mm:ss a");
                                                        //Toast.makeText(Registration.this, ""+format.format(calendar.getTime()), Toast.LENGTH_SHORT).show();

                                                        feedbackDetails = new Feedback_Details(
                                                                format.format(calendar.getTime()),
                                                                name,
                                                                "From daily poll",
                                                                "0",
                                                                input.getText().toString(),
                                                                email
                                                        );
                                                        myRef.child("feedbacks").child(key).setValue(feedbackDetails);
                                                        //Log.d("flag in registering",flag)

                                                        Toast.makeText(getActivity(),"Thanks for your Feedback ",Toast.LENGTH_SHORT).show();
                                                        Fragment myFragment=new FragmentBoard();
                                                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                                        ft.replace(R.id.content_frame,  myFragment, "MY_FRAGMENT");
                                                        ft.commit();
                                                        //Toast.makeText(Registration.this, "Welcome " + name, Toast.LENGTH_SHORT).show();
                                                    }
                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {

                                                        Log.w("feedback submit or not", "loadPost:onCancelled", databaseError.toException());
                                                    }
                                                });
                                    }
                                });

                                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        // Canceled.
                                    }
                                });

                                alert.show();
                            }

                            /*SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                            editor.putString("date_saved",date);
                            editor.commit();
                            Toast.makeText(getApplicationContext(),"Response taken, and you know what its good to have your say !",Toast.LENGTH_LONG).show();
                            */
                        }
                    });




                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






        tabLayout.setupWithViewPager(viewPager);


        final TextView notice_text=(TextView)rootview.findViewById(R.id.notice_board_text);



        final TextView diet_break = rootview.findViewById(R.id.diet_break);
        final TextView diet_lunch = rootview.findViewById(R.id.diet_lunch);
        final TextView diet_dinner = rootview.findViewById(R.id.diet_dinner);
        final TextView total_diets = rootview.findViewById(R.id.total_diets);
        final TextView eta_expense = rootview.findViewById(R.id.eta_expense);
        final TextView extras = rootview.findViewById(R.id.extras);
        final TextView service= rootview.findViewById(R.id.service);
        final TextView prev=rootview.findViewById(R.id.prev_cost);
        final TextView total_expense=rootview.findViewById(R.id.total_expense);
        final ImageView info_bf=rootview.findViewById(R.id.info_bf);
        final ImageView info_prev=rootview.findViewById(R.id.info_prev);
        final ImageView info_diets=rootview.findViewById(R.id.info_diets);
        final CardView card_cancel=rootview.findViewById(R.id.cv);
        final CardView card_feedback=rootview.findViewById(R.id.cv2);
        final CardView card_chat=rootview.findViewById(R.id.cv3);
        final TextView month=rootview.findViewById(R.id.month);
        Calendar c = Calendar.getInstance();
        String monthString;


        int month_index = c.get(Calendar.MONTH);

        monthString = new DateFormatSymbols().getMonths()[month_index];
        month.setText(monthString);
        card_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ChatActivity.class);
                startActivity(i);
            }
        });
       card_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FragmentCancel();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragment);

                fragmentTransaction.commit();



            }
        });
        card_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FragmentFeedback();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragment);

                fragmentTransaction.commit();

            }
        });




        info_bf.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Snackbar.make(getView(), "Breakfast has half diet", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();            }
        });
        info_diets.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Snackbar.make(getView(), "Lunch + Dinner + Half of breakfast", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
        info_prev.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Snackbar.make(getView(), "All the previous months expenses", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        SharedPreferences sharedPreferences;
        sharedPreferences = getActivity().getSharedPreferences(Constants.MY_PREFERENCE, Context.MODE_PRIVATE);
        String email = sharedPreferences.getString(Constants.email,"");
        String refinedEmail =  email.replaceAll("\\W+","");


        FirebaseDatabase BoardReference = FirebaseDatabase.getInstance();

        //Set text of all amount textviews here
        DatabaseReference mExpenseReference = BoardReference.getReference("expense_sheet");
        try {
            mExpenseReference.child("bills").child(refinedEmail).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                        ExpenseBoard bill = dataSnapshot.getValue(ExpenseBoard.class);
                        if(bill != null) {

                            diet_break.setText(("" + bill.diet_break));
                            diet_lunch.setText(("" + bill.diet_lunch));
                            diet_dinner.setText(("" + bill.diet_dinner));
                            total_diets.setText("" + bill.total_diets);
                            eta_expense.setText("" + bill.eta_cost+" ₹");
                            extras.setText("" + bill.extra);
                            service.setText(""+bill.service_charge +" ₹");
                            prev.setText(""+ bill.previous_cost +" ₹");
                            float total_cost=bill.previous_cost+bill.eta_cost;
                            total_expense.setText(""+total_cost +" ₹");
                        }
                    }
                    catch (Exception e)
                    {
                        //Toast.makeText(getActivity(), "Data Unavailable", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(getActivity(), "Data Unavailable", Toast.LENGTH_SHORT).show();
        }

        //Notice Message
        DatabaseReference mBoardReference = BoardReference.getReference("board_sheet");
        mBoardReference.child("notice_board").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                NoticeBoard message = dataSnapshot.getValue(NoticeBoard.class);
                notice_text.setText(message.message);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    return rootview;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Dashboard");
    }
    private Date parseDate(String date) {

        try {
            return inputParser.parse(date);
        } catch (java.text.ParseException e) {
            return new Date(0);
        }
    }
    private Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

}

