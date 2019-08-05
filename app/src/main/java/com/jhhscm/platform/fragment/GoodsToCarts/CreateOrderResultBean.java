package com.jhhscm.platform.fragment.GoodsToCarts;

import java.io.Serializable;

public class CreateOrderResultBean implements Serializable{

    /**
     * data : {"id":29,"orderCode":"201907314000001278964379","userCode":"1000000330781973","orderStatus":101,"consignee":"测试","mobile":"15927112992","address":"111 详细地址","message":"","goodsPrice":25,"freightPrice":3,"couponPrice":0,"integralPrice":0,"grouponPrice":0,"orderPrice":25,"actualPrice":0,"addTime":"2019-07-31 13:41:13","updateTime":"2019-07-31 13:41:13"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * id : 29
         * orderCode : 201907314000001278964379
         * userCode : 1000000330781973
         * orderStatus : 101
         * consignee : 测试
         * mobile : 15927112992
         * address : 111 详细地址
         * message :
         * goodsPrice : 25.0
         * freightPrice : 3.0
         * couponPrice : 0
         * integralPrice : 0
         * grouponPrice : 0
         * orderPrice : 25.0
         * actualPrice : 0
         * addTime : 2019-07-31 13:41:13
         * updateTime : 2019-07-31 13:41:13
         */

        private String id;
        private String orderCode;
        private String userCode;
        private String orderStatus;
        private String consignee;
        private String mobile;
        private String address;
        private String message;
        private double goodsPrice;
        private double freightPrice;
        private String couponPrice;
        private String integralPrice;
        private String grouponPrice;
        private double orderPrice;
        private String actualPrice;
        private String addTime;
        private String updateTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public String getUserCode() {
            return userCode;
        }

        public void setUserCode(String userCode) {
            this.userCode = userCode;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getConsignee() {
            return consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public double getGoodsPrice() {
            return goodsPrice;
        }

        public void setGoodsPrice(double goodsPrice) {
            this.goodsPrice = goodsPrice;
        }

        public double getFreightPrice() {
            return freightPrice;
        }

        public void setFreightPrice(double freightPrice) {
            this.freightPrice = freightPrice;
        }

        public String getCouponPrice() {
            return couponPrice;
        }

        public void setCouponPrice(String couponPrice) {
            this.couponPrice = couponPrice;
        }

        public String getIntegralPrice() {
            return integralPrice;
        }

        public void setIntegralPrice(String integralPrice) {
            this.integralPrice = integralPrice;
        }

        public String getGrouponPrice() {
            return grouponPrice;
        }

        public void setGrouponPrice(String grouponPrice) {
            this.grouponPrice = grouponPrice;
        }

        public double getOrderPrice() {
            return orderPrice;
        }

        public void setOrderPrice(double orderPrice) {
            this.orderPrice = orderPrice;
        }

        public String getActualPrice() {
            return actualPrice;
        }

        public void setActualPrice(String actualPrice) {
            this.actualPrice = actualPrice;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
