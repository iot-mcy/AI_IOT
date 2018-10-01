package com.mcy.iot;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.mcy.iot.base.utils.ToastManager;

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "Debug";
    protected int pageNumber = 1;
    protected int PAGE_SIZE = 10;

    protected Intent intent;

    private MaterialDialog materialDialog;

    // Whether there is a Wi-Fi connection.
    private static boolean wifiConnected = false;
    // Whether there is a mobile connection.
    private static boolean mobileConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        checkNetworkConnection();
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
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseActivity.super.onBackPressed();
            }
        });
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

    /**
     * Check whether the device is connected, and if so, whether the connection
     * is wifi or mobile (it could be something else).
     */
    private void checkNetworkConnection() {
        // BEGIN_INCLUDE(connect)
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()) {
            wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            if (wifiConnected) {
                ToastManager.showToastShort(this, getString(R.string.wifi_connection));
                Log.i(TAG, getString(R.string.wifi_connection));
            } else if (mobileConnected) {
                ToastManager.showToastShort(this, getString(R.string.mobile_connection));
                Log.i(TAG, getString(R.string.mobile_connection));
            }
        } else {
            ToastManager.showToastShort(this, getString(R.string.no_wifi_or_mobile));
            Log.i(TAG, getString(R.string.no_wifi_or_mobile));
        }
        // END_INCLUDE(connect)
    }

}
