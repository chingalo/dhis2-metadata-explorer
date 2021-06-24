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
import com.example.android.dhis2explorer.ui.program.adapters.ProgramStageListAdapter;
import com.example.android.dhis2explorer.ui.program.listeners.OnProgramStageSelectionListener;

import org.hisp.dhis.android.core.program.Program;
import org.hisp.dhis.android.core.program.ProgramStage;

import static android.text.TextUtils.isEmpty;

public class ProgramProgramStageListActivity extends ListActivity implements OnProgramStageSelectionListener {

    private String selectedProgramId;

    public static Intent getActivityIntent(Context context, String programId) {
        Bundle bundle = new Bundle();
        if (!isEmpty(programId))
            bundle.putString(ProgramProgramStageListActivity.IntentExtra.PROGRAM.name(), programId);
        Intent intent = new Intent(context, ProgramProgramStageListActivity.class);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    public void onSelectProgramStage(String programStageId) {
        ActivityStarter.startActivity(this, ProgramProgramStageInfoActivity.getActivityIntent(this, programStageId), false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUp(R.layout.activity_program_program_stage_list, R.id.programProgramStageListToolBar, R.id.programProgramStageListRecyclerView);
        selectedProgramId = getIntent().getStringExtra(ProgramProgramStageListActivity.IntentExtra.PROGRAM.name());
        setView();
    }

    void setView() {
        Program selectedProgram = getSelectedProgram();
        int stageCount = getProgramStageListCount();

        TextView programProgramStageCount = findViewById(R.id.programProgramStageCount);
        TextView programProgramStageName = findViewById(R.id.programProgramStageName);

        programProgramStageName.setText(selectedProgram.displayName());
        programProgramStageCount.setText("" + stageCount);

        setProgramStageListAdapter();
    }

    private void setProgramStageListAdapter() {
        ProgramStageListAdapter adapter = new ProgramStageListAdapter(this);
        recyclerView.setAdapter(adapter);

        LiveData<PagedList<ProgramStage>> liveData = Sdk.d2().programModule().programStages()
                .byProgramUid()
                .eq(selectedProgramId)
                .getPaged(10);
        liveData.observe(this, programStages -> adapter.submitList(programStages));
    }

    private int getProgramStageListCount() {
        return Sdk.d2().programModule()
                .programStages()
                .byProgramUid()
                .eq(selectedProgramId)
                .blockingCount();
    }

    Program getSelectedProgram() {
        return Sdk.d2().programModule()
                .programs()
                .byUid().eq(selectedProgramId)
                .blockingGet().get(0);
    }

    private enum IntentExtra {
        PROGRAM
    }


}