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
import com.example.android.dhis2explorer.ui.program.listeners.OnProgramSelectionListener;

import org.hisp.dhis.android.core.program.Program;
import org.hisp.dhis.android.core.program.ProgramType;

public class ProgramHomeAdapter extends PagedListAdapter<Program, ListItemCardHolder> {

    private final OnProgramSelectionListener programSelectionListener;

    public ProgramHomeAdapter(OnProgramSelectionListener programSelectionListener) {
        super(new DiffByIdItemCallback<>());
        this.programSelectionListener = programSelectionListener;
    }

    @NonNull
    @Override
    public ListItemCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_list_item_card,parent,false);
        return new ListItemCardHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemCardHolder holder, int position) {
        Program program = getItem(position);
        String programStatus = program.programType() == ProgramType.WITH_REGISTRATION ? "Program with registration" : "Program without registration";
        holder.title.setText(program.displayName());
        holder.subtitle.setText(programStatus);
        StyleBinderHelper.bindStyle(holder,program.style());
        holder.cardView.setOnClickListener(view->programSelectionListener.onProgramSelected(program.uid(), program.programType()));
    }
}
