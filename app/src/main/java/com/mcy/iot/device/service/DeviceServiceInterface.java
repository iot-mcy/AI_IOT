package com.mcy.iot.device.service;

import com.alibaba.fastjson.JSONObject;
import com.mcy.iot.base.baseEntity.ResponseEntity;
import com.mcy.iot.device.Device;
import com.mcy.iot.device.onenet.OneNETDevices;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DeviceServiceInterface {

    @POST("/device.svc/GetDeviceListByUserID/{UserID}")
    Flowable<ResponseEntity<List<Device>>> GetDeviceListByUserID(@Path("UserID") int UserID);

    /**
     * 设备注册
     */
    @POST("/register_de")
    Observable<String> RegisterDevice(@Query("register_code") String register_code, @Body JSONObject object);

    /**
     * 获取单个设备信息
     */
    @GET("/devices/{device_id}")
    Observable<String> getDevice(@Path("device_id") String device_id);

    /**
     * 获取多个设备信息
     */
    @GET("/devices")
    Observable<OneNETDevices> getDevices(@Query("device_id") String deviceIDs);
}
