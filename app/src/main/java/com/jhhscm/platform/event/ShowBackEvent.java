package com.jhhscm.platform.event;

import com.jhhscm.platform.tool.EventBusUtil;

public class ShowBackEvent implements EventBusUtil.IEvent {
    private int type;//租赁  1； 机械  2；底部跳转 0；

    public ShowBackEvent(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
