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
import com.example.android.dhis2explorer.ui.program.adapters.ProgramStageDataElementListAdapter;
import com.example.android.dhis2explorer.ui.program.listeners.OnProgramStageDataElementListener;

import org.hisp.dhis.android.core.program.ProgramStage;
import org.hisp.dhis.android.core.program.ProgramStageDataElement;

import static android.text.TextUtils.isEmpty;

public class ProgramProgramStageInfoActivity extends ListActivity implements OnProgramStageDataElementListener {

    private String selectedProgramStageId;

    public static Intent getActivityIntent(Context context, String programStageId) {
        Bundle bundle = new Bundle();
        if (!isEmpty(programStageId))
            bundle.putString(ProgramProgramStageInfoActivity.IntentExtra.PROGRAM_STAGE.name(), programStageId);
        Intent intent = new Intent(context, ProgramProgramStageInfoActivity.class);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    public void onProgramStageDataElementSelected(String programStageDataElementId) {
        try {
            ActivityStarter.startActivity(this, ProgramProgramStageDataElementActivity.getActivityIntent(this, programStageDataElementId), false);
        } catch (Exception d) {
            System.out.println(d.toString());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUp(R.layout.activity_program_program_stage_info, R.id.programProgramStageInfoToolbar, R.id.programProgramStageInfoDataElementListRecyclerView);
        selectedProgramStageId = getIntent().getStringExtra(ProgramProgramStageInfoActivity.IntentExtra.PROGRAM_STAGE.name());
        setView();
    }

    private void setView() {
        ProgramStage selectedProgramStage = getSelectedProgramStage();
        int dataElementCount = Sdk.d2().programModule().programStageDataElements().byProgramStage().eq(selectedProgramStageId).blockingCount();

        TextView programProgramStageInfoName = findViewById(R.id.programProgramStageInfoName);
        TextView programProgramStageInfoCount = findViewById(R.id.programProgramStageInfoCount);
        TextView programProgramStageInfoUid = findViewById(R.id.programProgramStageInfoUid);
        TextView programProgramStageInfoDescription = findViewById(R.id.programProgramStageInfoDescription);
        TextView programProgramStageInfoEventLabel = findViewById(R.id.programProgramStageInfoEventLabel);
        TextView programProgramStageInfoRepeatable = findViewById(R.id.programProgramStageInfoRepeatable);
        TextView programProgramStageInfoAutoGenerateEvent = findViewById(R.id.programProgramStageInfoAutoGenerateEvent);

        programProgramStageInfoName.setText(selectedProgramStage.displayName());
        programProgramStageInfoCount.setText("" + dataElementCount);
        programProgramStageInfoUid.setText(selectedProgramStage.uid());
        programProgramStageInfoDescription.setText(selectedProgramStage.displayDescription());
        programProgramStageInfoEventLabel.setText(selectedProgramStage.executionDateLabel());
        programProgramStageInfoRepeatable.setText(selectedProgramStage.repeatable() ? "Yes" : "No");
        programProgramStageInfoAutoGenerateEvent.setText(selectedProgramStage.autoGenerateEvent() ? "Yes" : "No");

        setDataElementListAdapter();
    }

    private void setDataElementListAdapter() {
        ProgramStageDataElementListAdapter adapter = new ProgramStageDataElementListAdapter(this);
        recyclerView.setAdapter(adapter);

        LiveData<PagedList<ProgramStageDataElement>> liveData = Sdk.d2().programModule()
                .programStageDataElements()
                .byProgramStage().eq(selectedProgramStageId)
                .getPaged(10);

        liveData.observe(this, programStageDataElements -> adapter.submitList(programStageDataElements));
    }

    ProgramStage getSelectedProgramStage() {
        return Sdk.d2().programModule()
                .programStages()
                .byUid().eq(selectedProgramStageId)
                .blockingGet().get(0);
    }

    private enum IntentExtra {
        PROGRAM_STAGE
    }
}