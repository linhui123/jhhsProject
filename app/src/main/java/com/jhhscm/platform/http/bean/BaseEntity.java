package com.jhhscm.platform.http.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/9.
 * 控制数据格式 这个根据需求更改
 *
 *
 */

public class BaseEntity<T>  implements Serializable {

    /**
     * code : 200
     * message : ok
     * sign : 91E9FE786FAD50BE4B0365B8EA15565A
     * content : ChifNoTlB8vo8j/9x39LED/Dgybv0SzzykuHetYLMJ0YR53fLUyvHBqFV4J8WUidLC9zZDWuN9WNW0toqfxkPabIokhIbcquYzAoqr33KduxF+S+Zfmjn51khrbpiVjr
     */

    private String code;
    private String message;
    private String sign;
    private String content;
    private T data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
