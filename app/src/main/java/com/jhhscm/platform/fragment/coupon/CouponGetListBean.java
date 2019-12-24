package com.jhhscm.platform.fragment.coupon;

import java.util.List;

public class CouponGetListBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * isGet : 0
         * code : 20191030151740001636663019
         * min : 0.0
         * name : 12
         * limit : 0
         * discount : 22.0
         * startTime : 2019-10-30 00:00:00
         * endTime : 2019-10-31 00:00:00
         * type : 1
         * desc : 44
         * status : 0
         */

        private String isGetAll;
        private String isGet;
        private String code;
        private double min;
        private String name;
        private int limit;
        private double discount;
        private String startTime;
        private String endTime;
        private int type;
        private String desc;
        private int status;

        public String getIsGetAll() {
            return isGetAll;
        }

        public void setIsGetAll(String isGetAll) {
            this.isGetAll = isGetAll;
        }

        public String getIsGet() {
            return isGet;
        }

        public void setIsGet(String isGet) {
            this.isGet = isGet;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public double getMin() {
            return min;
        }

        public void setMin(double min) {
            this.min = min;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public double getDiscount() {
            return discount;
        }

        public void setDiscount(double discount) {
            this.discount = discount;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
