package com.mcy.iot.base.user;

import com.alibaba.fastjson.JSONObject;
import com.mcy.iot.base.baseEntity.ResponseEntity;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserServiceInterface {
    @POST("/user.svc/login")
    Flowable<ResponseEntity<User>> login(@Body JSONObject object);

    @POST("/user.svc/register")
    Flowable<ResponseEntity<User>> register(@Body JSONObject object);

    @POST("/user.svc/logout")
    Flowable<String> logout();
}
