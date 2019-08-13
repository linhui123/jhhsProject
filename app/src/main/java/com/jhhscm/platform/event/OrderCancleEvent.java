package com.jhhscm.platform.event;

import com.jhhscm.platform.tool.EventBusUtil;

public class OrderCancleEvent implements EventBusUtil.IEvent {
    public String order_code;

    public OrderCancleEvent(String order_code) {
        this.order_code = order_code;
    }
}

