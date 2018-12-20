package com.example.shreyash.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class CancellationAdapter extends RecyclerView.Adapter<CancellationAdapter.CancellationViewHolder> {


    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<CancelListItem> cancelList;


    public CancellationAdapter(Context mCtx,  List<CancelListItem> cancelList) {
        this.mCtx = mCtx;
        this.cancelList = cancelList;
    }
    @Override
    public CancellationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.cancel_card, parent, false);
        return new CancellationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CancellationViewHolder holder, int position) {
        //getting the product of the specified position
        CancelListItem cancelitem = cancelList.get(position);

        //binding the data with the viewholder views
        holder.textViewDate.setText(String.valueOf(cancelitem.getCan_date()));
        holder.textViewDiets.setText(String.valueOf(cancelitem.getDiets()));
        holder.textStatus.setText(String.valueOf(cancelitem.getStatus()));


    }

    @Override
    public int getItemCount() {
        return cancelList.size();
    }

    public class CancellationViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDate,textViewDiets,textStatus;
        public CancellationViewHolder(View itemView) {
            super(itemView);

            textStatus=itemView.findViewById(R.id.list_status);
            textViewDate=itemView.findViewById(R.id.list_date);
            textViewDiets=itemView.findViewById(R.id.list_diets);
        }
    }
}
