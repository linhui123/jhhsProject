package com.jhhscm.platform.event;

import com.jhhscm.platform.tool.EventBusUtil;

public class OrderCancleEvent implements EventBusUtil.IEvent {
    public String order_code;
    public int activity;//0 我的订单列表 ； 1 店铺首页订单列表 ； 2 店铺会员服务订单列表
    public String type;
    public OrderCancleEvent(String order_code,String type) {
        this.order_code = order_code;
        this.type = type;
    }

    public OrderCancleEvent(String order_code,String type,int activity) {
        this.order_code = order_code;
        this.type = type;
        this.activity = activity;
    }

    public int getActivity() {
        return activity;
    }

    public void setActivity(int activity) {
        this.activity = activity;
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

