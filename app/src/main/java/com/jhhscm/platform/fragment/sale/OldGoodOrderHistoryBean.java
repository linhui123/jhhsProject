package com.jhhscm.platform.fragment.sale;

import java.util.List;

public class OldGoodOrderHistoryBean {

    /**
     * data : [{"good_code":"1","province":"北京市","city":"天津市","counter_price":10,"name":"EC480D","id":1,"pic_url":"https://wajuejifiles.oss-cn-beijing.aliyuncs.com/C%3A%5CUsers%5CAdministrator%5CDesktop%5C%E5%B7%A5%E5%85%B7%E7%AE%B1%5Ctest.png","old_time":2000,"factory_time":"2019","retail_price":0,"second":0},{"good_code":"2","province":"河北省","city":"石家庄市","counter_price":9,"name":"EC380D","id":2,"pic_url":"https://wajuejifiles.oss-cn-beijing.aliyuncs.com/C%3A%5CUsers%5CAdministrator%5CDesktop%5C%E5%B7%A5%E5%85%B7%E7%AE%B1%5Ctest.png","old_time":2000,"factory_time":"2019","retail_price":0,"second":0},{"good_code":"12","province":"河北省","city":"石家庄市","counter_price":5,"name":"SY215C","id":12,"pic_url":"https://wajuejifiles.oss-cn-beijing.aliyuncs.com/C%3A%5CUsers%5CAdministrator%5CDesktop%5C%E5%B7%A5%E5%85%B7%E7%AE%B1%5Ctest.png","old_time":2000,"factory_time":"2019","retail_price":0,"second":1}]
     * page : {"total":3,"startRow":1,"size":3,"navigateFirstPageNums":1,"prePage":0,"endRow":3,"pageSize":3,"pageNum":1,"navigateLastPageNums":1,"navigatePageNums":[1]}
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
         * total : 3
         * startRow : 1
         * size : 3
         * navigateFirstPageNums : 1
         * prePage : 0
         * endRow : 3
         * pageSize : 3
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
         * good_code : 1
         * province : 北京市
         * city : 天津市
         * counter_price : 10
         * name : EC480D
         * id : 1
         * pic_url : https://wajuejifiles.oss-cn-beijing.aliyuncs.com/C%3A%5CUsers%5CAdministrator%5CDesktop%5C%E5%B7%A5%E5%85%B7%E7%AE%B1%5Ctest.png
         * old_time : 2000
         * factory_time : 2019
         * retail_price : 0
         * second : 0
         * update_time
         */
        private String update_time;
        private String good_code;
        private String province;
        private String city;
        private String counter_price;
        private String name;
        private String id;
        private String pic_url;
        private String old_time;
        private String factory_time;
        private String retail_price;
        private String second;

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getGood_code() {
            return good_code;
        }

        public void setGood_code(String good_code) {
            this.good_code = good_code;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCounter_price() {
            return counter_price;
        }

        public void setCounter_price(String counter_price) {
            this.counter_price = counter_price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public String getOld_time() {
            return old_time;
        }

        public void setOld_time(String old_time) {
            this.old_time = old_time;
        }

        public String getFactory_time() {
            return factory_time;
        }

        public void setFactory_time(String factory_time) {
            this.factory_time = factory_time;
        }

        public String getRetail_price() {
            return retail_price;
        }

        public void setRetail_price(String retail_price) {
            this.retail_price = retail_price;
        }

        public String getSecond() {
            return second;
        }

        public void setSecond(String second) {
            this.second = second;
        }
    }
}
