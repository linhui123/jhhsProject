package com.jhhscm.platform.fragment.my.store.action;

import android.content.Context;

import com.jhhscm.platform.fragment.coupon.CouponListBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class FindBusUserServerListAction extends AHttpService<BaseEntity<FindBusUserServerListBean>> {

    private NetBean netBean;

    public static FindBusUserServerListAction newInstance(Context context, NetBean netBean) {
        return new FindBusUserServerListAction(context, netBean);
    }

    public FindBusUserServerListAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.findBusUserServerList(netBean);
    }
}