package com.jhhscm.platform.http.bean;

/**
 * Created by Administrator on 2018/8/31/031.
 */

public class BaseErrorInfo {

    private String msg;
    private int code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
