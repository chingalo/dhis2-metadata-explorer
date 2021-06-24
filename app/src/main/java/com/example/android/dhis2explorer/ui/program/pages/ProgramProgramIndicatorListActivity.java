package com.example.android.dhis2explorer.ui.program.pages;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.example.android.dhis2explorer.R;
import com.example.android.dhis2explorer.data.Sdk;
import com.example.android.dhis2explorer.data.service.ActivityStarter;
import com.example.android.dhis2explorer.ui.base.ListActivity;
import com.example.android.dhis2explorer.ui.program.adapters.ProgramIndicatorListAdapter;
import com.example.android.dhis2explorer.ui.program.listeners.OnProgramIndicatorSelectionListener;

import org.hisp.dhis.android.core.arch.repositories.scope.RepositoryScope;
import org.hisp.dhis.android.core.program.Program;
import org.hisp.dhis.android.core.program.ProgramIndicator;

import static android.text.TextUtils.isEmpty;

public class ProgramProgramIndicatorListActivity extends ListActivity implements OnProgramIndicatorSelectionListener {

    private String selectedProgramId;

    public static Intent getActivityIntent(Context context, String programId) {
        Bundle bundle = new Bundle();
        if (!isEmpty(programId))
            bundle.putString(ProgramProgramIndicatorListActivity.IntentExtra.PROGRAM.name(), programId);
        Intent intent = new Intent(context, ProgramProgramIndicatorListActivity.class);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    public void onProgramIndicatorSelection(String programIndicatorId) {
        ActivityStarter.startActivity(this, ProgramProgramIndicatorInfoActivity.getActivityIntent(this, programIndicatorId), false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUp(R.layout.activity_program_program_indicator_list, R.id.programProgramIndicatorListToolbar, R.id.programProgramIndicatorListView);
        selectedProgramId = getIntent().getStringExtra(IntentExtra.PROGRAM.name());
        setView();
    }

    private void setView() {
        Program selectedProgram = getSelectedProgram();
        int indicatorCount = getProgramIndicatorListCount();

        TextView programProgramIndicatorProgramName = findViewById(R.id.programProgramIndicatorProgramName);
        TextView programProgramIndicatorCount = findViewById(R.id.programProgramIndicatorCount);

        programProgramIndicatorProgramName.setText(selectedProgram.displayName());
        programProgramIndicatorCount.setText("" + indicatorCount);

        setProgramIndicatorListAdapter();

    }

    private void setProgramIndicatorListAdapter() {
        ProgramIndicatorListAdapter adapter = new ProgramIndicatorListAdapter(this);
        recyclerView.setAdapter(adapter);

        LiveData<PagedList<ProgramIndicator>> liveData = Sdk.d2().programModule()
                .programIndicators()
                .orderByDisplayName(RepositoryScope.OrderByDirection.ASC)
                .byProgramUid().eq(selectedProgramId)
                .getPaged(10);

        liveData.observe(this, programIndicators -> adapter.submitList(programIndicators));
    }

    private int getProgramIndicatorListCount() {
        return Sdk.d2().programModule()
                .programIndicators()
                .byProgramUid().eq(selectedProgramId)
                .blockingCount();
    }

    private Program getSelectedProgram() {
        return Sdk.d2().programModule()
                .programs()
                .withTrackedEntityType()
                .byUid().eq(selectedProgramId)
                .blockingGet()
                .get(0);
    }

    private enum IntentExtra {
        PROGRAM
    }
}