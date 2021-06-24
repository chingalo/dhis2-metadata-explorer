package com.example.android.dhis2explorer.ui.programIndicator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.example.android.dhis2explorer.R;
import com.example.android.dhis2explorer.data.Sdk;
import com.example.android.dhis2explorer.data.service.ActivityStarter;
import com.example.android.dhis2explorer.ui.base.ListActivity;
import com.example.android.dhis2explorer.ui.programIndicator.adapters.ProgramIndicatorListAdapter;
import com.example.android.dhis2explorer.ui.programIndicator.listeners.OnProgramIndicatorSelectionListener;
import com.example.android.dhis2explorer.ui.programIndicator.pages.ProgramProgramIndicatorInfoActivity;

import org.hisp.dhis.android.core.arch.repositories.scope.RepositoryScope;
import org.hisp.dhis.android.core.program.ProgramIndicator;

public class ProgramIndicatorHomeActivity extends ListActivity implements OnProgramIndicatorSelectionListener {

    public static Intent getActivityIntent(Context context) {
        return new Intent(context, ProgramIndicatorHomeActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUp(R.layout.activity_program_indicator_home, R.id.programIndicatorHomeToolbar, R.id.programIndicatorHomeListRecyclerView);
        discoveringProgramIndicators();
    }

    private void discoveringProgramIndicators() {
        ProgramIndicatorListAdapter adapter = new ProgramIndicatorListAdapter(this);
        recyclerView.setAdapter(adapter);

        LiveData<PagedList<ProgramIndicator>> liveData = Sdk.d2().programModule()
                .programIndicators()
                .orderByDisplayName(RepositoryScope.OrderByDirection.ASC)
                .getPaged(10);

        liveData.observe(this, programIndicators -> adapter.submitList(programIndicators));
    }

    @Override
    public void onProgramIndicatorSelection(String programIndicatorId) {
        ActivityStarter.startActivity(this, ProgramProgramIndicatorInfoActivity.getActivityIntent(this, programIndicatorId), false);
    }
}