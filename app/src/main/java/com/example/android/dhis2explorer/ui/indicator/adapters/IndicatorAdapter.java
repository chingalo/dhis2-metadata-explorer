package com.example.android.dhis2explorer.ui.indicator.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;

import com.example.android.dhis2explorer.R;
import com.example.android.dhis2explorer.ui.base.DiffByIdItemCallback;
import com.example.android.dhis2explorer.ui.base.ListItemCardHolder;
import com.example.android.dhis2explorer.ui.indicator.listeners.OnIndicatorSelectionListener;

import org.hisp.dhis.android.core.indicator.Indicator;

public class IndicatorAdapter extends PagedListAdapter<Indicator, ListItemCardHolder> {

    private final OnIndicatorSelectionListener indicatorSelectionListener;

    public IndicatorAdapter(OnIndicatorSelectionListener indicatorSelectionListener) {
        super(new DiffByIdItemCallback<>());
        this.indicatorSelectionListener = indicatorSelectionListener;
    }

    @NonNull
    @Override
    public ListItemCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_list_item_card, parent, false);
        return new ListItemCardHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemCardHolder holder, int position) {
        Indicator indicator = getItem(position);
        holder.title.setText(indicator.displayName());
        holder.subtitle.setText(indicator.description());
        holder.cardView.setOnClickListener(view -> indicatorSelectionListener.onIndicatorSelection(indicator.uid()));
    }
}
