package com.jhhscm.platform.fragment.sale;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FindOrderBean {

    /**
     * order : {"id":206,"orderCode":"201908018000000408337955","userCode":"1000000330781973","orderStatus":101,"consignee":"测试","mobile":"15927112992","address":"111 详细地址","message":"","goodsPrice":0,"freightPrice":0,"couponPrice":0,"integralPrice":0,"grouponPrice":0,"orderPrice":0,"actualPrice":0,"comments":0,"endTime":"2019-08-02 15:47:08","addTime":"2019-08-01 15:47:08","updateTime":"2019-08-01 15:47:08","deleted":0}
     * timestamp : 1564652499389
     */

    private OrderBean order;
    private String timestamp;
    /**
     * goodsList : [{"id":178,"orderCode":"201908022000000668659564","goodsCode":"21","goodsName":"斗齿01","productId":21,"number":2,"price":5,"specifications":"","picUrl":"https://wajuejifiles.oss-cn-beijing.aliyuncs.com/C%3A%5CUsers%5CAdministrator%5CDesktop%5C%E5%B7%A5%E5%85%B7%E7%AE%B1%5Ctest.png","comment":0,"addTime":"2019-08-02 17:24:18","updateTime":"2019-08-02 17:24:18","deleted":0}]
     * order : {"order_status":101,"ship_channel":"顺丰物流","address":"北京市市辖区通州区 详细地址","consignee":"小崔","goods_price":10,"freight_price":6,"mobile":"15927112992","coupon_price":0,"order_text":"未付款","ship_sn":"1234568949"}
     */
    private List<GoodsListBean> goodsList;
    /**
     * data : [{"order_code":"201908053000001556354865","order_status":101,"user_code":"1000000330781973","order_price":8,"order_text":"未付款","order_num":1,"id":292,"message":"","pic_url":"https://wajuejifiles.oss-cn-beijing.aliyuncs.com/C%3A%5CUsers%5CAdministrator%5CDesktop%5C%E5%B7%A5%E5%85%B7%E7%AE%B1%5Ctest.png","order_type":1,"add_time":"2019-08-05 09:53:20","order_name":"斗齿01"}]
     * page : {"total":1,"startRow":1,"size":1,"navigateFirstPageNums":1,"prePage":0,"endRow":1,"pageSize":10,"pageNum":1,"navigateLastPageNums":1,"navigatePageNums":[1]}
     */

    private PageBean page;
    private List<DataBean> data;

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


    public List<GoodsListBean> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsListBean> goodsList) {
        this.goodsList = goodsList;
    }

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
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
        private String end_time;
        /**
         * order_status : 101
         * ship_channel : 顺丰物流
         * address : 北京市市辖区通州区 详细地址
         * consignee : 小崔
         * goods_price : 10
         * freight_price : 6
         * mobile : 15927112992
         * coupon_price : 0
         * order_text : 未付款
         * ship_sn : 1234568949
         */

        private int order_status;
        private String ship_channel;
        private int goods_price;
        private int freight_price;
        private int coupon_price;
        private String order_text;
        private String ship_sn;
        private String order_price;

        public String getOrder_price() {
            return order_price;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public void setOrder_price(String order_price) {
            this.order_price = order_price;
        }

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


        public int getOrder_status() {
            return order_status;
        }

        public void setOrder_status(int order_status) {
            this.order_status = order_status;
        }

        public String getShip_channel() {
            return ship_channel;
        }

        public void setShip_channel(String ship_channel) {
            this.ship_channel = ship_channel;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getConsignee() {
            return consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        public int getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(int goods_price) {
            this.goods_price = goods_price;
        }

        public int getFreight_price() {
            return freight_price;
        }

        public void setFreight_price(int freight_price) {
            this.freight_price = freight_price;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getCoupon_price() {
            return coupon_price;
        }

        public void setCoupon_price(int coupon_price) {
            this.coupon_price = coupon_price;
        }

        public String getOrder_text() {
            return order_text;
        }

        public void setOrder_text(String order_text) {
            this.order_text = order_text;
        }

        public String getShip_sn() {
            return ship_sn;
        }

        public void setShip_sn(String ship_sn) {
            this.ship_sn = ship_sn;
        }
    }

    public static class GoodsListBean {
        /**
         * id : 178
         * orderCode : 201908022000000668659564
         * goodsCode : 21
         * goodsName : 斗齿01
         * productId : 21
         * number : 2
         * price : 5
         * specifications :
         * picUrl : https://wajuejifiles.oss-cn-beijing.aliyuncs.com/C%3A%5CUsers%5CAdministrator%5CDesktop%5C%E5%B7%A5%E5%85%B7%E7%AE%B1%5Ctest.png
         * comment : 0
         * addTime : 2019-08-02 17:24:18
         * updateTime : 2019-08-02 17:24:18
         * deleted : 0
         */

        private int id;
        private String orderCode;
        private String goodsCode;
        private String goodsName;
        private int productId;
        private int number;
        private int price;
        private String specifications;
        private String picUrl;
        private int comment;
        private String addTime;
        private String updateTime;
        private int deleted;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public String getGoodsCode() {
            return goodsCode;
        }

        public void setGoodsCode(String goodsCode) {
            this.goodsCode = goodsCode;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getSpecifications() {
            return specifications;
        }

        public void setSpecifications(String specifications) {
            this.specifications = specifications;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public int getComment() {
            return comment;
        }

        public void setComment(int comment) {
            this.comment = comment;
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

        public int getDeleted() {
            return deleted;
        }

        public void setDeleted(int deleted) {
            this.deleted = deleted;
        }
    }

    public static class PageBean {
        /**
         * total : 1
         * startRow : 1
         * size : 1
         * navigateFirstPageNums : 1
         * prePage : 0
         * endRow : 1
         * pageSize : 10
         * pageNum : 1
         * navigateLastPageNums : 1
         * navigatePageNums : [1]
         */

        private int total;
        private int startRow;
        private int size;
        private int navigateFirstPageNums;
        private int prePage;
        private int endRow;
        private int pageSize;
        private int pageNum;
        private int navigateLastPageNums;
        private List<Integer> navigatePageNums;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getStartRow() {
            return startRow;
        }

        public void setStartRow(int startRow) {
            this.startRow = startRow;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getNavigateFirstPageNums() {
            return navigateFirstPageNums;
        }

        public void setNavigateFirstPageNums(int navigateFirstPageNums) {
            this.navigateFirstPageNums = navigateFirstPageNums;
        }

        public int getPrePage() {
            return prePage;
        }

        public void setPrePage(int prePage) {
            this.prePage = prePage;
        }

        public int getEndRow() {
            return endRow;
        }

        public void setEndRow(int endRow) {
            this.endRow = endRow;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getNavigateLastPageNums() {
            return navigateLastPageNums;
        }

        public void setNavigateLastPageNums(int navigateLastPageNums) {
            this.navigateLastPageNums = navigateLastPageNums;
        }

        public List<Integer> getNavigatePageNums() {
            return navigatePageNums;
        }

        public void setNavigatePageNums(List<Integer> navigatePageNums) {
            this.navigatePageNums = navigatePageNums;
        }
    }

    public static class DataBean {
        /**
         * order_code : 201908053000001556354865
         * order_status : 101
         * user_code : 1000000330781973
         * order_price : 8.0
         * order_text : 未付款
         * order_num : 1
         * id : 292
         * message :
         * pic_url : https://wajuejifiles.oss-cn-beijing.aliyuncs.com/C%3A%5CUsers%5CAdministrator%5CDesktop%5C%E5%B7%A5%E5%85%B7%E7%AE%B1%5Ctest.png
         * order_type : 1
         * add_time : 2019-08-05 09:53:20
         * order_name : 斗齿01
         */

        private String order_code;
        private int order_status;
        private String user_code;
        private double order_price;
        private String order_text;
        private int order_num;
        private int id;
        private String message;
        private String pic_url;
        private int order_type;
        private String add_time;
        private String order_name;

        public String getOrder_code() {
            return order_code;
        }

        public void setOrder_code(String order_code) {
            this.order_code = order_code;
        }

        public int getOrder_status() {
            return order_status;
        }

        public void setOrder_status(int order_status) {
            this.order_status = order_status;
        }

        public String getUser_code() {
            return user_code;
        }

        public void setUser_code(String user_code) {
            this.user_code = user_code;
        }

        public double getOrder_price() {
            return order_price;
        }

        public void setOrder_price(double order_price) {
            this.order_price = order_price;
        }

        public String getOrder_text() {
            return order_text;
        }

        public void setOrder_text(String order_text) {
            this.order_text = order_text;
        }

        public int getOrder_num() {
            return order_num;
        }

        public void setOrder_num(int order_num) {
            this.order_num = order_num;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public int getOrder_type() {
            return order_type;
        }

        public void setOrder_type(int order_type) {
            this.order_type = order_type;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getOrder_name() {
            return order_name;
        }

        public void setOrder_name(String order_name) {
            this.order_name = order_name;
        }
    }
}
