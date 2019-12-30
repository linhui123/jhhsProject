package com.jhhscm.platform.fragment.my.mechanics;

import java.io.Serializable;
import java.util.List;

public class FindGoodsOwnerBean {

    /**
     * data : [{"pic_gallery_url_list":"[\"http://wajueji.oss-cn-shenzhen.aliyuncs.com/oldGood_water/53df10708e474b4fa9f46a32f28692e3.jpg?Expires=1888470463&OSSAccessKeyId=LTAI4F3Gt8M6rbEl&Signature=QzToW6wao6ASC2LC3kdrQL5oaH8%3D\"]","code":"20191107150950000041262251","fcatory_time":"2019-11-07 00:00:00","name":"002","brand_name":"三一","fixp17":"001","brand_id":3,"status":1},{"pic_gallery_url_list":"[\"http://wajueji.oss-cn-shenzhen.aliyuncs.com/oldGood_water/3e7775ab9d0444378410bfd2f42b964a.jpg?Expires=1888469613&OSSAccessKeyId=LTAI4F3Gt8M6rbEl&Signature=bUUTPt21KRvZvyystuYs77rayEo%3D\"]","code":"20191107145310000609959745","fcatory_time":"2019-11-07 00:00:00","name":"001","brand_name":"三一","fixp17":"项圈","brand_id":3,"status":1}]
     * page : {"total":2,"startRow":1,"size":2,"navigateFirstPageNums":1,"prePage":0,"endRow":2,"pageSize":10,"pageNum":1,"navigateLastPageNums":1,"navigatePageNums":[1]}
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

    public static class DataBean implements Serializable {
        /**
         * pic_gallery_url_list : ["http://wajueji.oss-cn-shenzhen.aliyuncs.com/oldGood_water/53df10708e474b4fa9f46a32f28692e3.jpg?Expires=1888470463&OSSAccessKeyId=LTAI4F3Gt8M6rbEl&Signature=QzToW6wao6ASC2LC3kdrQL5oaH8%3D"]
         * code : 20191107150950000041262251
         * fcatory_time : 2019-11-07 00:00:00
         * name : 002
         * brand_name : 三一
         * fixp17 : 001
         * brand_id : 3
         * status : 1
         */

        private String pic_gallery_url_list;
        private String code;
        private String fcatory_time;
        private String name;
        private String brand_name;
        private String fixp17;
        private String v1;
        private int brand_id;
        private int status;
        private boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getV1() {
            return v1;
        }

        public void setV1(String v1) {
            this.v1 = v1;
        }

        public String getPic_gallery_url_list() {
            return pic_gallery_url_list;
        }

        public void setPic_gallery_url_list(String pic_gallery_url_list) {
            this.pic_gallery_url_list = pic_gallery_url_list;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getFcatory_time() {
            return fcatory_time;
        }

        public void setFcatory_time(String fcatory_time) {
            this.fcatory_time = fcatory_time;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBrand_name() {
            return brand_name;
        }

        public void setBrand_name(String brand_name) {
            this.brand_name = brand_name;
        }

        public String getFixp17() {
            return fixp17;
        }

        public void setFixp17(String fixp17) {
            this.fixp17 = fixp17;
        }

        public int getBrand_id() {
            return brand_id;
        }

        public void setBrand_id(int brand_id) {
            this.brand_id = brand_id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
