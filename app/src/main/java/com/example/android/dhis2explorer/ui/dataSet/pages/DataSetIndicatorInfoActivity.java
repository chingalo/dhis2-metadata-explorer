package com.example.android.dhis2explorer.ui.dataSet.pages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android.dhis2explorer.R;
import com.example.android.dhis2explorer.data.Sdk;
import com.example.android.dhis2explorer.ui.base.DefaultActivity;

import org.hisp.dhis.android.core.indicator.Indicator;

import static android.text.TextUtils.isEmpty;

public class DataSetIndicatorInfoActivity extends DefaultActivity {

    private Indicator selectedIndicator;

    private String selectedIndicatorId;
    private enum IntentExtra {
        INDICATOR
    }

    public static Intent getActivityIntent(Context context, String indicatorId) {
        Bundle bundle = new Bundle();
        if (!isEmpty(indicatorId))
            bundle.putString(DataSetIndicatorInfoActivity.IntentExtra.INDICATOR.name(), indicatorId);
        Intent intent = new Intent(context, DataSetIndicatorInfoActivity.class);
        intent.putExtras(bundle);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUp(R.layout.activity_data_set_indicator_info,R.id.dataSetIndicatorInfoToolbar);
        selectedIndicatorId = getIntent().getStringExtra(IntentExtra.INDICATOR.name());
        setUpView();
    }

    private void setUpView() {
        selectedIndicator = getSelectedIndicator();

        //@TODO display other indicator properties
        TextView indicatorName = findViewById(R.id.indicatorName);

        indicatorName.setText(selectedIndicator.displayName());
    }

    Indicator getSelectedIndicator(){
        return Sdk.d2().indicatorModule().indicators()
                .byUid().eq(selectedIndicatorId)
                .blockingGet().get(0);
    }
}