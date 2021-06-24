package com.example.android.dhis2explorer.ui.options;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.example.android.dhis2explorer.R;
import com.example.android.dhis2explorer.data.Sdk;
import com.example.android.dhis2explorer.data.service.ActivityStarter;
import com.example.android.dhis2explorer.ui.base.ListActivity;
import com.example.android.dhis2explorer.ui.options.adapters.OptionListAdapter;
import com.example.android.dhis2explorer.ui.options.listeners.OnOptionSelectionListener;
import com.example.android.dhis2explorer.ui.options.pages.OptionInfoActivity;

import org.hisp.dhis.android.core.arch.repositories.scope.RepositoryScope;
import org.hisp.dhis.android.core.option.Option;

public class OptionHomeActivity extends ListActivity implements OnOptionSelectionListener {

    public static Intent getActivityIntent(Context context) {
        return new Intent(context, OptionHomeActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUp(R.layout.activity_option_home, R.id.optionHomeToolbar, R.id.optionHomeListRecyclerView);
        setOptionListAdapter();
    }

    private void setOptionListAdapter() {
        OptionListAdapter adapter = new OptionListAdapter(this);
        recyclerView.setAdapter(adapter);

        LiveData<PagedList<Option>> liveData = Sdk.d2().optionModule()
                .options()
                .orderByDisplayName(RepositoryScope.OrderByDirection.ASC)
                .getPaged(5);
        liveData.observe(this, options -> adapter.submitList(options));
    }

    @Override
    public void onOptionSelection(String optionId) {
        ActivityStarter.startActivity(this, OptionInfoActivity.getActivityIntent(this, optionId), false);
    }
}