package com.jhhscm.platform.event;

import com.jhhscm.platform.tool.EventBusUtil;

public class GetRegionEvent implements EventBusUtil.IEvent {
    public String pid;//行政区域父ID
    public String type;//1则是省，2则是市，3则是区
    public String name;

    public GetRegionEvent(String pid, String type) {
        this.pid = pid;
        this.type = type;
    }

    public GetRegionEvent(String pid, String name, String type) {
        this.pid = pid;
        this.type = type;
        this.name = name;
    }
}
