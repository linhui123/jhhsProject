package com.jhhscm.platform.fragment.invitation;

import java.util.List;

public class ReqListBean {

    /**
     * result : {"data":[{"deleted":0,"mobile":"15927112992","nickname":"","add_time":"2019-10-31","bus_code":"4000000034777111","user_code":"4000000034777111"}],"page":{"total":1,"startRow":1,"size":1,"navigateFirstPageNums":1,"prePage":0,"endRow":1,"pageSize":10,"pageNum":1,"navigateLastPageNums":1,"navigatePageNums":[1]}}
     */

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * data : [{"deleted":0,"mobile":"15927112992","nickname":"","add_time":"2019-10-31","bus_code":"4000000034777111","user_code":"4000000034777111"}]
         * page : {"total":1,"startRow":1,"size":1,"navigateFirstPageNums":1,"prePage":0,"endRow":1,"pageSize":10,"pageNum":1,"navigateLastPageNums":1,"navigatePageNums":[1]}
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
             * deleted : 0
             * mobile : 15927112992
             * nickname :
             * add_time : 2019-10-31
             * bus_code : 4000000034777111
             * user_code : 4000000034777111
             */

            private int deleted;
            private String mobile;
            private String nickname;
            private String add_time;
            private String bus_code;
            private String user_code;

            public int getDeleted() {
                return deleted;
            }

            public void setDeleted(int deleted) {
                this.deleted = deleted;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getBus_code() {
                return bus_code;
            }

            public void setBus_code(String bus_code) {
                this.bus_code = bus_code;
            }

            public String getUser_code() {
                return user_code;
            }

            public void setUser_code(String user_code) {
                this.user_code = user_code;
            }
        }
    }
}
