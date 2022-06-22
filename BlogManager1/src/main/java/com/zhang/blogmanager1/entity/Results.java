package com.zhang.blogmanager1.entity;

public class Results<T> {
    boolean success;
    String msg;
    T data;

    public Results(boolean success, String msg, T data) {
        this.success = success;
        this.msg = msg;
        this.data = data;
    }

    public Results(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}
