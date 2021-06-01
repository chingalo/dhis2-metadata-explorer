package com.example.android.androidskeletonapp.ui.program;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.android.androidskeletonapp.R;
import com.example.android.androidskeletonapp.ui.base.ListActivity;
import com.example.android.androidskeletonapp.ui.dataSet.DataSetHomeActivity;

public class ProgramHomeActivity extends ListActivity {

    public static Intent getProgramHomeActivityIntent(Context context) {
        return new Intent(context, ProgramHomeActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUp(R.layout.activity_program_home,R.id.programHomeToolbar);
    }
}