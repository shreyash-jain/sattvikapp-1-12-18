package com.jain.shreyash.myapplication;

import java.util.Date;

public class CheckCancelDate {
    public Date for_date;
    public int acceptance;
    public boolean ck_breakfast;
    public boolean ck_lunch;
    public boolean ck_dinner;

    public  CheckCancelDate(Date for_date,int acceptance,boolean ck_breakfast,boolean ck_lunch,boolean ck_dinner ){
        this.for_date=for_date;
        this.acceptance=acceptance;
        this.ck_breakfast=ck_breakfast;
        this.ck_dinner=ck_dinner;
        this.ck_lunch=ck_lunch;
    }
    public Date getFor_date(){
        return for_date;
    }
    public int getAcceptance(){
        return  acceptance;
    }
    public boolean getCk_breakfast(){
        return  ck_breakfast;
    }
    public boolean getCk_Dinner(){
        return ck_dinner;
    } public boolean getCk_Lunch(){
        return  ck_lunch;
    }
}
