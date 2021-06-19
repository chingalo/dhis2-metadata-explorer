package com.example.android.dhis2explorer.data.service;


import android.graphics.Color;

import androidx.core.content.ContextCompat;

import com.example.android.dhis2explorer.R;
import com.example.android.dhis2explorer.ui.base.ListItemCardHolder;

import org.hisp.dhis.android.core.common.ObjectStyle;

public class StyleBinderHelper {
    public static void bindStyle(ListItemCardHolder holder, ObjectStyle style) {
        if (style != null) {
            if (style.icon() != null) {
                String iconName = style.icon().startsWith("ic_") ? style.icon() : "ic_" + style.icon();
                int icon = holder.itemView.getContext().getResources().getIdentifier(
                        iconName, "drawable", holder.itemView.getContext().getPackageName());
                holder.icon.setImageResource(icon);
                int darkColor = ContextCompat.getColor(holder.itemView.getContext(), R.color.colorAccentDark);
                holder.icon.setBackgroundColor(darkColor);
                int colorWhite = ContextCompat.getColor(holder.itemView.getContext(), R.color.colorWhite);
                holder.icon.setColorFilter(colorWhite);
            }else{
                holder.icon.setImageResource(0);
            }
            if(style.color() != null){
                String color = style.color().startsWith("#") ? style.color() : "#" + style.color();
                int programColor = Color.parseColor(color);
                holder.title.setTextColor(programColor);
                holder.subtitle.setTextColor(programColor);
            }
        }else{
            holder.icon.setImageResource(0);
        }
    }

}
