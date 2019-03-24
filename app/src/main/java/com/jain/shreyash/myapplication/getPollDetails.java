package com.jain.shreyash.myapplication;
import java.io.Serializable;
/**
 * Created by Shreyash on 29-03-2018.
 */

public class getPollDetails implements Serializable {
    public String poll_item1,poll_item2,poll_item3,poll_name;
    public  Boolean poll_bool;
    public int poll_count1,poll_count2,poll_count3;
    public getPollDetails() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public getPollDetails(Boolean poll_bool,String poll_name,String poll_item1,String poll_item2,String poll_item3,int poll_count1,int poll_count2,int poll_count3) {
        this.poll_bool=poll_bool;
        this.poll_count1=poll_count1;
        this.poll_count2=poll_count2;
        this.poll_count3=poll_count3;
        this.poll_name=poll_name;
        this.poll_item1=poll_item1;
        this.poll_item2=poll_item2;
        this.poll_item3=poll_item3;


    }

}