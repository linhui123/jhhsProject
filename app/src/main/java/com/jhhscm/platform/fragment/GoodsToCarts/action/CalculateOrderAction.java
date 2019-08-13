package com.jhhscm.platform.fragment.GoodsToCarts.action;

import android.content.Context;

import com.jhhscm.platform.fragment.GoodsToCarts.CalculateOrderBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.ResultBean;

import retrofit2.Call;

public class CalculateOrderAction extends AHttpService<BaseEntity<CalculateOrderBean>> {

    private NetBean netBean;

    public static CalculateOrderAction newInstance(Context context, NetBean netBean) {
        return new CalculateOrderAction(context, netBean);
    }

    public CalculateOrderAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.calculateOrder(netBean);
    }
}
