package com.jhhscm.platform.event;

import com.jhhscm.platform.fragment.coupon.CouponListBean;
import com.jhhscm.platform.tool.EventBusUtil;

public class SelectCouponEvent implements EventBusUtil.IEvent {

    private CouponListBean.ResultBean resultBean;
    private int type;//0 列表选择（遍历单选）；1 选择返回

    public SelectCouponEvent(CouponListBean.ResultBean resultBean, int type) {
        this.resultBean = resultBean;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public CouponListBean.ResultBean getResultBean() {
        return resultBean;
    }

    public void setResultBean(CouponListBean.ResultBean resultBean) {
        this.resultBean = resultBean;
    }
}

