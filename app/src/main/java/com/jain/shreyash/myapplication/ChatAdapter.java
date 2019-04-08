package com.jain.shreyash.myapplication;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class ChatAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int MESSAGE = 0, IMAGE = 1;

    private  List<Object> chat_list;

    private Context ct;






    public ChatAdapter(List<Object> chat_list,Context ct) {
        this.chat_list = chat_list;
        this.ct=ct;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case MESSAGE:
                View v1 = inflater.inflate(R.layout.item_message_received, parent, false);
                viewHolder = new MyViewHolder(v1);
                break;
            case IMAGE:
                View v2 = inflater.inflate(R.layout.image_view_chat, parent, false);
                viewHolder = new ViewHolder2(v2);
                break;
            default:
                View v3 = inflater.inflate(R.layout.item_message_received, parent, false);
                viewHolder = new MyViewHolder(v3);
                break;
        }
        return viewHolder;


    }

    private void configureViewHolder1(MyViewHolder holder, int position) {
        ChatMessage chat = (ChatMessage) chat_list.get(position);
        holder.user_name.setText(chat.getname());
        holder.user_message.setText(chat.getmessage());
        holder.user_time.setText(chat.gettime());
        holder.user_special_text.setText(chat.getspecial_text());


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        params.weight = 1.0f;
        if(chat.getspecial_text().equals("pinned")){
            params.gravity = Gravity.CENTER;
            holder.outer_ll.setGravity(Gravity.CENTER);
            holder.user_message.setBackgroundResource(R.drawable.rounded_rectangle_blue);
        }

        else {
            if (chat.getMsg_type()) {
                params.gravity = Gravity.LEFT;
                holder.outer_ll.setGravity(Gravity.LEFT);
                holder.user_message.setBackgroundResource(R.drawable.rounded_rectangle_green);

            } else {
                params.gravity = Gravity.END;
                holder.outer_ll.setGravity(Gravity.RIGHT);
                holder.user_message.setBackgroundResource(R.drawable.rounded_rectangle_orange);
            }
        }
        if(chat.getspecial_text().equals("Sattvik Team")||chat.getspecial_text().equals("pinned")){
            holder.user_message.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
            Typeface bold = Typeface.create("san-serif", Typeface.BOLD);
            holder.user_message.setTypeface(bold);
        }
        holder.outer_ll.setLayoutParams(params);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case MESSAGE:
                MyViewHolder vh1 = (MyViewHolder) holder;
                configureViewHolder1(vh1, position);
                break;
            case IMAGE:
                ViewHolder2 vh2 = (ViewHolder2) holder;
                configureViewHolder2(vh2, position);
                break;
            default:
                MyViewHolder vh3 = (MyViewHolder) holder;
                configureViewHolder1(vh3, position);
                break;
        }





    }

    private void configureViewHolder2(ViewHolder2 holder, int position) {


        ImageMessage chat = (ImageMessage) chat_list.get(position);
        holder.user_name.setText(chat.getname());
        Picasso.with(ct).load(chat.iv).into(holder.ivExample);
        holder.user_time.setText(chat.gettime());
        holder.user_special_text.setText(chat.getspecial_text());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        params.weight = 1.0f;
        if(chat.getspecial_text().equals("pinned")){
            params.gravity = Gravity.CENTER;
            holder.outer_ll.setGravity(Gravity.CENTER);

        }

        else {
            if (chat.getMsg_type()) {
                params.gravity = Gravity.LEFT;
                holder.outer_ll.setGravity(Gravity.LEFT);


            } else {
                params.gravity = Gravity.END;
                holder.outer_ll.setGravity(Gravity.RIGHT);

            }
        }

        holder.outer_ll.setLayoutParams(params);
    }


    @Override
    public int getItemCount() {
        return chat_list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (chat_list.get(position) instanceof ChatMessage) {
            return MESSAGE;
            } else if (chat_list.get(position) instanceof ImageMessage) {
            return IMAGE;
        }
        return -1;
    }

}
