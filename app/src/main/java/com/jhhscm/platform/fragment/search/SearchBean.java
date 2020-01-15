package com.jhhscm.platform.fragment.search;

import java.util.List;

public class SearchBean {

    /**
     * data : [{"original_price":"93.00","good_code":"20191210114080000575880984","sale_num":"0","city":"146","counter_price":"93.00","num":"9999","fix_p_2":"","is_second":"0","fix_p_5":"","factory_time":"","retail_price":"","goods_type_name":"配件","user_code":"","province":"13","freight_price":"0.00","name":"唐纳森P502184液压油回油滤芯","id":"743","is_sell":"0","goods_type":"2","pic_url":"http://wajueji.oss-cn-shenzhen.aliyuncs.com/category_water/07b9758798c64a42904b394d975378fb.png?Expires=1893065304&OSSAccessKeyId=LTAI4F3Gt8M6rbEl&Signature=cYFEtsMHhWy05DpwmkfBybHnmqw%3D","old_time":"","add_time":"2019-12-17 14:57:02"}]
     * page : {"total":0,"startRow":0,"size":0,"navigateFirstPageNums":0,"prePage":0,"endRow":0,"pageSize":0,"pageNum":0,"navigateLastPageNums":0}
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
         * pageSize : 0
         * pageNum : 0
         * navigateLastPageNums : 0
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
    }

    public static class DataBean {
        /**
         * original_price : 93.00
         * good_code : 20191210114080000575880984
         * sale_num : 0
         * city : 146
         * counter_price : 93.00
         * num : 9999
         * fix_p_2 :
         * is_second : 0
         * fix_p_5 :
         * factory_time :
         * retail_price :
         * goods_type_name : 配件
         * user_code :
         * province : 13
         * freight_price : 0.00
         * name : 唐纳森P502184液压油回油滤芯
         * id : 743
         * is_sell : 0
         * goods_type : 2
         * pic_url : http://wajueji.oss-cn-shenzhen.aliyuncs.com/category_water/07b9758798c64a42904b394d975378fb.png?Expires=1893065304&OSSAccessKeyId=LTAI4F3Gt8M6rbEl&Signature=cYFEtsMHhWy05DpwmkfBybHnmqw%3D
         * old_time :
         * add_time : 2019-12-17 14:57:02
         */

        private String original_price;
        private String good_code;
        private String sale_num;
        private String city;
        private String counter_price;
        private String num;
        private String fix_p_2;
        private String is_second;
        private String fix_p_5;
        private String factory_time;
        private String retail_price;
        private String goods_type_name;
        private String user_code;
        private String province;
        private String freight_price;
        private String name;
        private String id;
        private String is_sell;
        private String goods_type;
        private String pic_url;
        private String old_time;
        private String add_time;
        private String bus_name;
        private String bus_code;

        public String getBus_name() {
            return bus_name;
        }

        public void setBus_name(String bus_name) {
            this.bus_name = bus_name;
        }

        public String getBus_code() {
            return bus_code;
        }

        public void setBus_code(String bus_code) {
            this.bus_code = bus_code;
        }

        public String getOriginal_price() {
            return original_price;
        }

        public void setOriginal_price(String original_price) {
            this.original_price = original_price;
        }

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

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getFix_p_2() {
            return fix_p_2;
        }

        public void setFix_p_2(String fix_p_2) {
            this.fix_p_2 = fix_p_2;
        }

        public String getIs_second() {
            return is_second;
        }

        public void setIs_second(String is_second) {
            this.is_second = is_second;
        }

        public String getFix_p_5() {
            return fix_p_5;
        }

        public void setFix_p_5(String fix_p_5) {
            this.fix_p_5 = fix_p_5;
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

        public String getGoods_type_name() {
            return goods_type_name;
        }

        public void setGoods_type_name(String goods_type_name) {
            this.goods_type_name = goods_type_name;
        }

        public String getUser_code() {
            return user_code;
        }

        public void setUser_code(String user_code) {
            this.user_code = user_code;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getFreight_price() {
            return freight_price;
        }

        public void setFreight_price(String freight_price) {
            this.freight_price = freight_price;
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

        public String getIs_sell() {
            return is_sell;
        }

        public void setIs_sell(String is_sell) {
            this.is_sell = is_sell;
        }

        public String getGoods_type() {
            return goods_type;
        }

        public void setGoods_type(String goods_type) {
            this.goods_type = goods_type;
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

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }
    }
}
