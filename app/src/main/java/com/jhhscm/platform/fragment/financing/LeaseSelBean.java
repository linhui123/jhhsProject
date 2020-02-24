package com.jhhscm.platform.fragment.financing;

import java.util.List;

public class LeaseSelBean {

    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * sec_name : 1
         * fix_p_17 : 12
         * item_name : 11
         * brand_name : 山河智能
         * lease_state : 2
         * add_time : 2019-10-24
         * lease_state_str : 否决
         * lease_code : 20191024134590001672411121
         * brand_id : 7
         */

        private String sec_name;
        private String fix_p_17;
        private String item_name;
        private String brand_name;
        private int lease_state;
        private String add_time;
        private String lease_state_str;
        private String lease_code;
        private int brand_id;

        public String getSec_name() {
            return sec_name;
        }

        public void setSec_name(String sec_name) {
            this.sec_name = sec_name;
        }

        public String getFix_p_17() {
            return fix_p_17;
        }

        public void setFix_p_17(String fix_p_17) {
            this.fix_p_17 = fix_p_17;
        }

        public String getItem_name() {
            return item_name;
        }

        public void setItem_name(String item_name) {
            this.item_name = item_name;
        }

        public String getBrand_name() {
            return brand_name;
        }

        public void setBrand_name(String brand_name) {
            this.brand_name = brand_name;
        }

        public int getLease_state() {
            return lease_state;
        }

        public void setLease_state(int lease_state) {
            this.lease_state = lease_state;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getLease_state_str() {
            return lease_state_str;
        }

        public void setLease_state_str(String lease_state_str) {
            this.lease_state_str = lease_state_str;
        }

        public String getLease_code() {
            return lease_code;
        }

        public void setLease_code(String lease_code) {
            this.lease_code = lease_code;
        }

        public int getBrand_id() {
            return brand_id;
        }

        public void setBrand_id(int brand_id) {
            this.brand_id = brand_id;
        }
    }
}
