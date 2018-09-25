package com.mcy.iot.device.service;

import com.mcy.iot.base.baseEntity.ResponseEntity;
import com.mcy.iot.base.service.OneNetServer;
import com.mcy.iot.base.service.Server;
import com.mcy.iot.device.Device;
import com.mcy.iot.device.onenet.OneNETDevices;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

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

    public static Flowable<ResponseEntity<List<Device>>> GetDeviceListByUserID(int userID) {
        return serviceInterface().GetDeviceListByUserID(userID);
    }

    public static Observable<OneNETDevices> getDevices(String deviceIDs) {
        return oneNetServiceInterface().getDevices(deviceIDs);
    }
}
