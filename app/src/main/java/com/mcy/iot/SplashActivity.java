package com.mcy.iot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.mcy.iot.base.app.Application;
import com.mcy.iot.base.baseEntity.ResponseEntity;
import com.mcy.iot.base.rxjava.Disposables;
import com.mcy.iot.base.user.User;
import com.mcy.iot.base.user.UserService;
import com.mcy.iot.databinding.ActivitySplashBinding;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.mcy.iot.base.baseEntity.ResponseEntity.SUCCESS_CODE;
import static com.mcy.iot.base.user.User.LastSignInAccount;
import static com.mcy.iot.base.user.User.LastSignInPassword;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;

    private boolean isOk = true;

    private Disposables disposables = new Disposables();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

        getCurrentUser();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                if (isOk) {
                    intent.setClass(SplashActivity.this, MainActivity.class);
                } else {
                    intent.setClass(SplashActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 2000);
    }

    @Override
    protected void onDestroy() {
        disposables.disposeAll();
        super.onDestroy();
    }

    /**
     * 获取本地用户
     */
    private void getCurrentUser() {
        SharedPreferences sharedPreferences = Application.sharedPreferences();
        String username = sharedPreferences.getString(LastSignInAccount, "");
        String password = sharedPreferences.getString(LastSignInPassword, "");

        if (TextUtils.isEmpty(username)) {
            isOk = false;
            return;
        }

        login(username, password);
    }

    private void login(String username, String password) {
        disposables.add(UserService.login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseEntity<User>>() {
                    @Override
                    public void accept(ResponseEntity<User> userResponseEntity) throws Exception {
                        Log.i("", "");
                        if (userResponseEntity.getStatus() == SUCCESS_CODE) {
                            isOk = true;
                            User.updateCurrentUser(userResponseEntity.getData());
                        } else {
                            isOk = false;
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        isOk = false;
                    }
                }));
    }
}
