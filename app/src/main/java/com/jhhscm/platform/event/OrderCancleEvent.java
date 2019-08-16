package com.jhhscm.platform.event;

import com.jhhscm.platform.tool.EventBusUtil;

public class OrderCancleEvent implements EventBusUtil.IEvent {
    public String order_code;
    public String type;
    public OrderCancleEvent(String order_code,String type) {
        this.order_code = order_code;
        this.type = type;
    }

    public String getOrder_code() {
        return order_code;
    }

    public void setOrder_code(String order_code) {
        this.order_code = order_code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

