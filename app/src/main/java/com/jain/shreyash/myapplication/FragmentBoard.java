package com.jain.shreyash.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormatSymbols;

import com.jain.shreyash.myapplication.R;
import com.jain.shreyash.utils.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.Calendar;

public class FragmentBoard extends Fragment  {
    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;

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
        final  TextView total_expense=rootview.findViewById(R.id.total_expense);
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
                Toast.makeText(getActivity(), "Breakfast has half diet", Toast.LENGTH_SHORT).show();
            }
        });
        info_diets.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Lunch + Dinner + Half of breakfast", Toast.LENGTH_SHORT).show();

            }
        });
        info_prev.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Toast.makeText(getActivity(), "All the previous months expenses", Toast.LENGTH_SHORT).show();
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

}

