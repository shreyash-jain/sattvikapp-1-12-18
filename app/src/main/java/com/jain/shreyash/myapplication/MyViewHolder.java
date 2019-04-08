package com.jain.shreyash.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyViewHolder extends RecyclerView.ViewHolder  {
    public TextView user_name, user_message, user_time, user_special_text;
    public LinearLayout outer_ll;

    public MyViewHolder(View view) {
        super(view);
        user_name = (TextView) view.findViewById(R.id.text_message_name);
        user_message = (TextView) view.findViewById(R.id.text_message_body);
        user_time = (TextView) view.findViewById(R.id.text_message_time);
        user_special_text=view.findViewById(R.id.text_message_stext);
        outer_ll=view.findViewById(R.id.chat_ll);
    }


}