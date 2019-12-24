package com.jhhscm.platform.fragment.coupon;

import android.content.Context;

import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class GetNewCouponslist2Action extends AHttpService<BaseEntity<GetNewCouponslistBean>> {

    private NetBean netBean;

    public static GetNewCouponslist2Action newInstance(Context context, NetBean netBean) {
        return new GetNewCouponslist2Action(context, netBean);
    }

    public GetNewCouponslist2Action(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.getFirstPageCouponslist2(netBean);
    }
}