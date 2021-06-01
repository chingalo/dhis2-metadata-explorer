package com.example.android.androidskeletonapp.ui.base;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.androidskeletonapp.R;

public class ListItemCardHolder extends RecyclerView.ViewHolder {

    public CardView cardView;
    public final TextView title;
    public final TextView subtitle;

    public ListItemCardHolder(@NonNull View itemView) {
        super(itemView);
        cardView = itemView.findViewById(R.id.listItemCard);
        title = itemView.findViewById(R.id.itemTitle);
        subtitle = itemView.findViewById(R.id.itemSubTitle);
    }
}


