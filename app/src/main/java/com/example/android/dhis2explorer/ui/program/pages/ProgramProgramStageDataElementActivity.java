package com.example.android.dhis2explorer.ui.program.pages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.example.android.dhis2explorer.R;
import com.example.android.dhis2explorer.data.Sdk;
import com.example.android.dhis2explorer.data.service.ActivityStarter;
import com.example.android.dhis2explorer.ui.base.ListActivity;
import com.example.android.dhis2explorer.ui.options.adapters.OptionListAdapter;
import com.example.android.dhis2explorer.ui.options.listeners.OnOptionSelectionListener;
import com.example.android.dhis2explorer.ui.options.pages.OptionInfoActivity;

import org.hisp.dhis.android.core.dataelement.DataElement;
import org.hisp.dhis.android.core.option.Option;
import org.hisp.dhis.android.core.program.ProgramStageDataElement;

import static android.text.TextUtils.isEmpty;

public class ProgramProgramStageDataElementActivity extends ListActivity implements OnOptionSelectionListener {

    private String selectedProgramStageDataElementId;

    public static Intent getActivityIntent(Context context, String selectedProgramStageDataElementId) {
        Bundle bundle = new Bundle();
        if (!isEmpty(selectedProgramStageDataElementId))
            bundle.putString(ProgramProgramStageDataElementActivity.IntentExtra.PROGRAM_STAGE_DATA_ELEMENT.name(), selectedProgramStageDataElementId);
        Intent intent = new Intent(context, ProgramProgramStageDataElementActivity.class);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    public void onOptionSelection(String optionId) {
        ActivityStarter.startActivity(this, OptionInfoActivity.getActivityIntent(this, optionId), false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUp(R.layout.activity_program_program_stage_data_element, R.id.programProgramStageDataElementToolbar, R.id.programProgramStageDataElementOptionSetListView);
        selectedProgramStageDataElementId = getIntent().getStringExtra(IntentExtra.PROGRAM_STAGE_DATA_ELEMENT.name());
        setUpView();
    }

    private void setUpView() {
        ProgramStageDataElement selectedProgramStageDataElement = getSelectedProgramStageDataElement();
        DataElement dataElement = getDataElement(selectedProgramStageDataElement);
        String optionSetId = dataElement.optionSetUid();

        TextView programProgramStageDataElementName = findViewById(R.id.programProgramStageDataElementName);
        TextView programProgramStageDataElementUid = findViewById(R.id.programProgramStageDataElementUid);
        TextView programProgramStageDataElementFormName = findViewById(R.id.programProgramStageDataElementFormName);
        TextView programProgramStageDataElementDescription = findViewById(R.id.programProgramStageDataElementDescription);
        TextView programProgramStageDataElementValueType = findViewById(R.id.programProgramStageDataElementValueType);
        TextView programProgramStageDataElementIsMandatory = findViewById(R.id.programProgramStageDataElementIsMandatory);
        LinearLayout programProgramStageDataElementOptionSetCard = findViewById(R.id.programProgramStageDataElementOptionSetCard);

        programProgramStageDataElementUid.setText(dataElement.uid());
        programProgramStageDataElementName.setText(dataElement.displayName());
        programProgramStageDataElementFormName.setText(dataElement.displayFormName());
        programProgramStageDataElementDescription.setText(dataElement.displayDescription());
        programProgramStageDataElementValueType.setText(dataElement.valueType().name());
        programProgramStageDataElementIsMandatory.setText(selectedProgramStageDataElement.compulsory() ? "Yes" : "No");

        if (optionSetId != null) {
            programProgramStageDataElementOptionSetCard.setVisibility(View.VISIBLE);
            setOptionListAdapter(optionSetId);
        } else {
            programProgramStageDataElementOptionSetCard.setVisibility(View.INVISIBLE);
        }

    }

    private DataElement getDataElement(ProgramStageDataElement selectedProgramStageDataElement) {
        return Sdk.d2().dataElementModule()
                .dataElements()
                .byUid().eq(selectedProgramStageDataElement.dataElement().uid())
                .blockingGet().get(0);
    }

    private void setOptionListAdapter(String optionSetId) {
        OptionListAdapter adapter = new OptionListAdapter(this);
        recyclerView.setAdapter(adapter);
        LiveData<PagedList<Option>> liveData = Sdk.d2().optionModule().options()
                .byOptionSetUid().eq(optionSetId)
                .getPaged(5);
        liveData.observe(this, options -> adapter.submitList(options));
    }

    ProgramStageDataElement getSelectedProgramStageDataElement() {
        return Sdk.d2().programModule()
                .programStageDataElements()
                .byUid().eq(selectedProgramStageDataElementId)
                .blockingGet().get(0);
    }

    private enum IntentExtra {
        PROGRAM_STAGE_DATA_ELEMENT
    }
}