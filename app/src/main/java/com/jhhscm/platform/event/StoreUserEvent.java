package com.jhhscm.platform.event;

import com.jhhscm.platform.tool.EventBusUtil;

public class StoreUserEvent implements EventBusUtil.IEvent {
    public String userCode;
    public String userName;
    public String userMobile;

    public StoreUserEvent(String userCode, String userName, String userMobile) {
        this.userCode = userCode;
        this.userName = userName;
        this.userMobile = userMobile;
    }
}
