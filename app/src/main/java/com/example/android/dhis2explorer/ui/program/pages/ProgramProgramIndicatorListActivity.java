package com.example.android.dhis2explorer.ui.program.pages;


import android.os.Bundle;

import com.example.android.dhis2explorer.R;
import com.example.android.dhis2explorer.ui.base.ListActivity;

public class ProgramProgramIndicatorListActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_program_indicator_list);
    }
}