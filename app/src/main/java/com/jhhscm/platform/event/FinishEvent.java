package com.jhhscm.platform.event;

import com.jhhscm.platform.tool.EventBusUtil;

public class FinishEvent implements EventBusUtil.IEvent {
    private int type;//1 选择用户返回关闭；2 关闭购物车

    public FinishEvent() {
    }

    public FinishEvent(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
