package com.example.android.dhis2explorer.ui.program.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;

import com.example.android.dhis2explorer.R;
import com.example.android.dhis2explorer.data.Sdk;
import com.example.android.dhis2explorer.data.service.StyleBinderHelper;
import com.example.android.dhis2explorer.ui.base.DiffByIdItemCallback;
import com.example.android.dhis2explorer.ui.base.ListItemCardHolder;
import com.example.android.dhis2explorer.ui.program.listeners.OnProgramStageDataElementListener;

import org.hisp.dhis.android.core.dataelement.DataElement;
import org.hisp.dhis.android.core.program.ProgramStageDataElement;

public class ProgramStageDataElementListAdapter extends PagedListAdapter<ProgramStageDataElement, ListItemCardHolder> {
    final OnProgramStageDataElementListener programStageDataElementListener;

    public ProgramStageDataElementListAdapter(OnProgramStageDataElementListener programStageDataElementListener) {
        super(new DiffByIdItemCallback<>());
        this.programStageDataElementListener = programStageDataElementListener;
    }

    @NonNull
    @Override
    public ListItemCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_list_item_card, parent, false);
        return new ListItemCardHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemCardHolder holder, int position) {
        ProgramStageDataElement programStageDataElement = getItem(position);
        DataElement dataElement = Sdk.d2().dataElementModule()
                .dataElements()
                .byUid().eq(programStageDataElement.dataElement().uid())
                .blockingGet().get(0);
        holder.title.setText(dataElement.displayName());
        holder.subtitle.setText(dataElement.valueType().name());
        StyleBinderHelper.bindStyle(holder, programStageDataElement.dataElement().style());
        holder.cardView.setOnClickListener(view -> programStageDataElementListener.onProgramStageDataElementSelected(programStageDataElement.uid()));
    }
}
