package com.jhhscm.platform.fragment.aftersale;

import java.util.List;

public class BusinessDetailBean {


    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * distance : 0.10
         * city : 32
         * user_name : 测试00444
         * mobile : 123
         * county : 376
         * county_name : 东城区
         * province_name : 北京市
         * address_detail : 2222
         * city_name : 市辖区
         * province : 1
         * id : 8
         * pic_url : [{"name":"http://wajueji.oss-cn-shenzhen.aliyuncs.com/business/368f7a402b2d4314bb51d3e201738ecd.jpg?Expires=1888987302&OSSAccessKeyId=LTAI4F3Gt8M6rbEl&Signature=1rq1HHTq5lfyvaNAQ9MRvi0s%2Ff8%3D","url":"http://wajueji.oss-cn-shenzhen.aliyuncs.com/business/368f7a402b2d4314bb51d3e201738ecd.jpg?Expires=1888987302&OSSAccessKeyId=LTAI4F3Gt8M6rbEl&Signature=1rq1HHTq5lfyvaNAQ9MRvi0s%2Ff8%3D","uid":1573627216338,"status":"success"},{"name":"http://wajueji.oss-cn-shenzhen.aliyuncs.com/business/9ceb2ac833e247bd8cbe83743363f2ff.png?Expires=1888992567&OSSAccessKeyId=LTAI4F3Gt8M6rbEl&Signature=BUcTzlYJyZicf4GL0SaoOFKJOQs%3D","url":"http://wajueji.oss-cn-shenzhen.aliyuncs.com/business/9ceb2ac833e247bd8cbe83743363f2ff.png?Expires=1888992567&OSSAccessKeyId=LTAI4F3Gt8M6rbEl&Signature=BUcTzlYJyZicf4GL0SaoOFKJOQs%3D","uid":1573632481977,"status":"success"}]
         * bus_name : 测试444
         * add_time : 2019-11-13T06:41:44.000+0000
         * bus_code : 2019111310001599105392
         * status : 0
         * desc : 3333
         */

        private String distance;
        private String city;
        private String user_name;
        private String mobile;
        private String county;
        private String county_name;
        private String province_name;
        private String address_detail;
        private String city_name;
        private String province;
        private int id;
        private String pic_url;
        private String bus_name;
        private String add_time;
        private String bus_code;
        private int status;
        private String desc;
        private String v1;
        private String v2;

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

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
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

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getBus_code() {
            return bus_code;
        }

        public void setBus_code(String bus_code) {
            this.bus_code = bus_code;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
