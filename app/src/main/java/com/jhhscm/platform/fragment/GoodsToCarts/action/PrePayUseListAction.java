package com.jhhscm.platform.fragment.GoodsToCarts.action;

import android.content.Context;

import com.jhhscm.platform.fragment.coupon.CouponListBean;
import com.jhhscm.platform.fragment.my.store.action.PayUseListAction;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class PrePayUseListAction extends AHttpService<BaseEntity<CouponListBean>> {

    private NetBean netBean;

    public static PrePayUseListAction newInstance(Context context, NetBean netBean) {
        return new PrePayUseListAction(context, netBean);
    }

    public PrePayUseListAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.prePayUseList(netBean);
    }
}
