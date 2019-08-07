package com.jhhscm.platform.fragment.casebase;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/10/17/017.
 */

public class FindCostomerByNameMhEntity implements Serializable {

    /**
     * RECORD : {"CURRENTPAGE":1,"TOTALRESULT":3,"SHOWCOUNT":5}
     * CUSTOMERDETAILS : [{"TIME":"2018-10-04","NAME":"DRHJY的滴","P_SUM":1,"IMG_URL":"1110101","CASE_ID":2},{"TIME":"2018-10-11","NAME":"htydh","P_SUM":1,"IMG_URL":"1110101","CASE_ID":6},{"TIME":"2018-10-05","NAME":"fsreyghtr","P_SUM":1,"IMG_URL":"1110101","CASE_ID":7}]
     */

    private RECORDBean RECORD;
    private List<CUSTOMERDETAILSBean> CASE_PAGE1;

    public RECORDBean getRECORD() {
        return RECORD;
    }

    public void setRECORD(RECORDBean RECORD) {
        this.RECORD = RECORD;
    }

    public List<CUSTOMERDETAILSBean> getCUSTOMERDETAILS() {
        return CASE_PAGE1;
    }

    public void setCUSTOMERDETAILS(List<CUSTOMERDETAILSBean> CUSTOMERDETAILS) {
        this.CASE_PAGE1 = CUSTOMERDETAILS;
    }

    public static class RECORDBean implements Serializable {
        /**
         * CURRENTPAGE : 1
         * TOTALRESULT : 3
         * SHOWCOUNT : 5
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

    public static class CUSTOMERDETAILSBean implements Serializable {
        /**
         * TIME : 2018-10-04
         * NAME : DRHJY的滴
         * P_SUM : 1
         * IMG_URL : 1110101
         * CASE_ID : 2
         */

        private String TIME;
        private String NAME;
        private int P_SUM;
        private String IMG_URL;
        private String CASE_ID;

        public String getTIME() {
            return TIME;
        }

        public void setTIME(String TIME) {
            this.TIME = TIME;
        }

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }

        public int getP_SUM() {
            return P_SUM;
        }

        public void setP_SUM(int p_SUM) {
            P_SUM = p_SUM;
        }

        public String getCASE_ID() {
            return CASE_ID;
        }

        public void setCASE_ID(String CASE_ID) {
            this.CASE_ID = CASE_ID;
        }

        public String getIMG_URL() {
            return IMG_URL;
        }

        public void setIMG_URL(String IMG_URL) {
            this.IMG_URL = IMG_URL;
        }
    }
}
