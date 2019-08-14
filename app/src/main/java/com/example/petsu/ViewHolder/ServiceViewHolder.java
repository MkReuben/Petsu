package com.example.petsu.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.petsu.R;

public class ServiceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView  txtServiceName,txtServiceDescription,txtServicePrice,txtServiceLocation;
    public ImageView imageView;
    public ItemClickListener listener;

    public ServiceViewHolder(@NonNull View itemView)
    {
        super(itemView);

        imageView=(ImageView) itemView.findViewById(R.id.service_image);
        txtServiceDescription=(TextView) itemView.findViewById(R.id.service_description);
        txtServiceName=(TextView) itemView.findViewById(R.id.service_name);
        txtServicePrice=(TextView) itemView.findViewById(R.id.service_price);
        txtServiceLocation=(TextView) itemView.findViewById(R.id.service_location);
    }

    @Override
    public void onClick(View view)
    {
        ItemClickListener.onClick(view,getAdapterPosition(),false);

    }
    public  void setItemClickListener(ItemClickListener listener)
    {
        this.listener=listener;
    }


}
