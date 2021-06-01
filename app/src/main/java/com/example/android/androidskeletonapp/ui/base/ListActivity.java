package com.example.android.androidskeletonapp.ui.base;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

public abstract class ListActivity extends AppCompatActivity {

    protected RecyclerView recyclerView;

    protected void setUp(int contentViewId, int toolbarId) {
        setContentView(contentViewId);
        Toolbar toolbar = findViewById(toolbarId);
        setSupportActionBar(toolbar);
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
