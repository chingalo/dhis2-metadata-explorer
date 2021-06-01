package com.example.android.androidskeletonapp.ui.dataSet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.android.androidskeletonapp.R;
import com.example.android.androidskeletonapp.ui.program.ProgramWithOutRegistrationActivity;

import static android.text.TextUtils.isEmpty;

public class DataSetInfoActivity extends AppCompatActivity {

    private String selectedDataSet;
    private enum IntentExtra {
        DATA_SET
    }

    public static Intent getActivityIntent(Context context, String programId) {
        Bundle bundle = new Bundle();
        if (!isEmpty(programId))
            bundle.putString(IntentExtra.DATA_SET.name(), programId);
        Intent intent = new Intent(context, DataSetInfoActivity.class);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_set_info);
        selectedDataSet = getIntent().getStringExtra(IntentExtra.DATA_SET.name());
    }
}