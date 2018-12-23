package com.example.shreyash.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
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
        // Reading from internal storage
        List<CancelDetails> cancelDetailsArrayTemp = new ArrayList<>();
        String filename = "CancelData";
        try {
            Toast.makeText(CancelList.this, "reading", Toast.LENGTH_SHORT).show();
            //FileInputStream inStream = new FileInputStream(filename);
            FileInputStream inStream = openFileInput(filename);
            ObjectInputStream objectInStream = new ObjectInputStream(inStream);
            int count = objectInStream.readInt();// Get the number of cancel requests
            for (int c=0; c < count; c++)
                cancelDetailsArrayTemp.add((CancelDetails) objectInStream.readObject());
            objectInStream.close();
        }
        catch (Exception e) {
            Toast.makeText(CancelList.this, "ohhh", Toast.LENGTH_SHORT).show();
            Log.e("reading error",""+e);
            e.printStackTrace();
        }

        cancelList=new ArrayList<>();
        for(int i = 0; i< cancelDetailsArrayTemp.size();i++) {

            Toast.makeText(CancelList.this, "" + cancelDetailsArrayTemp.get(i).request_date, Toast.LENGTH_SHORT).show();
            String status = cancelDetailsArrayTemp.get(i).Acceptance;
            if(status.equals("-1")) status = "pending";
            else if(status.equals("1")) status = "accepted";
            else if(status.equals("0")) status = "rejected";
            boolean b = cancelDetailsArrayTemp.get(i).b.equals("1");
            boolean l = cancelDetailsArrayTemp.get(i).l.equals("1");
            boolean d = cancelDetailsArrayTemp.get(i).d.equals("1");
            cancelList.add(
                    new CancelListItem(status, cancelDetailsArrayTemp.get(i).date, cancelDetailsArrayTemp.get(i).request_date, b, l, d, false)
            );
        }
        CancellationAdapter adapter = new CancellationAdapter(this, cancelList);
        recyclerView.setAdapter(adapter);
    }
}
