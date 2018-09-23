package com.mcy.iot;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.mcy.iot.base.baseEntity.ResponseEntity;
import com.mcy.iot.base.rxjava.Disposables;
import com.mcy.iot.base.user.User;
import com.mcy.iot.base.user.UserService;
import com.mcy.iot.databinding.ActivityRegisterBinding;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.mcy.iot.base.baseEntity.ResponseEntity.SUCCESS_CODE;

public class RegisterActivity extends BaseActivity {

    private ActivityRegisterBinding binding;

    private Disposables disposables = new Disposables();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        setToolbar(binding.toolbar);

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

        String confirmPassword = binding.etConfirmPassword.getText().toString().trim();
        if (TextUtils.isEmpty(confirmPassword)) {
            showHintSnackbar(binding.etConfirmPassword, "请输入确认密码");
            return false;
        }

        if (confirmPassword.length() < 6 || confirmPassword.length() > 20) {
            showHintSnackbar(binding.etConfirmPassword, "确认密码需要6-20个字节");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            showHintSnackbar(binding.etConfirmPassword, "密码不一致");
            return false;
        }

        return true;
    }

    private void submit() {
        showLoadDialog("正在注册...");
        String username = binding.etPhone.getText().toString();
        String password = binding.etPassword.getText().toString();
        disposables.add(UserService.register(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseEntity<User>>() {
                    @Override
                    public void accept(ResponseEntity<User> userResponseEntity) throws Exception {
                        if (userResponseEntity.getStatus() == SUCCESS_CODE) {
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

    /**
     * 注册
     */
    public void setOnClickBySignUp(View view) {
        if (verify()) {
            submit();
        }
    }
}
