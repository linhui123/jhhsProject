package com.jhhscm.platform.event;

import com.jhhscm.platform.fragment.Mechanics.bean.GetGoodsByBrandBean;
import com.jhhscm.platform.tool.EventBusUtil;

public class JumpEvent implements EventBusUtil.IEvent {

    private String type;
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