package com.example.dj.storyeditor.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.dj.storyeditor.R;

/**
 * Created by DJ on 04-09-2017.
 */

public class PreviewActivity extends AppCompatActivity {

    public static final String PREVIEW_DATA = "preview_data";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        String data = getIntent().getStringExtra(PREVIEW_DATA);
        TextView textView = (TextView) findViewById(R.id.preview_tv);
        textView.setText(data);
        setTitleWithBackPress("Preview");
    }

    public void setTitleWithBackPress(String title) {
        final ColorDrawable cd = new ColorDrawable(getResources().getColor(R.color.colorPrimary));
        ActionBar mActionBar = getSupportActionBar();

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(17, 17, 17));
        }

        if (mActionBar != null) {
            mActionBar.setHomeAsUpIndicator(R.drawable.back);
            mActionBar.setBackgroundDrawable(cd);
            mActionBar.setTitle(title);
            mActionBar.setDisplayHomeAsUpEnabled(true); //to activate back pressed on home button press
            mActionBar.setDisplayShowHomeEnabled(false); //
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}
