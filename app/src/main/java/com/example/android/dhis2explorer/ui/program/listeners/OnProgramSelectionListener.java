package com.example.android.dhis2explorer.ui.program.listeners;

import org.hisp.dhis.android.core.program.ProgramType;

public interface OnProgramSelectionListener {
    void onProgramSelected(String programUid, ProgramType programType);
}
