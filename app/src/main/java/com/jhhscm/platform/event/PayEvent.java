package com.jhhscm.platform.event;

import com.jhhscm.platform.tool.EventBusUtil;

public class PayEvent implements EventBusUtil.IEvent {
    public String order_code;
    public String type;
    public String price;
    public PayEvent() {

    }

    public PayEvent(String order_code, String type) {
        this.order_code = order_code;
        this.type = type;
    }

    public PayEvent(String order_code, String price, String type) {
        this.order_code = order_code;
        this.price = price;
        this.type = type;
    }
}
