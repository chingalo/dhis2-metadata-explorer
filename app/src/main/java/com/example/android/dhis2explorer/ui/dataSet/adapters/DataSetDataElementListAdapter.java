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
import com.example.android.dhis2explorer.ui.dataSet.listeners.OnDataElementSelectionListener;

import org.hisp.dhis.android.core.dataelement.DataElement;
import org.hisp.dhis.android.core.indicator.Indicator;

public class DataSetDataElementListAdapter extends PagedListAdapter<DataElement, ListItemCardHolder> {

    final OnDataElementSelectionListener dataElementSelectionListener;

    public DataSetDataElementListAdapter(OnDataElementSelectionListener dataElementSelectionListener) {
        super(new DiffByIdItemCallback<>());
        this.dataElementSelectionListener = dataElementSelectionListener;
    }

    @NonNull
    @Override
    public ListItemCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_list_item_card,parent,false);
        return new ListItemCardHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemCardHolder holder, int position) {
        DataElement dataElement = getItem(position);
        holder.title.setText(dataElement.displayName());
        holder.subtitle.setText(dataElement.valueType().name());
        StyleBinderHelper.bindStyle(holder,dataElement.style());
        holder.cardView.setOnClickListener(view-> dataElementSelectionListener.onDataElementSelection(dataElement.uid()));
    }
}
