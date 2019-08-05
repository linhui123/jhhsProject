package com.jhhscm.platform.bean;

public class LogingResultBean {

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
    /**
     * expire : 2592000
     * token : 526d631e4576438eaebeb2bfdcc99e2c
     * timestamp : 1563853421801
     */

    private String expire;
    private String token;
    private String timestamp;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
