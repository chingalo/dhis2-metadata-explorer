package com.example.android.dhis2explorer.ui.program.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;

import com.example.android.dhis2explorer.R;
import com.example.android.dhis2explorer.ui.base.DiffByIdItemCallback;
import com.example.android.dhis2explorer.ui.base.ListItemCardHolder;
import com.example.android.dhis2explorer.ui.program.listeners.OnProgramIndicatorSelectionListener;

import org.hisp.dhis.android.core.program.ProgramIndicator;

public class ProgramIndicatorListAdapter extends PagedListAdapter<ProgramIndicator, ListItemCardHolder>  {
    final private OnProgramIndicatorSelectionListener programIndicatorSelectionListener;

    public ProgramIndicatorListAdapter(OnProgramIndicatorSelectionListener programIndicatorSelectionListener) {
        super(new DiffByIdItemCallback<>());
        this.programIndicatorSelectionListener = programIndicatorSelectionListener;
    }

    @NonNull
    @Override
    public ListItemCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_list_item_card, parent, false);
        return new ListItemCardHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemCardHolder holder, int position) {
        ProgramIndicator programIndicator = getItem(position);
        holder.title.setText(programIndicator.displayName());
        holder.subtitle.setText(programIndicator.displayDescription());
        holder.cardView.setOnClickListener(view-> programIndicatorSelectionListener.onProgramIndicatorSelection(programIndicator.uid()));
    }
}
