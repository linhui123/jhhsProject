package com.jhhscm.platform.fragment.GoodsToCarts.action;

import android.content.Context;

import com.jhhscm.platform.fragment.GoodsToCarts.CreateOrderResultBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class CreateOrderAction extends AHttpService<BaseEntity<CreateOrderResultBean>> {

    private NetBean netBean;

    public static CreateOrderAction newInstance(Context context, NetBean netBean) {
        return new CreateOrderAction(context, netBean);
    }

    public CreateOrderAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.createOrder(netBean);
    }
}
