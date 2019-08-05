package com.jhhscm.platform.event;

import com.jhhscm.platform.fragment.GoodsToCarts.FindAddressListBean;
import com.jhhscm.platform.tool.EventBusUtil;

public class AddressResultEvent implements EventBusUtil.IEvent {
    private FindAddressListBean.ResultBean.DataBean resultBean;

    public AddressResultEvent(FindAddressListBean.ResultBean.DataBean resultBean) {
        this.resultBean = resultBean;
    }

    public FindAddressListBean.ResultBean.DataBean getResultBean() {
        return resultBean;
    }

    public void setResultBean(FindAddressListBean.ResultBean.DataBean resultBean) {
        this.resultBean = resultBean;
    }
}
