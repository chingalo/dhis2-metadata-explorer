package com.example.android.dhis2explorer.ui.program.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;

import com.example.android.dhis2explorer.R;
import com.example.android.dhis2explorer.ui.base.DiffByIdItemCallback;
import com.example.android.dhis2explorer.ui.base.ListItemCardHolder;
import com.example.android.dhis2explorer.ui.program.listeners.OnProgramAttributeSelectionListener;

import org.hisp.dhis.android.core.program.ProgramTrackedEntityAttribute;

public class ProgramAttributeListAdapter extends PagedListAdapter<ProgramTrackedEntityAttribute, ListItemCardHolder> {

    final OnProgramAttributeSelectionListener programAttributeSelectionListener;

    public ProgramAttributeListAdapter( OnProgramAttributeSelectionListener programAttributeSelectionListener) {
        super(new DiffByIdItemCallback<>());
        this.programAttributeSelectionListener = programAttributeSelectionListener;
    }

    @NonNull
    @Override
    public ListItemCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_list_item_card,parent,false);
        return new ListItemCardHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemCardHolder holder, int position) {
        ProgramTrackedEntityAttribute programTrackedEntityAttribute = getItem(position);
        holder.title.setText(programTrackedEntityAttribute.displayName());
        holder.subtitle.setText(programTrackedEntityAttribute.mandatory() ? "Mandatory field" : "Optional field");
        holder.cardView.setOnClickListener(view->programAttributeSelectionListener.onProgramAttributeSelected(programTrackedEntityAttribute.uid()));

    }
}
