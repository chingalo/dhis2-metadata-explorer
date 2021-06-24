package com.example.android.dhis2explorer.ui.base;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.dhis2explorer.R;

public class ListItemCardHolder extends RecyclerView.ViewHolder {

    public final TextView title;
    public final TextView subtitle;
    public CardView cardView;
    public ImageView icon;

    public ListItemCardHolder(@NonNull View itemView) {
        super(itemView);
        cardView = itemView.findViewById(R.id.listItemCard);
        icon = itemView.findViewById(R.id.itemIcon);
        title = itemView.findViewById(R.id.itemTitle);
        subtitle = itemView.findViewById(R.id.itemSubTitle);
    }
}


