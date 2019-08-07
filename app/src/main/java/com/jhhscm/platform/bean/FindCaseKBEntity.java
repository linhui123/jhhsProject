package com.jhhscm.platform.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/10/23/023.
 */

public class FindCaseKBEntity implements Serializable {

    /**
     * NAME : 腹股沟管
     * ADDRESS : [{"IMG_URL":"http://blsfiles.oss-cn-qingdao.aliyuncs.com/appimages/023f90cd6be444f3b24e8b9f33a5ed85.png?Expires=1862970515&OSSAccessKeyId=LTAIcoyXo1U2nZ2l&Signature=EoCRC98CmkO76vnczDG%2BMAyUsKY%3D","MENU_CATALOGUES":"appimages/023f90cd6be444f3b24e8b9f33a5ed85.png"}]
     * LEVEL : 1
     * REMARK : 发广告发发发
     */

    private String NAME;
    private String LEVEL;
    private String REMARK;
    private List<ADDRESSBean> ADDRESS;

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getLEVEL() {
        return LEVEL;
    }

    public void setLEVEL(String LEVEL) {
        this.LEVEL = LEVEL;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }

    public List<ADDRESSBean> getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(List<ADDRESSBean> ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public static class ADDRESSBean {
        /**
         * IMG_URL : http://blsfiles.oss-cn-qingdao.aliyuncs.com/appimages/023f90cd6be444f3b24e8b9f33a5ed85.png?Expires=1862970515&OSSAccessKeyId=LTAIcoyXo1U2nZ2l&Signature=EoCRC98CmkO76vnczDG%2BMAyUsKY%3D
         * MENU_CATALOGUES : appimages/023f90cd6be444f3b24e8b9f33a5ed85.png
         */

        private String IMG_URL;
        private String MENU_CATALOGUES;
        private String PHOTO_ID;

        public String getPHOTO_ID() {
            return PHOTO_ID;
        }

        public void setPHOTO_ID(String PHOTO_ID) {
            this.PHOTO_ID = PHOTO_ID;
        }

        public String getIMG_URL() {
            return IMG_URL;
        }

        public void setIMG_URL(String IMG_URL) {
            this.IMG_URL = IMG_URL;
        }

        public String getMENU_CATALOGUES() {
            return MENU_CATALOGUES;
        }

        public void setMENU_CATALOGUES(String MENU_CATALOGUES) {
            this.MENU_CATALOGUES = MENU_CATALOGUES;
        }
    }
}
