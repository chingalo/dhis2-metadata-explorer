package com.example.android.dhis2explorer.ui.optionSet.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;

import com.example.android.dhis2explorer.R;
import com.example.android.dhis2explorer.ui.base.DiffByIdItemCallback;
import com.example.android.dhis2explorer.ui.base.ListItemCardHolder;
import com.example.android.dhis2explorer.ui.optionSet.listeners.OnOptionSetSelectionListener;

import org.hisp.dhis.android.core.option.OptionSet;

public class OptionSetListAdapter extends PagedListAdapter<OptionSet, ListItemCardHolder> {
    final OnOptionSetSelectionListener optionSetSelectionListener;
    public OptionSetListAdapter(OnOptionSetSelectionListener optionSetSelectionListener) {
        super(new DiffByIdItemCallback<>());
        this.optionSetSelectionListener = optionSetSelectionListener;
    }

    @NonNull
    @Override
    public ListItemCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_list_item_card, parent, false);
        return new ListItemCardHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemCardHolder holder, int position) {
        OptionSet optionSet = getItem(position);
        holder.title.setText(optionSet.displayName());
        holder.subtitle.setText(optionSet.valueType().name());
        holder.cardView.setOnClickListener(view-> optionSetSelectionListener.onOptionSetSelection(optionSet.uid()));
    }
}
