package com.jain.shreyash.myapplication;

import android.text.Editable;

public class Feedback_Details {
    public String category;
    public String rating;
    public String description;
    public String name;
    public String email;
    public String feedback_date;
    public Feedback_Details() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public Feedback_Details(String feedback_date, String name, String category, String rating, String description, String email ){
        this.feedback_date =feedback_date;
        this.name = name;
        this.category = category;
        this.rating = rating;
        this.description = description;
        this.email = email;
    }
}

