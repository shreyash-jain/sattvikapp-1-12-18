package com.jain.shreyash.myapplication;

public class ChatMessage {
    private String message, time, special_text;
    Boolean msg_type;//true for received and false for sent
    String name;
    public ChatMessage(){}

    public  ChatMessage(String name,String message, String time, String special_text, Boolean msg_type)
    {
        this.message=message;
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
    public String getmessage(){
        return message;
    }
    public String getspecial_text(){
        return special_text;
    }
    public Boolean getMsg_type(){
        return msg_type;
    }
}

