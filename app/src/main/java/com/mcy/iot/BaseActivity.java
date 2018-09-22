package com.mcy.iot;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "Debug";
    protected int total_count = 0;
    protected int page = 1;
    protected String per_page = "10";

    protected Intent intent;

    private MaterialDialog materialDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        dismissLoadDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public void setToolbarNotNav(Toolbar toolbar) {
        setSupportActionBar(toolbar);
    }

    public void setToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseActivity.super.onBackPressed();
            }
        });
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                BaseActivity.super.onBackPressed();
//            }
//        });
    }

    /**
     * Snackbar
     *
     * @param view
     * @param text
     */
    protected void showHintSnackbar(View view, String text) {
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * Log
     *
     * @param msg
     */
    protected void log(String msg) {
        Log.i(TAG, "===============================================================================");
        Log.i(TAG, msg);
    }

    /**
     * 加载框
     */
    protected void showLoadDialog(@NonNull String title) {
        materialDialog = new MaterialDialog.Builder(this)
                .title(title)
                .progress(true, 0)
                .progressIndeterminateStyle(true)
                .theme(Theme.LIGHT)
                .show();
    }

    /**
     * 取消加载对话框
     */
    protected void dismissLoadDialog() {
        if (materialDialog != null) {
            materialDialog.dismiss();
        }
    }

}
