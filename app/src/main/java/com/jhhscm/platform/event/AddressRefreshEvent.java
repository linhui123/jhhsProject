package com.jhhscm.platform.event;

import com.jhhscm.platform.fragment.GoodsToCarts.FindAddressListBean;
import com.jhhscm.platform.tool.EventBusUtil;

public class AddressRefreshEvent implements EventBusUtil.IEvent {
    private int type;//1;刷新；2 回调上级
    private FindAddressListBean.ResultBean.DataBean dataBean;

    public AddressRefreshEvent(int type) {
        this.type = type;
    }

    public AddressRefreshEvent(int type,FindAddressListBean.ResultBean.DataBean dataBean) {
        this.type = type;
        this.dataBean = dataBean;
    }

    public FindAddressListBean.ResultBean.DataBean getDataBean() {
        return dataBean;
    }

    public void setDataBean(FindAddressListBean.ResultBean.DataBean dataBean) {
        this.dataBean = dataBean;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
