package com.jain.shreyash.myapplication;

/**
 * Created by Shreyash on 29-03-2018.
 */

public class CheckpollActive {
    public Boolean current;
    public CheckpollActive() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public CheckpollActive(Boolean current) {
        this.current = current;
    }

}