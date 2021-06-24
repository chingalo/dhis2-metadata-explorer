package com.example.android.dhis2explorer.ui.dataSet.pages;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.example.android.dhis2explorer.R;
import com.example.android.dhis2explorer.data.Sdk;
import com.example.android.dhis2explorer.data.service.ActivityStarter;
import com.example.android.dhis2explorer.ui.base.ListActivity;
import com.example.android.dhis2explorer.ui.dataSet.adapters.DataElementListAdapter;
import com.example.android.dhis2explorer.ui.dataSet.listeners.OnDataElementSelectionListener;

import org.hisp.dhis.android.core.dataelement.DataElement;
import org.hisp.dhis.android.core.dataset.DataSet;
import org.hisp.dhis.android.core.dataset.DataSetElement;

import java.util.ArrayList;
import java.util.List;

import static android.text.TextUtils.isEmpty;

public class DataSetDataElementListActivity extends ListActivity implements OnDataElementSelectionListener {
    private DataSet selectedDataSet;

    private String selectedDataSetId;

    public static Intent getActivityIntent(Context context, String dataSetId) {
        Bundle bundle = new Bundle();
        if (!isEmpty(dataSetId))
            bundle.putString(DataSetDataElementListActivity.IntentExtra.DATA_SET.name(), dataSetId);
        Intent intent = new Intent(context, DataSetDataElementListActivity.class);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    public void onDataElementSelection(String dataElementId) {
        ActivityStarter.startActivity(this, DataSetDataElementInfoActivity.getActivityIntent(this, dataElementId), false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUp(R.layout.activity_data_set_data_element_list, R.id.dataSetDataElementListToolbar, R.id.dataSetDataElementListRecyclerView);
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
        dataElementListCount.setText("" + dataSetDataElementCount);

        setDataSetDataElementListAdapter();
    }

    private void setDataSetDataElementListAdapter() {
        DataElementListAdapter adapter = new DataElementListAdapter(this);
        recyclerView.setAdapter(adapter);
        List<String> dataElementIds = getDataElementIds();
        LiveData<PagedList<DataElement>> liveData = Sdk.d2().dataElementModule().dataElements().byUid().in(dataElementIds).getPaged(5);
        liveData.observe(this, dataElements -> adapter.submitList(dataElements));
    }

    List<String> getDataElementIds() {
        List<String> dataElementIds = new ArrayList<String>();
        for (DataSetElement dataSetElement : selectedDataSet.dataSetElements()) {
            dataElementIds.add(dataSetElement.dataElement().uid());
        }
        return dataElementIds;
    }

    private DataSet getSelectedDataSet() {
        return Sdk.d2().dataSetModule().dataSets()
                .byUid().eq(selectedDataSetId)
                .withIndicators()
                .withDataSetElements()
                .blockingGet().get(0);
    }


    private enum IntentExtra {
        DATA_SET
    }
}