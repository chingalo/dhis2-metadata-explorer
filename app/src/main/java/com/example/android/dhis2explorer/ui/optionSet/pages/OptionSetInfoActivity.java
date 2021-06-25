package com.example.android.dhis2explorer.ui.optionSet.pages;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android.dhis2explorer.R;
import com.example.android.dhis2explorer.data.Sdk;
import com.example.android.dhis2explorer.ui.base.ListActivity;

import org.hisp.dhis.android.core.option.OptionSet;

import static android.text.TextUtils.isEmpty;

public class OptionSetInfoActivity extends ListActivity  {

    private String selectedOptionSetId;

    public static Intent getActivityIntent(Context context, String selectedOptionSetId) {
        Bundle bundle = new Bundle();
        if (!isEmpty(selectedOptionSetId))
            bundle.putString(IntentExtra.OPTION_SET.name(), selectedOptionSetId);
        Intent intent = new Intent(context, OptionSetInfoActivity.class);
        intent.putExtras(bundle);
        return intent;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUp(R.layout.activity_option_set_info, R.id.optionSetInfoListRecyclerView, R.id.optionSetInfoListRecyclerView);
        selectedOptionSetId = getIntent().getStringExtra(IntentExtra.OPTION_SET.name());
        setView();
    }

    private void setView() {
        OptionSet optionSet = getOptionSet();

        TextView optionSetInfoUid = findViewById(R.id.optionSetInfoUid);
        TextView optionSetInfoName = findViewById(R.id.optionSetInfoName);
        TextView optionSetInfoValueType = findViewById(R.id.optionSetInfoValueType);

        optionSetInfoUid.setText(optionSet.uid());
        optionSetInfoName.setText(optionSet.displayName());
        optionSetInfoValueType.setText(optionSet.valueType().name());

        setOptionListAdapter();

    }

    private void setOptionListAdapter() {
    }


    private OptionSet getOptionSet() {
        return Sdk.d2().optionModule()
                .optionSets()
                .byUid().eq(selectedOptionSetId)
                .one()
                .blockingGet();
    }


    private enum IntentExtra {
        OPTION_SET
    }

}