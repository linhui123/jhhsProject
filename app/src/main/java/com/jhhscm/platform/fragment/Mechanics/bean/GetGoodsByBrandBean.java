package com.jhhscm.platform.fragment.Mechanics.bean;

import java.util.List;

public class GetGoodsByBrandBean {

    /**
     * result : {"data":[{"id":13,"code":"13","name":"SY75C","categoryId":6,"brandId":3,"keywords":"三一","brief":"","isOnSale":1,"sortOrder":100,"picUrl":"https://wajuejifiles.oss-cn-beijing.aliyuncs.com/C%3A%5CUsers%5CAdministrator%5CDesktop%5C%E5%B7%A5%E5%85%B7%E7%AE%B1%5Ctest.png","isHot":0,"isSecond":0,"isNew":0,"unit":"\u2019件\u2018","freightPrice":3,"counterPrice":5,"retailPrice":0,"num":0,"saleNum":0,"fixP1":"2","fixP2":"3","fixP3":"4","fixP4":"4","fixP5":"4","fixP6":"4","fixP7":"5","fixP8":"5","province":"3","city":"34","county":"453","oldTime":2000,"factoryTime":"2019-02-01 16:02:35","goodsType":1,"deleted":0}],"page":{"total":1,"startRow":1,"size":1,"navigateFirstPageNums":1,"prePage":0,"endRow":1,"pageSize":10,"pageNum":1,"navigateLastPageNums":1,"navigatePageNums":[1]}}
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
         * data : [{"id":13,"code":"13","name":"SY75C","categoryId":6,"brandId":3,"keywords":"三一","brief":"","isOnSale":1,"sortOrder":100,"picUrl":"https://wajuejifiles.oss-cn-beijing.aliyuncs.com/C%3A%5CUsers%5CAdministrator%5CDesktop%5C%E5%B7%A5%E5%85%B7%E7%AE%B1%5Ctest.png","isHot":0,"isSecond":0,"isNew":0,"unit":"\u2019件\u2018","freightPrice":3,"counterPrice":5,"retailPrice":0,"num":0,"saleNum":0,"fixP1":"2","fixP2":"3","fixP3":"4","fixP4":"4","fixP5":"4","fixP6":"4","fixP7":"5","fixP8":"5","province":"3","city":"34","county":"453","oldTime":2000,"factoryTime":"2019-02-01 16:02:35","goodsType":1,"deleted":0}]
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
             * id : 13
             * code : 13
             * name : SY75C
             * categoryId : 6
             * brandId : 3
             * keywords : 三一
             * brief :
             * isOnSale : 1
             * sortOrder : 100
             * picUrl : https://wajuejifiles.oss-cn-beijing.aliyuncs.com/C%3A%5CUsers%5CAdministrator%5CDesktop%5C%E5%B7%A5%E5%85%B7%E7%AE%B1%5Ctest.png
             * isHot : 0
             * isSecond : 0
             * isNew : 0
             * unit : ’件‘
             * freightPrice : 3.0
             * counterPrice : 5.0
             * retailPrice : 0.0
             * num : 0
             * saleNum : 0
             * fixP1 : 2
             * fixP2 : 3
             * fixP3 : 4
             * fixP4 : 4
             * fixP5 : 4
             * fixP6 : 4
             * fixP7 : 5
             * fixP8 : 5
             * province : 3
             * city : 34
             * county : 453
             * oldTime : 2000
             * factoryTime : 2019-02-01 16:02:35
             * goodsType : 1
             * deleted : 0
             */

            private int id;
            private String code;
            private String name;
            private int categoryId;
            private int brandId;
            private String keywords;
            private String brief;
            private int isOnSale;
            private int sortOrder;
            private String picUrl;
            private int isHot;
            private int isSecond;
            private int isNew;
            private String unit;
            private double freightPrice;
            private double counterPrice;
            private double retailPrice;
            private int num;
            private int saleNum;
            private String fixP1;
            private String fixP2;
            private String fixP3;
            private String fixP4;
            private String fixP5;
            private String fixP6;
            private String fixP7;
            private String fixP8;
            private String province;
            private String city;
            private String county;
            private int oldTime;
            private String factoryTime;
            private int goodsType;
            private int deleted;
            private boolean select;

