package com.jhhscm.platform.fragment.repayment;

import java.util.List;

public class ContractDetailBean {

    /**
     * moneyPlan : [{"id":98,"contractCode":"20190916154410000287176657","state":1,"num":1,"repayTime":"2019-10-01 10:39:22","rentMoney":0.03,"principal":0.01,"interest":0.02,"defaultInterest":0,"overdueDay":0,"residuePrincipal":0,"payPrincipal":0,"payInterest":0,"payDefaultInterest":0,"paySumMoney":0,"taxDefaultInterest":0,"noPayMoney":0,"addTime":"2019-09-29 10:39:22","updateTime":"2019-09-29 10:39:22","deleted":0},{"id":116,"contractCode":"20190916154410000287176657","state":1,"num":1,"repayTime":"2019-10-01 10:39:22","rentMoney":0.38,"principal":0.36,"interest":0.02,"defaultInterest":0,"overdueDay":0,"residuePrincipal":0,"payPrincipal":0,"payInterest":0,"payDefaultInterest":0,"paySumMoney":0,"taxDefaultInterest":0,"noPayMoney":0,"addTime":"2019-09-29 10:39:22","updateTime":"2019-09-29 10:39:22","deleted":0},{"id":99,"contractCode":"20190916154410000287176657","state":1,"num":2,"repayTime":"2019-10-01 10:39:22","rentMoney":0.38,"principal":0.36,"interest":0.02,"defaultInterest":0,"overdueDay":0,"residuePrincipal":0,"payPrincipal":0,"payInterest":0,"payDefaultInterest":0,"paySumMoney":0,"taxDefaultInterest":0,"noPayMoney":0,"addTime":"2019-09-29 10:39:22","updateTime":"2019-09-29 10:39:22","deleted":0},{"id":117,"contractCode":"20190916154410000287176657","state":1,"num":2,"repayTime":"2019-10-01 10:39:22","rentMoney":0.38,"principal":0.36,"interest":0.02,"defaultInterest":0,"overdueDay":0,"residuePrincipal":0,"payPrincipal":0,"payInterest":0,"payDefaultInterest":0,"paySumMoney":0,"taxDefaultInterest":0,"noPayMoney":0,"addTime":"2019-09-29 10:39:22","updateTime":"2019-09-29 10:39:22","deleted":0},{"id":118,"contractCode":"20190916154410000287176657","state":1,"num":3,"repayTime":"2019-10-01 10:39:22","rentMoney":0.38,"principal":0.36,"interest":0.02,"defaultInterest":0,"overdueDay":0,"residuePrincipal":0,"payPrincipal":0,"payInterest":0,"payDefaultInterest":0,"paySumMoney":0,"taxDefaultInterest":0,"noPayMoney":0,"addTime":"2019-09-29 10:39:22","updateTime":"2019-09-29 10:39:22","deleted":0},{"id":100,"contractCode":"20190916154410000287176657","state":1,"num":3,"repayTime":"2019-10-01 10:39:22","rentMoney":0.38,"principal":0.36,"interest":0.02,"defaultInterest":0,"overdueDay":0,"residuePrincipal":0,"payPrincipal":0,"payInterest":0,"payDefaultInterest":0,"paySumMoney":0,"taxDefaultInterest":0,"noPayMoney":0,"addTime":"2019-09-29 10:39:22","updateTime":"2019-09-29 10:39:22","deleted":0},{"id":119,"contractCode":"20190916154410000287176657","state":1,"num":4,"repayTime":"2019-10-01 10:39:22","rentMoney":0.38,"principal":0.36,"interest":0.02,"defaultInterest":0,"overdueDay":0,"residuePrincipal":0,"payPrincipal":0,"payInterest":0,"payDefaultInterest":0,"paySumMoney":0,"taxDefaultInterest":0,"noPayMoney":0,"addTime":"2019-09-29 10:39:22","updateTime":"2019-09-29 10:39:22","deleted":0},{"id":101,"contractCode":"20190916154410000287176657","state":1,"num":4,"repayTime":"2019-10-01 10:39:22","rentMoney":0.38,"principal":0.36,"interest":0.02,"defaultInterest":0,"overdueDay":0,"residuePrincipal":0,"payPrincipal":0,"payInterest":0,"payDefaultInterest":0,"paySumMoney":0,"taxDefaultInterest":0,"noPayMoney":0,"addTime":"2019-09-29 10:39:22","updateTime":"2019-09-29 10:39:22","deleted":0},{"id":102,"contractCode":"20190916154410000287176657","state":1,"num":5,"repayTime":"2019-10-01 10:39:22","rentMoney":0.38,"principal":0.36,"interest":0.02,"defaultInterest":0,"overdueDay":0,"residuePrincipal":0,"payPrincipal":0,"payInterest":0,"payDefaultInterest":0,"paySumMoney":0,"taxDefaultInterest":0,"noPayMoney":0,"addTime":"2019-09-29 10:39:22","updateTime":"2019-09-29 10:39:22","deleted":0},{"id":120,"contractCode":"20190916154410000287176657","state":1,"num":5,"repayTime":"2019-10-01 10:39:22","rentMoney":0.38,"principal":0.36,"interest":0.02,"defaultInterest":0,"overdueDay":0,"residuePrincipal":0,"payPrincipal":0,"payInterest":0,"payDefaultInterest":0,"paySumMoney":0,"taxDefaultInterest":0,"noPayMoney":0,"addTime":"2019-09-29 10:39:22","updateTime":"2019-09-29 10:39:22","deleted":0},{"id":103,"contractCode":"20190916154410000287176657","state":1,"num":6,"repayTime":"2019-10-01 10:39:22","rentMoney":0.37,"principal":0.36,"interest":0.01,"defaultInterest":0,"overdueDay":0,"residuePrincipal":0,"payPrincipal":0,"payInterest":0,"payDefaultInterest":0,"paySumMoney":0,"taxDefaultInterest":0,"noPayMoney":0,"addTime":"2019-09-29 10:39:22","updateTime":"2019-09-29 10:39:22","deleted":0},{"id":121,"contractCode":"20190916154410000287176657","state":1,"num":6,"repayTime":"2019-10-01 10:39:22","rentMoney":0.37,"principal":0.36,"interest":0.01,"defaultInterest":0,"overdueDay":0,"residuePrincipal":0,"payPrincipal":0,"payInterest":0,"payDefaultInterest":0,"paySumMoney":0,"taxDefaultInterest":0,"noPayMoney":0,"addTime":"2019-09-29 10:39:22","updateTime":"2019-09-29 10:39:22","deleted":0},{"id":104,"contractCode":"20190916154410000287176657","state":1,"num":7,"repayTime":"2019-10-01 10:39:22","rentMoney":0.37,"principal":0.36,"interest":0.01,"defaultInterest":0,"overdueDay":0,"residuePrincipal":0,"payPrincipal":0,"payInterest":0,"payDefaultInterest":0,"paySumMoney":0,"taxDefaultInterest":0,"noPayMoney":0,"addTime":"2019-09-29 10:39:22","updateTime":"2019-09-29 10:39:22","deleted":0},{"id":122,"contractCode":"20190916154410000287176657","state":1,"num":7,"repayTime":"2019-10-01 10:39:22","rentMoney":0.37,"principal":0.36,"interest":0.01,"defaultInterest":0,"overdueDay":0,"residuePrincipal":0,"payPrincipal":0,"payInterest":0,"payDefaultInterest":0,"paySumMoney":0,"taxDefaultInterest":0,"noPayMoney":0,"addTime":"2019-09-29 10:39:22","updateTime":"2019-09-29 10:39:22","deleted":0},{"id":105,"contractCode":"20190916154410000287176657","state":1,"num":8,"repayTime":"2019-10-01 10:39:22","rentMoney":0.37,"principal":0.36,"interest":0.01,"defaultInterest":0,"overdueDay":0,"residuePrincipal":0,"payPrincipal":0,"payInterest":0,"payDefaultInterest":0,"paySumMoney":0,"taxDefaultInterest":0,"noPayMoney":0,"addTime":"2019-09-29 10:39:22","updateTime":"2019-09-29 10:39:22","deleted":0},{"id":123,"contractCode":"20190916154410000287176657","state":1,"num":8,"repayTime":"2019-10-01 10:39:22","rentMoney":0.37,"principal":0.36,"interest":0.01,"defaultInterest":0,"overdueDay":0,"residuePrincipal":0,"payPrincipal":0,"payInterest":0,"payDefaultInterest":0,"paySumMoney":0,"taxDefaultInterest":0,"noPayMoney":0,"addTime":"2019-09-29 10:39:22","updateTime":"2019-09-29 10:39:22","deleted":0},{"id":106,"contractCode":"20190916154410000287176657","state":1,"num":9,"repayTime":"2019-10-01 10:39:22","rentMoney":0.37,"principal":0.36,"interest":0.01,"defaultInterest":0,"overdueDay":0,"residuePrincipal":0,"payPrincipal":0,"payInterest":0,"payDefaultInterest":0,"paySumMoney":0,"taxDefaultInterest":0,"noPayMoney":0,"addTime":"2019-09-29 10:39:22","updateTime":"2019-09-29 10:39:22","deleted":0},{"id":124,"contractCode":"20190916154410000287176657","state":1,"num":9,"repayTime":"2019-10-01 10:39:22","rentMoney":0.37,"principal":0.36,"interest":0.01,"defaultInterest":0,"overdueDay":0,"residuePrincipal":0,"payPrincipal":0,"payInterest":0,"payDefaultInterest":0,"paySumMoney":0,"taxDefaultInterest":0,"noPayMoney":0,"addTime":"2019-09-29 10:39:22","updateTime":"2019-09-29 10:39:22","deleted":0},{"id":107,"contractCode":"20190916154410000287176657","state":1,"num":10,"repayTime":"2019-10-01 10:39:22","rentMoney":0.37,"principal":0.36,"interest":0.01,"defaultInterest":0,"overdueDay":0,"residuePrincipal":0,"payPrincipal":0,"payInterest":0,"payDefaultInterest":0,"paySumMoney":0,"taxDefaultInterest":0,"noPayMoney":0,"addTime":"2019-09-29 10:39:22","updateTime":"2019-09-29 10:39:22","deleted":0},{"id":125,"contractCode":"20190916154410000287176657","state":1,"num":10,"repayTime":"2019-10-01 10:39:22","rentMoney":0.37,"principal":0.36,"interest":0.01,"defaultInterest":0,"overdueDay":0,"residuePrincipal":0,"payPrincipal":0,"payInterest":0,"payDefaultInterest":0,"paySumMoney":0,"taxDefaultInterest":0,"noPayMoney":0,"addTime":"2019-09-29 10:39:22","updateTime":"2019-09-29 10:39:22","deleted":0},{"id":126,"contractCode":"20190916154410000287176657","state":1,"num":11,"repayTime":"2019-10-01 10:39:22","rentMoney":0.37,"principal":0.36,"interest":0.01,"defaultInterest":0,"overdueDay":0,"residuePrincipal":0,"payPrincipal":0,"payInterest":0,"payDefaultInterest":0,"paySumMoney":0,"taxDefaultInterest":0,"noPayMoney":0,"addTime":"2019-09-29 10:39:22","updateTime":"2019-09-29 10:39:22","deleted":0},{"id":108,"contractCode":"20190916154410000287176657","state":1,"num":11,"repayTime":"2019-10-01 10:39:22","rentMoney":0.37,"principal":0.36,"interest":0.01,"defaultInterest":0,"overdueDay":0,"residuePrincipal":0,"payPrincipal":0,"payInterest":0,"payDefaultInterest":0,"paySumMoney":0,"taxDefaultInterest":0,"noPayMoney":0,"addTime":"2019-09-29 10:39:22","updateTime":"2019-09-29 10:39:22","deleted":0},{"id":127,"contractCode":"20190916154410000287176657","state":1,"num":12,"repayTime":"2019-10-01 10:39:22","rentMoney":0.37,"principal":0.36,"interest":0.01,"defaultInterest":0,"overdueDay":0,"residuePrincipal":0,"payPrincipal":0,"payInterest":0,"payDefaultInterest":0,"paySumMoney":0,"taxDefaultInterest":0,"noPayMoney":0,"addTime":"2019-09-29 10:39:22","updateTime":"2019-09-29 10:39:22","deleted":0},{"id":109,"contractCode":"20190916154410000287176657","state":1,"num":12,"repayTime":"2019-10-01 10:39:22","rentMoney":0.37,"principal":0.36,"interest":0.01,"defaultInterest":0,"overdueDay":0,"residuePrincipal":0,"payPrincipal":0,"payInterest":0,"payDefaultInterest":0,"paySumMoney":0,"taxDefaultInterest":0,"noPayMoney":0,"addTime":"2019-09-29 10:39:22","updateTime":"2019-09-29 10:39:22","deleted":0},{"id":110,"contractCode":"20190916154410000287176657","state":1,"num":13,"repayTime":"2019-10-01 10:39:22","rentMoney":0.36,"principal":0.36,"interest":0,"defaultInterest":0,"overdueDay":0,"residuePrincipal":0,"payPrincipal":0,"payInterest":0,"payDefaultInterest":0,"paySumMoney":0,"taxDefaultInterest":0,"noPayMoney":0,"addTime":"2019-09-29 10:39:22","updateTime":"2019-09-29 10:39:22","deleted":0},{"id":128,"contractCode":"20190916154410000287176657","state":1,"num":13,"repayTime":"2019-10-01 10:39:22","rentMoney":0.36,"principal":0.36,"interest":0,"defaultInterest":0,"overdueDay":0,"residuePrincipal":0,"payPrincipal":0,"payInterest":0,"payDefaultInterest":0,"paySumMoney":0,"taxDefaultInterest":0,"noPayMoney":0,"addTime":"2019-09-29 10:39:22","updateTime":"2019-09-29 10:39:22","deleted":0},{"id":111,"contractCode":"20190916154410000287176657","state":1,"num":14,"repayTime":"2019-10-01 10:39:22","rentMoney":0.36,"principal":0.36,"interest":0,"defaultInterest":0,"overdueDay":0,"residuePrincipal":0,"payPrincipal":0,"payInterest":0,"payDefaultInterest":0,"paySumMoney":0,"taxDefaultInterest":0,"noPayMoney":0,"addTime":"2019-09-29 10:39:22","updateTime":"2019-09-29 10:39:22","deleted":0},{"id":129,"contractCode":"20190916154410000287176657","state":1,"num":14,"repayTime":"2019-10-01 10:39:22","rentMoney":0.36,"principal":0.36,"interest":0,"defaultInterest":0,"overdueDay":0,"residuePrincipal":0,"payPrincipal":0,"payInterest":0,"payDefaultInterest":0,"paySumMoney":0,"taxDefaultInterest":0,"noPayMoney":0,"addTime":"2019-09-29 10:39:22","updateTime":"2019-09-29 10:39:22","deleted":0},{"id":112,"contractCode":"20190916154410000287176657","state":1,"num":15,"repayTime":"2019-10-01 10:39:22","rentMoney":0.36,"principal":0.36,"interest":0,"defaultInterest":0,"overdueDay":0,"residuePrincipal":0,"payPrincipal":0,"payInterest":0,"payDefaultInterest":0,"paySumMoney":0,"taxDefaultInterest":0,"noPayMoney":0,"addTime":"2019-09-29 10:39:22","updateTime":"2019-09-29 10:39:22","deleted":0},{"id":130,"contractCode":"20190916154410000287176657","state":1,"num":15,"repayTime":"2019-10-01 10:39:22","rentMoney":0.36,"principal":0.36,"interest":0,"defaultInterest":0,"overdueDay":0,"residuePrincipal":0,"payPrincipal":0,"payInterest":0,"payDefaultInterest":0,"paySumMoney":0,"taxDefaultInterest":0,"noPayMoney":0,"addTime":"2019-09-29 10:39:22","updateTime":"2019-09-29 10:39:22","deleted":0},{"id":113,"contractCode":"20190916154410000287176657","state":1,"num":16,"repayTime":"2019-10-01 10:39:22","rentMoney":0.36,"principal":0.36,"interest":0,"defaultInterest":0,"overdueDay":0,"residuePrincipal":0,"payPrincipal":0,"payInterest":0,"payDefaultInterest":0,"paySumMoney":0,"taxDefaultInterest":0,"noPayMoney":0,"addTime":"2019-09-29 10:39:22","updateTime":"2019-09-29 10:39:22","deleted":0},{"id":131,"contractCode":"20190916154410000287176657","state":1,"num":16,"repayTime":"2019-10-01 10:39:22","rentMoney":0.36,"principal":0.36,"interest":0,"defaultInterest":0,"overdueDay":0,"residuePrincipal":0,"payPrincipal":0,"payInterest":0,"payDefaultInterest":0,"paySumMoney":0,"taxDefaultInterest":0,"noPayMoney":0,"addTime":"2019-09-29 10:39:22","updateTime":"2019-09-29 10:39:22","deleted":0},{"id":114,"contractCode":"20190916154410000287176657","state":1,"num":17,"repayTime":"2019-10-01 10:39:22","rentMoney":0.36,"principal":0.36,"interest":0,"defaultInterest":0,"overdueDay":0,"residuePrincipal":0,"payPrincipal":0,"payInterest":0,"payDefaultInterest":0,"paySumMoney":0,"taxDefaultInterest":0,"noPayMoney":0,"addTime":"2019-09-29 10:39:22","updateTime":"2019-09-29 10:39:22","deleted":0},{"id":132,"contractCode":"20190916154410000287176657","state":1,"num":17,"repayTime":"2019-10-01 10:39:22","rentMoney":0.36,"principal":0.36,"interest":0,"defaultInterest":0,"overdueDay":0,"residuePrincipal":0,"payPrincipal":0,"payInterest":0,"payDefaultInterest":0,"paySumMoney":0,"taxDefaultInterest":0,"noPayMoney":0,"addTime":"2019-09-29 10:39:22","updateTime":"2019-09-29 10:39:22","deleted":0},{"id":115,"contractCode":"20190916154410000287176657","state":1,"num":18,"repayTime":"2019-10-01 10:39:22","rentMoney":0.36,"principal":0.36,"interest":0,"defaultInterest":0,"overdueDay":0,"residuePrincipal":0,"payPrincipal":0,"payInterest":0,"payDefaultInterest":0,"paySumMoney":0,"taxDefaultInterest":0,"noPayMoney":0,"addTime":"2019-09-29 10:39:22","updateTime":"2019-09-29 10:39:22","deleted":0},{"id":133,"contractCode":"20190916154410000287176657","state":1,"num":18,"repayTime":"2019-10-01 10:39:22","rentMoney":0.36,"principal":0.36,"interest":0,"defaultInterest":0,"overdueDay":0,"residuePrincipal":0,"payPrincipal":0,"payInterest":0,"payDefaultInterest":0,"paySumMoney":0,"taxDefaultInterest":0,"noPayMoney":0,"addTime":"2019-09-29 10:39:22","updateTime":"2019-09-29 10:39:22","deleted":0}]
     * count : 36
     */

