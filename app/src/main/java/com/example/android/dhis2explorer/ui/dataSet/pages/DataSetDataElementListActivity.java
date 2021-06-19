package com.example.android.dhis2explorer.ui.dataSet.pages;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android.dhis2explorer.R;
import com.example.android.dhis2explorer.data.Sdk;
import com.example.android.dhis2explorer.ui.base.ListActivity;

import org.hisp.dhis.android.core.dataset.DataSet;

import static android.text.TextUtils.isEmpty;

public class DataSetDataElementListActivity extends ListActivity {
    private DataSet selectedDataSet;

    private String selectedDataSetId;
    private enum IntentExtra {
        DATA_SET
    }

    public static Intent getActivityIntent(Context context, String dataSetId) {
        Bundle bundle = new Bundle();
        if (!isEmpty(dataSetId))
            bundle.putString(DataSetDataElementListActivity.IntentExtra.DATA_SET.name(), dataSetId);
        Intent intent = new Intent(context, DataSetDataElementListActivity.class);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUp(R.layout.activity_data_set_data_element_list, R.id.dataSetDataElementListToolbar,R.id.dataSetDataElementListRecyclerView );
        selectedDataSetId = getIntent().getStringExtra(DataSetDataElementListActivity.IntentExtra.DATA_SET.name());
        setDataSetDataElementListView();

    }

    private void setDataSetDataElementListView() {
        selectedDataSet = getSelectedDataSet();
        selectedDataSet = getSelectedDataSet();
        int dataSetDataElementCount = selectedDataSet.dataSetElements().size();

        TextView dataSetInfoName = findViewById(R.id.dataSetInfoName);
        TextView dataElementListCount = findViewById(R.id.dataElementListCount);

        dataSetInfoName.setText(selectedDataSet.displayName());
        dataElementListCount.setText(""+dataSetDataElementCount);

    }

    private DataSet getSelectedDataSet(){
        return Sdk.d2().dataSetModule().dataSets()
                .byUid().eq(selectedDataSetId)
                .withIndicators()
                .withDataSetElements()
                .blockingGet().get(0);
    }
}