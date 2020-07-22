package com.example.myapplication;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class cartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView prodName, prodQty, prodPrice;
    private ItemClickListener itemClickListener;

    public cartViewHolder(@NonNull View itemView) {
        super(itemView);

        prodName = itemView.findViewById(R.id.cartName);
        prodQty = itemView.findViewById(R.id.cartQty);
        prodPrice = itemView.findViewById(R.id.abc);

    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(),false );

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {

        this.itemClickListener = itemClickListener;
    }
}
