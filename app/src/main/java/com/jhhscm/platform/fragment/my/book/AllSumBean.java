package com.jhhscm.platform.fragment.my.book;

public class AllSumBean {

    /**
     * data : {"price_1":500,"price_2":1000,"price_3":1500}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * price_1 : 500.0
         * price_2 : 1000.0
         * price_3 : 1500.0
         */

        private String price_1;
        private String price_2;
        private String price_3;

        public String getPrice_1() {
            return price_1;
        }

        public void setPrice_1(String price_1) {
            this.price_1 = price_1;
        }

        public String getPrice_2() {
            return price_2;
        }

        public void setPrice_2(String price_2) {
            this.price_2 = price_2;
        }

        public String getPrice_3() {
            return price_3;
        }

        public void setPrice_3(String price_3) {
            this.price_3 = price_3;
        }
    }
}
