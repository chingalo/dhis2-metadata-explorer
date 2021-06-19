package com.example.android.dhis2explorer.ui.dataSet.pages;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.example.android.dhis2explorer.R;
import com.example.android.dhis2explorer.data.Sdk;
import com.example.android.dhis2explorer.ui.base.ListActivity;
import com.example.android.dhis2explorer.ui.dataSet.adapters.DataSetIndicatorAdapter;
import com.example.android.dhis2explorer.ui.dataSet.listeners.OnIndicatorSelectionListener;

import org.hisp.dhis.android.core.dataset.DataSet;
import org.hisp.dhis.android.core.indicator.Indicator;

import static android.text.TextUtils.isEmpty;

public class DataSetIndicatorListActivity extends ListActivity implements OnIndicatorSelectionListener {

    private DataSet selectedDataSet;

    private String selectedDataSetId;

    @Override
    public void onIndicatorSelection( String indicatorId) {
        //@TODO handling rendering of view for indicator info
        System.out.println("dataSet " + selectedDataSetId + " with data element " + indicatorId);
    }

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
        DataSetIndicatorAdapter adapter = new DataSetIndicatorAdapter(this);
        recyclerView.setAdapter(adapter);
        LiveData<PagedList<Indicator>> liveData  = Sdk.d2().indicatorModule().indicators().byDataSetUid(selectedDataSetId).getPaged(5);
        liveData.observe(this,indicators -> adapter.submitList(indicators));
    }

    private DataSet getSelectedDataSet(){
        return Sdk.d2().dataSetModule().dataSets()
                .byUid().eq(selectedDataSetId)
                .withIndicators()
                .withDataSetElements()
                .blockingGet().get(0);
    }
}