package com.jhhscm.platform.fragment.my.store.action;

import java.util.List;

public class BusinessSumdataBean {

    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * user_code : 4000000034777111
         * sum_users : 1
         * sum_users_all : 2
         * sum_goods_price : 1
         * sum_goods_price_all : 2
         * bus_name : xxxxx
         */

        private String user_code;
        private int sum_users;
        private int sum_users_all;
        private int sum_goods_price;
        private int sum_goods_price_all;
        private String bus_name;

        public String getUser_code() {
            return user_code;
        }

        public void setUser_code(String user_code) {
            this.user_code = user_code;
        }

        public int getSum_users() {
            return sum_users;
        }

        public void setSum_users(int sum_users) {
            this.sum_users = sum_users;
        }

        public int getSum_users_all() {
            return sum_users_all;
        }

        public void setSum_users_all(int sum_users_all) {
            this.sum_users_all = sum_users_all;
        }

        public int getSum_goods_price() {
            return sum_goods_price;
        }

        public void setSum_goods_price(int sum_goods_price) {
            this.sum_goods_price = sum_goods_price;
        }

        public int getSum_goods_price_all() {
            return sum_goods_price_all;
        }

        public void setSum_goods_price_all(int sum_goods_price_all) {
            this.sum_goods_price_all = sum_goods_price_all;
        }

        public String getBus_name() {
            return bus_name;
        }

        public void setBus_name(String bus_name) {
            this.bus_name = bus_name;
        }
    }
}
