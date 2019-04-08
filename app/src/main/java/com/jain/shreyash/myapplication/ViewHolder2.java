package com.jain.shreyash.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewHolder2 extends RecyclerView.ViewHolder {

    public ImageView ivExample;
    public TextView user_name, user_time, user_special_text;
    public LinearLayout outer_ll;

    public ViewHolder2(View v) {
        super(v);
        ivExample = (ImageView) v.findViewById(R.id.ivExample);
        user_name = (TextView) v.findViewById(R.id.text_message_name);

        user_time = (TextView) v.findViewById(R.id.text_message_time);
        user_special_text=v.findViewById(R.id.text_message_stext);
        outer_ll=v.findViewById(R.id.chat_ll);
    }


}