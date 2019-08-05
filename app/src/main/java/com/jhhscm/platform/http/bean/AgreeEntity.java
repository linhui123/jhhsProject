package com.jhhscm.platform.http.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/9.
 * 控制数据格式 这个根据需求更改
 *
 *
 */

public class AgreeEntity implements Serializable {

    /**
     * AGREE_CHANGE : -1
     */

    private String AGREE_CHANGE;

    public String getAGREE_CHANGE() {
        return AGREE_CHANGE;
    }

    public void setAGREE_CHANGE(String AGREE_CHANGE) {
        this.AGREE_CHANGE = AGREE_CHANGE;
    }
}
