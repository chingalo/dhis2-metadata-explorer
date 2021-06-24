package com.example.android.dhis2explorer.ui.dataElement.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;

import com.example.android.dhis2explorer.R;
import com.example.android.dhis2explorer.data.service.StyleBinderHelper;
import com.example.android.dhis2explorer.ui.base.DiffByIdItemCallback;
import com.example.android.dhis2explorer.ui.base.ListItemCardHolder;
import com.example.android.dhis2explorer.ui.dataElement.listeners.OnDataElementSelectionListener;

import org.hisp.dhis.android.core.dataelement.DataElement;

public class DataElementListAdapter extends PagedListAdapter<DataElement, ListItemCardHolder> {

    final OnDataElementSelectionListener dataElementSelectionListener;
    final boolean shouldDisplayDomainType;

    public DataElementListAdapter(OnDataElementSelectionListener dataElementSelectionListener, boolean shouldDisplayDomainType) {
        super(new DiffByIdItemCallback<>());
        this.dataElementSelectionListener = dataElementSelectionListener;
        this.shouldDisplayDomainType = shouldDisplayDomainType;
    }

    @NonNull
    @Override
    public ListItemCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_list_item_card, parent, false);
        return new ListItemCardHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemCardHolder holder, int position) {
        DataElement dataElement = getItem(position);
        holder.title.setText(dataElement.displayName());
        holder.subtitle.setText(shouldDisplayDomainType ? dataElement.domainType() : dataElement.valueType().name());
        StyleBinderHelper.bindStyle(holder, dataElement.style());
        holder.cardView.setOnClickListener(view -> dataElementSelectionListener.onDataElementSelection(dataElement.uid()));
    }
}
