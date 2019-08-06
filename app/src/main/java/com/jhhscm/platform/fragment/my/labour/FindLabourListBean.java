package com.jhhscm.platform.fragment.my.labour;

import java.util.List;

public class FindLabourListBean {

    /**
     * data : [{"labour_code":"f647a9f2c2e14bbe94d63939a6f722b0","update_time":"2019-08-05T08:12:19.000+0000","num":0,"name":"测试发布求职信息","id":6},{"labour_code":"5ae639ed2582405f98e039389c83f0f1","update_time":"2019-08-05T08:12:56.000+0000","num":0,"name":"测试发布求职信息","id":7},{"labour_code":"f107f2d2701c4be78e8572be6cfa4f12","update_time":"2019-08-05T08:12:57.000+0000","num":1,"name":"测试发布求职信息","id":8}]
     * page : {"total":3,"startRow":1,"size":3,"navigateFirstPageNums":1,"prePage":0,"endRow":3,"pageSize":5,"pageNum":1,"navigateLastPageNums":1,"navigatePageNums":[1]}
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
         * labour_code : f647a9f2c2e14bbe94d63939a6f722b0
         * update_time : 2019-08-05T08:12:19.000+0000
         * num : 0
         * name : 测试发布求职信息
         * id : 6
         */

        private String labour_code;
        private String update_time;
        private int num;
        private String name;
        private String id;
        private String type;
        public DataBean(String id, String labour_code) {
            this.id = id;
            this.labour_code = labour_code;
        }
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getLabour_code() {
            return labour_code;
        }

        public void setLabour_code(String labour_code) {
            this.labour_code = labour_code;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
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
    }
}
