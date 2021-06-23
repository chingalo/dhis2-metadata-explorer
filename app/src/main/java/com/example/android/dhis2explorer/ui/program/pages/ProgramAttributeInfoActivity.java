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

import org.hisp.dhis.android.core.option.Option;
import org.hisp.dhis.android.core.program.ProgramTrackedEntityAttribute;
import org.hisp.dhis.android.core.trackedentity.TrackedEntityAttribute;

import static android.text.TextUtils.isEmpty;

public class ProgramAttributeInfoActivity extends ListActivity {

    private String selectedProgramTrackedEntityAttributeId;
    private ProgramTrackedEntityAttribute selectedProgramTrackedEntityAttribute;

    private enum IntentExtra {
        PROGRAM_TRACKED_ENTITY_ATTRIBUTE
    }

    public static Intent getActivityIntent(Context context, String programTrackedEntityAttributeId) {
        Bundle bundle = new Bundle();
        if (!isEmpty(programTrackedEntityAttributeId))
            bundle.putString(ProgramAttributeInfoActivity.IntentExtra.PROGRAM_TRACKED_ENTITY_ATTRIBUTE.name(), programTrackedEntityAttributeId);
        Intent intent = new Intent(context, ProgramAttributeInfoActivity.class);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUp(R.layout.activity_program_attribute_info, R.id.programAttributeInfoToolbar, R.id.programAttributeOptionSetListView);
        selectedProgramTrackedEntityAttributeId = getIntent().getStringExtra(IntentExtra.PROGRAM_TRACKED_ENTITY_ATTRIBUTE.name());
        setView();
    }

    private void setView() {
        selectedProgramTrackedEntityAttribute = getSelectedProgramTrackedEntityAttribute();
        String attributeId = selectedProgramTrackedEntityAttribute.trackedEntityAttribute().uid();
        TrackedEntityAttribute trackedEntityAttribute = getTrackedEntityAttribute(attributeId);

//        trackedEntityAttribute.valueType();
//        trackedEntityAttribute.displayFormName();
//        trackedEntityAttribute.unique();
//        trackedEntityAttribute.generated();
//        trackedEntityAttribute.displayInListNoProgram();
//        trackedEntityAttribute.optionSet().uid()

        //@TODO add other properties
        TextView programAttributeName = findViewById(R.id.programAttributeName);
        LinearLayout programAttributeOptionSetCard = findViewById(R.id.programAttributeOptionSetCard);

        programAttributeName.setText(selectedProgramTrackedEntityAttribute.displayName());
        try {
            String optionSetId = trackedEntityAttribute.optionSet().uid();
            programAttributeOptionSetCard.setVisibility(View.VISIBLE);
            setOptionSetListAdapter(optionSetId);
        } catch (Exception e) {
            programAttributeOptionSetCard.setVisibility(View.INVISIBLE);
        }
    }

    private void setOptionSetListAdapter(String optionSetId) {
        OptionListAdapter adapter = new OptionListAdapter();
        recyclerView.setAdapter(adapter);

        LiveData<PagedList<Option>> liveData = Sdk.d2().optionModule().options()
                .byOptionSetUid().eq(optionSetId)
                .getPaged(5);
        liveData.observe(this, options -> adapter.submitList(options));
    }

    private TrackedEntityAttribute getTrackedEntityAttribute(String attributeId) {
        return Sdk.d2().trackedEntityModule().trackedEntityAttributes().byUid().eq(attributeId).blockingGet().get(0);
    }


    ProgramTrackedEntityAttribute getSelectedProgramTrackedEntityAttribute() {
        return Sdk.d2().programModule()
                .programTrackedEntityAttributes()
                .byUid().eq(selectedProgramTrackedEntityAttributeId)
                .blockingGet().get(0);
    }

}