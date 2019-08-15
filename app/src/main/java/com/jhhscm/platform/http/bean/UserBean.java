package com.jhhscm.platform.http.bean;

public class UserBean {

    /**
     * data : {"mobile":"15927112992","nickname":"","userCode":"4000000034777111","status":0}
     * timestamp : 1563865175473
     */

    private DataBean data;
    private String timestamp;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public static class DataBean {
        /**
         * mobile : 15927112992
         * nickname :
         * userCode : 4000000034777111
         * status : 0
         */

        private String mobile;
        private String nickname;
        private String userCode;
        private String avatar;
        private int status;
        private String is_check;//是否实名 0：未认证 1是已认证

        public String getIs_check() {
            return is_check;
        }

        public void setIs_check(String is_check) {
            this.is_check = is_check;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
}
