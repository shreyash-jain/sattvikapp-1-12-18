package com.example.shreyash.myapplication;

/**
 * Created by Shreyash on 29-03-2018.
 */

public class NoticeBoard {
    public String message;
    public NoticeBoard() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public NoticeBoard(String message) {
        this.message = message;
    }

}