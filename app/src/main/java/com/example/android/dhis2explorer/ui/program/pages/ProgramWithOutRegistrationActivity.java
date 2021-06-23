package com.example.android.dhis2explorer.ui.program.pages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.android.dhis2explorer.R;
import com.example.android.dhis2explorer.data.Sdk;
import com.example.android.dhis2explorer.data.service.ActivityStarter;
import com.example.android.dhis2explorer.ui.base.DefaultActivity;
import com.google.android.material.snackbar.Snackbar;

import org.hisp.dhis.android.core.common.FeatureType;
import org.hisp.dhis.android.core.program.Program;
import org.hisp.dhis.android.core.program.ProgramIndicator;

import java.util.List;

import static android.text.TextUtils.isEmpty;

public class ProgramWithOutRegistrationActivity extends DefaultActivity {

    private int ProgramStageListCount;
    private int programIndicatorListCount;

    private Program selectedProgram;
    private String selectedProgramId;

    private enum IntentExtra {
        PROGRAM
    }

    public static Intent getActivityIntent(Context context, String programId) {
        Bundle bundle = new Bundle();
        if (!isEmpty(programId))
            bundle.putString(ProgramWithOutRegistrationActivity.IntentExtra.PROGRAM.name(), programId);
        Intent intent = new Intent(context, ProgramWithOutRegistrationActivity.class);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUp(R.layout.activity_program_with_out_registration, R.id.programWithOutRegistrationToolbar);
        selectedProgramId = getIntent().getStringExtra(IntentExtra.PROGRAM.name());
        setProgramInfoView();
        setCardViewListener();
    }

    private void setCardViewListener() {
        CardView programIndicatorCard = findViewById(R.id.programIndicatorCard);
        CardView ProgramStageCard = findViewById(R.id.programStageCard);
        String programName = selectedProgram.name();

        ProgramStageCard.setOnClickListener(view -> {
            if (ProgramStageListCount == 0) {
                Snackbar.make(view, "There is no program stages for " + programName, Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            } else if (ProgramStageListCount == 1) {
                String programStageId = Sdk.d2().programModule()
                        .programStages()
                        .byProgramUid()
                        .eq(selectedProgramId).blockingGetUids().get(0);
                ActivityStarter.startActivity(this, ProgramProgramStageInfoActivity.getActivityIntent(this, programStageId), false);
            } else {
                ActivityStarter.startActivity(this, ProgramProgramStageListActivity.getActivityIntent(this, selectedProgramId), false);
            }
        });
        programIndicatorCard.setOnClickListener(view -> {
            if (programIndicatorListCount == 0) {
                Snackbar.make(view, "There is no program indicators for " + programName, Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            } else {
                List<ProgramIndicator> programIndicatorList = Sdk.d2().programModule().programIndicators().byProgramUid().eq(selectedProgramId).blockingGet();
                System.out.println(programIndicatorList);
            }
        });
    }

    private void setProgramInfoView() {
        selectedProgram = getSelectedProgram();
        ProgramStageListCount = getProgramStageListCount();
        programIndicatorListCount = getProgramIndicatorListCount();
        TextView uid = findViewById(R.id.programId);
        TextView name = findViewById(R.id.programName);
        TextView description = findViewById(R.id.programDescription);
        TextView incidentDate = findViewById(R.id.programIncidentDate);
        TextView enrollmentDate = findViewById(R.id.programEnrollmentDate);
        TextView canCaptureCoordinate = findViewById(R.id.programCanCaptureCoordinate);

        TextView ProgramStageCount = findViewById(R.id.programStageCount);
        TextView programIndicatorCount = findViewById(R.id.programIndicatorCount);

        TextView trackedEntityType = findViewById(R.id.programTrackedEntityType);
        TextView trackedEntityTypeLabel = findViewById(R.id.programTrackedEntityTypeLabel);
        trackedEntityType.setVisibility(View.GONE);
        trackedEntityTypeLabel.setVisibility(View.GONE);

        uid.setText(selectedProgramId);
        name.setText(selectedProgram.displayName());
        description.setText(selectedProgram.displayDescription());
        incidentDate.setText(selectedProgram.incidentDateLabel());
        enrollmentDate.setText(selectedProgram.enrollmentDateLabel());
        canCaptureCoordinate.setText(selectedProgram.featureType() == FeatureType.NONE ? "No" : "Yes");
        programIndicatorCount.setText("" + programIndicatorListCount);
        ProgramStageCount.setText("" + ProgramStageListCount);
    }

    private int getProgramStageListCount() {
        return Sdk.d2().programModule()
                .programStages()
                .byProgramUid()
                .eq(selectedProgramId)
                .blockingCount();
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
}