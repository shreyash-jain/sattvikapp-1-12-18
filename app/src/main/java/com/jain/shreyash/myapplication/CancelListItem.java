package com.jain.shreyash.myapplication;

public class CancelListItem {
    String status;
    String requestdate;
    String can_date;
    boolean breakfast;
    boolean lunch;
    boolean dinner;
    boolean enable;
    int scolor;


    public CancelListItem(String status,String requestdate, String can_date, Boolean breakfast, Boolean lunch, Boolean dinner,int scolor,Boolean enable){
        this.status=status;
        this.requestdate=requestdate;
        this.can_date=can_date;
        this.breakfast=breakfast;
        this.lunch=lunch;
        this.dinner=dinner;
        this.enable=enable;
        this.scolor=scolor;
    }

    public String getStatus() {
        return status;
    }

    public String getCan_date() {
        return can_date;
    }

    public String getRequestdate() {
        return requestdate;
    }

    public boolean getbf(){ return  breakfast;}

    public boolean getln(){ return  lunch; }

    public  boolean getdn() { return  dinner; }
    public int getScolor(){
        return scolor;
    }

    public  boolean unsetenable(){
        return  enable;
    }
}

