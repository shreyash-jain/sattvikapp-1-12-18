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

public class FragmentDAT extends Fragment {
    View view;
    public FragmentDAT() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_dat,container,false);
        final TextView datbft = (TextView) view.findViewById(R.id.dat_bft);
        final TextView datlnc = (TextView) view.findViewById(R.id.dat_lnc);
        final TextView datdnr = (TextView) view.findViewById(R.id.dat_dnr);
        FirebaseDatabase BoardReference = FirebaseDatabase.getInstance();
        DatabaseReference mBoardReference = BoardReference.getReference("board_sheet");
        mBoardReference.child("menu_board").child("next").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                MenuBoard message = dataSnapshot.getValue(MenuBoard.class);
                //String msg = "Breakfast: "+message.getBreakfast()+"\nLunch: "+message.getLunch()+"\nDinner: "+message.getDinner();
                //menu_today.setText(msg);
                datbft.setText(message.getBreakfast());
                datlnc.setText(message.getLunch());
                datdnr.setText(message.getDinner());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }
}
