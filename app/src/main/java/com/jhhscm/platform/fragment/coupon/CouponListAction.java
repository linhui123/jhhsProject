package com.jhhscm.platform.fragment.coupon;

import android.content.Context;

import com.jhhscm.platform.fragment.my.store.action.BusinessSumdataAction;
import com.jhhscm.platform.fragment.my.store.action.BusinessSumdataBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class CouponListAction extends AHttpService<BaseEntity<CouponListBean>> {

    private NetBean netBean;

    public static CouponListAction newInstance(Context context, NetBean netBean) {
        return new CouponListAction(context, netBean);
    }

    public CouponListAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.coupon_list(netBean);
    }
}

