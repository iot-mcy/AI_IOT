package com.mcy.iot.device;

public class Device {
    /**
     * deviceID : 17338064
     * deviceType : 1
     * apikey : 8Fk64HaOFTRU8otR7Yka4Qrxfj0=
     * deviceName : 台灯
     * user_id : 1000
     */

    private int deviceID;
    private int deviceType;
    private String apikey;
    private String deviceName;
    private int user_id;

    public int getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(int deviceID) {
        this.deviceID = deviceID;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
