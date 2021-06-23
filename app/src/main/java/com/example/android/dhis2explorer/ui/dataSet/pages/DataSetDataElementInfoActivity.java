package com.example.android.dhis2explorer.ui.dataSet.pages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.example.android.dhis2explorer.R;
import com.example.android.dhis2explorer.data.Sdk;
import com.example.android.dhis2explorer.ui.base.ListActivity;
import com.example.android.dhis2explorer.ui.common.adapters.OptionListAdapter;

import org.hisp.dhis.android.core.dataelement.DataElement;
import org.hisp.dhis.android.core.option.Option;

import static android.text.TextUtils.isEmpty;

public class DataSetDataElementInfoActivity extends ListActivity {

    private DataElement selectedDataElement;

    private String selectedDataElementId;

    private enum IntentExtra {
        DATA_ELEMENT
    }

    public static Intent getActivityIntent(Context context, String dataElementId) {
        Bundle bundle = new Bundle();
        if (!isEmpty(dataElementId))
            bundle.putString(DataSetDataElementInfoActivity.IntentExtra.DATA_ELEMENT.name(), dataElementId);
        Intent intent = new Intent(context, DataSetDataElementInfoActivity.class);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUp(R.layout.activity_data_set_data_element_info, R.id.dataSetDataElementInfoToolbar, R.id.dataElementOptionSetRecyclerView);
        selectedDataElementId = getIntent().getStringExtra(IntentExtra.DATA_ELEMENT.name());
        setUpView();
    }

    private void setUpView() {
        selectedDataElement = getSelectedDataElement();
        String optionSetId = selectedDataElement.optionSetUid();

        TextView dataSetDataElementName = findViewById(R.id.dataSetDataElementName);
        TextView dataSetDataElementUid = findViewById(R.id.dataSetDataElementUid);
        TextView dataSetDataElementFormName = findViewById(R.id.dataSetDataElementFormName);
        TextView dataSetDataElementAggregationType = findViewById(R.id.dataSetDataElementAggregationType);
        TextView dataSetDataElementValueType = findViewById(R.id.dataSetDataElementValueType);
        TextView dataSetDataElementCategoryComboUid = findViewById(R.id.dataSetDataElementCategoryComboUid);
        TextView dataSetDataElementDescription = findViewById(R.id.dataSetDataElementDescription);
        LinearLayout dataElementOptionSetCard = findViewById(R.id.dataElementOptionSetCard);

        dataSetDataElementName.setText(selectedDataElement.displayName());
        dataSetDataElementUid.setText(selectedDataElement.uid());
        dataSetDataElementFormName.setText(selectedDataElement.displayFormName());
        dataSetDataElementAggregationType.setText(selectedDataElement.aggregationType());
        dataSetDataElementValueType.setText(selectedDataElement.valueType().name());
        dataSetDataElementCategoryComboUid.setText(selectedDataElement.categoryComboUid());
        dataSetDataElementDescription.setText(selectedDataElement.displayDescription());

        if (optionSetId != null) {
            dataElementOptionSetCard.setVisibility(View.VISIBLE);
            OptionListAdapter adapter = new OptionListAdapter();
            recyclerView.setAdapter(adapter);
            LiveData<PagedList<Option>> liveData = Sdk.d2().optionModule().options()
                    .byOptionSetUid().eq(optionSetId)
                    .getPaged(5);
            liveData.observe(this, options -> adapter.submitList(options));
        } else {
            dataElementOptionSetCard.setVisibility(View.GONE);
        }
    }

    DataElement getSelectedDataElement() {
        return Sdk.d2().dataElementModule().dataElements()
                .byUid().eq(selectedDataElementId)
                .blockingGet().get(0);
    }
}