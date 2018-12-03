package com.example.shreyash.myapplication;

/**
 * Created by Shreyash on 29-03-2018.
 */

public class MenuBoard {
    private String breakfast;
    private String lunch;
    private String dinner;

    public MenuBoard(){
    }
    public MenuBoard(String breakfast, String lunch, String dinner) {
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
    }

    public String getBreakfast() {
        return breakfast;
    }

    public String getLunch() {
        return lunch;
    }

    public String getDinner() {
        return dinner;
    }
}
