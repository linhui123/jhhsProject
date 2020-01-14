package com.jhhscm.platform.fragment.Mechanics.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BrandModel2Bean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * brand_name : 斗山
         * brand_pic : http://wajueji.oss-cn-shenzhen.aliyuncs.com/category/d79a2a8bfa384acabdb6d9f310422ad6.png
         * type : [{"type_name":"小挖","type_id":"2","brand_model_list":[{"model_name":"DH50","model_id":1639}]},{"type_name":"中挖","3":"中挖","brand_model_list":[{"model_name":"DH130-2","model_id":1661},{"model_name":"DH150LC","model_id":1662}]},{"type_name":"大挖","4":"大挖","brand_model_list":[{"model_name":"DH300LC-V","model_id":1704},{"model_name":"DH300LC-7","model_id":1705},{"model_name":"DH420-9","model_id":1724}]}]
         * brand_id : 2
         */

        private String brand_name;
        private String brand_pic;
        private String brand_id;
        private List<TypeBean> type;

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

        public List<TypeBean> getType() {
            return type;
        }

        public void setType(List<TypeBean> type) {
            this.type = type;
        }

        public static class TypeBean {
            /**
             * type_name : 小挖
             * type_id : 2
             * brand_model_list : [{"model_name":"DH50","model_id":1639}]
             * 3 : 中挖
             * 4 : 大挖
             */

            private String type_name;
            private String type_id;
            @SerializedName("3")
            private String _$3;
            @SerializedName("4")
            private String _$4;
            private List<BrandModelListBean> brand_model_list;

            public String getType_name() {
                return type_name;
            }

            public void setType_name(String type_name) {
                this.type_name = type_name;
            }

            public String getType_id() {
                return type_id;
            }

            public void setType_id(String type_id) {
                this.type_id = type_id;
            }

            public String get_$3() {
                return _$3;
            }

            public void set_$3(String _$3) {
                this._$3 = _$3;
            }

            public String get_$4() {
                return _$4;
            }

            public void set_$4(String _$4) {
                this._$4 = _$4;
            }

            public List<BrandModelListBean> getBrand_model_list() {
                return brand_model_list;
            }

            public void setBrand_model_list(List<BrandModelListBean> brand_model_list) {
                this.brand_model_list = brand_model_list;
            }

            public static class BrandModelListBean {
                /**
                 * model_name : DH50
                 * model_id : 1639
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
}
