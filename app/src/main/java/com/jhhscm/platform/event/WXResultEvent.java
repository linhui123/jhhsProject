package com.jhhscm.platform.event;

import com.jhhscm.platform.tool.EventBusUtil;

public class WXResultEvent implements EventBusUtil.IEvent {
    public int errCode;
    public String type;

    public WXResultEvent(int errCode, String type) {
        this.errCode = errCode;
        this.type = type;
    }
}