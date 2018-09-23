package com.mcy.iot.base.service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

/**
 * 作者 mcy
 * 日期 2018/8/7 17:34
 * OneNet请求服务
 */
public class OneNetServer {

    /**
     *
     */
    private volatile static OneNetServer sInstance;

    /**
     *
     */
    private transient Retrofit retrofit;

    /**
     * @return
     */
    private static OneNetServer getInstance() {
        if (sInstance == null) {
            synchronized (OneNetServer.class) {
                if (sInstance == null) {
                    sInstance = new OneNetServer();
                }
            }
        }
        return sInstance;
    }

    /**
     *
     */
    private OneNetServer() {
        retrofit = newRetrofit();
    }

    /**
     * @return
     */
    private Retrofit newRetrofit() {
        String baseUrl = "http://" + "api.heclouds.com";
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(newClientBuilder().build())
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * @return
     */
    private OkHttpClient.Builder newClientBuilder() {
        long timeOut = 1000 * 60;//60秒
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(timeOut, TimeUnit.MILLISECONDS)
                .connectTimeout(timeOut, TimeUnit.MILLISECONDS)
                .writeTimeout(timeOut, TimeUnit.MILLISECONDS)
                .addInterceptor(interceptor);
        return builder;
    }

    /**
     * 目的添加token
     */
    private Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request authorised = request.newBuilder()
                    .header("api-key", "a2nx0OVwnvUWZXkYqDgHET8XexE=")
                    .build();
            return chain.proceed(authorised);
        }
    };

    /**
     * @return
     */
    private Retrofit getRetrofit() {
        return retrofit;
    }

    /**
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T getService(Class<T> tClass) {
        return getInstance().getRetrofit().create(tClass);
    }
}
