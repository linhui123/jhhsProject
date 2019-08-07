package com.jhhscm.platform.bean;

import java.io.Serializable;

/**
 * 类说明：
 * 作者：huangqiuxin on 2016/6/17 14:27
 * 邮箱：648859026@qq.com
 */
public class UploadImage implements Serializable {
    private String imageUrl;
    private String imageToken;

    private String catalogues;
    private String allfilePath;

    private String IMG_URL;
    private String MENU_CATALOGUES;
    public UploadImage(String imageUrl){
        setImageUrl(imageUrl);
    }

    public UploadImage(String imageUrl, String imageToken){
        setImageUrl(imageUrl);
        setImageToken(imageToken);
    }


    public UploadImage(String imageUrl, String catalogues, String allfilePath, String imageToken){
        setImageUrl(imageUrl);
        setCatalogues(catalogues);
        setAllfilePath(allfilePath);
        setImageToken(imageToken);
    }

    public String getCatalogues() {
        return catalogues;
    }

    public void setCatalogues(String catalogues) {
        this.catalogues = catalogues;
    }

    public String getAllfilePath() {
        return allfilePath;
    }

    public void setAllfilePath(String allfilePath) {
        this.allfilePath = allfilePath;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageToken() {
        return imageToken;
    }

    public void setImageToken(String imageToken) {
        this.imageToken = imageToken;
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

    @Override
    public boolean equals(Object o) {
        if (o != null) {
            if (o instanceof UploadImage) {
                UploadImage other = (UploadImage) o;
                if (imageUrl != null && imageUrl.equals(other.imageUrl)) {
                    return true;
                }
            } else if (o instanceof String) {
                String other = (String) o;
                if (imageUrl != null && imageUrl.equals(other)) {
                    return true;
                }
            }
        }
        return false;
    }
}
