package com.jhhscm.platform.event;

import com.jhhscm.platform.tool.EventBusUtil;

public class WebCountEvent implements EventBusUtil.IEvent {
    private String count;

    public WebCountEvent(String count) {
        this.count = count;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
