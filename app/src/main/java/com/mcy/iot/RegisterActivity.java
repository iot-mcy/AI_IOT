package com.mcy.iot;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.mcy.iot.databinding.ActivityRegisterBinding;

public class RegisterActivity extends BaseActivity {

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        setToolbar(binding.toolbar);

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
//        User user = new User();
////        user.setUsername(binding.etPhone.getText().toString().trim());
////        user.setPassword(binding.etConfirmPassword.getText().toString().trim());
////        addSubscription(user.signUp(new SaveListener<User>() {
////            @Override
////            public void done(User user, BmobException e) {
////                dismissLoadDialog();
////                if (e == null) {
////                    showHintSnackbar(binding.getRoot(), "注册成功");
////                    finish();
////                } else {
////                    showHintSnackbar(binding.getRoot(), "注册失败");
////                }
////            }
////        }));
    }

    /**
     * 查找用户
     */
    private void findUser() {
//        showLoadDialog("正在注册...");
//        BmobQuery<User> query = new BmobQuery<>();
//        query.addWhereEqualTo("username", binding.etPhone.getText().toString().trim());
//        addSubscription(query.findObjects(new FindListener<User>() {
//            @Override
//            public void done(List<User> list, BmobException e) {
//                if (e == null) {
//                    showHintSnackbar(binding.etPhone, "该用户名已经存在");
//                    dismissLoadDialog();
//                } else {
//                    submit();
//                }
//            }
//        }));
    }

    /**
     * 注册
     */
    public void setOnClickBySignUp(View view) {
        if (verify()) {
            findUser();
        }
    }
}
