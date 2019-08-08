package com.jhhscm.platform.fragment.sale;

import java.io.Serializable;

public class FindGoodsAssessBean implements Serializable{

    /**
     * data : {"good_code":"a409a9afd6274054aa36559b50734509","city":"朔州市","counter_price":15000,"factory_time":"2019","retail_price":0,"second":0,"province":"河北省","name":"","id":41,"is_sell":0,"old_time":1500}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * good_code : a409a9afd6274054aa36559b50734509
         * city : 朔州市
         * counter_price : 15000
         * factory_time : 2019
         * retail_price : 0
         * second : 0
         * province : 河北省
         * name :
         * id : 41
         * is_sell : 0
         * old_time : 1500
         */

        private String message;
        private String good_code;
        private String city;
        private String counter_price;
        private String factory_time;
        private String retail_price;
        private int second;
        private String province;
        private String name;
        private int id;
        private int is_sell;
        private String old_time;
        private String original_price;

        public String getOriginal_price() {
            return original_price;
        }

        public void setOriginal_price(String original_price) {
            this.original_price = original_price;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getGood_code() {
            return good_code;
        }

        public void setGood_code(String good_code) {
            this.good_code = good_code;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String  getCounter_price() {
            return counter_price;
        }

        public void setCounter_price(String counter_price) {
            this.counter_price = counter_price;
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

        public int getSecond() {
            return second;
        }

        public void setSecond(int second) {
            this.second = second;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIs_sell() {
            return is_sell;
        }

        public void setIs_sell(int is_sell) {
            this.is_sell = is_sell;
        }

        public String  getOld_time() {
            return old_time;
        }

        public void setOld_time(String old_time) {
            this.old_time = old_time;
        }
    }
}
