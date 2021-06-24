package com.example.android.dhis2explorer.ui.indicator;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.android.dhis2explorer.R;
import com.example.android.dhis2explorer.data.Sdk;
import com.example.android.dhis2explorer.data.service.ActivityStarter;
import com.example.android.dhis2explorer.ui.base.ListActivity;
import com.example.android.dhis2explorer.ui.indicator.adapters.IndicatorAdapter;
import com.example.android.dhis2explorer.ui.indicator.listeners.OnIndicatorSelectionListener;
import com.example.android.dhis2explorer.ui.indicator.pages.IndicatorInfoActivity;

import org.hisp.dhis.android.core.arch.repositories.scope.RepositoryScope;
import org.hisp.dhis.android.core.indicator.Indicator;

public class IndicatorHomeActivity extends ListActivity implements OnIndicatorSelectionListener {

    public static Intent getActivityIntent(Context context) {
        return new Intent(context, IndicatorHomeActivity.class);
    }

    @Override
    public void onIndicatorSelection(String indicatorId) {
        ActivityStarter.startActivity(this, IndicatorInfoActivity.getActivityIntent(this,indicatorId), false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUp(R.layout.activity_indicator_home, R.id.indicatorHomeToolbar, R.id.indicatorHomeListRecyclerView);
        setIndicatorListAdapter();
    }

    private void setIndicatorListAdapter() {
        IndicatorAdapter adapter = new IndicatorAdapter(this);
        recyclerView.setAdapter(adapter);

        LiveData<PagedList<Indicator>> liveData = Sdk.d2().indicatorModule()
                .indicators()
                .orderByDisplayName(RepositoryScope.OrderByDirection.ASC)
                .getPaged(5);
        liveData.observe(this,indicators -> adapter.submitList(indicators));
    }
}