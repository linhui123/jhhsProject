package com.jhhscm.platform.event;


import com.jhhscm.platform.tool.EventBusUtil;

/**
 * Created by Administrator on 2017/6/9.
 */

public class LoginErrorEvent implements EventBusUtil.IEvent {
    public String extraMsg;
    public LoginErrorEvent(String extraMsg){
        this.extraMsg=extraMsg;
    }
    public LoginErrorEvent(){
        extraMsg="操作完成，请重新登录！";
    }
}
