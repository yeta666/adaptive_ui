package com.adaptive.ui.domain;

/**
 * Created by yeta on 2017/4/6/006.
 * http请求返回的最外层对象
 */
public class Result {
    //请求状态
    private boolean status;
    //提示信息
    private String message;
    //数据
    private Object data;

    //构造方法
    public Result() {
    }

    public Result(boolean status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    //getter和setter方法
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
