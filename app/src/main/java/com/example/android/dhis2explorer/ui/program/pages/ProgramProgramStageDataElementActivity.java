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
import com.example.android.dhis2explorer.ui.base.ListActivity;
import com.example.android.dhis2explorer.ui.common.adapters.OptionListAdapter;

import org.hisp.dhis.android.core.dataelement.DataElement;
import org.hisp.dhis.android.core.option.Option;
import org.hisp.dhis.android.core.program.ProgramStageDataElement;

import static android.text.TextUtils.isEmpty;

public class ProgramProgramStageDataElementActivity extends ListActivity {

    private String selectedProgramStageDataElementId;

    private enum IntentExtra {
        PROGRAM_STAGE_DATA_ELEMENT
    }

    public static Intent getActivityIntent(Context context, String selectedProgramStageDataElementId) {
        Bundle bundle = new Bundle();
        if (!isEmpty(selectedProgramStageDataElementId))
            bundle.putString(ProgramProgramStageDataElementActivity.IntentExtra.PROGRAM_STAGE_DATA_ELEMENT.name(), selectedProgramStageDataElementId);
        Intent intent = new Intent(context, ProgramProgramStageDataElementActivity.class);
        intent.putExtras(bundle);
        return intent;
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

        //@TODO displaying missing fields
        LinearLayout programProgramStageDataElementOptionSetCard = findViewById(R.id.programProgramStageDataElementOptionSetCard);
        TextView programProgramStageDataElementName = findViewById(R.id.programProgramStageDataElementName);

        programProgramStageDataElementName.setText(dataElement.displayName());

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
        OptionListAdapter adapter = new OptionListAdapter();
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
}