package com.example.android.androidskeletonapp.ui.dataSet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.android.androidskeletonapp.R;
import com.example.android.androidskeletonapp.ui.main.MainActivity;

public class DataSetHomeActivity extends AppCompatActivity {

    public static Intent getDataSetHomeActivityIntent(Context context) {
        return new Intent(context, DataSetHomeActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_set_home);
    }
}