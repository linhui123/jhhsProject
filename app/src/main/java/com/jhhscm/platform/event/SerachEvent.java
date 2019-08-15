package com.jhhscm.platform.event;

import com.jhhscm.platform.tool.EventBusUtil;

public class SerachEvent implements EventBusUtil.IEvent {
    public String content;

    public SerachEvent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
