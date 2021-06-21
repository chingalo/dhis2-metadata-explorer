package com.example.android.dhis2explorer.ui.common.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;

import com.example.android.dhis2explorer.R;
import com.example.android.dhis2explorer.data.service.StyleBinderHelper;
import com.example.android.dhis2explorer.ui.base.DiffByIdItemCallback;
import com.example.android.dhis2explorer.ui.base.ListItemCardHolder;
import org.hisp.dhis.android.core.option.Option;

public class OptionListAdapter extends PagedListAdapter<Option, ListItemCardHolder> {
    public OptionListAdapter(){
        super(new DiffByIdItemCallback<>());
    }

    @NonNull
    @Override
    public ListItemCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_list_item_card,parent,false);
        return new ListItemCardHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemCardHolder holder, int position) {
        Option option = getItem(position);
        holder.title.setText(option.displayName());
        holder.subtitle.setText(option.code());
        StyleBinderHelper.bindStyle(holder,option.style());
    }
}
