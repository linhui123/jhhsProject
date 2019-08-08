package com.jhhscm.platform.fragment.Mechanics.push;

import java.io.Serializable;

/**
 * Created by intel on 2018/12/25.
 */

public class UploadInvalidOrderImgEntity implements Serializable {

    /**
     * catalogues : invalidorder/02924adcbe1f4aec956aa54c974c2ef0.png
     * code : 0
     * allfilePath : http://blsfiles.oss-cn-qingdao.aliyuncs.com/invalidorder/02924adcbe1f4aec956aa54c974c2ef0.png?Expires=1861092886&OSSAccessKeyId=LTAIcoyXo1U2nZ2l&Signature=k7fmVXdL%2FoYwiAbhnvGyfrC48fY%3D
     {IMG_URL:xxx,MENU_CATALOGUES:xxx,}
     */

    private String catalogues;
    private String code;
    private String allfilePath;
    private String IMG_URL;
    private String MENU_CATALOGUES;

    public String getCatalogues() {
        return catalogues;
    }

    public void setCatalogues(String catalogues) {
        this.catalogues = catalogues;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAllfilePath() {
        return allfilePath;
    }

    public void setAllfilePath(String allfilePath) {
        this.allfilePath = allfilePath;
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
