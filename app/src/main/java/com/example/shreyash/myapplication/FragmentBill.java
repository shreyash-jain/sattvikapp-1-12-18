package com.example.shreyash.myapplication;

/**
 * Created by Shreyash on 17-02-2018.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentBill extends Fragment {

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View rootview= inflater.inflate(R.layout.fragment_bill, container, false);
        setHasOptionsMenu(true);
        TextView amount_prev = (TextView) rootview.findViewById(R.id.text_bill_amount_prev);
        TextView amount_b = (TextView) rootview.findViewById(R.id.text_bill_amount_b);
        TextView amount_l = (TextView) rootview.findViewById(R.id.text_bill_amount_l);
        TextView amount_d = (TextView) rootview.findViewById(R.id.text_bill_amount_d);
        TextView amount_ex = (TextView) rootview.findViewById(R.id.text_bill_amount_ex);
        TextView amount_es = (TextView) rootview.findViewById(R.id.text_bill_amount_estimated);
        //TODO: Set text of all textViews here
        amount_prev.setText("450");

        return rootview;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Diet Bill");
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.dashboard,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_refresh){
            //TODO: Refresh data bill
            Toast.makeText(getActivity(),"Refreshed!",Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}

