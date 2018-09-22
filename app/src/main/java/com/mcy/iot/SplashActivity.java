package com.mcy.iot;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.mcy.iot.base.user.User;
import com.mcy.iot.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;

    private boolean isOk = true;

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

    /**
     * 获取本地用户
     */
    private void getCurrentUser() {
//        String username = (String) BmobUser.getObjectByKey("username");
//        String password = (String) BmobUser.getObjectByKey("password");
//
//        if (TextUtils.isEmpty(username)) {
//            isOk = false;
//        }

//        login(username, password);
    }

    private void login(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
//        user.login(new SaveListener<User>() {
//            @Override
//            public void done(User user, BmobException e) {
//                if (e == null) {
//                    isOk = true;
//                } else {
//                    isOk = false;
//                }
//            }
//        });
    }
}
