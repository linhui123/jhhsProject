package com.jhhscm.platform.fragment.my;

import android.content.Context;

import com.jhhscm.platform.fragment.sale.FindOrderBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.ResultBean;

import retrofit2.Call;

public class FindOrderListCountAction extends AHttpService<BaseEntity<FindOrderBean>> {

    private NetBean netBean;

    public static FindOrderListCountAction newInstance(Context context, NetBean netBean) {
        return new FindOrderListCountAction(context, netBean);
    }

    public FindOrderListCountAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.findOrderListCount(netBean);
    }
}