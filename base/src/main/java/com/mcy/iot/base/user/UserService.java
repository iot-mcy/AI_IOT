package com.mcy.iot.base.user;

import com.alibaba.fastjson.JSONObject;
import com.mcy.iot.base.baseEntity.ResponseEntity;
import com.mcy.iot.base.service.Server;

import io.reactivex.Flowable;

public class UserService {
    public UserService() {
    }

    private static UserServiceInterface userServiceInterface() {
        synchronized (UserServiceInterface.class) {
            return Server.getService(UserServiceInterface.class);
        }
    }

    public static Flowable<ResponseEntity<User>> login(String username, String password) {
        JSONObject object = new JSONObject();
        object.put("username", username);
        object.put("password", password);
        return userServiceInterface().login(object);
    }

    public static Flowable<ResponseEntity<User>> register(String username, String password) {
        JSONObject object = new JSONObject();
        object.put("username", username);
        object.put("password", password);
        return userServiceInterface().register(object);
    }

    public static Flowable<String> logout() {
        return userServiceInterface().logout();
    }
}
