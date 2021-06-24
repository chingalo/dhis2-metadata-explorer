package com.example.android.dhis2explorer.ui.program.pages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android.dhis2explorer.R;
import com.example.android.dhis2explorer.data.Sdk;
import com.example.android.dhis2explorer.ui.base.DefaultActivity;

import org.hisp.dhis.android.core.program.ProgramIndicator;

import static android.text.TextUtils.isEmpty;

public class ProgramProgramIndicatorInfoActivity extends DefaultActivity {

    private ProgramIndicator selectedProgramIndicator;
    private String selectedProgramIndicatorId;

    private enum IntentExtra {
        PROGRAM_INDICATOR
    }

    public static Intent getActivityIntent(Context context, String programIndicatorId) {
        Bundle bundle = new Bundle();
        if (!isEmpty(programIndicatorId))
            bundle.putString(ProgramProgramIndicatorInfoActivity.IntentExtra.PROGRAM_INDICATOR.name(), programIndicatorId);
        Intent intent = new Intent(context, ProgramProgramIndicatorInfoActivity.class);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUp(R.layout.activity_program_program_indicator_info, R.id.programProgramIndicatorInfoToolbar);
        selectedProgramIndicatorId = getIntent().getStringExtra(IntentExtra.PROGRAM_INDICATOR.name());
        setView();
    }

    private void setView() {
        selectedProgramIndicator = getSelectedProgramIndicator();

        TextView ProgramProgramIndicatorInfoUid = findViewById(R.id.ProgramProgramIndicatorInfoUid);
        TextView ProgramProgramIndicatorInfoName = findViewById(R.id.ProgramProgramIndicatorInfoName);
        TextView ProgramProgramIndicatorInfoDescription = findViewById(R.id.ProgramProgramIndicatorInfoDescription);
        TextView ProgramProgramIndicatorInfoExpression = findViewById(R.id.ProgramProgramIndicatorInfoExpression);
        TextView ProgramProgramIndicatorInfoFilter = findViewById(R.id.ProgramProgramIndicatorInfoFilter);
        TextView ProgramProgramIndicatorInfoDisplayInForm = findViewById(R.id.ProgramProgramIndicatorInfoDisplayInForm);

        ProgramProgramIndicatorInfoUid.setText(selectedProgramIndicator.uid());
        ProgramProgramIndicatorInfoName.setText(selectedProgramIndicator.displayName());
        ProgramProgramIndicatorInfoDescription.setText(selectedProgramIndicator.displayDescription());
        ProgramProgramIndicatorInfoExpression.setText(selectedProgramIndicator.expression());
        ProgramProgramIndicatorInfoFilter.setText(selectedProgramIndicator.filter());
        ProgramProgramIndicatorInfoDisplayInForm.setText(selectedProgramIndicator.displayInForm()?"Yes":"No");
    }

    ProgramIndicator getSelectedProgramIndicator() {
        return Sdk.d2().programModule()
                .programIndicators()
                .byUid().eq(selectedProgramIndicatorId)
                .blockingGet().get(0);
    }
}