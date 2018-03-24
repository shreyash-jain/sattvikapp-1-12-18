package com.example.shreyash.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
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

public class FragmentBoard extends Fragment  {
    private ViewPager mViewPager;

    private CardPagerAdapter mCardAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View rootview= inflater.inflate(R.layout.fragment_board, container, false);
        setHasOptionsMenu(true);
        mViewPager = (ViewPager) rootview.findViewById(R.id.viewPager);
        TextView notice_text=(TextView)rootview.findViewById(R.id.text_notice);
        //TODO: Get data from firebase for notice board
        String notice = "1.Now Enjoy Extra Items on demand in your daily meals." + "\n\n"+
                "2.You can ask for Namkeen, Milk, Butter, Sweets and many more items." + "\n\n"+
                "3.With the motto to serve pure vegetarian food in the institute, we start with the registration procedure for another session.";
        notice_text.setText(notice);
        //TODO:get data from firebase for each day meal and use it below
        mCardAdapter = new CardPagerAdapter();
        mCardAdapter.addCardItem(new CardItem("Today","Breakfast:\nLunch:\nDinner"));
        mCardAdapter.addCardItem(new CardItem("Tomorrow","Breakfast:\nLunch:\nDinner"));
        mCardAdapter.addCardItem(new CardItem("Next Day","Breakfast:\nLunch:\nDinner"));
        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setOffscreenPageLimit(3);

    return rootview;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Dashboard");
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
            //TODO: Refresh data
            Toast.makeText(getActivity(),"Refreshed!",Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}

