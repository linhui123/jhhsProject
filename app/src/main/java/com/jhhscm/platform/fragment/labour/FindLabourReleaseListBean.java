package com.jhhscm.platform.fragment.labour;

import java.io.Serializable;
import java.util.List;

public class FindLabourReleaseListBean {
    /**
     * data : [{"labour_code":"1","salay_money":"薪资2000-4000元/月","province":"河北省","city":"新疆维吾尔自治区","name":"招聘挖掘机小能手","id":1},{"labour_code":"2","salay_money":"薪资4000-5000元/月","province":"河北省","city":"新疆维吾尔自治区","name":"招聘挖掘机小能手","id":2},{"labour_code":"3","salay_money":"薪资2000元以下","province":"河北省","city":"新疆维吾尔自治区","name":"招聘挖掘机小能手","id":3},{"labour_code":"4","salay_money":"薪资面议","province":"河北省","city":"新疆维吾尔自治区","name":"招聘挖掘机小能手","id":4},{"labour_code":"5","salay_money":"薪资2000-4000元/月","province":"河北省","city":"新疆维吾尔自治区","name":"招聘挖掘机小能手","id":5}]
     * page : {"total":6,"startRow":1,"size":5,"navigateFirstPageNums":1,"prePage":0,"endRow":5,"pageSize":5,"pageNum":1,"navigateLastPageNums":2,"navigatePageNums":[1,2]}
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
         * total : 6
         * startRow : 1
         * size : 5
         * navigateFirstPageNums : 1
         * prePage : 0
         * endRow : 5
         * pageSize : 5
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

    public static class DataBean implements Serializable {
        /**
         * labour_code : 1
         * salay_money : 薪资2000-4000元/月
         * province : 河北省
         * city : 新疆维吾尔自治区
         * name : 招聘挖掘机小能手
         * id : 1
         */

        private String work_time;
        private String add_time;
        private String labour_code;
        private String salay_money;
        private String province;
        private String city;
        private String name;
        private String id;
        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getWork_time() {
            return work_time;
        }

        public void setWork_time(String work_time) {
            this.work_time = work_time;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getLabour_code() {
            return labour_code;
        }

        public void setLabour_code(String labour_code) {
            this.labour_code = labour_code;
        }

        public String getSalay_money() {
            return salay_money;
        }

        public void setSalay_money(String salay_money) {
            this.salay_money = salay_money;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
