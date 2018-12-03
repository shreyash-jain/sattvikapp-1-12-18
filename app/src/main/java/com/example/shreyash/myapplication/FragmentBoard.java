package com.example.shreyash.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class FragmentBoard extends Fragment  {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View rootview= inflater.inflate(R.layout.fragment_board, container, false);
        final TextView noticeText=rootview.findViewById(R.id.textcheck);
        TextView amountPrev = rootview.findViewById(R.id.textcheck);
        TextView amountB = rootview.findViewById(R.id.textcheck);
        TextView amountL = rootview.findViewById(R.id.textcheck);
        TextView amountD = rootview.findViewById(R.id.textcheck);
        TextView amountEx = rootview.findViewById(R.id.textcheck);
        TextView amountEs = rootview.findViewById(R.id.textcheck);
        final TextView menuToday =  rootview.findViewById(R.id.textcheck);
        final TextView menuTom =  rootview.findViewById(R.id.textcheck);
        final TextView menuNext =  rootview.findViewById(R.id.textcheck);
        tabLayout=rootview.findViewById(R.id.tab_id);
        viewPager=rootview.findViewById(R.id.view_pager);
        ViewPagerAdapter adapter= new ViewPagerAdapter(getFragmentManager());
        adapter.AddFragment(new FragmentBreakfast(),"Today");
        adapter.AddFragment(new FragmentLunch(),"Tomorrow");
        adapter.AddFragment(new FragmentDinner(),"Next");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        Calendar calendar = Calendar.getInstance();
        int currentWeekDay = calendar.get(Calendar.DAY_OF_WEEK);
//
        viewPager.setCurrentItem(currentWeekDay-1);
        //displaySelectedScreen(item.getItemId());
        //TODO: Set text of all amount textviews here
        amountPrev.setText("450");

        FirebaseDatabase BoardReference = FirebaseDatabase.getInstance();
        DatabaseReference mBoardReference = BoardReference.getReference("board_sheet");
        mBoardReference.child("notice_board").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("background run","hohoho");
                NoticeBoard message = dataSnapshot.getValue(NoticeBoard.class);
                noticeText.setText(message.message);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mBoardReference.child("menu_board").child("today").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                MenuBoard message = dataSnapshot.getValue(MenuBoard.class);
                String msg = "Breakfast: "+message.getBreakfast()+"\nLunch: "+message.getLunch()+"\nDinner: "+message.getDinner();
                menuToday.setText(msg);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mBoardReference.child("menu_board").child("tomorrow").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                MenuBoard message = dataSnapshot.getValue(MenuBoard.class);
                String msg = "Breakfast: "+message.getBreakfast()+"\nLunch: "+message.getLunch()+"\nDinner: "+message.getDinner();
                menuTom.setText(msg);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mBoardReference.child("menu_board").child("next").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                MenuBoard message = dataSnapshot.getValue(MenuBoard.class);
                String msg = "Breakfast: "+message.getBreakfast()+"\nLunch: "+message.getLunch()+"\nDinner: "+message.getDinner();
                menuNext.setText(msg);
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

