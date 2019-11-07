package com.jhhscm.platform.fragment.my.store.action;

import java.util.List;

public class BusinessFindcategorybyBuscodeBean {

    /**
     * data : [{"categoryName":"类目","brandName":"天津日石","good_code":"20190822150130002000225867","sale_num":0,"counter_price":450,"name":"柴机油 CI-4 15W-40/20W-50","id":242,"pic_url":"http://wajueji.oss-cn-shenzhen.aliyuncs.com/category/a85212cbb3b3472dabf0b648767c1351.jpg?Expires=1881713326&OSSAccessKeyId=LTAI4F3Gt8M6rbEl&Signature=S4Gj2S7Wv81fsrOzwsInrsIMZ6w%3D","brand_id":10}]
     * page : {"total":1,"startRow":1,"size":1,"navigateFirstPageNums":1,"prePage":0,"endRow":1,"pageSize":5,"pageNum":1,"navigateLastPageNums":1,"navigatePageNums":[1]}
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
         * total : 1
         * startRow : 1
         * size : 1
         * navigateFirstPageNums : 1
         * prePage : 0
         * endRow : 1
         * pageSize : 5
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
         * categoryName : 类目
         * brandName : 天津日石
         * good_code : 20190822150130002000225867
         * sale_num : 0
         * counter_price : 450.0
         * name : 柴机油 CI-4 15W-40/20W-50
         * id : 242
         * pic_url : http://wajueji.oss-cn-shenzhen.aliyuncs.com/category/a85212cbb3b3472dabf0b648767c1351.jpg?Expires=1881713326&OSSAccessKeyId=LTAI4F3Gt8M6rbEl&Signature=S4Gj2S7Wv81fsrOzwsInrsIMZ6w%3D
         * brand_id : 10
         */

        private String categoryName;
        private String brandName;
        private String good_code;
        private int sale_num;
        private double counter_price;
        private String name;
        private int id;
        private String pic_url;
        private int brand_id;

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getGood_code() {
            return good_code;
        }

        public void setGood_code(String good_code) {
            this.good_code = good_code;
        }

        public int getSale_num() {
            return sale_num;
        }

        public void setSale_num(int sale_num) {
            this.sale_num = sale_num;
        }

        public double getCounter_price() {
            return counter_price;
        }

        public void setCounter_price(double counter_price) {
            this.counter_price = counter_price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public int getBrand_id() {
            return brand_id;
        }

        public void setBrand_id(int brand_id) {
            this.brand_id = brand_id;
        }
    }
}
