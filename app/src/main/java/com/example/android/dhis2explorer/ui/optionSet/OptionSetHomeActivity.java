package com.example.android.dhis2explorer.ui.optionSet;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.android.dhis2explorer.R;
import com.example.android.dhis2explorer.data.Sdk;
import com.example.android.dhis2explorer.data.service.ActivityStarter;
import com.example.android.dhis2explorer.ui.base.ListActivity;
import com.example.android.dhis2explorer.ui.optionSet.adapters.OptionSetListAdapter;
import com.example.android.dhis2explorer.ui.optionSet.listeners.OnOptionSetSelectionListener;
import com.example.android.dhis2explorer.ui.optionSet.pages.OptionSetInfoActivity;

import org.hisp.dhis.android.core.arch.repositories.scope.RepositoryScope;
import org.hisp.dhis.android.core.option.OptionSet;

public class OptionSetHomeActivity extends ListActivity implements OnOptionSetSelectionListener {

    public static Intent getActivityIntent(Context context) {
        return new Intent(context, OptionSetHomeActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUp(R.layout.activity_option_set_home,R.id.optionSetHomeToolbar,R.id.optionSetListRecyclerView);
        setOptionSetListAdapter();
    }

    private void setOptionSetListAdapter() {
        OptionSetListAdapter adapter = new OptionSetListAdapter(this);
        recyclerView.setAdapter(adapter);

        LiveData<PagedList<OptionSet>> liveData = Sdk.d2().optionModule()
                .optionSets()
                .orderByDisplayName(RepositoryScope.OrderByDirection.ASC)
                .getPaged(5);

        liveData.observe(this,optionSets -> adapter.submitList(optionSets));

    }

    @Override
    public void onOptionSetSelection(String optionSetId) {
        ActivityStarter.startActivity(this, OptionSetInfoActivity.getActivityIntent(this, optionSetId), false);
        System.out.println("optionSetId : " +optionSetId);
    }
}