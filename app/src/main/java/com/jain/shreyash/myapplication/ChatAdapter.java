package com.jain.shreyash.myapplication;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;


public class ChatAdapter extends  RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    private  List<ChatMessage> chat_list;
    public class MyViewHolder extends RecyclerView.ViewHolder {
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

    public ChatAdapter(List<ChatMessage> chat_list) {
        this.chat_list = chat_list;
    }

    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message_received, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ChatMessage chat=chat_list.get(position);

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
    public int getItemCount() {
        return chat_list.size();
    }
}
