package com.jhhscm.platform.event;


import com.jhhscm.platform.tool.EventBusUtil;

public class ScrollEvent implements EventBusUtil.IEvent {
    public boolean isScroll = true;

    public ScrollEvent() {
    }

    public ScrollEvent(boolean isScroll) {
        this.isScroll = isScroll;
    }
}
