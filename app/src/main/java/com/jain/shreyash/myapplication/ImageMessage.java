package com.jain.shreyash.myapplication;

import android.widget.ImageView;

public class ImageMessage {
    private String  time, special_text;
    Boolean msg_type;//true for received and false for sent
    String name;
    String iv;
    public ImageMessage(){}

    public ImageMessage(String name, String iv, String time, String special_text, Boolean msg_type)
    {
       this.iv=iv;
        this.name=name;
        this.special_text=special_text;
        this.time=time;
        this.msg_type=msg_type;

    }

    public String getname(){
        return name;
    }
    public String gettime(){
        return time;
    }
    public String getimage(){
        return iv;
    }
    public String getspecial_text(){
        return special_text;
    }
    public Boolean getMsg_type(){
        return msg_type;
    }
}

