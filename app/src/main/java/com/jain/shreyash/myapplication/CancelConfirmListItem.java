package com.jain.shreyash.myapplication;

public class CancelConfirmListItem {


    String can_date;
    boolean breakfast;
    boolean lunch;
    boolean dinner;
    boolean enable;
    int scolor;


    public CancelConfirmListItem(  String can_date, Boolean breakfast, Boolean lunch, Boolean dinner, int scolor, Boolean enable){

        this.can_date=can_date;
        this.breakfast=breakfast;
        this.lunch=lunch;
        this.dinner=dinner;
        this.enable=enable;
        this.scolor=scolor;
    }



    public String getCan_date() {
        return can_date;
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

