package com.jhhscm.platform.fragment.my.store.action;

import android.content.Context;

import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.ResultBean;

import retrofit2.Call;

public class BusorderCreateOrderAction  extends AHttpService<BaseEntity<ResultBean>> {

    private NetBean netBean;

    public static BusorderCreateOrderAction newInstance(Context context, NetBean netBean) {
        return new BusorderCreateOrderAction(context, netBean);
    }

    public BusorderCreateOrderAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.busorder_createOrder(netBean);
    }
}

