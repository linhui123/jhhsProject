package com.jhhscm.platform.event;

import com.jhhscm.platform.tool.EventBusUtil;

public class DelPhotoEvent implements EventBusUtil.IEvent {
    private String url;

    public DelPhotoEvent() {
    }

    public DelPhotoEvent(String imageUrl) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

