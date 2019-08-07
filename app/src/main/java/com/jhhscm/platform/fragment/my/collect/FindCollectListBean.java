package com.jhhscm.platform.fragment.my.collect;

import java.util.List;

public class FindCollectListBean {

    /**
     * data : [{"code":"5","province":"河北省","city":"石家庄市","name":"EC60D","county":"峰峰矿区","fix_p_2":"3","id":5,"pic_url":"https://wajuejifiles.oss-cn-beijing.aliyuncs.com/C%3A%5CUsers%5CAdministrator%5CDesktop%5C%E5%B7%A5%E5%85%B7%E7%AE%B1%5Ctest.png","fix_p_5":"4","old_time":2000,"factory_time":"2019"}]
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
         * code : 5
         * province : 河北省
         * city : 石家庄市
         * name : EC60D
         * county : 峰峰矿区
         * fix_p_2 : 3
         * id : 5
         * pic_url : https://wajuejifiles.oss-cn-beijing.aliyuncs.com/C%3A%5CUsers%5CAdministrator%5CDesktop%5C%E5%B7%A5%E5%85%B7%E7%AE%B1%5Ctest.png
         * fix_p_5 : 4
         * old_time : 2000
         * factory_time : 2019
         */
        private String good_code;
        private String original_price;
        private String code;
        private String province;
        private String city;
        private String name;
        private String county;
        private String fix_p_2;
        private String id;
        private String pic_url;
        private String fix_p_5;
        private String old_time;
        private String factory_time;
        private String type;
        private String sale_num;

        public String getGood_code() {
            return good_code;
        }

        public void setGood_code(String good_code) {
            this.good_code = good_code;
        }

        public String getSale_num() {
            return sale_num;
        }

        public void setSale_num(String sale_num) {
            this.sale_num = sale_num;
        }

        public String getOriginal_price() {
            return original_price;
        }

        public void setOriginal_price(String original_price) {
            this.original_price = original_price;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public String getFix_p_2() {
            return fix_p_2;
        }

        public void setFix_p_2(String fix_p_2) {
            this.fix_p_2 = fix_p_2;
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

        public String getFix_p_5() {
            return fix_p_5;
        }

        public void setFix_p_5(String fix_p_5) {
            this.fix_p_5 = fix_p_5;
        }

        public String  getOld_time() {
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
    }
}
