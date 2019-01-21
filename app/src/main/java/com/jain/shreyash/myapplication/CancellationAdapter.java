package com.jain.shreyash.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jain.shreyash.myapplication.R;

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
        holder.textRequestDate.setText(String.valueOf(cancelitem.getRequestdate()));
        holder.textStatus.setText(String.valueOf(cancelitem.getStatus()));
        holder.check_bf.setChecked(cancelitem.getbf());
        holder.check_ln.setChecked(cancelitem.getln());
        holder.check_dn.setChecked(cancelitem.getdn());
        holder.check_bf.setEnabled(cancelitem.unsetenable());
        holder.check_dn.setEnabled(cancelitem.unsetenable());
        holder.check_ln.setEnabled(cancelitem.unsetenable());
        holder.textStatus.setTextColor(cancelitem.getScolor());


    }

    @Override
    public int getItemCount() {
        return cancelList.size();
    }

    public class CancellationViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDate,textStatus,textRequestDate;
        CheckBox check_bf,check_ln,check_dn,unsetenable;
        public CancellationViewHolder(View itemView) {
            super(itemView);

            textStatus=itemView.findViewById(R.id.list_status);
            textViewDate=itemView.findViewById(R.id.list_date);
            textRequestDate=itemView.findViewById(R.id.request_date);
            check_bf=itemView.findViewById(R.id.check_bk);
            check_ln=itemView.findViewById(R.id.check_ln);
            check_dn=itemView.findViewById(R.id.check_dn);


        }
    }
}
