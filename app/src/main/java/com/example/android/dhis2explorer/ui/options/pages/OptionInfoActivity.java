package com.example.android.dhis2explorer.ui.options.pages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android.dhis2explorer.R;
import com.example.android.dhis2explorer.data.Sdk;
import com.example.android.dhis2explorer.ui.base.DefaultActivity;

import org.hisp.dhis.android.core.option.Option;
import org.hisp.dhis.android.core.option.OptionSet;

import static android.text.TextUtils.isEmpty;

public class OptionInfoActivity extends DefaultActivity {

    private String selectedOptionId;

    public static Intent getActivityIntent(Context context, String selectedOptionId) {
        Bundle bundle = new Bundle();
        if (!isEmpty(selectedOptionId))
            bundle.putString(OptionInfoActivity.IntentExtra.OPTION.name(), selectedOptionId);
        Intent intent = new Intent(context, OptionInfoActivity.class);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUp(R.layout.activity_option_info, R.id.optionInfoToolbar);
        selectedOptionId = getIntent().getStringExtra(IntentExtra.OPTION.name());
        setView();
    }

    private void setView() {
        Option option = getOption();
        String optionSetId = option.optionSet().uid();
        OptionSet optionSet = getOptionSet(optionSetId);

        TextView optionUid = findViewById(R.id.optionUid);
        TextView optionName = findViewById(R.id.optionName);
        TextView optionCode = findViewById(R.id.optionCode);
        TextView optionOptionSetUid = findViewById(R.id.optionOptionSetUid);
        TextView optionOptionSetUName = findViewById(R.id.optionOptionSetUName);

        optionUid.setText(option.uid());
        optionName.setText(option.displayName());
        optionCode.setText(option.code());
        optionOptionSetUid.setText(optionSetId);
        optionOptionSetUName.setText(optionSet.displayName());
    }

    private OptionSet getOptionSet(String optionSetId) {
        return Sdk.d2().optionModule()
                .optionSets()
                .byUid().eq(optionSetId)
                .one()
                .blockingGet();
    }

    private Option getOption() {
        return Sdk.d2().optionModule()
                .options()
                .byUid().eq(selectedOptionId)
                .one()
                .blockingGet();
    }

    private enum IntentExtra {
        OPTION
    }
}