package com.jhhscm.platform.event;

import com.jhhscm.platform.fragment.Mechanics.bean.GetGoodsByBrandBean;
import com.jhhscm.platform.fragment.home.AdBean;
import com.jhhscm.platform.tool.EventBusUtil;

public class JumpEvent implements EventBusUtil.IEvent {
    private AdBean.ResultBean resultBean;
    private String type;

    public JumpEvent(String type, AdBean.ResultBean resultBean) {
        this.type = type;
        this.resultBean = resultBean;
    }

    public AdBean.ResultBean getResultBean() {
        return resultBean;
    }

    public void setResultBean(AdBean.ResultBean resultBean) {
        this.resultBean = resultBean;
    }

    public JumpEvent(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}