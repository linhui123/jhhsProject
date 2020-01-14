package com.jhhscm.platform.fragment.Mechanics.bean;

import java.util.List;

public class BrandModel1Bean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * brand_name : 山河智能
         * brand_pic : http://wajueji.oss-cn-shenzhen.aliyuncs.com/category/cffa5b2b76d249b4bfc418f8c7a64ec3.png
         * brand_id : 7
         */

        private String brand_name;
        private String brand_pic;
        private String brand_id;

        public DataBean(String brand_name, String brand_id) {
            this.brand_name = brand_name;
            this.brand_id = brand_id;
        }

        public String getBrand_name() {
            return brand_name;
        }

        public void setBrand_name(String brand_name) {
            this.brand_name = brand_name;
        }

        public String getBrand_pic() {
            return brand_pic;
        }

        public void setBrand_pic(String brand_pic) {
            this.brand_pic = brand_pic;
        }

        public String getBrand_id() {
            return brand_id;
        }

        public void setBrand_id(String brand_id) {
            this.brand_id = brand_id;
        }
    }
}

