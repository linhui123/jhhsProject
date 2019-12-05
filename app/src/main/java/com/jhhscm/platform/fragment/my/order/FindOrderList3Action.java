package com.jhhscm.platform.fragment.my.order;

import android.content.Context;

import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class FindOrderList3Action extends AHttpService<BaseEntity<FindOrderListBean>> {

    private NetBean netBean;

    public static FindOrderList3Action newInstance(Context context, NetBean netBean) {
        return new FindOrderList3Action(context, netBean);
    }

    public FindOrderList3Action(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.findOrderList3(netBean);
    }
}

