package com.mcy.iot.base.AppUpdate;

import com.mcy.iot.base.service.DownloadServer;
import com.mcy.iot.base.utils.ProgressListener;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * 下载apk的服务类
 */
public class AppService {
    public AppService() {
    }

    /**
     * @param progressListener
     * @return
     */
    private static AppServiceInterface serviceInterface(ProgressListener progressListener) {
        synchronized (AppServiceInterface.class) {
            return DownloadServer.getService(AppServiceInterface.class, progressListener);
        }
    }

    /**
     * @param path
     * @param progressListener
     * @return
     */
    public static Call<ResponseBody> downloadApk(String path, ProgressListener progressListener) {
        return serviceInterface(progressListener).downloadApk(path);
    }
}
