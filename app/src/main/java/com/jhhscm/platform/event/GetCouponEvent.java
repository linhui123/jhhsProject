package com.jhhscm.platform.event;

import com.jhhscm.platform.tool.EventBusUtil;

public class GetCouponEvent implements EventBusUtil.IEvent {
    public String coupon_code;
    public String start;
    public String end;

    public GetCouponEvent(String coupon_code, String start, String end) {
        this.coupon_code = coupon_code;
        this.start = start;
        this.end = end;
    }
}