            public boolean isSelect() {
                return select;
            }

            public void setSelect(boolean select) {
                this.select = select;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(int categoryId) {
                this.categoryId = categoryId;
            }

            public int getBrandId() {
                return brandId;
            }

            public void setBrandId(int brandId) {
                this.brandId = brandId;
            }

            public String getKeywords() {
                return keywords;
            }

            public void setKeywords(String keywords) {
                this.keywords = keywords;
            }

            public String getBrief() {
                return brief;
            }

            public void setBrief(String brief) {
                this.brief = brief;
            }

            public int getIsOnSale() {
                return isOnSale;
            }

            public void setIsOnSale(int isOnSale) {
                this.isOnSale = isOnSale;
            }

            public int getSortOrder() {
                return sortOrder;
            }

            public void setSortOrder(int sortOrder) {
                this.sortOrder = sortOrder;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public int getIsHot() {
                return isHot;
            }

            public void setIsHot(int isHot) {
                this.isHot = isHot;
            }

            public int getIsSecond() {
                return isSecond;
            }

            public void setIsSecond(int isSecond) {
                this.isSecond = isSecond;
            }

            public int getIsNew() {
                return isNew;
            }

            public void setIsNew(int isNew) {
                this.isNew = isNew;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public double getFreightPrice() {
                return freightPrice;
            }

            public void setFreightPrice(double freightPrice) {
                this.freightPrice = freightPrice;
            }

            public double getCounterPrice() {
                return counterPrice;
            }

            public void setCounterPrice(double counterPrice) {
                this.counterPrice = counterPrice;
            }

            public double getRetailPrice() {
                return retailPrice;
            }

            public void setRetailPrice(double retailPrice) {
                this.retailPrice = retailPrice;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public int getSaleNum() {
                return saleNum;
            }

            public void setSaleNum(int saleNum) {
                this.saleNum = saleNum;
            }

            public String getFixP1() {
                return fixP1;
            }

            public void setFixP1(String fixP1) {
                this.fixP1 = fixP1;
            }

            public String getFixP2() {
                return fixP2;
            }

            public void setFixP2(String fixP2) {
                this.fixP2 = fixP2;
            }

            public String getFixP3() {
                return fixP3;
            }

            public void setFixP3(String fixP3) {
                this.fixP3 = fixP3;
            }

            public String getFixP4() {
                return fixP4;
            }

            public void setFixP4(String fixP4) {
                this.fixP4 = fixP4;
            }

            public String getFixP5() {
                return fixP5;
            }

            public void setFixP5(String fixP5) {
                this.fixP5 = fixP5;
            }

            public String getFixP6() {
                return fixP6;
            }

            public void setFixP6(String fixP6) {
                this.fixP6 = fixP6;
            }

            public String getFixP7() {
                return fixP7;
            }

            public void setFixP7(String fixP7) {
                this.fixP7 = fixP7;
            }

            public String getFixP8() {
                return fixP8;
            }

            public void setFixP8(String fixP8) {
                this.fixP8 = fixP8;
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

            public String getCounty() {
                return county;
            }

            public void setCounty(String county) {
                this.county = county;
            }

            public int getOldTime() {
                return oldTime;
            }

            public void setOldTime(int oldTime) {
                this.oldTime = oldTime;
            }

            public String getFactoryTime() {
                return factoryTime;
            }

            public void setFactoryTime(String factoryTime) {
                this.factoryTime = factoryTime;
            }

            public int getGoodsType() {
                return goodsType;
            }

            public void setGoodsType(int goodsType) {
                this.goodsType = goodsType;
            }

            public int getDeleted() {
                return deleted;
            }

            public void setDeleted(int deleted) {
                this.deleted = deleted;
            }
        }
    }
}
