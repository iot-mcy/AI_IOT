package com.mcy.iot.base.service;

import com.mcy.iot.base.user.User;
import com.mcy.iot.base.utils.ProgressListener;
import com.mcy.iot.base.utils.ProgressResponseBody;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

import static com.mcy.iot.base.app.Application.SERVICE_HOST;
import static com.mcy.iot.base.app.Application.SERVICE_PORT;
import static com.mcy.iot.base.app.Application.getMetaInt;
import static com.mcy.iot.base.app.Application.getMetaString;

/**
 * 作者 mcy
 * 日期 2018/8/7 17:34
 * 请求服务
 */
public class Server {

    /**
     *
     */
    private volatile static Server sInstance;

    /**
     *
     */
    private transient Retrofit retrofit;

    /**
     * @param progressListener
     * @return
     */
    private static Server getInstance(ProgressListener progressListener) {
        if (sInstance == null) {
            synchronized (Server.class) {
                if (sInstance == null) {
                    sInstance = new Server(progressListener);
                }
            }
        }
        return sInstance;
    }

    /**
     * @param progressListener
     */
    private Server(ProgressListener progressListener) {
        retrofit = newRetrofit(progressListener);
    }

    /**
     * @param progressListener
     * @return
     */
    private Retrofit newRetrofit(ProgressListener progressListener) {
        String host = getMetaString(SERVICE_HOST, "172.16.200.192");
        int port = getMetaInt(SERVICE_PORT, 8080);
        String baseUrl = "http://" + host + ":" + port;
        String url = "http://acj3.pc6.com";
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(newClientBuilder(progressListener).build())
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * @param progressListener
     * @return
     */
    private OkHttpClient.Builder newClientBuilder(final ProgressListener progressListener) {
        long timeOut = 1000 * 60 * 60;//目前设置1个小时，防止上下传附件过大是超时，可以考虑其它办法
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(timeOut, TimeUnit.MILLISECONDS)
                .connectTimeout(timeOut, TimeUnit.MILLISECONDS)
                .writeTimeout(timeOut, TimeUnit.MILLISECONDS)
                .addInterceptor(interceptor);

        if (progressListener != null) {//用于下载监听进度
            builder.addNetworkInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                }
            });
        }
        return builder;
    }

    /**
     * 目的添加token
     */
    private Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request authorised = request.newBuilder().header("Token", User.getInstance().getToken()).build();
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
        return getInstance(null).getRetrofit().create(tClass);
    }

    /**
     * @param tClass
     * @param progressListener 用于下载监听进度
     * @param <T>
     * @return
     */
    public static <T> T getService(Class<T> tClass, ProgressListener progressListener) {
        return getInstance(progressListener).getRetrofit().create(tClass);
    }
}
