package com.jhhscm.platform.fragment.my.order;

import android.content.Context;

import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class FindOrderDetail3Action extends AHttpService<BaseEntity<FindOrderListBean.OrderDetail>> {

    private NetBean netBean;

    public static FindOrderDetail3Action newInstance(Context context, NetBean netBean) {
        return new FindOrderDetail3Action(context, netBean);
    }

    public FindOrderDetail3Action(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.find_OrderDetail3(netBean);
    }
}
