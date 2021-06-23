package com.example.android.dhis2explorer.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
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
import com.example.android.dhis2explorer.ui.dataSet.DataSetHomeActivity;
import com.example.android.dhis2explorer.ui.program.ProgramHomeActivity;
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

    private CardView programCardView;
    private CardView dataSetCardView;

    private TextView syncStatusText;
    private ProgressBar progressBar;

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
        greeting.setText(String.format("Hi %s!", user.displayName()));

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
        syncMetadataButton = findViewById(R.id.syncMetadataButton);
        progressBar = findViewById(R.id.syncProgressBar);
        dataSetCardView = findViewById(R.id.dataSetListCard);
        programCardView = findViewById(R.id.programListCard);

        programCardView.setOnClickListener(view -> {
            if (!isSyncing) {
                int programCount = SyncStatusHelper.programCount();
                if (programCount > 0) {
                    ActivityStarter.startActivity(this, ProgramHomeActivity.getProgramHomeActivityIntent(this), false);
                } else {
                    Snackbar.make(view, "You have no program at moment try to sync first", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }

            }
        });

        dataSetCardView.setOnClickListener(view -> {
            if (!isSyncing) {
                int dataSetCount = SyncStatusHelper.dataSetCount();
                if (dataSetCount > 0) {
                    ActivityStarter.startActivity(this, DataSetHomeActivity.getDataSetHomeActivityIntent(this), false);
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

    private void startMetadataSync() {
        syncStatusText = findViewById(R.id.notificator);
        syncStatusText.setText(R.string.syncing_metadata);
        setSyncing();
        syncMetadata();
    }

    private void setSyncing() {
        isSyncing = true;
        progressBar.setVisibility(View.VISIBLE);
        syncStatusText.setVisibility(View.VISIBLE);
        updateSyncDataAndButtons();
    }

    private void setSyncingFinished() {
        isSyncing = false;
        progressBar.setVisibility(View.INVISIBLE);
        syncStatusText.setVisibility(View.INVISIBLE);
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

    private void updateSyncDataAndButtons() {
        disableAllButtons();

        int programCount = SyncStatusHelper.programCount();
        int dataSetCount = SyncStatusHelper.dataSetCount();

        enablePossibleButtons(programCount + dataSetCount > 0);

        TextView downloadedProgramsText = findViewById(R.id.programListCount);
        TextView downloadedDataSetsText = findViewById(R.id.dataSetListCount);
        downloadedProgramsText.setText(MessageFormat.format("{0}", programCount));
        downloadedDataSetsText.setText(MessageFormat.format("{0}", dataSetCount));
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
        firstName.setText(user.firstName());
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


    private void wipeData() {
        compositeDisposable.add(
                Observable.fromCallable(() -> Sdk.d2().wipeModule().wipeData())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnError(Throwable::printStackTrace)
                        .doOnComplete(this::setSyncingFinished)
                        .subscribe());
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
