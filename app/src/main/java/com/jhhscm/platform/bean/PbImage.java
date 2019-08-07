package com.jhhscm.platform.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/10/16/016.
 */

public class PbImage implements Serializable {

    private String mUrl;
    private String mToken;

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public String getmToken() {
        return mToken;
    }

    public void setmToken(String mToken) {
        this.mToken = mToken;
    }
}
