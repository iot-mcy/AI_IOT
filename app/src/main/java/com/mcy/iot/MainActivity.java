package com.mcy.iot;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mcy.iot.adapter.DataBindingAdapter;
import com.mcy.iot.base.baseEntity.ResponseEntity;
import com.mcy.iot.base.rxjava.Disposables;
import com.mcy.iot.base.user.User;
import com.mcy.iot.base.user.UserService;
import com.mcy.iot.databinding.ActivityMainBinding;
import com.mcy.iot.device.Device;
import com.mcy.iot.device.DeviceService;
import com.mcy.iot.device.onenet.OneNETDevice;
import com.mcy.iot.device.onenet.OneNETDevices;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        OnRefreshListener, OnRefreshLoadMoreListener {

    private ActivityMainBinding binding;
    public ObservableField<String> data = new ObservableField<>("hello world!");
    private Disposables disposables = new Disposables();
    private DataBindingAdapter<OneNETDevice> adapter = new DataBindingAdapter<>(R.layout.item_device_list_layout, BR.item);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setToolbar(binding.toolbar);
        binding.setActivity(this);

        initView();
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, binding.toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        adapter.setOnClickListener(listenerByAdapter);

        binding.navView.setNavigationItemSelectedListener(this);
        binding.refresh.setOnRefreshListener(this);
        binding.refresh.setOnLoadMoreListener(this);
        binding.refresh.autoRefresh();

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHintSnackbar(binding.getRoot(), "敬请期待");
//                startActivity(new Intent(view.getContext(), AddDeviceActivity.class));
            }
        });
    }

    /**
     * 列表点击事件
     */
    private View.OnClickListener listenerByAdapter = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // TODO: 2018/9/23
        }
    };

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
     */
    public void GetDeviceListByUserID() {
        disposables.add(DeviceService.GetDeviceListByUserID(User.getInstance().getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseEntity<List<Device>>>() {
                    @Override
                    public void accept(ResponseEntity<List<Device>> devices) throws Exception {
//                        RefreshState state = binding.refresh.getState();
                        if (devices.getStatus() == ResponseEntity.SUCCESS_CODE) {
                            getDevices(devices.getData());
                        } else {
                            binding.refresh.finishRefresh();
                            binding.refresh.finishLoadMore();
                        }
//                        showHintSnackbar(binding.getRoot(), devices.getMsg());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        binding.refresh.finishRefresh();
                        binding.refresh.finishLoadMore();
                        showHintSnackbar(binding.getRoot(), throwable.getMessage());
                    }
                }));
    }

    /**
     * 这里是从OneNET平台上取回设备的实时数据
     */
    private void getDevices(List<Device> list) {
        StringBuilder devices = new StringBuilder();
        for (Device device : list) {
            devices.append(device.getDeviceID() + ",");
        }
        String devs = devices.toString().substring(0, devices.toString().length() - 1);
        disposables.add(DeviceService.getDevices(devs)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<OneNETDevices>() {
                    @Override
                    public void accept(OneNETDevices object) throws Exception {
                        binding.refresh.finishRefresh();
                        binding.refresh.finishLoadMore();
                        if (object != null) {
                            if (object.getErrno() == 0) {
                                setDevices(object);
                            } else {
                                showHintSnackbar(binding.getRoot(), object.getError());
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        binding.refresh.finishRefresh();
                        binding.refresh.finishLoadMore();
                    }
                }));
    }

    /**
     * 绑定数据
     */
    private void setDevices(OneNETDevices devices) {
        List<OneNETDevice> deviceList = devices.getData().getDevices();
        if (deviceList.size() == 0) {
            showHintSnackbar(binding.getRoot(), "您还没有设备，请添加后操作");
            return;
        }

        if (pageNumber == 1) {
            adapter.clearAll();
        }
        adapter.addAll(deviceList);
        binding.refresh.finishRefresh();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        showHintSnackbar(binding.getRoot(), "敬请期待");
        return true;
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (PAGE_SIZE <= adapter.getListSize()) {
            GetDeviceListByUserID();
        } else {
            binding.refresh.finishLoadMore();
            showHintSnackbar(binding.getRoot(), "设备已全部加载");
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNumber = 1;
        GetDeviceListByUserID();
    }
}
