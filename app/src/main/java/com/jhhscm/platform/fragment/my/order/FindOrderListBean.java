package com.jhhscm.platform.fragment.my.order;

import com.jhhscm.platform.fragment.sale.FindOrderBean;

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

    public static class DataBean {
        /**
         * order_code : 201907315000000098975909
         * order_status : 101
         * user_code : 1000000330781973
         * order_price : 25
         * order_text : 未付款
         * id : 28
         * message :
         */

        private String order_code;
        private String order_status;
        private String user_code;
        private String order_price;
        private String order_text;
        private String id;
        private String message;
        private List<FindOrderBean.GoodsListBean> goodsListBeans;

        public List<FindOrderBean.GoodsListBean> getGoodsListBeans() {
            return goodsListBeans;
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

        public String  getOrder_status() {
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
    }
}