    private int count;
    private List<MoneyPlanBean> moneyPlan;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<MoneyPlanBean> getMoneyPlan() {
        return moneyPlan;
    }

    public void setMoneyPlan(List<MoneyPlanBean> moneyPlan) {
        this.moneyPlan = moneyPlan;
    }

    public static class MoneyPlanBean {
        /**
         * id : 98
         * contractCode : 20190916154410000287176657
         * state : 1
         * num : 1
         * repayTime : 2019-10-01 10:39:22
         * rentMoney : 0.03
         * principal : 0.01
         * interest : 0.02
         * defaultInterest : 0
         * overdueDay : 0
         * residuePrincipal : 0
         * payPrincipal : 0
         * payInterest : 0
         * payDefaultInterest : 0
         * paySumMoney : 0
         * taxDefaultInterest : 0
         * noPayMoney : 0
         * addTime : 2019-09-29 10:39:22
         * updateTime : 2019-09-29 10:39:22
         * deleted : 0
         */

        private String id;
        private String contractCode;
        private int state;
        private int num;
        private String repayTime;
        private double rentMoney;
        private double principal;
        private double interest;
        private int defaultInterest;
        private int overdueDay;
        private int residuePrincipal;
        private int payPrincipal;
        private int payInterest;
        private int payDefaultInterest;
        private int paySumMoney;
        private int taxDefaultInterest;
        private int noPayMoney;
        private String addTime;
        private String updateTime;
        private int deleted;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContractCode() {
            return contractCode;
        }

