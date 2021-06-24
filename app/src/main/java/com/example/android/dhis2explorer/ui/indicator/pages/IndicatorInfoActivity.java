package com.example.android.dhis2explorer.ui.indicator.pages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android.dhis2explorer.R;
import com.example.android.dhis2explorer.data.Sdk;
import com.example.android.dhis2explorer.ui.base.DefaultActivity;

import org.hisp.dhis.android.core.indicator.Indicator;

import static android.text.TextUtils.isEmpty;

public class IndicatorInfoActivity extends DefaultActivity {

    private String selectedIndicatorId;

    public static Intent getActivityIntent(Context context, String indicatorId) {
        Bundle bundle = new Bundle();
        if (!isEmpty(indicatorId))
            bundle.putString(IndicatorInfoActivity.IntentExtra.INDICATOR.name(), indicatorId);
        Intent intent = new Intent(context, IndicatorInfoActivity.class);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUp(R.layout.activity_data_set_indicator_info, R.id.dataSetIndicatorInfoToolbar);
        selectedIndicatorId = getIntent().getStringExtra(IntentExtra.INDICATOR.name());
        setUpView();
    }

    private void setUpView() {
        Indicator selectedIndicator = getSelectedIndicator();
        TextView indicatorName = findViewById(R.id.indicatorName);
        TextView indicatorUid = findViewById(R.id.indicatorUid);
        TextView indicatorDescription = findViewById(R.id.indicatorDescription);
        TextView indicatorNumeratorDescription = findViewById(R.id.indicatorNumeratorDescription);
        TextView indicatorNumerator = findViewById(R.id.indicatorNumerator);
        TextView indicatorDenominator = findViewById(R.id.indicatorDenominator);
        TextView indicatorDenominatorDescription = findViewById(R.id.indicatorDenominatorDescription);

        indicatorName.setText(selectedIndicator.displayName());
        indicatorUid.setText(selectedIndicator.uid());
        indicatorDescription.setText(selectedIndicator.displayDescription());
        indicatorNumeratorDescription.setText(selectedIndicator.numeratorDescription());
        indicatorNumerator.setText(selectedIndicator.numerator());
        indicatorDenominator.setText(selectedIndicator.denominator());
        indicatorDenominatorDescription.setText(selectedIndicator.denominatorDescription());
    }

    Indicator getSelectedIndicator() {
        return Sdk.d2().indicatorModule().indicators()
                .byUid().eq(selectedIndicatorId)
                .blockingGet().get(0);
    }

    private enum IntentExtra {
        INDICATOR
    }
}