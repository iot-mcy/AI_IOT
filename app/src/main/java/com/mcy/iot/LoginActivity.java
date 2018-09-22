package com.mcy.iot;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.mcy.iot.databinding.ActivityLoginBinding;

public class LoginActivity extends BaseActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        setToolbarNotNav(binding.toolbar);
        binding.setActivity(this);

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
//            User user = new User();
//            user.setUsername(binding.etPhone.getText().toString().trim());
//            user.setPassword(binding.etPassword.getText().toString().trim());
//            user.login(new SaveListener<User>() {
//                @Override
//                public void done(User user, BmobException e) {
//                    dismissLoadDialog();
//                    if (e == null) {
//                        showHintSnackbar(binding.getRoot(), "登录成功");
//                        startActivity(new Intent(view.getContext(), MainActivity.class));
//                        finish();
//                    } else {
//                        showHintSnackbar(binding.getRoot(), e.getErrorCode() == 101 ? "用户名或密码错误" : e.toString());
//                    }
//                }
//            });
        }
    }

    /**
     * 注册
     */
    public void setOnClickByRegister(View view) {
        startActivity(new Intent(view.getContext(), RegisterActivity.class));
    }
}
