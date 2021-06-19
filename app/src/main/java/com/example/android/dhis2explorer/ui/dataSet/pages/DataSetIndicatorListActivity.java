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

public class DataSetIndicatorListActivity extends ListActivity {

    private DataSet selectedDataSet;

    private String selectedDataSetId;
    private enum IntentExtra {
        DATA_SET
    }

    public static Intent getActivityIntent(Context context, String dataSetId) {
        Bundle bundle = new Bundle();
        if (!isEmpty(dataSetId))
            bundle.putString(DataSetIndicatorListActivity.IntentExtra.DATA_SET.name(), dataSetId);
        Intent intent = new Intent(context, DataSetIndicatorListActivity.class);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUp(R.layout.activity_data_set_indicator_list, R.id.dataSetIndicatorListToolbar,R.id.dataSetIndicatorListRecyclerView );
        selectedDataSetId = getIntent().getStringExtra(DataSetIndicatorListActivity.IntentExtra.DATA_SET.name());
        setDataSetIndicatorListView();
    }

    private void setDataSetIndicatorListView() {
        selectedDataSet = getSelectedDataSet();
        int dataSetIndicatorCount = selectedDataSet.indicators().size();

        TextView dataSetInfoName = findViewById(R.id.indicatorDataSetInfoName);
        TextView indicatorListCount = findViewById(R.id.indicatorListCount);

        dataSetInfoName.setText(selectedDataSet.displayName());
        indicatorListCount.setText(""+dataSetIndicatorCount);

        setIndicatorListAdaptor();
    }

    private void setIndicatorListAdaptor() {

    }

    private DataSet getSelectedDataSet(){
        return Sdk.d2().dataSetModule().dataSets()
                .byUid().eq(selectedDataSetId)
                .withIndicators()
                .withDataSetElements()
                .blockingGet().get(0);
    }
}