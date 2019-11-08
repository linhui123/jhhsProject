package com.jhhscm.platform.fragment.coupon;

import android.content.Context;

import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class CouponGetListAction extends AHttpService<BaseEntity<CouponGetListBean>> {

    private NetBean netBean;

    public static CouponGetListAction newInstance(Context context, NetBean netBean) {
        return new CouponGetListAction(context, netBean);
    }

    public CouponGetListAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.coupon_getlist(netBean);
    }
}
