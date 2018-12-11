package com.example.shreyash.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentToday extends Fragment {
    View view;
    public FragmentToday() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_today,container,false);
        final TextView todaybft = (TextView) view.findViewById(R.id.today_bf);
        final TextView todaylnc = (TextView) view.findViewById(R.id.today_lnc);
        final TextView todaydnr = (TextView) view.findViewById(R.id.today_dn);
        FirebaseDatabase BoardReference = FirebaseDatabase.getInstance();
        DatabaseReference mBoardReference = BoardReference.getReference("board_sheet");
        mBoardReference.child("menu_board").child("today").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                MenuBoard message = dataSnapshot.getValue(MenuBoard.class);
                //String msg = "Breakfast: "+message.getBreakfast()+"\nLunch: "+message.getLunch()+"\nDinner: "+message.getDinner();
                //menu_today.setText(msg);
                todaybft.setText(message.getBreakfast());
                todaylnc.setText(message.getLunch());
                todaydnr.setText(message.getDinner());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }
}
