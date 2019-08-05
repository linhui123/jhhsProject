package com.jhhscm.platform.fragment.sale;

public class FindOrderBean {

    /**
     * order : {"id":206,"orderCode":"201908018000000408337955","userCode":"1000000330781973","orderStatus":101,"consignee":"测试","mobile":"15927112992","address":"111 详细地址","message":"","goodsPrice":0,"freightPrice":0,"couponPrice":0,"integralPrice":0,"grouponPrice":0,"orderPrice":0,"actualPrice":0,"comments":0,"endTime":"2019-08-02 15:47:08","addTime":"2019-08-01 15:47:08","updateTime":"2019-08-01 15:47:08","deleted":0}
     * timestamp : 1564652499389
     */

    private OrderBean order;
    private String timestamp;

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public static class OrderBean {
        /**
         * id : 206
         * orderCode : 201908018000000408337955
         * userCode : 1000000330781973
         * orderStatus : 101
         * consignee : 测试
         * mobile : 15927112992
         * address : 111 详细地址
         * message :
         * goodsPrice : 0.0
         * freightPrice : 0.0
         * couponPrice : 0.0
         * integralPrice : 0.0
         * grouponPrice : 0.0
         * orderPrice : 0.0
         * actualPrice : 0.0
         * comments : 0
         * endTime : 2019-08-02 15:47:08
         * addTime : 2019-08-01 15:47:08
         * updateTime : 2019-08-01 15:47:08
         * deleted : 0
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
        private double couponPrice;
        private double integralPrice;
        private double grouponPrice;
        private double orderPrice;
        private double actualPrice;
        private String comments;
        private String endTime;
        private String addTime;
        private String updateTime;
        private String deleted;

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

        public double getCouponPrice() {
            return couponPrice;
        }

        public void setCouponPrice(double couponPrice) {
            this.couponPrice = couponPrice;
        }

        public double getIntegralPrice() {
            return integralPrice;
        }

        public void setIntegralPrice(double integralPrice) {
            this.integralPrice = integralPrice;
        }

        public double getGrouponPrice() {
            return grouponPrice;
        }

        public void setGrouponPrice(double grouponPrice) {
            this.grouponPrice = grouponPrice;
        }

        public double getOrderPrice() {
            return orderPrice;
        }

        public void setOrderPrice(double orderPrice) {
            this.orderPrice = orderPrice;
        }

        public double getActualPrice() {
            return actualPrice;
        }

        public void setActualPrice(double actualPrice) {
            this.actualPrice = actualPrice;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
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

        public String getDeleted() {
            return deleted;
        }

        public void setDeleted(String deleted) {
            this.deleted = deleted;
        }
    }
}
