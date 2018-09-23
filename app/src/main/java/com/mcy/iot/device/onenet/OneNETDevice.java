package com.mcy.iot.device.onenet;

import java.io.Serializable;
import java.util.List;

public class OneNETDevice implements Serializable {
    /**
     * private : true
     * protocol : EDP
     * create_time : 2017-09-22 17:12:52
     * online : true
     * location : {"lat":22.862212105942,"lon":108.27491994449}
     * id : 17338064
     * auth_info : mcy20170922
     * title : 台灯
     * tags : []
     * desc : 台灯插座
     */

    private boolean privateX;
    private String protocol;
    private String create_time;
    private boolean online;
    private LocationBean location;
    private String id;
    private String auth_info;
    private String title;
    private String desc;
    private List<?> tags;

    public boolean isPrivateX() {
        return privateX;
    }

    public void setPrivateX(boolean privateX) {
        this.privateX = privateX;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public LocationBean getLocation() {
        return location;
    }

    public void setLocation(LocationBean location) {
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuth_info() {
        return auth_info;
    }

    public void setAuth_info(String auth_info) {
        this.auth_info = auth_info;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<?> getTags() {
        return tags;
    }

    public void setTags(List<?> tags) {
        this.tags = tags;
    }

    public static class LocationBean implements Serializable {
        /**
         * lat : 22.862212105942
         * lon : 108.27491994449
         */

        private double lat;
        private double lon;

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }
    }
}
