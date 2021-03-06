package com.example.android.dhis2explorer.ui.program.pages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.android.dhis2explorer.R;
import com.example.android.dhis2explorer.data.Sdk;
import com.example.android.dhis2explorer.data.service.ActivityStarter;
import com.example.android.dhis2explorer.ui.base.DefaultActivity;
import com.google.android.material.snackbar.Snackbar;

import org.hisp.dhis.android.core.common.FeatureType;
import org.hisp.dhis.android.core.program.Program;

import static android.text.TextUtils.isEmpty;

public class ProgramWithRegistrationActivity extends DefaultActivity {

    private int programAttributeListCount;
    private int ProgramStageListCount;
    private int programIndicatorListCount;

    private Program selectedProgram;
    private String selectedProgramId;

    public static Intent getActivityIntent(Context context, String programId) {
        Bundle bundle = new Bundle();
        if (!isEmpty(programId))
            bundle.putString(IntentExtra.PROGRAM.name(), programId);
        Intent intent = new Intent(context, ProgramWithRegistrationActivity.class);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUp(R.layout.activity_program_with_registration, R.id.programWithRegistrationToolbar);
        selectedProgramId = getIntent().getStringExtra(IntentExtra.PROGRAM.name());
        setProgramInfoView();
        setCardViewListener();
    }

    private void setCardViewListener() {
        CardView programIndicatorCard = findViewById(R.id.programIndicatorCard);
        CardView ProgramStageCard = findViewById(R.id.programStageCard);
        CardView programAttributeCard = findViewById(R.id.programAttributeCard);
        String programName = selectedProgram.name();

        programAttributeCard.setOnClickListener(view -> {
            if (programAttributeListCount == 0) {
                Snackbar.make(view, "There is no support to view program attributes for " + programName, Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            } else {
                ActivityStarter.startActivity(this, ProgramAttributeListActivity.getActivityIntent(this, selectedProgramId), false);
            }
        });
        ProgramStageCard.setOnClickListener(view -> {
            if (ProgramStageListCount == 0) {
                Snackbar.make(view, "There is no program stages for " + programName, Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            } else {
                ActivityStarter.startActivity(this, ProgramProgramStageListActivity.getActivityIntent(this, selectedProgramId), false);
            }
        });
        programIndicatorCard.setOnClickListener(view -> {
            if (programIndicatorListCount == 0) {
                Snackbar.make(view, "There is no program indicators for " + programName, Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            } else {
                ActivityStarter.startActivity(this, ProgramProgramIndicatorListActivity.getActivityIntent(this, selectedProgramId), false);
            }
        });
    }

    private void setProgramInfoView() {
        selectedProgram = getSelectedProgram();
        programAttributeListCount = getProgramAttributeListCount();
        ProgramStageListCount = getProgramStageListCount();
        programIndicatorListCount = getProgramIndicatorListCount();
        TextView uid = findViewById(R.id.programId);
        TextView name = findViewById(R.id.programName);
        TextView description = findViewById(R.id.programDescription);
        TextView incidentDate = findViewById(R.id.programIncidentDate);
        TextView enrollmentDate = findViewById(R.id.programEnrollmentDate);
        TextView canCaptureCoordinate = findViewById(R.id.programCanCaptureCoordinate);
        TextView trackedEntityType = findViewById(R.id.programTrackedEntityType);
        TextView programAttributeCount = findViewById(R.id.programAttributeCount);
        TextView ProgramStageCount = findViewById(R.id.programStageCount);
        TextView programIndicatorCount = findViewById(R.id.programIndicatorCount);

        uid.setText(selectedProgramId);
        name.setText(selectedProgram.displayName());
        description.setText(selectedProgram.displayDescription());
        incidentDate.setText(selectedProgram.incidentDateLabel());
        enrollmentDate.setText(selectedProgram.enrollmentDateLabel());
        canCaptureCoordinate.setText(selectedProgram.featureType() == FeatureType.NONE ? "No" : "Yes");
        trackedEntityType.setText(selectedProgram.trackedEntityType().displayName());
        programAttributeCount.setText("" + programAttributeListCount);
        programIndicatorCount.setText("" + programIndicatorListCount);
        ProgramStageCount.setText("" + ProgramStageListCount);
    }

    private int getProgramAttributeListCount() {
        return Sdk.d2().programModule()
                .programTrackedEntityAttributes()
                .byProgram().eq(selectedProgramId)
                .blockingCount();
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
                .one()
                .blockingGet();
    }

    private enum IntentExtra {
        PROGRAM
    }
}