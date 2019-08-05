package com.jhhscm.platform.fragment.GoodsToCarts.action;

import android.content.Context;

import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.ResultBean;

import retrofit2.Call;

public class DelAddressAction extends AHttpService<BaseEntity<ResultBean>> {

    private NetBean netBean;

    public static DelAddressAction newInstance(Context context, NetBean netBean) {
        return new DelAddressAction(context, netBean);
    }

    public DelAddressAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.delAddress(netBean);
    }
}
