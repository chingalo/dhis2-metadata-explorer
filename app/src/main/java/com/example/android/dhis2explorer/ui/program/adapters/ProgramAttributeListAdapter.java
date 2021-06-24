package com.example.android.dhis2explorer.ui.program.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;

import com.example.android.dhis2explorer.R;
import com.example.android.dhis2explorer.data.Sdk;
import com.example.android.dhis2explorer.ui.base.DiffByIdItemCallback;
import com.example.android.dhis2explorer.ui.base.ListItemCardHolder;
import com.example.android.dhis2explorer.ui.program.listeners.OnProgramAttributeSelectionListener;

import org.hisp.dhis.android.core.program.ProgramTrackedEntityAttribute;
import org.hisp.dhis.android.core.trackedentity.TrackedEntityAttribute;

public class ProgramAttributeListAdapter extends PagedListAdapter<ProgramTrackedEntityAttribute, ListItemCardHolder> {

    final OnProgramAttributeSelectionListener programAttributeSelectionListener;

    public ProgramAttributeListAdapter(OnProgramAttributeSelectionListener programAttributeSelectionListener) {
        super(new DiffByIdItemCallback<>());
        this.programAttributeSelectionListener = programAttributeSelectionListener;
    }

    @NonNull
    @Override
    public ListItemCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_list_item_card, parent, false);
        return new ListItemCardHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemCardHolder holder, int position) {
        ProgramTrackedEntityAttribute programTrackedEntityAttribute = getItem(position);
        String attributeId = programTrackedEntityAttribute.trackedEntityAttribute().uid();
        TrackedEntityAttribute trackedEntityAttribute = getTrackedEntityAttribute(attributeId);
        holder.title.setText(trackedEntityAttribute.displayName());
        holder.subtitle.setText(trackedEntityAttribute.valueType().name());
        holder.cardView.setOnClickListener(view -> programAttributeSelectionListener.onProgramAttributeSelected(programTrackedEntityAttribute.uid()));

    }

    private TrackedEntityAttribute getTrackedEntityAttribute(String attributeId) {
        return Sdk.d2().trackedEntityModule()
                .trackedEntityAttributes()
                .byUid().eq(attributeId)
                .one()
                .blockingGet();
    }
}
