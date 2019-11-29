package com.jhhscm.platform.event;

import com.jhhscm.platform.tool.EventBusUtil;

public class GetCouponEvent implements EventBusUtil.IEvent {
    public String coupon_code;
    public String start;
    public String end;
    public int type;//0 首页； 1 领券中心
    public GetCouponEvent(String coupon_code, String start, String end,int type) {
        this.coupon_code = coupon_code;
        this.start = start;
        this.end = end;
        this.type = type;
    }
}
