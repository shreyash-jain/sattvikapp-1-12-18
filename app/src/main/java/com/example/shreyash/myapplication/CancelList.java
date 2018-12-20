package com.example.shreyash.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class CancelList extends AppCompatActivity {

    List<CancelListItem> cancelList;

    //the recyclerview
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_list);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewcCancel);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cancelList=new ArrayList<>();
        cancelList.add(
                new CancelListItem(1,"Lunch and Dinner","On 24/12/18")
        );
        cancelList.add(
                new CancelListItem(0,"Breakfast and Dinner","On 22/12/18")
        );
        CancellationAdapter adapter = new CancellationAdapter(this, cancelList);
        recyclerView.setAdapter(adapter);
    }
}
