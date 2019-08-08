package com.jhhscm.platform.fragment.Mechanics.push;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/1/11/011.
 */

public class UpdateImageBean implements Serializable {


    /**
     * MENU_CATALOGUES : 图片目录
     * IMG_URL : 图片地址
     * PATIENT_IMAGE_NODE : 节点编码
     */

    private String MENU_CATALOGUES;
    private String IMG_URL;
    private String PATIENT_IMAGE_NODE;

    public String getMENU_CATALOGUES() {
        return MENU_CATALOGUES;
    }

    public void setMENU_CATALOGUES(String MENU_CATALOGUES) {
        this.MENU_CATALOGUES = MENU_CATALOGUES;
    }

    public String getIMG_URL() {
        return IMG_URL;
    }

    public void setIMG_URL(String IMG_URL) {
        this.IMG_URL = IMG_URL;
    }

    public String getPATIENT_IMAGE_NODE() {
        return PATIENT_IMAGE_NODE;
    }

    public void setPATIENT_IMAGE_NODE(String PATIENT_IMAGE_NODE) {
        this.PATIENT_IMAGE_NODE = PATIENT_IMAGE_NODE;
    }
}
