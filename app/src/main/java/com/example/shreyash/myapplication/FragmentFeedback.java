package com.example.shreyash.myapplication;

/**
 * Created by Shreyash on 17-02-2018.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.RatingBar.OnRatingBarChangeListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import android.widget.AdapterView;

import java.util.Calendar;
import java.util.List;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;


import com.example.shreyash.utils.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentFeedback extends Fragment implements OnItemSelectedListener {

    private EditText disctext;
    private RatingBar ratingBar;
    private  String field;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("feedback_sheet");
    SharedPreferences sharedpreferences;// = getSharedPreferences(Constants.myPreference, Context.MODE_PRIVATE);

    FirebaseDatabase PostReference = FirebaseDatabase.getInstance();
    DatabaseReference mPostReference = PostReference.getReference("feedback_sheet");


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        setHasOptionsMenu(true);



        List categories = new ArrayList<>();
        categories.add("Over All");
        categories.add("Bills Issue");
        categories.add("Registration Issue");
        categories.add("Menu");
        categories.add("Taste");
        categories.add("Service");
        categories.add("Quality");
        categories.add("Workers");
        categories.add("Student's Team");
        categories.add("Mobile App");
        ArrayAdapter dataAdapter = new ArrayAdapter (getActivity(), android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        disctext=view.findViewById(R.id.discription);
        ratingBar = (RatingBar) view.findViewById(R.id.rating_bar);

        if (getArguments()!=null ) {
            int myInt = getArguments().getInt("pass", 1);
            if (myInt==0){
                spinner.setSelection(7);
                ratingBar.setRating(Float.parseFloat("1.0"));
            }
        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sendinbar, menu);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Feedback and Suggestions");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //TODO:Send feedback here



        //Submit feedback
        sharedpreferences = getActivity().getSharedPreferences(Constants.MY_PREFERENCE, Context.MODE_PRIVATE);
        final String name =sharedpreferences.getString(Constants.name,"");
        final String email = sharedpreferences.getString(Constants.email,"");
        final String rating= String.valueOf(ratingBar.getRating());
        final String destxt = disctext.getText().toString();
        //TODO: Check for internet connectivity
        //If no error continue else prompt user to start internet

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.MyAlertDialogStyle);
        progressDialog.setIndeterminate(true);
        progressDialog.setTitle("Submitting...");
        progressDialog.setMessage("loading");
        progressDialog.show();

        final Calendar calendar = Calendar.getInstance();
        DatabaseReference offsetRef = FirebaseDatabase.getInstance().getReference(".info/serverTimeOffset");
        offsetRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                double offset = snapshot.getValue(Double.class);
                double estimatedServerTimeMs = System.currentTimeMillis() + offset;
                calendar.setTimeInMillis(((long) estimatedServerTimeMs));
                Log.d("inter",""+calendar.getTime());
            }
            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Listener was cancelled");
            }
        });


        final String key = myRef.child("feedbacks").push().getKey();
        mPostReference.child("feedbacks").
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Feedback_Details feedbackDetails;
                        //Creating record to Firebase
                        SimpleDateFormat format = new SimpleDateFormat("yyyy/M/d h:mm:ss a");
                        //Toast.makeText(Registration.this, ""+format.format(calendar.getTime()), Toast.LENGTH_SHORT).show();

                        feedbackDetails = new Feedback_Details(
                                format.format(calendar.getTime()),
                                name,
                                field,
                                rating,
                                destxt,
                                email
                        );
                        myRef.child("feedbacks").child(key).setValue(feedbackDetails);
                        //Log.d("flag in registering",flag)
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(),"Thanks for your Feedback ",Toast.LENGTH_SHORT).show();
                        Fragment myFragment=new FragmentBoard();
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame,  myFragment, "MY_FRAGMENT");
                        ft.commit();
                        //Toast.makeText(Registration.this, "Welcome " + name, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        progressDialog.dismiss();
                        Log.w("feedback submit or not", "loadPost:onCancelled", databaseError.toException());
                    }
                });


        return  true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        field = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), "Selected: " + field, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}

