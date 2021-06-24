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
import com.example.android.dhis2explorer.ui.program.adapters.ProgramAttributeListAdapter;
import com.example.android.dhis2explorer.ui.program.listeners.OnProgramAttributeSelectionListener;

import org.hisp.dhis.android.core.arch.repositories.scope.RepositoryScope;
import org.hisp.dhis.android.core.program.Program;
import org.hisp.dhis.android.core.program.ProgramTrackedEntityAttribute;

import static android.text.TextUtils.isEmpty;

public class ProgramAttributeListActivity extends ListActivity implements OnProgramAttributeSelectionListener {

    private String selectedProgramId;

    public static Intent getActivityIntent(Context context, String programId) {
        Bundle bundle = new Bundle();
        if (!isEmpty(programId))
            bundle.putString(ProgramAttributeListActivity.IntentExtra.PROGRAM.name(), programId);
        Intent intent = new Intent(context, ProgramAttributeListActivity.class);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    public void onProgramAttributeSelected(String programTrackedEntityAttributeId) {
        ActivityStarter.startActivity(this, ProgramAttributeInfoActivity.getActivityIntent(this, programTrackedEntityAttributeId), false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUp(R.layout.activity_program_attribute_list, R.id.programAttributeListToolbar, R.id.programProgramAttributeListRecyclerView);
        selectedProgramId = getIntent().getStringExtra(IntentExtra.PROGRAM.name());
        setView();
    }

    void setView() {
        Program selectedProgram = getSelectedProgram();
        int attributeCount = getProgramAttributeListCount();

        TextView programProgramAttributeName = findViewById(R.id.programProgramAttributeName);
        TextView programProgramAttributeCount = findViewById(R.id.programProgramAttributeCount);

        programProgramAttributeName.setText(selectedProgram.displayName());
        programProgramAttributeCount.setText("" + attributeCount);

        setListAdapterForAttribute();
    }

    private void setListAdapterForAttribute() {
        ProgramAttributeListAdapter adapter = new ProgramAttributeListAdapter(this);
        recyclerView.setAdapter(adapter);
        LiveData<PagedList<ProgramTrackedEntityAttribute>> liveData = Sdk.d2().programModule()
                .programTrackedEntityAttributes()
                .orderByDisplayName(RepositoryScope.OrderByDirection.ASC)
                .byProgram().eq(selectedProgramId)
                .getPaged(10);
        liveData.observe(this, programTrackedEntityAttributes -> adapter.submitList(programTrackedEntityAttributes));
    }

    private int getProgramAttributeListCount() {
        return Sdk.d2().programModule()
                .programTrackedEntityAttributes()
                .byProgram().eq(selectedProgramId)
                .blockingCount();
    }

    Program getSelectedProgram() {
        return Sdk.d2().programModule()
                .programs()
                .byUid().eq(selectedProgramId)
                .one()
                .blockingGet();
    }

    private enum IntentExtra {
        PROGRAM
    }
}