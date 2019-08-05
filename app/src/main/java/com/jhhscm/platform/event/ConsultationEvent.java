package com.jhhscm.platform.event;

import com.jhhscm.platform.tool.EventBusUtil;

public class ConsultationEvent implements EventBusUtil.IEvent {
    public String phone;

    public ConsultationEvent(String phone) {
        this.phone = phone;
    }
}
