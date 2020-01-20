package com.jhhscm.platform.fragment.sale;

import java.util.List;

public class FindOrderBean {


    /**
     * goodsList : [{"id":253,"orderCode":"201908144000000974675442","goodsCode":"1000000866892354","goodsName":"铰座液压静力压桩机","productId":138,"number":4,"price":0.01,"specifications":"","picUrl":"http://wajueji.oss-cn-shenzhen.aliyuncs.com/category/c0b96757ac364b5e83463fe0d0b8c4e0.jpg?Expires=1881035427&OSSAccessKeyId=LTAI4F3Gt8M6rbEl&Signature=l3G5Bcn6t%2BreYLdDfFU2XuKcPLU%3D","comment":0,"addTime":"2019-08-14 15:38:51","updateTime":"2019-08-14 15:38:51","deleted":0},{"id":254,"orderCode":"201908144000000974675442","goodsCode":"1000001746446591","goodsName":"液压静力压桩机","productId":137,"number":2,"price":0.01,"specifications":"","picUrl":"http://wajueji.oss-cn-shenzhen.aliyuncs.com/category/9138d691c35a43f694c110606967f90e.jpg?Expires=1881035369&OSSAccessKeyId=LTAI4F3Gt8M6rbEl&Signature=lMsVArz%2Be05MNwWQgK6v0tKCnAA%3D","comment":0,"addTime":"2019-08-14 15:38:51","updateTime":"2019-08-14 15:38:51","deleted":0}]
     * order : {"order_status":201,"address":"天津市市辖区河西区 大门阁楼","consignee":"陈先生","goods_price":0.06,"freight_price":0,"mobile":"18030129696","end_time":"2019-08-15 15:38:51","coupon_price":0,"order_price":0.06,"order_text":"已付款"}
     */

    private OrderBean order;
    private DataBean data;
    private List<GoodsListBean> goodsList;

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public List<GoodsListBean> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsListBean> goodsList) {
        this.goodsList = goodsList;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class OrderBean {
        /**
         * order_status : 201
         * address : 天津市市辖区河西区 大门阁楼
         * consignee : 陈先生
         * goods_price : 0.06
         * freight_price : 0.0
         * mobile : 18030129696
         * end_time : 2019-08-15 15:38:51
         * coupon_price : 0.0
         * order_price : 0.06
         * order_text : 已付款
         */

        private String order_status;
        private String address;
        private String consignee;
        private String goods_price;
        private String freight_price;
        private String mobile;
        private String end_time;
        private String coupon_price;
        private String order_price;
        private String order_text;
        private String other_price;
        private String ship_time;
        private String ship_sn;
        private String ship_channel;

        public String getOther_price() {
            return other_price;
        }

        public void setOther_price(String other_price) {
            this.other_price = other_price;
        }

        public String getShip_time() {
            return ship_time;
        }

        public void setShip_time(String ship_time) {
            this.ship_time = ship_time;
        }

        public String getShip_sn() {
            return ship_sn;
        }

        public void setShip_sn(String ship_sn) {
            this.ship_sn = ship_sn;
        }

        public String getShip_channel() {
            return ship_channel;
        }

        public void setShip_channel(String ship_channel) {
            this.ship_channel = ship_channel;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
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

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getFreight_price() {
            return freight_price;
        }

        public void setFreight_price(String freight_price) {
            this.freight_price = freight_price;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getCoupon_price() {
            return coupon_price;
        }

        public void setCoupon_price(String coupon_price) {
            this.coupon_price = coupon_price;
        }

        public String getOrder_price() {
            return order_price;
        }

        public void setOrder_price(String order_price) {
            this.order_price = order_price;
        }

        public String getOrder_text() {
            return order_text;
        }

        public void setOrder_text(String order_text) {
            this.order_text = order_text;
        }
    }

    public static class GoodsListBean {
        /**
         * id : 253
         * orderCode : 201908144000000974675442
         * goodsCode : 1000000866892354
         * goodsName : 铰座液压静力压桩机
         * productId : 138
         * number : 4
         * price : 0.01
         * specifications :
         * picUrl : http://wajueji.oss-cn-shenzhen.aliyuncs.com/category/c0b96757ac364b5e83463fe0d0b8c4e0.jpg?Expires=1881035427&OSSAccessKeyId=LTAI4F3Gt8M6rbEl&Signature=l3G5Bcn6t%2BreYLdDfFU2XuKcPLU%3D
         * comment : 0
         * addTime : 2019-08-14 15:38:51
         * updateTime : 2019-08-14 15:38:51
         * deleted : 0
         */

        private String id;
        private String orderCode;
        private String goodsCode;
        private String goodsName;
        private String productId;
        private String number;
        private String price;
        private String specifications;
        private String picUrl;
        private String comment;
        private String addTime;
        private String updateTime;
        private String deleted;
        private String Order_text;
        private String Order_status;

        public String getOrder_status() {
            return Order_status;
        }

        public void setOrder_status(String order_status) {
            Order_status = order_status;
        }

        public String getOrder_text() {
            return Order_text;
        }

        public void setOrder_text(String order_text) {
            Order_text = order_text;
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

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
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

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
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

        public String getDeleted() {
            return deleted;
        }

        public void setDeleted(String deleted) {
            this.deleted = deleted;
        }
    }

    public static class GoodsOwnerListBean {
        /**
         * fixs :
         * brands :
         * v3s : 11
         * pic_url :
         */

        private String fixs;
        private String brands;
        private String v3s;
//        private String pic_url;

        public String getFixs() {
            return fixs;
        }

        public void setFixs(String fixs) {
            this.fixs = fixs;
        }

        public String getBrands() {
            return brands;
        }

        public void setBrands(String brands) {
            this.brands = brands;
        }

        public String getV3s() {
            return v3s;
        }

        public void setV3s(String v3s) {
            this.v3s = v3s;
        }

//        public String getPic_url() {
//            return pic_url;
//        }
//
//        public void setPic_url(String pic_url) {
//            this.pic_url = pic_url;
//        }
    }

    public static class DataBean {

        private int count;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
