package com.jhhscm.platform.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/10/17/017.
 */

public class FindCaseGGEntity implements Serializable {

    /**
     * CASE1 : {"CURRENTPAGE":1,"TOTALRESULT":2,"SHOWCOUNT":10}
     * CASE_PAGE1 : [{"NAME":"DAES得的","P_SUM":2,"IMG_URL":"1110101","CASE_ID":1},{"NAME":"DRHJY的滴","P_SUM":1,"IMG_URL":"1110101","CASE_ID":2}]
     */

    private CASE1Bean CASE1;
    private List<CASEPAGE1Bean> CASE_PAGE1;

    public CASE1Bean getCASE1() {
        return CASE1;
    }

    public void setCASE1(CASE1Bean CASE1) {
        this.CASE1 = CASE1;
    }

    public List<CASEPAGE1Bean> getCASE_PAGE1() {
        return CASE_PAGE1;
    }

    public void setCASE_PAGE1(List<CASEPAGE1Bean> CASE_PAGE1) {
        this.CASE_PAGE1 = CASE_PAGE1;
    }

    public static class CASE1Bean implements Serializable {
        /**
         * CURRENTPAGE : 1
         * TOTALRESULT : 2
         * SHOWCOUNT : 10
         */

        private int CURRENTPAGE;
        private int TOTALRESULT;
        private int SHOWCOUNT;

        public int getCURRENTPAGE() {
            return CURRENTPAGE;
        }

        public void setCURRENTPAGE(int CURRENTPAGE) {
            this.CURRENTPAGE = CURRENTPAGE;
        }

        public int getTOTALRESULT() {
            return TOTALRESULT;
        }

        public void setTOTALRESULT(int TOTALRESULT) {
            this.TOTALRESULT = TOTALRESULT;
        }

        public int getSHOWCOUNT() {
            return SHOWCOUNT;
        }

        public void setSHOWCOUNT(int SHOWCOUNT) {
            this.SHOWCOUNT = SHOWCOUNT;
        }
    }

    public static class CASEPAGE1Bean implements Serializable {
        /**
         * NAME : DAES得的
         * P_SUM : 2
         * IMG_URL : 1110101
         * CASE_ID : 1
         */

        private String NAME;
        private int P_SUM;
        private String IMG_URL;
        private String CASE_ID;

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }

        public int getP_SUM() {
            return P_SUM;
        }

        public void setP_SUM(int P_SUM) {
            this.P_SUM = P_SUM;
        }

        public String getIMG_URL() {
            return IMG_URL;
        }

        public void setIMG_URL(String IMG_URL) {
            this.IMG_URL = IMG_URL;
        }

        public String getCASE_ID() {
            return CASE_ID;
        }

        public void setCASE_ID(String CASE_ID) {
            this.CASE_ID = CASE_ID;
        }
    }
}
