package com.example.android.dhis2explorer.ui.options.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;

import com.example.android.dhis2explorer.R;
import com.example.android.dhis2explorer.data.service.StyleBinderHelper;
import com.example.android.dhis2explorer.ui.base.DiffByIdItemCallback;
import com.example.android.dhis2explorer.ui.base.ListItemCardHolder;
import com.example.android.dhis2explorer.ui.options.listeners.OnOptionSelectionListener;

import org.hisp.dhis.android.core.option.Option;

public class OptionListAdapter extends PagedListAdapter<Option, ListItemCardHolder> {

    final OnOptionSelectionListener optionSelectionListener;

    public OptionListAdapter(OnOptionSelectionListener optionSelectionListener) {
        super(new DiffByIdItemCallback<>());
        this.optionSelectionListener = optionSelectionListener;
    }

    @NonNull
    @Override
    public ListItemCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_list_item_card, parent, false);
        return new ListItemCardHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemCardHolder holder, int position) {
        Option option = getItem(position);
        holder.title.setText(option.displayName());
        holder.subtitle.setText(option.code());
        StyleBinderHelper.bindStyle(holder, option.style());
        holder.cardView.setOnClickListener(view -> optionSelectionListener.onOptionSelection(option.uid()));
    }
}
