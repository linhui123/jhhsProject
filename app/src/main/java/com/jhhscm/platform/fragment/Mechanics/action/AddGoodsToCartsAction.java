package com.jhhscm.platform.fragment.Mechanics.action;

import android.content.Context;

import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class AddGoodsToCartsAction extends AHttpService<BaseEntity> {

    private NetBean netBean;

    public static AddGoodsToCartsAction newInstance(Context context, NetBean netBean) {
        return new AddGoodsToCartsAction(context, netBean);
    }

    public AddGoodsToCartsAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.addGoodsToCarts(netBean);
    }
}