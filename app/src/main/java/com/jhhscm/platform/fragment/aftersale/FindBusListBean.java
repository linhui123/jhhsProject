package com.jhhscm.platform.fragment.aftersale;

import java.util.List;

public class FindBusListBean {

    /**
     * data : [{"city":"32","user_name":"测试00444","mobile":"123","county":"376","county_name":"东城区","province_name":"北京市","address_detail":"2222","city_name":"市辖区","user_code":"2019111310001599105392","province":"1","pic_url":"[{\"name\":\"http://wajueji.oss-cn-shenzhen.aliyuncs.com/business/368f7a402b2d4314bb51d3e201738ecd.jpg?Expires=1888987302&OSSAccessKeyId=LTAI4F3Gt8M6rbEl&Signature=1rq1HHTq5lfyvaNAQ9MRvi0s%2Ff8%3D\",\"url\":\"http://wajueji.oss-cn-shenzhen.aliyuncs.com/business/368f7a402b2d4314bb51d3e201738ecd.jpg?Expires=1888987302&OSSAccessKeyId=LTAI4F3Gt8M6rbEl&Signature=1rq1HHTq5lfyvaNAQ9MRvi0s%2Ff8%3D\",\"uid\":1573627216338,\"status\":\"success\"},{\"name\":\"http://wajueji.oss-cn-shenzhen.aliyuncs.com/business/9ceb2ac833e247bd8cbe83743363f2ff.png?Expires=1888992567&OSSAccessKeyId=LTAI4F3Gt8M6rbEl&Signature=BUcTzlYJyZicf4GL0SaoOFKJOQs%3D\",\"url\":\"http://wajueji.oss-cn-shenzhen.aliyuncs.com/business/9ceb2ac833e247bd8cbe83743363f2ff.png?Expires=1888992567&OSSAccessKeyId=LTAI4F3Gt8M6rbEl&Signature=BUcTzlYJyZicf4GL0SaoOFKJOQs%3D\",\"uid\":1573632481977,\"status\":\"success\"}]","bus_name":"测试444","add_time":"2019-11-13T06:41:44.000+0000","status":1,"v1":"","v2":""}]
     * page : {"total":8,"startRow":1,"size":8,"navigateFirstPageNums":1,"prePage":0,"endRow":8,"pageSize":10,"pageNum":0,"navigateLastPageNums":1,"navigatePageNums":[1]}
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
         * total : 8
         * startRow : 1
         * size : 8
         * navigateFirstPageNums : 1
         * prePage : 0
         * endRow : 8
         * pageSize : 10
         * pageNum : 0
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
         * city : 32
         * user_name : 测试00444
         * mobile : 123
         * county : 376
         * county_name : 东城区
         * province_name : 北京市
         * address_detail : 2222
         * city_name : 市辖区
         * user_code : 2019111310001599105392
         * province : 1
         * pic_url : [{"name":"http://wajueji.oss-cn-shenzhen.aliyuncs.com/business/368f7a402b2d4314bb51d3e201738ecd.jpg?Expires=1888987302&OSSAccessKeyId=LTAI4F3Gt8M6rbEl&Signature=1rq1HHTq5lfyvaNAQ9MRvi0s%2Ff8%3D","url":"http://wajueji.oss-cn-shenzhen.aliyuncs.com/business/368f7a402b2d4314bb51d3e201738ecd.jpg?Expires=1888987302&OSSAccessKeyId=LTAI4F3Gt8M6rbEl&Signature=1rq1HHTq5lfyvaNAQ9MRvi0s%2Ff8%3D","uid":1573627216338,"status":"success"},{"name":"http://wajueji.oss-cn-shenzhen.aliyuncs.com/business/9ceb2ac833e247bd8cbe83743363f2ff.png?Expires=1888992567&OSSAccessKeyId=LTAI4F3Gt8M6rbEl&Signature=BUcTzlYJyZicf4GL0SaoOFKJOQs%3D","url":"http://wajueji.oss-cn-shenzhen.aliyuncs.com/business/9ceb2ac833e247bd8cbe83743363f2ff.png?Expires=1888992567&OSSAccessKeyId=LTAI4F3Gt8M6rbEl&Signature=BUcTzlYJyZicf4GL0SaoOFKJOQs%3D","uid":1573632481977,"status":"success"}]
         * bus_name : 测试444
         * add_time : 2019-11-13T06:41:44.000+0000
         * status : 1
         * v1 :
         * v2 :
         * distance
         */

        private String city;
        private String user_name;
        private String mobile;
        private String county;
        private String county_name;
        private String province_name;
        private String address_detail;
        private String city_name;
        private String user_code;
        private String province;
        private String pic_url;
        private String bus_name;
        private String bus_code;
        private String distance;
        private String add_time;
        private int status;
        private String v1;
        private String v2;
        private String x;
        private String y;
        private int type;//0为正常，其他为广告

        public DataBean(String pic_url, int type) {
            this.pic_url = pic_url;
            this.type = type;
        }

        public String getX() {
            return x;
        }

        public void setX(String x) {
            this.x = x;
        }

        public String getY() {
            return y;
        }

        public void setY(String y) {
            this.y = y;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getBus_code() {
            return bus_code;
        }

        public void setBus_code(String bus_code) {
            this.bus_code = bus_code;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public String getCounty_name() {
            return county_name;
        }

        public void setCounty_name(String county_name) {
            this.county_name = county_name;
        }

        public String getProvince_name() {
            return province_name;
        }

        public void setProvince_name(String province_name) {
            this.province_name = province_name;
        }

        public String getAddress_detail() {
            return address_detail;
        }

        public void setAddress_detail(String address_detail) {
            this.address_detail = address_detail;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
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

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public String getBus_name() {
            return bus_name;
        }

        public void setBus_name(String bus_name) {
            this.bus_name = bus_name;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getV1() {
            return v1;
        }

        public void setV1(String v1) {
            this.v1 = v1;
        }

        public String getV2() {
            return v2;
        }

        public void setV2(String v2) {
            this.v2 = v2;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
