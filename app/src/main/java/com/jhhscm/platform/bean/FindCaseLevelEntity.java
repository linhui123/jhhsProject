package com.jhhscm.platform.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/10/17/017.
 */

public class FindCaseLevelEntity implements Serializable {


    private List<CASEPAGE2Bean> CASE_PAGE2;

    public List<CASEPAGE2Bean> getCASE_PAGE2() {
        return CASE_PAGE2;
    }

    public void setCASE_PAGE2(List<CASEPAGE2Bean> CASE_PAGE2) {
        this.CASE_PAGE2 = CASE_PAGE2;
    }

    public static class CASEPAGE2Bean implements Serializable {
        /**
         * CASELEVELNAME : M型一级
         * CASELEVEL : 1
         */

        private String CASELEVELNAME;
        private String CASELEVEL;

        public String getCASELEVELNAME() {
            return CASELEVELNAME;
        }

        public void setCASELEVELNAME(String CASELEVELNAME) {
            this.CASELEVELNAME = CASELEVELNAME;
        }

        public String getCASELEVEL() {
            return CASELEVEL;
        }

        public void setCASELEVEL(String CASELEVEL) {
            this.CASELEVEL = CASELEVEL;
        }
    }
}
