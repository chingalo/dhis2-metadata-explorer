package com.example.android.dhis2explorer.ui.dataSet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.example.android.dhis2explorer.R;
import com.example.android.dhis2explorer.data.Sdk;
import com.example.android.dhis2explorer.data.service.ActivityStarter;
import com.example.android.dhis2explorer.ui.base.ListActivity;
import com.example.android.dhis2explorer.ui.dataSet.adapters.DataSetHomeAdapter;
import com.example.android.dhis2explorer.ui.dataSet.listeners.OnDataSetSelectionListener;
import com.example.android.dhis2explorer.ui.dataSet.pages.DataSetInfoActivity;

import org.hisp.dhis.android.core.dataset.DataSet;

public class DataSetHomeActivity extends ListActivity implements OnDataSetSelectionListener {

    public static Intent getActivityIntent(Context context) {
        return new Intent(context, DataSetHomeActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUp(R.layout.activity_data_set_home, R.id.dataSetHomeToolbar, R.id.dataSetListRecyclerView);
        discoveringDataSet();
    }

    private void discoveringDataSet() {
        DataSetHomeAdapter adapter = new DataSetHomeAdapter(this);
        recyclerView.setAdapter(adapter);

        LiveData<PagedList<DataSet>> liveData = Sdk.d2().dataSetModule().dataSets()
                .getPaged(5);
        liveData.observe(this, dataSetPagedList -> adapter.submitList(dataSetPagedList));


    }

    @Override
    public void onDataSetSelected(String dataSetId) {
        ActivityStarter.startActivity(this, DataSetInfoActivity.getActivityIntent(this, dataSetId), false);
    }
}