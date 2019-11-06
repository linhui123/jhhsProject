package com.jhhscm.platform.fragment.my.mechanics;

import java.io.Serializable;
import java.util.List;

public class FindGoodsOwnerBean {

    /**
     * data : [{"name":"设备名称","code":"设备编码","fixp17":"设备型号","brand_id":"品牌ID","brand_name":"品牌名字","fcatory_time":"出厂时间","status":"状态"}]
     * page : {"total":0,"startRow":0,"size":0,"navigateFirstPageNums":0,"prePage":0,"endRow":0,"pageSize":5,"pageNum":1,"navigateLastPageNums":0,"navigatePageNums":[]}
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
         * total : 0
         * startRow : 0
         * size : 0
         * navigateFirstPageNums : 0
         * prePage : 0
         * endRow : 0
         * pageSize : 5
         * pageNum : 1
         * navigateLastPageNums : 0
         * navigatePageNums : []
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
        private List<?> navigatePageNums;

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

        public List<?> getNavigatePageNums() {
            return navigatePageNums;
        }

        public void setNavigatePageNums(List<?> navigatePageNums) {
            this.navigatePageNums = navigatePageNums;
        }
    }

    public static class DataBean implements Serializable {
        /**
         * name : 设备名称
         * code : 设备编码
         * fixp17 : 设备型号
         * brand_id : 品牌ID
         * brand_name : 品牌名字
         * fcatory_time : 出厂时间
         * status : 状态
         */

        private String name;
        private String code;
        private String fixp17;
        private String brand_id;
        private String brand_name;
        private String fcatory_time;
        private String status;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getFixp17() {
            return fixp17;
        }

        public void setFixp17(String fixp17) {
            this.fixp17 = fixp17;
        }

        public String getBrand_id() {
            return brand_id;
        }

        public void setBrand_id(String brand_id) {
            this.brand_id = brand_id;
        }

        public String getBrand_name() {
            return brand_name;
        }

        public void setBrand_name(String brand_name) {
            this.brand_name = brand_name;
        }

        public String getFcatory_time() {
            return fcatory_time;
        }

        public void setFcatory_time(String fcatory_time) {
            this.fcatory_time = fcatory_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
