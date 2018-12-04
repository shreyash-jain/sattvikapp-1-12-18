package com.example.shreyash.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

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
        final TextView notice_text=(TextView)rootview.findViewById(R.id.notice_board_text);
        TextView amount_prev = (TextView) rootview.findViewById(R.id.notice_board_text);
        /*TextView amount_b = (TextView) rootview.findViewById(R.id.notice_board_text);
        TextView amount_l = (TextView) rootview.findViewById(R.id.notice_board_text);
        TextView amount_d = (TextView) rootview.findViewById(R.id.notice_board_text);
        TextView amount_ex = (TextView) rootview.findViewById(R.id.notice_board_text);
        TextView amount_es = (TextView) rootview.findViewById(R.id.notice_board_text);
        final TextView menu_today = (TextView) rootview.findViewById(R.id.notice_board_text);
        final TextView menu_tom = (TextView) rootview.findViewById(R.id.notice_board_text);
        final TextView menu_next = (TextView) rootview.findViewById(R.id.notice_board_text);*/
        tabLayout=rootview.findViewById(R.id.tab_id);
        appBarLayout=rootview.findViewById(R.id.appboard);
        viewPager=rootview.findViewById(R.id.view_pager);
        ViewPagerAdapter adapter= new ViewPagerAdapter(getFragmentManager());
        adapter.AddFragment(new FragmentToday(),"Today");
        adapter.AddFragment(new FragmentTomorrow(),"Tomorrow");
        adapter.AddFragment(new FragmentDAT(),"Next");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        Calendar calendar = Calendar.getInstance();
        int currentweekday = calendar.get(Calendar.DAY_OF_WEEK);
//
        viewPager.setCurrentItem(currentweekday-1);
        //displaySelectedScreen(item.getItemId());
        //TODO: Set text of all amount textviews here
        amount_prev.setText("450");

        FirebaseDatabase BoardReference = FirebaseDatabase.getInstance();
        DatabaseReference mBoardReference = BoardReference.getReference("board_sheet");
        mBoardReference.child("notice_board").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("background run","hohoho");
                Notice_Board message = dataSnapshot.getValue(Notice_Board.class);
                notice_text.setText(message.message);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


     /*   mBoardReference.child("menu_board").child("today").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Menu_Board message = dataSnapshot.getValue(Menu_Board.class);
                String msg = "Breakfast: "+message.getBreakfast()+"\nLunch: "+message.getLunch()+"\nDinner: "+message.getDinner();
                menu_today.setText(msg);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mBoardReference.child("menu_board").child("tomorrow").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Menu_Board message = dataSnapshot.getValue(Menu_Board.class);
                String msg = "Breakfast: "+message.getBreakfast()+"\nLunch: "+message.getLunch()+"\nDinner: "+message.getDinner();
                menu_tom.setText(msg);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mBoardReference.child("menu_board").child("next").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Menu_Board message = dataSnapshot.getValue(Menu_Board.class);
                String msg = "Breakfast: "+message.getBreakfast()+"\nLunch: "+message.getLunch()+"\nDinner: "+message.getDinner();
                menu_next.setText(msg);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

    return rootview;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Dashboard");
    }
}

