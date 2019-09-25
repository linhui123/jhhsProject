package com.jhhscm.platform.bean;

public class PostLoginBean {

    /**
     * appid : {{appid}} "336abf9e97cd4276bf8aecde9d32ed99"
     * content : {{_content}}
     * sign : {{_sign}}
     */

    private String appid;
    private String content;
    private String sign;
    private String token;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
