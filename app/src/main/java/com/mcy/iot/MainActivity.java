package com.mcy.iot;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.mcy.iot.base.baseEntity.ResponseEntity;
import com.mcy.iot.base.rxjava.Disposables;
import com.mcy.iot.base.user.User;
import com.mcy.iot.base.user.UserService;
import com.mcy.iot.base.utils.ToastManager;
import com.mcy.iot.databinding.ActivityMainBinding;
import com.mcy.iot.device.DeviceService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.mcy.iot.base.baseEntity.ResponseEntity.SUCCESS_CODE;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;
    public ObservableField<String> data = new ObservableField<>("hello world!");
    private Disposables disposables = new Disposables();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);
    }

    /**
     * 登录
     *
     * @param view
     */
    public void setOnClickByLogin(View view) {
        String username = binding.etUsername.getText().toString();
        String password = binding.etPassword.getText().toString();
        if (TextUtils.isEmpty(username)) {
            ToastManager.showToastShort(this, "用户名不能为空");
            return;
        }
        disposables.add(UserService.login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseEntity<User>>() {
                    @Override
                    public void accept(ResponseEntity<User> userResponseEntity) throws Exception {
                        Log.i("", "");
                        data.set(JSON.toJSONString(userResponseEntity));
                        if (userResponseEntity.getStatus() == SUCCESS_CODE) {
                            User.updateCurrentUser(userResponseEntity.getData());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i("", "");
                        data.set(throwable.getMessage());
                    }
                }));
    }


    /**
     * 注册
     *
     * @param view
     */
    public void setOnClickByRegister(View view) {
        String username = binding.etUsername.getText().toString();
        String password = binding.etPassword.getText().toString();
        if (TextUtils.isEmpty(username)) {
            ToastManager.showToastShort(this, "用户名不能为空");
            return;
        }
        disposables.add(UserService.register(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseEntity<User>>() {
                    @Override
                    public void accept(ResponseEntity<User> userResponseEntity) throws Exception {
                        Log.i("", "");
                        data.set(JSON.toJSONString(userResponseEntity));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i("", "");
                        data.set(throwable.getMessage());
                    }
                }));
    }

    /**
     * 注销
     *
     * @param view
     */
    public void setOnClickByLogout(View view) {
        disposables.add(UserService.logout()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i("", "");
                        data.set(s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i("", "");
                        data.set(throwable.getMessage());
                    }
                }));
    }

    /**
     * 获取用户设备列表
     *
     * @param view
     */
    public void GetDeviceListByUserID(View view) {
        disposables.add(DeviceService.GetDeviceListByUserID(User.getInstance().getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i("", "");
                        data.set(s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i("", "");
                        data.set(throwable.getMessage());
                    }
                }));
    }


    /**
     * 清除log
     *
     * @param view
     */
    public void setOnClickByLog(View view) {
        data.set("");
        binding.progressBar.setProgress(0);
//        binding.btDownload.setText("下载附件");
    }
}
