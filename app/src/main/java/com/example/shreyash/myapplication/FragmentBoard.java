package com.example.shreyash.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class FragmentBoard extends Fragment  {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View rootview= inflater.inflate(R.layout.fragment_board, container, false);
        final TextView notice_text=(TextView)rootview.findViewById(R.id.text_notice);
        TextView amount_prev = (TextView) rootview.findViewById(R.id.text_bill_amount_prev);
        TextView amount_b = (TextView) rootview.findViewById(R.id.text_bill_amount_b);
        TextView amount_l = (TextView) rootview.findViewById(R.id.text_bill_amount_l);
        TextView amount_d = (TextView) rootview.findViewById(R.id.text_bill_amount_d);
        TextView amount_ex = (TextView) rootview.findViewById(R.id.text_bill_amount_ex);
        TextView amount_es = (TextView) rootview.findViewById(R.id.text_bill_amount_estimated);
        final TextView menu_today = (TextView) rootview.findViewById(R.id.text_menu_today);
        final TextView menu_tom = (TextView) rootview.findViewById(R.id.text_menu_tom);
        final TextView menu_next = (TextView) rootview.findViewById(R.id.text_menu_next);

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


        mBoardReference.child("menu_board").child("today").addValueEventListener(new ValueEventListener() {
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

