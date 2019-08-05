package com.jhhscm.platform.tool;

import android.content.Context;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;


/**
 * Created by Administrator on 2018/8/6.
 */

public class NetWorkToast {
    public void setNetWorkErr(Context cn, Throwable e){
        if (e instanceof SocketTimeoutException) {
            ToastUtil.show(cn, "连接超时");
        }
        if (e instanceof ConnectException) {
            ToastUtil.show(cn, "服务器连接异常");
        }
        if(e instanceof HttpException){
            ToastUtil.show(cn, "网络异常");
        }
        if(e instanceof JSONException){
            ToastUtil.show(cn, "数据异常");
        }
    }
}
