package com.jain.shreyash.myapplication;

/**
 * Created by Shreyash on 26-03-2018.
 */

public class PersonDetails {
    public String name;
 //   public String branch;
 //   public String year;
    public String room;
    public String hostel;
    public String mobile;
    public String du;
    public String email;
    public String registrationDate;
    public String pin;
    public String isactive;
    public String mess;
    public String roll_no;
    public PersonDetails() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public PersonDetails(String mess, String roll_no,String registrationDate, String name, /*String branch, String year,*/ String room, String hostel, String mobile, String email, String du , String password, String isactive ){
        this.registrationDate = registrationDate;
        this.name = name;
     //   this.branch = branch;
     //   this.year = year;
        this.room = room;
        this.hostel = hostel;
        this.mobile = mobile;
        this.email = email;
        this.pin= password;
        this.du = du;
        this.isactive= isactive;
        this.mess=mess;
        this.roll_no=roll_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHostel() {
        return hostel;
    }

    public void setHostel(String hostel) {
        this.hostel = hostel;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDu() {
        return du;
    }

    public void setDu(String du) {
        this.du = du;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public String getRoll_no() {
        return roll_no;
    }

    public void setRoll_no(String roll_no) {
        this.roll_no = roll_no;
    }
}


