package com.mcy.iot.device.onenet;

import java.io.Serializable;
import java.util.List;

public class OneNETDevices implements Serializable {

    /**
     * errno : 0
     * data : {"per_page":10,"devices":[{"private":true,"protocol":"EDP","create_time":"2017-09-22 17:12:52","online":true,"location":{"lat":22.862212105942,"lon":108.27491994449},"id":"17338064","auth_info":"mcy20170922","title":"台灯","tags":[],"desc":"台灯插座"}],"total_count":1,"page":1}
     * error : succ
     */

    private int errno;
    private DataBean data;
    private String error;

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public static class DataBean implements Serializable {
        /**
         * per_page : 10
         * devices : [{"private":true,"protocol":"EDP","create_time":"2017-09-22 17:12:52","online":true,"location":{"lat":22.862212105942,"lon":108.27491994449},"id":"17338064","auth_info":"mcy20170922","title":"台灯","tags":[],"desc":"台灯插座"}]
         * total_count : 1
         * page : 1
         */

        private int per_page;
        private int total_count;
        private int page;
        private List<OneNETDevice> devices;

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public int getTotal_count() {
            return total_count;
        }

        public void setTotal_count(int total_count) {
            this.total_count = total_count;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public List<OneNETDevice> getDevices() {
            return devices;
        }

        public void setDevices(List<OneNETDevice> devices) {
            this.devices = devices;
        }

    }
}
