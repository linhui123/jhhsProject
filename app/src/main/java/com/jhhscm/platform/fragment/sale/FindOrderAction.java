package com.jhhscm.platform.fragment.sale;

import android.content.Context;

import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class FindOrderAction extends AHttpService<BaseEntity<FindOrderBean>> {

    private NetBean netBean;

    public static FindOrderAction newInstance(Context context, NetBean netBean) {
        return new FindOrderAction(context, netBean);
    }

    public FindOrderAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.findOrder(netBean);
    }
}