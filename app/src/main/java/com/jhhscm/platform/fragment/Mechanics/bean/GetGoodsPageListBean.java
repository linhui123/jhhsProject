package com.jhhscm.platform.fragment.Mechanics.bean;

import java.util.List;

public class GetGoodsPageListBean {


    /**
     * data : [{"FixP5":"4","good_code":"","name":"306E2小型液压挖掘机","id":18,"pic_url":"https://wajuejifiles.oss-cn-beijing.aliyuncs.com/C%3A%5CUsers%5CAdministrator%5CDesktop%5C%E5%B7%A5%E5%85%B7%E7%AE%B1%5Ctest.png","FixP2":"6","second":0},{"FixP5":"4","good_code":"","name":"313D2 GC小型液压挖掘机","id":17,"pic_url":"https://wajuejifiles.oss-cn-beijing.aliyuncs.com/C%3A%5CUsers%5CAdministrator%5CDesktop%5C%E5%B7%A5%E5%85%B7%E7%AE%B1%5Ctest.png","FixP2":"3","second":0},{"FixP5":"4","good_code":"","name":"新一代Cat®320 GC液压挖掘机","id":16,"pic_url":"https://wajuejifiles.oss-cn-beijing.aliyuncs.com/C%3A%5CUsers%5CAdministrator%5CDesktop%5C%E5%B7%A5%E5%85%B7%E7%AE%B1%5Ctest.png","FixP2":"3","second":0},{"FixP5":"4","good_code":"","name":"349D2/D2L液压挖掘机","id":15,"pic_url":"https://wajuejifiles.oss-cn-beijing.aliyuncs.com/C%3A%5CUsers%5CAdministrator%5CDesktop%5C%E5%B7%A5%E5%85%B7%E7%AE%B1%5Ctest.png","FixP2":"3","second":0},{"FixP5":"4","good_code":"","name":"新一代Cat®336 GC液压挖掘机","id":14,"pic_url":"https://wajuejifiles.oss-cn-beijing.aliyuncs.com/C%3A%5CUsers%5CAdministrator%5CDesktop%5C%E5%B7%A5%E5%85%B7%E7%AE%B1%5Ctest.png","FixP2":"3","second":0},{"FixP5":"4","good_code":"","name":"SY75C","id":13,"pic_url":"https://wajuejifiles.oss-cn-beijing.aliyuncs.com/C%3A%5CUsers%5CAdministrator%5CDesktop%5C%E5%B7%A5%E5%85%B7%E7%AE%B1%5Ctest.png","FixP2":"3","second":0},{"FixP5":"4","good_code":"7","name":"DX300LC-9C","id":7,"pic_url":"https://wajuejifiles.oss-cn-beijing.aliyuncs.com/C%3A%5CUsers%5CAdministrator%5CDesktop%5C%E5%B7%A5%E5%85%B7%E7%AE%B1%5Ctest.png","FixP2":"3","second":0},{"FixP5":"4","good_code":"6","name":"DX500LC-9C","id":6,"pic_url":"https://wajuejifiles.oss-cn-beijing.aliyuncs.com/C%3A%5CUsers%5CAdministrator%5CDesktop%5C%E5%B7%A5%E5%85%B7%E7%AE%B1%5Ctest.png","FixP2":"3","second":0},{"FixP5":"4","good_code":"5","name":"EC60D","id":5,"pic_url":"https://wajuejifiles.oss-cn-beijing.aliyuncs.com/C%3A%5CUsers%5CAdministrator%5CDesktop%5C%E5%B7%A5%E5%85%B7%E7%AE%B1%5Ctest.png","FixP2":"3","second":0},{"FixP5":"4","good_code":"4","name":"EC120D","id":4,"pic_url":"https://wajuejifiles.oss-cn-beijing.aliyuncs.com/C%3A%5CUsers%5CAdministrator%5CDesktop%5C%E5%B7%A5%E5%85%B7%E7%AE%B1%5Ctest.png","FixP2":"3","second":0}]
     * page : {"total":13,"startRow":1,"size":10,"navigateFirstPageNums":1,"prePage":0,"endRow":10,"pageSize":10,"pageNum":1,"navigateLastPageNums":2,"navigatePageNums":[1,2]}
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
         * total : 13
         * startRow : 1
         * size : 10
         * navigateFirstPageNums : 1
         * prePage : 0
         * endRow : 10
         * pageSize : 10
         * pageNum : 1
         * navigateLastPageNums : 2
         * navigatePageNums : [1,2]
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
         * FixP5 : 4
         * good_code :
         * name : 306E2小型液压挖掘机
         * id : 18
         * pic_url : https://wajuejifiles.oss-cn-beijing.aliyuncs.com/C%3A%5CUsers%5CAdministrator%5CDesktop%5C%E5%B7%A5%E5%85%B7%E7%AE%B1%5Ctest.png
         * FixP2 : 6
         * second : 0
         */
        private String counter_price;
        private String FixP5;
        private String good_code;
        private String name;
        private String id;
        private String pic_url;
        private String FixP2;
        private String second;
        private String num;
        private boolean select;
        private String sale_num;

        public String getSale_num() {
            return sale_num;
        }

        public void setSale_num(String sale_num) {
            this.sale_num = sale_num;
        }

        public boolean isSelect() {
            return select;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getCounter_price() {
            return counter_price;
        }

        public void setCounter_price(String counter_price) {
            this.counter_price = counter_price;
        }

        public String getFixP5() {
            return FixP5;
        }

        public void setFixP5(String FixP5) {
            this.FixP5 = FixP5;
        }

        public String getGood_code() {
            return good_code;
        }

        public void setGood_code(String good_code) {
            this.good_code = good_code;
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

        public String getFixP2() {
            return FixP2;
        }

        public void setFixP2(String FixP2) {
            this.FixP2 = FixP2;
        }

        public String getSecond() {
            return second;
        }

        public void setSecond(String second) {
            this.second = second;
        }
    }
}
