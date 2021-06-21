package com.example.android.dhis2explorer.ui.program.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;

import com.example.android.dhis2explorer.R;
import com.example.android.dhis2explorer.data.service.StyleBinderHelper;
import com.example.android.dhis2explorer.ui.base.DiffByIdItemCallback;
import com.example.android.dhis2explorer.ui.base.ListItemCardHolder;
import com.example.android.dhis2explorer.ui.program.listeners.OnProgramStageSelectionListener;

import org.hisp.dhis.android.core.program.ProgramStage;

public class ProgramStageListAdapter extends PagedListAdapter<ProgramStage, ListItemCardHolder> {
     private final OnProgramStageSelectionListener programStageSelectionListener;

    public ProgramStageListAdapter(OnProgramStageSelectionListener programStageSelectionListener) {
        super(new DiffByIdItemCallback<>());
        this.programStageSelectionListener = programStageSelectionListener;
    }

    @NonNull
    @Override
    public ListItemCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_list_item_card,parent,false);
        return new ListItemCardHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemCardHolder holder, int position) {
        ProgramStage programStage = getItem(position);
        holder.title.setText(programStage.displayName());
        holder.subtitle.setText(programStage.repeatable()? "Repeatable" : "Non-repeatable");
        StyleBinderHelper.bindStyle(holder,programStage.style());
        holder.cardView.setOnClickListener(view->programStageSelectionListener.onSelectProgramStage(programStage.uid()));
    }
}
