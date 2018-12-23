package com.example.shreyash.myapplication;

import java.io.Serializable;

public class CancelDetails implements Serializable {
    public String Acceptance;
    public String b;
    public String d;
    public String date;
    public String diet;
    public String email;
    public String l;
    public String name;
    public String request_date;

    public CancelDetails() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public CancelDetails(String Acceptance , String b, String d, String date, String diet, String email, String l, String name, String request_date){
        this.Acceptance = Acceptance;
        this.b = b;
        this.d = d;
        this.date = date;
        this.diet = diet;
        this.email = email;
        this.l = l;
        this.name = name;
        this.request_date= request_date;
    }

}
