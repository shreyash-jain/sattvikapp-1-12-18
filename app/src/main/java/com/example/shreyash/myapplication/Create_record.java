package com.example.shreyash.myapplication;

/**
 * Created by Shreyash on 26-03-2018.
 */

public class Create_record {
    public String name;
    public String branch;
    public String year;
    public String room;
    public String hostel;
    public String mobile;
    public String du;
    public String email;
    public String registration_date;
    public  String password;
    public Create_record() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public Create_record(String registration_date, String name, String branch, String year,String room,String hostel,String mobile,String email,String du ,String password ){
        this.registration_date =registration_date;
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


