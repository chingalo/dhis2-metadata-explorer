package com.example.android.dhis2explorer.ui.program.pages;

import android.os.Bundle;

import com.example.android.dhis2explorer.R;
import com.example.android.dhis2explorer.ui.base.ListActivity;

import org.hisp.dhis.android.core.program.ProgramStageDataElement;

public class ProgramProgramStageDataElementActivity extends ListActivity   {

    private String programStageDataElementId;
    private ProgramStageDataElement selectedProgramStageDataElement;

    private enum IntentExtra {
        PROGRAM_STAGE_DATA_ELEMENT
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_program_stage_data_element);
    }
}