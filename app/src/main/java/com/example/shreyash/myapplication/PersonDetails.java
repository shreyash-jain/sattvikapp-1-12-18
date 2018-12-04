package com.example.shreyash.myapplication;

/**
 * Created by Shreyash on 26-03-2018.
 */

public class PersonDetails {
    public String name;
    public String branch;
    public String year;
    public String room;
    public String hostel;
    public String mobile;
    public String du;
    public String email;
    public String registrationDate;
    public  String password;
    public PersonDetails() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public PersonDetails(String registrationDate, String name, String branch, String year, String room, String hostel, String mobile, String email, String du , String password ){
        this.registrationDate = registrationDate;
        this.name = name;
        this.branch = branch;
        this.year = year;
        this.room = room;
        this.hostel = hostel;
        this.mobile = mobile;
        this.email = email;
        this.password= password;
        this.du = du;
    }
}


