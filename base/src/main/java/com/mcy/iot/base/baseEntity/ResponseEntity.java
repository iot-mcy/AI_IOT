package com.mcy.iot.base.baseEntity;

public class ResponseEntity<T> {

    public static final int SUCCESS_CODE = 1;
    public static final int ERROR_CODE = 0;
    public static final String SUCCESS_STR = "成功";
    public static final String ERROR_STR = "失败";

    private int status = -1;
    private T data;
    private String msg;

    public ResponseEntity() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
