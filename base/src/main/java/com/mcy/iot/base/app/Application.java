package com.mcy.iot.base.app;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.mcy.iot.base.user.User;

import static android.content.pm.PackageManager.GET_META_DATA;
import static android.os.Bundle.EMPTY;

public class Application extends android.app.Application {

    /**
     * 服务的IP
     */
    @NonNull
    public static final String SERVICE_HOST = "SERVICE_HOST";
    /**
     * 服务的端口
     */
    @NonNull
    public static final String SERVICE_PORT = "SERVICE_PORT";

    @NonNull
    private static Application currentApp;

    @Nullable
    private static transient Bundle meta;

    @Nullable
    private static transient SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        User.getInstance(this);
        currentApp = this;
//        initPush();
    }

    @NonNull
    public static final String getMetaString(@NonNull String key, @NonNull String defaultValue) {
        final String value = getMetaData().getString(key);
        return value == null ? defaultValue : value;
    }

    public static final int getMetaInt(@NonNull String key, int defaultValue) {
        return getMetaData().getInt(key, defaultValue);
    }

    @NonNull
    private static Bundle getMetaData() {
        if (meta == null) try {
            final PackageManager pm = currentApp.getPackageManager();
            final String packageName = currentApp.getPackageName();
            meta = pm.getApplicationInfo(packageName, GET_META_DATA).metaData;
            if (meta == null) meta = EMPTY;
        } catch (Exception | Error e) {
            Log.e("Application", "getMetaData()", e);
            return EMPTY;
        }
        return meta;
    }

    @NonNull
    public static final SharedPreferences sharedPreferences() {
        if (sharedPreferences == null) {
            return sharedPreferences = currentApp
                    .getSharedPreferences(currentApp.getPackageName(), MODE_PRIVATE);
        } else {
            return sharedPreferences;
        }
    }

    private void initPush() {
//        //初始化组件化基础库, 统计SDK/推送SDK/分享SDK都必须调用此初始化接口
//        UMConfigure.init(this, "5b98c9bbf43e4845fc000035", "Umeng", UMConfigure.DEVICE_TYPE_PHONE,
//                "bb7fc49d2609865f1ca8f2d1651aa88c");
//
//
//        PushAgent mPushAgent = PushAgent.getInstance(this);
//        //注册推送服务，每次调用register方法都会回调该接口
//        mPushAgent.register(new IUmengRegisterCallback() {
//
//            @Override
//            public void onSuccess(String deviceToken) {
//                //注册成功会返回device token
//                Log.d("", "");
//            }
//
//            @Override
//            public void onFailure(String s, String s1) {
//                Log.d("", "");
//
//            }
//        });
//
//        mPushAgent.setAlias("1000", "mcy", new UTrack.ICallBack() {
//
//            @Override
//            public void onMessage(boolean isSuccess, String message) {
//                Log.d("", "");
//            }
//
//        });
//        mPushAgent.deleteAlias("zhangsan@sina.com", ALIAS_TYPE.SINA_WEIBO, new UTrack.ICallBack() {
//
//            @Override
//            public void onMessage(boolean isSuccess, String message) {
//
//            }
//
//        });

        //使用完全自定义处理
//        mPushAgent.setPushIntentServiceClass(UmengNotificationService.class);

//        HuaWeiRegister.register(this);
    }
}
