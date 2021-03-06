package com.example.android.dhis2explorer.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.android.dhis2explorer.R;
import com.example.android.dhis2explorer.data.Sdk;
import com.example.android.dhis2explorer.data.service.ActivityStarter;
import com.example.android.dhis2explorer.data.service.SyncStatusHelper;
import com.example.android.dhis2explorer.ui.dataElement.DataElementHomeActivity;
import com.example.android.dhis2explorer.ui.dataSet.DataSetHomeActivity;
import com.example.android.dhis2explorer.ui.indicator.IndicatorHomeActivity;
import com.example.android.dhis2explorer.ui.optionSet.OptionSetHomeActivity;
import com.example.android.dhis2explorer.ui.options.OptionHomeActivity;
import com.example.android.dhis2explorer.ui.program.ProgramHomeActivity;
import com.example.android.dhis2explorer.ui.programIndicator.ProgramIndicatorHomeActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.hisp.dhis.android.core.arch.call.D2Progress;
import org.hisp.dhis.android.core.user.User;

import java.text.MessageFormat;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.android.dhis2explorer.data.service.LogOutService.logOut;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private CompositeDisposable compositeDisposable;

    private FloatingActionButton syncMetadataButton;

    private TextView syncStatusText;
    private RelativeLayout syncContainer;

    private boolean isSyncing = false;

    public static Intent getMainActivityIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        compositeDisposable = new CompositeDisposable();

        User user = getUser();

        TextView greeting = findViewById(R.id.welcomeNote);
        greeting.setText(String.format("Hello %s", user.displayName()));

        inflateMainView();
        createNavigationView(user);
    }


    @Override
    protected void onResume() {
        super.onResume();
        updateSyncDataAndButtons();
    }

    private User getUser() {
        return Sdk.d2().userModule().user().blockingGet();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    private void inflateMainView() {
        syncStatusText = findViewById(R.id.notificator);
        syncContainer = findViewById(R.id.syncContainer);
        syncMetadataButton = findViewById(R.id.syncMetadataButton);
        CardView dataSetCardView = findViewById(R.id.dataSetListCard);
        CardView programCardView = findViewById(R.id.programListCard);
        CardView dataElementListCard = findViewById(R.id.dataElementListCard);
        CardView indicatorListCard = findViewById(R.id.indicatorListCard);
        CardView optionListCard = findViewById(R.id.optionListCard);
        CardView programIndicatorListCard = findViewById(R.id.programIndicatorListCard);
        CardView optionSetListCard = findViewById(R.id.optionSetListCard);
        CardView optionGroupListCard = findViewById(R.id.optionGroupListCard);

        programCardView.setOnClickListener(view -> {
            if (!isSyncing) {
                int programCount = SyncStatusHelper.programCount();
                if (programCount > 0) {
                    ActivityStarter.startActivity(this, ProgramHomeActivity.getActivityIntent(this), false);
                } else {
                    Snackbar.make(view, "You have no program at moment try to sync first", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }

            }
        });

        programIndicatorListCard.setOnClickListener(view -> {
            if (!isSyncing) {
                int programIndicatorListCount = SyncStatusHelper.programIndicatorCount();
                if (programIndicatorListCount > 0) {
                    ActivityStarter.startActivity(this, ProgramIndicatorHomeActivity.getActivityIntent(this), false);
                } else {
                    Snackbar.make(view, "You have no program indicators at moment try to sync first", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }

            }
        });
        indicatorListCard.setOnClickListener(view -> {
            if (!isSyncing) {
                int indicatorListCount = SyncStatusHelper.indicatorCount();
                if (indicatorListCount > 0) {
                    ActivityStarter.startActivity(this, IndicatorHomeActivity.getActivityIntent(this), false);
                } else {
                    Snackbar.make(view, "You have no indicators at moment try to sync first", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }

            }
        });
        optionListCard.setOnClickListener(view -> {
            if (!isSyncing) {
                int optionListCount = SyncStatusHelper.optionCount();
                if (optionListCount > 0) {
                    ActivityStarter.startActivity(this, OptionHomeActivity.getActivityIntent(this), false);
                } else {
                    Snackbar.make(view, "You have no options at moment try to sync first", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }

            }
        });

        optionSetListCard.setOnClickListener(view -> {
            if (!isSyncing) {
                int optionSetListCount = SyncStatusHelper.optionSetCount();
                if (optionSetListCount > 0) {
                     ActivityStarter.startActivity(this, OptionSetHomeActivity.getActivityIntent(this), false);
                } else {
                    Snackbar.make(view, "You have no option sets at moment try to sync first", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }

            }
        });

        optionGroupListCard.setOnClickListener(view -> {
            if (!isSyncing) {
                int optionGroupListCount = SyncStatusHelper.optionGroupCount();
                if (optionGroupListCount > 0) {
                    // ActivityStarter.startActivity(this, OptionHomeActivity.getActivityIntent(this), false);
                } else {
                    Snackbar.make(view, "You have no option groups at moment try to sync first", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }

            }
        });

        dataElementListCard.setOnClickListener(view -> {
            if (!isSyncing) {
                int dataElementCount = SyncStatusHelper.dataElementCount();
                if (dataElementCount > 0) {
                    ActivityStarter.startActivity(this, DataElementHomeActivity.getActivityIntent(this), false);
                } else {
                    Snackbar.make(view, "You have no data element at moment try to sync first", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }

            }
        });

        dataSetCardView.setOnClickListener(view -> {
            if (!isSyncing) {
                int dataSetCount = SyncStatusHelper.dataSetCount();
                if (dataSetCount > 0) {
                    ActivityStarter.startActivity(this, DataSetHomeActivity.getActivityIntent(this), false);
                } else {
                    Snackbar.make(view, "You have no data set at moment try to sync first", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }

            }
        });

        syncMetadataButton.setOnClickListener(view -> {
            Snackbar.make(view, "Syncing metadata", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            startMetadataSync();
        });
    }

    private void updateSyncDataAndButtons() {
        disableAllButtons();

        //@TODO add more metadata
        int programCount = SyncStatusHelper.programCount();
        int dataSetCount = SyncStatusHelper.dataSetCount();
        int dataElementCount = SyncStatusHelper.dataElementCount();
        int indicatorListCount = SyncStatusHelper.indicatorCount();
        int optionListCount = SyncStatusHelper.optionCount();
        int programIndicatorListCount = SyncStatusHelper.programIndicatorCount();
        int optionSetListCount = SyncStatusHelper.optionSetCount();
        int optionGroupListCount = SyncStatusHelper.optionGroupCount();

        boolean shouldEnablePossibleButtons = programCount + dataSetCount + indicatorListCount > 0;

        enablePossibleButtons(shouldEnablePossibleButtons);

        TextView downloadedProgramsText = findViewById(R.id.programListCount);
        TextView downloadedDataSetsText = findViewById(R.id.dataSetListCount);
        TextView downloadedDataElementsText = findViewById(R.id.dataElementListCount);
        TextView downloadedIndicatorsText = findViewById(R.id.indicatorListCount);
        TextView downloadedOptionsText = findViewById(R.id.optionListCount);
        TextView downloadedOptionSetsText = findViewById(R.id.optionSetListCount);
        TextView downloadedOptionGroupsText = findViewById(R.id.optionGroupListCount);
        TextView downloadedProgramIndicatorsText = findViewById(R.id.programIndicatorListCount);

        downloadedProgramsText.setText(MessageFormat.format("{0}", programCount));
        downloadedDataSetsText.setText(MessageFormat.format("{0}", dataSetCount));
        downloadedDataElementsText.setText(MessageFormat.format("{0}", dataElementCount));
        downloadedOptionsText.setText(MessageFormat.format("{0}", optionListCount));
        downloadedIndicatorsText.setText(MessageFormat.format("{0}", indicatorListCount));
        downloadedOptionGroupsText.setText(MessageFormat.format("{0}", optionGroupListCount));
        downloadedOptionSetsText.setText(MessageFormat.format("{0}", optionSetListCount));
        downloadedProgramIndicatorsText.setText(MessageFormat.format("{0}", programIndicatorListCount));
    }

    private void startMetadataSync() {
        syncStatusText.setText(R.string.syncing_metadata);
        setSyncing();
        syncMetadata();
    }

    private void setSyncing() {
        isSyncing = true;
        syncContainer.setVisibility(View.VISIBLE);
        updateSyncDataAndButtons();
    }

    private void setSyncingFinished() {
        isSyncing = false;
        syncContainer.setVisibility(View.GONE);
        updateSyncDataAndButtons();
    }

    private void disableAllButtons() {
        setEnabledButton(syncMetadataButton, false);
    }

    private void enablePossibleButtons(boolean metadataSynced) {
        if (!isSyncing) {
            setEnabledButton(syncMetadataButton, true);
        }
    }

    private void setEnabledButton(FloatingActionButton floatingActionButton, boolean enabled) {
        floatingActionButton.setEnabled(enabled);
        floatingActionButton.setAlpha(enabled ? 1.0f : 0.3f);
    }


    private void createNavigationView(User user) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navView);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);

        TextView firstName = headerView.findViewById(R.id.firstName);
        TextView email = headerView.findViewById(R.id.email);
        firstName.setText(user.displayName());
        email.setText(user.email());
    }

    private void syncMetadata() {
        compositeDisposable.add(downloadMetadata()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(Throwable::printStackTrace)
                .doOnComplete(this::setSyncingFinished)
                .subscribe());
    }

    private Observable<D2Progress> downloadMetadata() {
        return Sdk.d2().metadataModule().download();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.navExit) {
            compositeDisposable.add(logOut(this));
        }

        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
