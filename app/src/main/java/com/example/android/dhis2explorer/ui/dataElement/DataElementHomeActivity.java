package com.example.android.dhis2explorer.ui.dataElement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.example.android.dhis2explorer.R;
import com.example.android.dhis2explorer.data.Sdk;
import com.example.android.dhis2explorer.ui.base.ListActivity;
import com.example.android.dhis2explorer.ui.dataElement.adapters.DataElementListAdapter;
import com.example.android.dhis2explorer.ui.dataElement.listeners.OnDataElementSelectionListener;

import org.hisp.dhis.android.core.dataelement.DataElement;

public class DataElementHomeActivity extends ListActivity implements OnDataElementSelectionListener {

    @Override
    public void onDataElementSelection(String dataElementId) {
        DataElement dataElement = getDataElement(dataElementId);



        System.out.println(dataElementId + "  "+ dataElement.domainType());
    }

    private DataElement getDataElement(String dataElementId) {
        return Sdk.d2().dataElementModule()
                .dataElements()
                .byUid().eq(dataElementId)
                .blockingGet().get(0);
    }

    public static Intent getActivityIntent(Context context) {
        return new Intent(context, DataElementHomeActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUp(R.layout.activity_data_element_home, R.id.dataElementHomeToolbar, R.id.dataElementHomeToolbarListRecyclerView);
        setDataElementListAdapter();
    }

    private void setDataElementListAdapter() {
        DataElementListAdapter adapter = new DataElementListAdapter(this, true);
        recyclerView.setAdapter(adapter);
        LiveData<PagedList<DataElement>> liveData = Sdk.d2().dataElementModule()
                .dataElements()
                .getPaged(5);
        liveData.observe(this, dataElements -> adapter.submitList(dataElements));
    }


}