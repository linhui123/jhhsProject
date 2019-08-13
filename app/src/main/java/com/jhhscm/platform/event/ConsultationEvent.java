package com.jhhscm.platform.event;

import com.jhhscm.platform.tool.EventBusUtil;

public class ConsultationEvent implements EventBusUtil.IEvent {
    public String phone;
    public int type;//信息咨询位置；首页1；新机2；二手机3；
    public ConsultationEvent(String phone) {
        this.phone = phone;
    }

    public ConsultationEvent(int type) {
        this.type = type;
    }
}
