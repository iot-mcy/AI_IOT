package com.mcy.iot;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.mcy.iot.base.app.Application;
import com.mcy.iot.base.baseEntity.ResponseEntity;
import com.mcy.iot.base.rxjava.Disposables;
import com.mcy.iot.base.user.User;
import com.mcy.iot.base.user.UserService;
import com.mcy.iot.databinding.ActivityLoginBinding;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.mcy.iot.base.baseEntity.ResponseEntity.SUCCESS_CODE;
import static com.mcy.iot.base.user.User.LastSignInAccount;
import static com.mcy.iot.base.user.User.LastSignInPassword;

public class LoginActivity extends BaseActivity {

    private ActivityLoginBinding binding;

    private Disposables disposables = new Disposables();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        setToolbarNotNav(binding.toolbar);
        binding.setActivity(this);

    }

    @Override
    protected void onDestroy() {
        disposables.disposeAll();
        super.onDestroy();
    }

    private boolean verify() {
        String userName = binding.etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            showHintSnackbar(binding.etPhone, "请输入用户名");
            return false;
        }

        if (userName.length() < 2 || userName.length() > 16) {
            showHintSnackbar(binding.etPhone, "用户名需要2-16个字节");
            return false;
        }

        String password = binding.etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            showHintSnackbar(binding.etPassword, "请输入密码");
            return false;
        }

        if (password.length() < 6 || password.length() > 20) {
            showHintSnackbar(binding.etPassword, "密码需要6-20个字节");
            return false;
        }

        return true;
    }

    /**
     * 登录
     */
    public void setOnClickByLogin(final View view) {
        if (verify()) {
            showLoadDialog("正在登录...");
            disposables.add(UserService.login(binding.etPhone.getText().toString().trim(),
                    binding.etPassword.getText().toString().trim())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ResponseEntity<User>>() {
                        @Override
                        public void accept(ResponseEntity<User> userResponseEntity) throws Exception {
                            Log.i("", "");
                            if (userResponseEntity.getStatus() == SUCCESS_CODE) {
                                User.updateCurrentUser(userResponseEntity.getData());
                                Application.sharedPreferences()
                                        .edit()
                                        .putString(LastSignInAccount, userResponseEntity.getData().getUsername())
                                        .putString(LastSignInPassword, userResponseEntity.getData().getPassword())
                                        .apply();
                                startActivity(new Intent(view.getContext(), MainActivity.class));
                                finish();
                            }
                            showHintSnackbar(binding.getRoot(), userResponseEntity.getMsg());
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            dismissLoadDialog();
                            showHintSnackbar(binding.getRoot(), throwable.getMessage());
                        }
                    }));
        }
    }

    /**
     * 注册
     */
    public void setOnClickByRegister(View view) {
        startActivity(new Intent(view.getContext(), RegisterActivity.class));
    }
}
