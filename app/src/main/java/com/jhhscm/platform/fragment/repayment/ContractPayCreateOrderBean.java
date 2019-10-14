package com.jhhscm.platform.fragment.repayment;

public class ContractPayCreateOrderBean {

    /**
     * data : {"id":433,"payTime":"2019-10-14 10:14:25","orderCode":"20191014101430000746066736","userCode":"4000000034777111","contractCode":"20190916154410000287176657","repayNum":1,"contractPrice":0.03,"endTime":"2019-10-15 10:14:25","addTime":"2019-10-14 10:14:25","updateTime":"2019-10-14 10:14:25"}
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
         * id : 433
         * payTime : 2019-10-14 10:14:25
         * orderCode : 20191014101430000746066736
         * userCode : 4000000034777111
         * contractCode : 20190916154410000287176657
         * repayNum : 1
         * contractPrice : 0.03
         * endTime : 2019-10-15 10:14:25
         * addTime : 2019-10-14 10:14:25
         * updateTime : 2019-10-14 10:14:25
         */

        private String id;
        private String payTime;
        private String orderCode;
        private String userCode;
        private String contractCode;
        private int repayNum;
        private double contractPrice;
        private String endTime;
        private String addTime;
        private String updateTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPayTime() {
            return payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public String getUserCode() {
            return userCode;
        }

        public void setUserCode(String userCode) {
            this.userCode = userCode;
        }

        public String getContractCode() {
            return contractCode;
        }

        public void setContractCode(String contractCode) {
            this.contractCode = contractCode;
        }

        public int getRepayNum() {
            return repayNum;
        }

        public void setRepayNum(int repayNum) {
            this.repayNum = repayNum;
        }

        public double getContractPrice() {
            return contractPrice;
        }

        public void setContractPrice(double contractPrice) {
            this.contractPrice = contractPrice;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
