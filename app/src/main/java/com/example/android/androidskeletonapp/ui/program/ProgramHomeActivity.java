package com.example.android.androidskeletonapp.ui.program;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.android.androidskeletonapp.R;
import com.example.android.androidskeletonapp.ui.dataSet.DataSetHomeActivity;

public class ProgramHomeActivity extends AppCompatActivity {

    public static Intent getProgramHomeActivityIntent(Context context) {
        return new Intent(context, ProgramHomeActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_home);
    }
}