package com.jhhscm.platform.event;

import com.jhhscm.platform.tool.EventBusUtil;

/**
 * Created by Administrator on 2017/6/9.
 */

public class LoginH5Event implements EventBusUtil.IEvent {
    public String url;
    public LoginH5Event(String url){
        this.url=url;
    }
}
