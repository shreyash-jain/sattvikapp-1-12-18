package com.example.shreyash.myapplication;

public class CancelListItem {
    int status;
    String diets;
    String can_date;


    public CancelListItem(int status,String diets, String can_date){
        this.status=status;
        this.diets=diets;
        this.can_date=can_date;
    }

    public int getStatus() {
        return status;
    }

    public String getCan_date() {
        return can_date;
    }

    public String getDiets() {
        return diets;
    }
}

