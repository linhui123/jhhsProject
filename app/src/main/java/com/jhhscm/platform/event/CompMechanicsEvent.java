package com.jhhscm.platform.event;

import com.jhhscm.platform.fragment.Mechanics.bean.GetGoodsByBrandBean;
import com.jhhscm.platform.tool.EventBusUtil;

public class CompMechanicsEvent implements EventBusUtil.IEvent {
    public GetGoodsByBrandBean.ResultBean resultBean;

    public CompMechanicsEvent(GetGoodsByBrandBean.ResultBean resultBean) {
        this.resultBean = resultBean;
    }
}
