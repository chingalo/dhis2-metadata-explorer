package com.example.android.androidskeletonapp.ui.dataSet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.android.androidskeletonapp.R;
import com.example.android.androidskeletonapp.ui.base.DefaultActivity;

import static android.text.TextUtils.isEmpty;

public class DataSetInfoActivity extends DefaultActivity {

    private String selectedDataSet;
    private enum IntentExtra {
        DATA_SET
    }

    public static Intent getActivityIntent(Context context, String dataSetId) {
        Bundle bundle = new Bundle();
        if (!isEmpty(dataSetId))
            bundle.putString(IntentExtra.DATA_SET.name(), dataSetId);
        Intent intent = new Intent(context, DataSetInfoActivity.class);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUp(R.layout.activity_data_set_info,R.id.dataSetInfoToolbar);
        selectedDataSet = getIntent().getStringExtra(IntentExtra.DATA_SET.name());
        System.out.println(selectedDataSet);
    }
}