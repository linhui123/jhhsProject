package com.jhhscm.platform.event;


import com.jhhscm.platform.tool.EventBusUtil;

public class EmptyEvent implements EventBusUtil.IEvent {

    private boolean empty;

    public EmptyEvent() {
    }

    public EmptyEvent(boolean empty) {
        this.empty = empty;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
}
