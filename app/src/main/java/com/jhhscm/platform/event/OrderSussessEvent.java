package com.jhhscm.platform.event;

import com.jhhscm.platform.tool.EventBusUtil;

public class OrderSussessEvent implements EventBusUtil.IEvent {
    public String phone;

    public OrderSussessEvent(String phone) {
        this.phone = phone;
    }
}
