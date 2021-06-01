package com.example.android.androidskeletonapp.ui.program;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.android.androidskeletonapp.R;
import com.example.android.androidskeletonapp.data.Sdk;
import com.example.android.androidskeletonapp.data.service.ActivityStarter;
import com.example.android.androidskeletonapp.ui.base.ListActivity;

import org.hisp.dhis.android.core.program.Program;
import org.hisp.dhis.android.core.program.ProgramType;

public class ProgramHomeActivity extends ListActivity implements  OnProgramSelectionListener{

    public static Intent getProgramHomeActivityIntent(Context context) {
        return new Intent(context, ProgramHomeActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUp(R.layout.activity_program_home,R.id.programHomeToolbar, R.id.programListRecyclerView);
        discoveringProgram();
    }

    private void discoveringProgram(){
        ProgramHomeAdapter adapter = new ProgramHomeAdapter(this);
        recyclerView.setAdapter(adapter);
        LiveData<PagedList<Program>> liveData = Sdk.d2().programModule().programs()
                .getPaged(10);
        liveData.observe(this,programPagedList -> adapter.submitList(programPagedList));

    }

    @Override
    public void onProgramSelected(String programUid, ProgramType programType) {
        if(programType == ProgramType.WITH_REGISTRATION ){
            ActivityStarter.startActivity(this, ProgramWithRegistrationActivity.getActivityIntent(this,programUid),false);
        }else{
            ActivityStarter.startActivity(this, ProgramWithOutRegistrationActivity.getActivityIntent(this,programUid),false);
        }
    }
}