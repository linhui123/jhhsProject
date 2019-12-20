package com.jhhscm.platform.fragment.my.store.action;

import java.io.Serializable;
import java.util.List;

public class FindUserGoodsOwnerBean implements Serializable {

    private List<DataBean> data;
    private PageBean page;

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

    public static class DataBean implements Serializable {
        /**
         * name : 设备名称
         * code : 设备编码
         * fixp17 : 设备型号
         * brand_id : 品牌ID
         * brand_name : 品牌名字
         * fcatory_time : 出厂时间
         * status : 状态
         * pic_gallery_url_list :
         */

        private String name;
        private String code;
        private String fixp17;
        private String brand_id;
        private String brand_name;
        private String fcatory_time;
        private String status;
        private String pic_gallery_url_list;
        private String no;
        private String gps_no;
        private String error_no;
        private String v_1;
        private String v_2;
        private String v_3;
        private boolean isSelect;

        public String getV_1() {
            return v_1;
        }

        public void setV_1(String v_1) {
            this.v_1 = v_1;
        }

        public String getV_2() {
            return v_2;
        }

        public void setV_2(String v_2) {
            this.v_2 = v_2;
        }

        public String getV_3() {
            return v_3;
        }

        public void setV_3(String v_3) {
            this.v_3 = v_3;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getGps_no() {
            return gps_no;
        }

        public void setGps_no(String gps_no) {
            this.gps_no = gps_no;
        }

        public String getError_no() {
            return error_no;
        }

        public void setError_no(String error_no) {
            this.error_no = error_no;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

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

        public String getPic_gallery_url_list() {
            return pic_gallery_url_list;
        }

        public void setPic_gallery_url_list(String pic_gallery_url_list) {
            this.pic_gallery_url_list = pic_gallery_url_list;
        }
    }

    public static class PageBean {
        /**
         * total : 2
         * startRow : 1
         * size : 2
         * navigateFirstPageNums : 1
         * prePage : 0
         * endRow : 2
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
}
