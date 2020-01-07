package com.jhhscm.platform.event;

import com.jhhscm.platform.fragment.my.store.action.FindUserGoodsOwnerBean;
import com.jhhscm.platform.tool.EventBusUtil;

import java.util.List;

public class StoreDeviceEvent implements EventBusUtil.IEvent {
    public List<FindUserGoodsOwnerBean.DataBean> dataBeans;
    public int position;

    public StoreDeviceEvent(List<FindUserGoodsOwnerBean.DataBean> dataBeans) {
        this.dataBeans = dataBeans;
    }

    public StoreDeviceEvent() {

    }

    public StoreDeviceEvent(List<FindUserGoodsOwnerBean.DataBean> dataBeans, int position) {
        this.dataBeans = dataBeans;
        this.position = position;
    }
}
