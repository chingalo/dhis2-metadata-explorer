package com.example.android.androidskeletonapp.ui.dataSet;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.android.androidskeletonapp.R;
import com.example.android.androidskeletonapp.data.Sdk;
import com.example.android.androidskeletonapp.data.service.ActivityStarter;
import com.example.android.androidskeletonapp.ui.base.ListActivity;

import org.hisp.dhis.android.core.dataset.DataSet;

public class DataSetHomeActivity extends ListActivity implements OnDataSetSelectionListener {

    public static Intent getDataSetHomeActivityIntent(Context context) {
        return new Intent(context, DataSetHomeActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUp(R.layout.activity_data_set_home, R.id.dataSetHomeToolbar,R.id.dataSetListRecyclerView );
        discoveringDataSet();
    }

    private void discoveringDataSet(){
        DataSetHomeAdapter adapter = new DataSetHomeAdapter(this);
        recyclerView.setAdapter(adapter);

        LiveData<PagedList<DataSet>> liveData = Sdk.d2().dataSetModule().dataSets()
                .getPaged(5);
        liveData.observe(this,dataSetPagedList-> adapter.submitList(dataSetPagedList));



    }

    @Override
    public void onDataSetSelected(String dataSetId) {
        ActivityStarter.startActivity(this,DataSetInfoActivity.getActivityIntent(this,dataSetId),false);
    }
}