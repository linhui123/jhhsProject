package com.jhhscm.platform.event;

import com.jhhscm.platform.fragment.Mechanics.push.UpdateImageBean;
import com.jhhscm.platform.tool.EventBusUtil;

import java.util.List;

public class ImageUpdataEvent implements EventBusUtil.IEvent {
    private  List<UpdateImageBean> updateImageBean;
    private int type=0;// 0,店铺提交订单上传票据；
    private boolean success = true;

    public ImageUpdataEvent(List<UpdateImageBean> updateImageBean, int type) {
        this.updateImageBean = updateImageBean;
        this.type = type;
    }

    public ImageUpdataEvent(List<UpdateImageBean> updateImageBean, int type, boolean success) {
        this.updateImageBean = updateImageBean;
        this.type = type;
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<UpdateImageBean> getUpdateImageBean() {
        return updateImageBean;
    }

    public void setUpdateImageBean(List<UpdateImageBean> updateImageBean) {
        this.updateImageBean = updateImageBean;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

