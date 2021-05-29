package com.example.android.androidskeletonapp.ui.base;

import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

public class ListActivity extends AppCompatActivity {

    protected  void setUp(int contentViewId, int toolBarId){
        setContentView(contentViewId);
        //@TODO set toold bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
