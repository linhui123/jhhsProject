package com.jhhscm.platform.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/6/19.
 */

public class PayReusltBean {

//{"sign":"4DA8B5300D55AEADADC4BAD60A18DC0D","package":"Sign\u003dWXPay","partnerid":"1486916432","appid":"wxef67cd9d70bf047d","timestamp":"1502098360","noncestr":"1732404364","prepayid":"wx201708071732289324d306f40806153910"}
    /**
     * flag : true
     * obj : {"sign":"7172F8BADE5B59250948C64E8C58E250","timestamp":"1502093240","noncestr":"1607049104","partnerid":"1486916432","prepayid":"wx20170807160656556da0196c0229992254","package":"Sign=WXPay","appid":"wxef67cd9d70bf047d","driverLineId":"160"}
     * total : 0
     * msg : 操作成功
     * code : 1
     */
    private String flag;
    private ObjBean obj;
    private String total;
    private String msg;
    private String code;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class ObjBean {
        /**
         * sign : 7172F8BADE5B59250948C64E8C58E250
         * timestamp : 1502093240
         * noncestr : 1607049104
         * partnerid : 1486916432
         * prepayid : wx20170807160656556da0196c0229992254
         * package : Sign=WXPay
         * appid : wxef67cd9d70bf047d
         * driverLineId : 160
         */

        private String sign;
        private String timestamp;
        private String noncestr;
        private String partnerid;
        private String prepayid;
        @SerializedName("package")
        private String packageX;
        private String appid;
        private String driverLineId;

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getDriverLineId() {
            return driverLineId;
        }

        public void setDriverLineId(String driverLineId) {
            this.driverLineId = driverLineId;
        }
    }
}
