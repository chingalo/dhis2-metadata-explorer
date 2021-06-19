package com.example.android.dhis2explorer.ui.dataSet.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;

import com.example.android.dhis2explorer.R;
import com.example.android.dhis2explorer.data.service.StyleBinderHelper;
import com.example.android.dhis2explorer.ui.base.DiffByIdItemCallback;
import com.example.android.dhis2explorer.ui.base.ListItemCardHolder;
import com.example.android.dhis2explorer.ui.dataSet.listeners.OnDataSetSelectionListener;

import org.hisp.dhis.android.core.dataset.DataSet;

public class DataSetHomeAdapter extends PagedListAdapter<DataSet, ListItemCardHolder> {
    private final OnDataSetSelectionListener dataSetSelectionListener;
    public DataSetHomeAdapter(OnDataSetSelectionListener dataSetSelectionListener) {
        super(new DiffByIdItemCallback<>());
        this.dataSetSelectionListener = dataSetSelectionListener;
    }

    @NonNull
    @Override
    public ListItemCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_list_item_card,parent,false);
        return new ListItemCardHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemCardHolder holder, int position) {
        DataSet  dataSet = getItem(position);
        holder.title.setText(dataSet.displayName());
        holder.subtitle.setText(dataSet.periodType().name());
        StyleBinderHelper.bindStyle(holder,dataSet.style());
        holder.cardView.setOnClickListener(view-> dataSetSelectionListener.onDataSetSelected(dataSet.uid()));
    }
}
