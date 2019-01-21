package com.jain.shreyash.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jain.shreyash.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentTomorrow extends Fragment {
    View view;
    public FragmentTomorrow() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_tomorrow,container,false);
        final TextView tombft = (TextView) view.findViewById(R.id.tom_bf);
        final TextView tomlnc = (TextView) view.findViewById(R.id.tom_lnc);
        final TextView tomdnr = (TextView) view.findViewById(R.id.tom_dn);
        FirebaseDatabase BoardReference = FirebaseDatabase.getInstance();
        DatabaseReference mBoardReference = BoardReference.getReference("board_sheet");
        mBoardReference.child("menu_board").child("tomorrow").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                MenuBoard message = dataSnapshot.getValue(MenuBoard.class);
                //String msg = "Breakfast: "+message.getBreakfast()+"\nLunch: "+message.getLunch()+"\nDinner: "+message.getDinner();
                //menu_today.setText(msg);
                tombft.setText(message.getBreakfast());
                tomlnc.setText(message.getLunch());
                tomdnr.setText(message.getDinner());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }
}
