package com.jhhscm.platform.fragment.my;

public class UserCenterBean {

    /**
     * result : {"user_points":0,"is_bus":0,"bus_points":0,"coupons_count":0,"bus_pointdesc":"","collect_count":3,"bususer_count":0}
     */

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * user_points : 0
         * is_bus : 0
         * bus_points : 0
         * coupons_count : 0
         * bus_pointdesc :
         * collect_count : 3
         * bususer_count : 0
         */

        private int user_points;
        private int is_bus;
        private int bus_points;
        private int coupons_count;
        private String bus_pointdesc;
        private String user_pointdesc;
        private int collect_count;
        private int bususer_count;

        public String getUser_pointdesc() {
            return user_pointdesc;
        }

        public void setUser_pointdesc(String user_pointdesc) {
            this.user_pointdesc = user_pointdesc;
        }

        public int getUser_points() {
            return user_points;
        }

        public void setUser_points(int user_points) {
            this.user_points = user_points;
        }

        public int getIs_bus() {
            return is_bus;
        }

        public void setIs_bus(int is_bus) {
            this.is_bus = is_bus;
        }

        public int getBus_points() {
            return bus_points;
        }

        public void setBus_points(int bus_points) {
            this.bus_points = bus_points;
        }

        public int getCoupons_count() {
            return coupons_count;
        }

        public void setCoupons_count(int coupons_count) {
            this.coupons_count = coupons_count;
        }

        public String getBus_pointdesc() {
            return bus_pointdesc;
        }

        public void setBus_pointdesc(String bus_pointdesc) {
            this.bus_pointdesc = bus_pointdesc;
        }

        public int getCollect_count() {
            return collect_count;
        }

        public void setCollect_count(int collect_count) {
            this.collect_count = collect_count;
        }

        public int getBususer_count() {
            return bususer_count;
        }

        public void setBususer_count(int bususer_count) {
            this.bususer_count = bususer_count;
        }
    }
}
