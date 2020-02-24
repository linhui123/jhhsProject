package com.jhhscm.platform.fragment.financing;

import android.content.Context;

import com.jhhscm.platform.fragment.coupon.CouponListAction;
import com.jhhscm.platform.fragment.coupon.CouponListBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class LeaseSelAction extends AHttpService<BaseEntity<LeaseSelBean>> {

    private NetBean netBean;

    public static LeaseSelAction newInstance(Context context, NetBean netBean) {
        return new LeaseSelAction(context, netBean);
    }

    public LeaseSelAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.lease_sel(netBean);
    }
}


