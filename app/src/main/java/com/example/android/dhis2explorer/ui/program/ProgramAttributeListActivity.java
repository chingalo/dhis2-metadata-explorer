package com.example.android.dhis2explorer.ui.program;

import android.os.Bundle;

import com.example.android.dhis2explorer.R;
import com.example.android.dhis2explorer.ui.base.ListActivity;

public class ProgramAttributeListActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_attribute_list);
    }
}