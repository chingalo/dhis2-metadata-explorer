package com.example.android.dhis2explorer.ui.dataSet.pages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.android.dhis2explorer.R;
import com.example.android.dhis2explorer.data.Sdk;
import com.example.android.dhis2explorer.ui.base.DefaultActivity;

import org.hisp.dhis.android.core.dataelement.DataElement;
import org.hisp.dhis.android.core.option.Option;

import java.util.List;

import static android.text.TextUtils.isEmpty;

public class DataSetDataElementInfoActivity extends DefaultActivity {

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
        setUp(R.layout.activity_data_set_data_element_info, R.id.dataSetDataElementInfoToolbar);
        selectedDataElementId = getIntent().getStringExtra(IntentExtra.DATA_ELEMENT.name());
        setUpView();
    }

    private void setUpView() {
        selectedDataElement = getSelectedDataElement();

        //@TODO display other data element properties
        TextView dataSetDataElementName = findViewById(R.id.dataSetDataElementName);
        TextView dataElementOptionSetListView = findViewById(R.id.dataElementOptionSetListView);
        CardView dataElementOptionSetCard = findViewById(R.id.dataElementOptionSetCard);

        dataSetDataElementName.setText(selectedDataElement.displayName());
        //@TODO rendering styled list of options set
        String optionSetId = selectedDataElement.optionSetUid();
        Sdk.d2().optionModule().options().blockingGet();
        if (optionSetId != null) {
            System.out.println("optionList : " + optionSetId);
            List<Option> options = getDataElementOptionSet(optionSetId);
            if (options.size() > 0) {
                StringBuilder optionList = new StringBuilder();
                for (Option option : options) {
                    optionList.append("uid : ").append(option.uid()).append("\n");
                    optionList.append("name : ").append(option.displayName()).append("\n");
                    optionList.append("code : ").append(option.code()).append("\n\n\n");
                }
                System.out.println("optionList : " + optionList);
                System.out.println("optionList : " + optionList.toString());
                dataElementOptionSetCard.setVisibility(View.VISIBLE);
                dataElementOptionSetListView.setText(optionList);
            }
        }
    }

    List<Option> getDataElementOptionSet(String optionSetId) {
        return Sdk.d2().optionModule().options()
                .byOptionSetUid().eq(optionSetId)
                .blockingGet();
    }

    DataElement getSelectedDataElement() {
        return Sdk.d2().dataElementModule().dataElements()
                .byUid().eq(selectedDataElementId)
                .blockingGet().get(0);
    }
}