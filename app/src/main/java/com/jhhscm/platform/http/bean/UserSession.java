package com.jhhscm.platform.http.bean;

public class UserSession {

    private String token;
    private String mobile;
    private String expire;
    private String timestamp;
    private String nickname;
    private String userCode;
    private String status;
    private String avatar;
    private String is_check;//是否实名 0：未认证 1是已认证
    private int is_bus;//是否商户 0 不是 1是
    private String user_level;// "0"普通会员 "1"高级会员  "2" VIP会员

    public String getUser_level() {
        return user_level;
    }

    public void setUser_level(String user_level) {
        this.user_level = user_level;
    }
    public int getIs_bus() {
        return is_bus;
    }

    public void setIs_bus(int is_bus) {
        this.is_bus = is_bus;
    }

    public String getIs_check() {
        return is_check;
    }

    public void setIs_check(String is_check) {
        this.is_check = is_check;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
