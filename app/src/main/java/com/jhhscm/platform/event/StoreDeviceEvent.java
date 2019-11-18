package com.jhhscm.platform.event;

import com.jhhscm.platform.fragment.my.mechanics.FindGoodsOwnerBean;
import com.jhhscm.platform.tool.EventBusUtil;

import java.util.List;

public class StoreDeviceEvent implements EventBusUtil.IEvent {
    public List<FindGoodsOwnerBean.DataBean> dataBeans;

    public StoreDeviceEvent(List<FindGoodsOwnerBean.DataBean> dataBeans) {
        this.dataBeans = dataBeans;
    }
}
