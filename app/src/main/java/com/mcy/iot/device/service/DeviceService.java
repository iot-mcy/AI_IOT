package com.mcy.iot.device.service;

import com.alibaba.fastjson.JSONObject;
import com.mcy.iot.base.baseEntity.ResponseEntity;
import com.mcy.iot.base.service.OneNetServer;
import com.mcy.iot.base.service.Server;
import com.mcy.iot.device.Device;
import com.mcy.iot.device.onenet.OneNETDevices;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

import static com.mcy.iot.base.app.Application.REGISTER_CODE;

public class DeviceService {
    public DeviceService() {
    }

    /**
     * 连私有服务
     *
     * @return
     */
    private static DeviceServiceInterface serviceInterface() {
        synchronized (DeviceServiceInterface.class) {
            return Server.getService(DeviceServiceInterface.class);
        }
    }

    /**
     * 连OneNet服务
     *
     * @return
     */
    private static DeviceServiceInterface oneNetServiceInterface() {
        synchronized (DeviceServiceInterface.class) {
            return OneNetServer.getService(DeviceServiceInterface.class);
        }
    }

    /**
     * 从私有云中获取用户的设备列表
     *
     * @param userID
     * @return
     */
    public static Flowable<ResponseEntity<List<Device>>> getDeviceListByUserID(int userID) {
        return serviceInterface().getDeviceListByUserID(userID);
    }

    /**
     * 注册新设备
     *
     * @param sn    用户自定义产品序列号，最大长度512
     * @param title 设备名称，最大长度32，若为空，平台会默认分配设备名为“auto_register”
     * @return
     */
    public static Observable<String> registerDevice(String sn, String title) {
        JSONObject object = new JSONObject();
        object.put("sn", sn);
        object.put("title", title);
        return oneNetServiceInterface().registerDevice(REGISTER_CODE, object);
    }

    public static Observable<OneNETDevices> getDevices(String deviceIDs) {
        return oneNetServiceInterface().getDevices(deviceIDs);
    }
}
