package com.jhhscm.platform.fragment.Mechanics.bean;

import java.io.Serializable;
import java.util.List;

public class BrandModelBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * brand_model_list : [{"model_name":"XXX机型","model_id":2},{"model_name":"YYYY机型","model_id":5}]
         * brand_name : 泉永
         * brand_pic : http://wajueji.oss-cn-shenzhen.aliyuncs.com/brand/8000000258249480.png?Expires=1884141116&OSSAccessKeyId=LTAI4F3Gt8M6rbEl&Signature=G893pdlqf9JhE7ziu1FOKL4QSVw%3D
         * brand_id : 9
         */

        private String brand_name;
        private String brand_pic;
        private String brand_id;
        private List<BrandModelListBean> brand_model_list;
        private boolean isSelect=false;

        public DataBean(String name, String id) {
            this.brand_name = name;
            this.brand_id = id;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
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

        public List<BrandModelListBean> getBrand_model_list() {
            return brand_model_list;
        }

        public void setBrand_model_list(List<BrandModelListBean> brand_model_list) {
            this.brand_model_list = brand_model_list;
        }

        public static class BrandModelListBean implements Serializable{
            /**
             * model_name : XXX机型
             * model_id : 2
             */

            private String model_name;
            private int model_id;

            public String getModel_name() {
                return model_name;
            }

            public void setModel_name(String model_name) {
                this.model_name = model_name;
            }

            public int getModel_id() {
                return model_id;
            }

            public void setModel_id(int model_id) {
                this.model_id = model_id;
            }
        }
    }
}
