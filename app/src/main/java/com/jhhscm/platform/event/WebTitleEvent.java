package com.jhhscm.platform.event;


import com.jhhscm.platform.tool.EventBusUtil;

/**
 * Created by Administrator on 2016/6/24.
 */
public class WebTitleEvent implements EventBusUtil.IEvent {
    private String title;

    public WebTitleEvent(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
