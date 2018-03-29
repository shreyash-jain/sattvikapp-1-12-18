package com.example.shreyash.myapplication;

/**
 * Created by Shreyash on 29-03-2018.
 */

public class Notice_Board {
    public String message;
    public Notice_Board() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Notice_Board(String message) {
        this.message = message;
    }

}