        public void setContractCode(String contractCode) {
            this.contractCode = contractCode;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getRepayTime() {
            return repayTime;
        }

        public void setRepayTime(String repayTime) {
            this.repayTime = repayTime;
        }

        public double getRentMoney() {
            return rentMoney;
        }

        public void setRentMoney(double rentMoney) {
            this.rentMoney = rentMoney;
        }

        public double getPrincipal() {
            return principal;
        }

        public void setPrincipal(double principal) {
            this.principal = principal;
        }

        public double getInterest() {
            return interest;
        }

        public void setInterest(double interest) {
            this.interest = interest;
        }

        public int getDefaultInterest() {
            return defaultInterest;
        }

        public void setDefaultInterest(int defaultInterest) {
            this.defaultInterest = defaultInterest;
        }

        public int getOverdueDay() {
            return overdueDay;
        }

        public void setOverdueDay(int overdueDay) {
            this.overdueDay = overdueDay;
        }

        public int getResiduePrincipal() {
            return residuePrincipal;
        }

        public void setResiduePrincipal(int residuePrincipal) {
            this.residuePrincipal = residuePrincipal;
        }

        public int getPayPrincipal() {
            return payPrincipal;
        }

        public void setPayPrincipal(int payPrincipal) {
            this.payPrincipal = payPrincipal;
        }

        public int getPayInterest() {
            return payInterest;
        }

        public void setPayInterest(int payInterest) {
            this.payInterest = payInterest;
        }

        public int getPayDefaultInterest() {
            return payDefaultInterest;
        }

        public void setPayDefaultInterest(int payDefaultInterest) {
            this.payDefaultInterest = payDefaultInterest;
        }

        public int getPaySumMoney() {
            return paySumMoney;
        }

        public void setPaySumMoney(int paySumMoney) {
            this.paySumMoney = paySumMoney;
        }

        public int getTaxDefaultInterest() {
            return taxDefaultInterest;
        }

        public void setTaxDefaultInterest(int taxDefaultInterest) {
            this.taxDefaultInterest = taxDefaultInterest;
        }

        public int getNoPayMoney() {
            return noPayMoney;
        }

        public void setNoPayMoney(int noPayMoney) {
            this.noPayMoney = noPayMoney;
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

        public int getDeleted() {
            return deleted;
        }

        public void setDeleted(int deleted) {
            this.deleted = deleted;
        }
    }
}
