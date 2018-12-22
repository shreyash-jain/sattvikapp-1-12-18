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
               new CancelListItem("pending","22/12/18","24/12/18",true,false,true,false)
        );
        cancelList.add(
                new CancelListItem("accepted","22/12/18","25/12/18",false,false,true,false)
        );
        CancellationAdapter adapter = new CancellationAdapter(this, cancelList);
        recyclerView.setAdapter(adapter);
    }
}
