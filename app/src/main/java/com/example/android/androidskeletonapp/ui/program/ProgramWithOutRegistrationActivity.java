package com.example.android.androidskeletonapp.ui.program;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.android.androidskeletonapp.R;
import com.example.android.androidskeletonapp.ui.base.DefaultActivity;

import static android.text.TextUtils.isEmpty;

public class ProgramWithOutRegistrationActivity extends DefaultActivity {

    private String selectedProgram;
    private enum IntentExtra {
        PROGRAM
    }

    public static Intent getActivityIntent(Context context, String programId) {
        Bundle bundle = new Bundle();
        if (!isEmpty(programId))
            bundle.putString(ProgramWithOutRegistrationActivity.IntentExtra.PROGRAM.name(), programId);
        Intent intent = new Intent(context, ProgramWithOutRegistrationActivity.class);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUp(R.layout.activity_program_with_out_registration, R.id.programWithOutRegistrationToolbar);
        selectedProgram = getIntent().getStringExtra(IntentExtra.PROGRAM.name());
        System.out.println(selectedProgram);
    }
}