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
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DeviceServiceInterface {

    @POST("/device.svc/GetDeviceListByUserID/{UserID}")
    Flowable<ResponseEntity<List<Device>>> getDeviceListByUserID(@Path("UserID") int UserID);

    /**
     * 设备注册
     *
     * @param register_code
     * @param object
     * @return
     */
    @POST("/register_de")
    Observable<String> registerDevice(@Query("register_code") String register_code, @Body JSONObject object);

    /**
     * 更新设备
     *
     * @param deviceID
     * @return
     */
    @PUT("/devices/{deviceID}")
    Observable<String> updateDevice(@Path("deviceID") int deviceID);

    /**
     * 获取单个设备信息
     */
    @GET("/devices/{device_id}")
    Observable<String> getDevice(@Path("device_id") int device_id);

    /**
     * 获取多个设备信息
     */
    @GET("/devices")
    Observable<OneNETDevices> getDevices(@Query("device_id") String deviceIDs);
}
