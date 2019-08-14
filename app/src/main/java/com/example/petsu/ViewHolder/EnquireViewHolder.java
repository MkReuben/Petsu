package com.example.petsu.ViewHolder;


import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.petsu.R;

public class EnquireViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtServiceName, txtServicePrice;
    private AdapterView.OnItemClickListener onItemClickListener;

    public EnquireViewHolder(@NonNull View itemView)
    {
        super(itemView);

        txtServiceName=itemView.findViewById(R.id.enquiry_service_name);
        txtServicePrice=itemView.findViewById(R.id.enquiry_price);

    }

    @Override
    public void onClick(View view)
    {
        ItemClickListener.onClick(view,getAdapterPosition(),false);

    }

    public void setItemClickListener(AdapterView.OnItemClickListener itemClickListener)
    {

        this.onItemClickListener = itemClickListener;
    }
}
