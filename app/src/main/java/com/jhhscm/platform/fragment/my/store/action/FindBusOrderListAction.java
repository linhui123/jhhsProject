package com.jhhscm.platform.fragment.my.store.action;

import android.content.Context;

import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class FindBusOrderListAction extends AHttpService<BaseEntity<FindBusOrderListBean>> {

    private NetBean netBean;

    public static FindBusOrderListAction newInstance(Context context, NetBean netBean) {
        return new FindBusOrderListAction(context, netBean);
    }

    public FindBusOrderListAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.findBusOrderList(netBean);
    }
}
