package com.jhhscm.platform.fragment.Mechanics.bean;

import java.io.Serializable;
import java.util.List;

public class GetComboBoxBean implements Serializable{

    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable {
        /**
         * key_name : 1
         * id : 14
         * key_value : 国产
         * desc : 国产
         */
        private String pic_url;
        private String key_name;
        private String id;
        private String key_value;
        private double discount;
        private String desc;
        private boolean isSelect;

        public ResultBean( String key_name, String key_value) {
            this.key_name = key_name;
            this.key_value = key_value;
        }

        public ResultBean(boolean isSelect, String key_name, String id) {
            this.isSelect = isSelect;
            this.key_name = key_name;
            this.id = id;
            this.key_value = key_value;
        }

        public ResultBean(String key_name, String key_value, double discount) {
            this.key_value = key_value;
            this.key_name = key_name;
            this.discount = discount;
        }

        public ResultBean(boolean isSelect, String key_name, String id, String pic_url) {
            this.isSelect = isSelect;
            this.key_name = key_name;
            this.id = id;
            this.pic_url = pic_url;
        }

        public double getDiscount() {
            return discount;
        }

        public void setDiscount(double discount) {
            this.discount = discount;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getKey_name() {
            return key_name;
        }

        public void setKey_name(String key_name) {
            this.key_name = key_name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getKey_value() {
            return key_value;
        }

        public void setKey_value(String key_value) {
            this.key_value = key_value;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
