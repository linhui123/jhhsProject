package com.jhhscm.platform.event;

import com.jhhscm.platform.tool.EventBusUtil;

public class DelDeviceEvent implements EventBusUtil.IEvent {
    public String code;

    public DelDeviceEvent(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

