package com.example.android.dhis2explorer.ui.dataSet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.android.dhis2explorer.R;
import com.example.android.dhis2explorer.data.Sdk;
import com.example.android.dhis2explorer.ui.base.DefaultActivity;
import com.google.android.material.snackbar.Snackbar;

import org.hisp.dhis.android.core.dataset.DataSet;

import static android.text.TextUtils.isEmpty;

public class DataSetInfoActivity extends DefaultActivity {

    private int dataSetIndicatorCount;
    private int dataSetDataElementCount;
    private DataSet selectedDataSet;

    private String selectedDataSetId;
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
        selectedDataSetId = getIntent().getStringExtra(IntentExtra.DATA_SET.name());
        setUpView();
        setCardClickListener();
    }

    private void  setCardClickListener(){
        CardView dataElementCard = findViewById(R.id.dataSetDataElementCard);
        CardView indicatorCard = findViewById(R.id.dataSetIndicatorCard);

        String dataSetName  = selectedDataSet.displayName();

        dataElementCard.setOnClickListener(view->{
            if(dataSetDataElementCount == 0){
                Snackbar.make(view, "There is no data elements for " + dataSetName , Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }else{
                System.out.println(selectedDataSet.dataSetElements());
            }
        });

        indicatorCard.setOnClickListener(view->{
            if(dataSetIndicatorCount == 0){
                Snackbar.make(view, "There is no indicators for " + dataSetName , Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }else{
                System.out.println(selectedDataSet.indicators());
            }
        });
    }

    private void setUpView(){
        selectedDataSet = getSelectedDataSet();
        dataSetIndicatorCount = selectedDataSet.indicators().size();
        dataSetDataElementCount = selectedDataSet.dataSetElements().size();
        TextView uid = findViewById(R.id.dataSetId);
        TextView name = findViewById(R.id.dataSetName);
        TextView description = findViewById(R.id.dataSetDescription);
        TextView periodType = findViewById(R.id.dataSetPeriodType);
        TextView dataElementCount = findViewById(R.id.dataSetDataElementCount);
        TextView indicatorCount = findViewById(R.id.dataSetIndicatorCount);

        uid.setText(selectedDataSet.uid());
        name.setText(selectedDataSet.displayName());
        description.setText(selectedDataSet.displayDescription());
        periodType.setText(selectedDataSet.periodType().name());
        dataElementCount.setText(""+dataSetDataElementCount);
        indicatorCount.setText(""+dataSetIndicatorCount);
    }

    private DataSet getSelectedDataSet(){
        return Sdk.d2().dataSetModule().dataSets()
                .byUid().eq(selectedDataSetId)
                .withIndicators()
                .withDataSetElements()
                .blockingGet().get(0);
    }
}