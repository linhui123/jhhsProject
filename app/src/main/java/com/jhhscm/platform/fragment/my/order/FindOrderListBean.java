package com.jhhscm.platform.fragment.my.order;

import com.jhhscm.platform.fragment.my.store.action.FindBusOrderListBean;
import com.jhhscm.platform.fragment.sale.FindOrderBean;

import java.io.Serializable;
import java.util.List;

public class FindOrderListBean {

    /**
     * data : [{"order_code":"201907315000000098975909","order_status":101,"user_code":"1000000330781973","order_price":25,"order_text":"未付款","id":28,"message":""},{"order_code":"201907315000000098975909","order_status":101,"user_code":"1000000330781973","order_price":25,"order_text":"未付款","id":28,"message":""},{"order_code":"201907315000000098975909","order_status":101,"user_code":"1000000330781973","order_price":25,"order_text":"未付款","id":28,"message":""},{"order_code":"201907315000000098975909","order_status":101,"user_code":"1000000330781973","order_price":25,"order_text":"未付款","id":28,"message":""},{"order_code":"201907315000000098975909","order_status":101,"user_code":"1000000330781973","order_price":25,"order_text":"未付款","id":28,"message":""}]
     * page : {"total":221,"startRow":1,"size":5,"navigateFirstPageNums":1,"prePage":0,"endRow":5,"pageSize":5,"pageNum":1,"navigateLastPageNums":8,"navigatePageNums":[1,2,3,4,5,6,7,8]}
     */

    private PageBean page;
    private List<DataBean> data;

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

    public static class PageBean {
        /**
         * total : 221
         * startRow : 1
         * size : 5
         * navigateFirstPageNums : 1
         * prePage : 0
         * endRow : 5
         * pageSize : 5
         * pageNum : 1
         * navigateLastPageNums : 8
         * navigatePageNums : [1,2,3,4,5,6,7,8]
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

    public static class DataBean implements Serializable {
        /**
         * order_code : 201907315000000098975909
         * order_status : 101
         * user_code : 1000000330781973
         * order_price : 25
         * order_text : 未付款
         * id : 28
         * message :
         */
        private String ship_time;
        private String ship_sn;
        private String ship_channel;
        private String order_code;
        private String order_status;
        private String user_code;
        private String order_price;
        private String order_text;
        private String id;
        private String message;

        private String goods_price;
        private String other_price;
        private String order_name;
        private String order_num;
        private String pic_url;
        private String order_type;
        private String add_time;
        private String bus_name;
        private String bus_code;
        private String bus_pic_url;
        private String is_payframe;
        private List<GoodsListBean> goodsList;//维修订单配件列表
        private List<GoodsOwnerListBean> goodsOwnerList;//维修订单设备列表
        private List<FindOrderBean.GoodsListBean> goodsListBeans;//平台订单商品列表

        public List<GoodsListBean> getGoodsList() {
            return goodsList;
        }

        public void setGoodsList(List<GoodsListBean> goodsList) {
            this.goodsList = goodsList;
        }

        public List<GoodsOwnerListBean> getGoodsOwnerList() {
            return goodsOwnerList;
        }

        public void setGoodsOwnerList(List<GoodsOwnerListBean> goodsOwnerList) {
            this.goodsOwnerList = goodsOwnerList;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getOther_price() {
            return other_price;
        }

        public void setOther_price(String other_price) {
            this.other_price = other_price;
        }

        public String getOrder_num() {
            return order_num;
        }

        public void setOrder_num(String order_num) {
            this.order_num = order_num;
        }

        public List<FindOrderBean.GoodsListBean> getGoodsListBeans() {
            return goodsListBeans;
        }

        public String getIs_payframe() {
            return is_payframe;
        }

        public void setIs_payframe(String is_payframe) {
            this.is_payframe = is_payframe;
        }

        public String getOrder_name() {
            return order_name;
        }

        public void setOrder_name(String order_name) {
            this.order_name = order_name;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public String getOrder_type() {
            return order_type;
        }

        public void setOrder_type(String order_type) {
            this.order_type = order_type;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getBus_name() {
            return bus_name;
        }

        public void setBus_name(String bus_name) {
            this.bus_name = bus_name;
        }

        public String getBus_code() {
            return bus_code;
        }

        public void setBus_code(String bus_code) {
            this.bus_code = bus_code;
        }

        public String getBus_pic_url() {
            return bus_pic_url;
        }

        public void setBus_pic_url(String bus_pic_url) {
            this.bus_pic_url = bus_pic_url;
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

        public void setGoodsListBeans(List<FindOrderBean.GoodsListBean> goodsListBeans) {
            this.goodsListBeans = goodsListBeans;
        }

        public String getOrder_code() {
            return order_code;
        }

        public void setOrder_code(String order_code) {
            this.order_code = order_code;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public String getUser_code() {
            return user_code;
        }

        public void setUser_code(String user_code) {
            this.user_code = user_code;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public static class GoodsOwnerListBean implements Serializable {
            /**
             * fixs :
             * brands :
             * v3s : 11
             * pic_url :
             */

            private String fixs;
            private String brands;
            private String v3s;
            private String pic_url;
            private String goods_code;
            private String v_2;
            private String v_1;
            private String v_3;
            private String bus_code;

            public String getGoods_code() {
                return goods_code;
            }

            public void setGoods_code(String goods_code) {
                this.goods_code = goods_code;
            }

            public String getV_2() {
                return v_2;
            }

            public void setV_2(String v_2) {
                this.v_2 = v_2;
            }

            public String getV_1() {
                return v_1;
            }

            public void setV_1(String v_1) {
                this.v_1 = v_1;
            }

            public String getV_3() {
                return v_3;
            }

            public void setV_3(String v_3) {
                this.v_3 = v_3;
            }

            public String getBus_code() {
                return bus_code;
            }

            public void setBus_code(String bus_code) {
                this.bus_code = bus_code;
            }

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

            public String getPic_url() {
                return pic_url;
            }

            public void setPic_url(String pic_url) {
                this.pic_url = pic_url;
            }
        }

        public static class GoodsListBean implements Serializable {
            /**
             * order_code : 2019111850001343562906
             * goods_name : 380到480空气滤芯套件
             * number : 3
             * price : 963.0
             * pic_url : http://wajueji.oss-cn-shenzhen.aliyuncs.com/category_water/84143dd9abe8429bacfd24c9a9d86a68.png?Expires=1886144915&OSSAccessKeyId=LTAI4F3Gt8M6rbEl&Signature=%2BhUVd8tB2KjZO3Re8fgoGp5dN60%3D
             */

            private String order_code;
            private String goods_name;
            private int number;
            private double price;
            private String pic_url;

            public String getOrder_code() {
                return order_code;
            }

            public void setOrder_code(String order_code) {
                this.order_code = order_code;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getPic_url() {
                return pic_url;
            }

            public void setPic_url(String pic_url) {
                this.pic_url = pic_url;
            }
        }
    }
}
