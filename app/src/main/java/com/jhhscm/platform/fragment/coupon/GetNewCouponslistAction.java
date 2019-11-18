package com.jhhscm.platform.fragment.coupon;

import android.content.Context;

import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.ResultBean;

import retrofit2.Call;

public class GetNewCouponslistAction extends AHttpService<BaseEntity<GetNewCouponslistBean>> {

    private NetBean netBean;

    public static GetNewCouponslistAction newInstance(Context context, NetBean netBean) {
        return new GetNewCouponslistAction(context, netBean);
    }

    public GetNewCouponslistAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.getFirstPageCouponslist(netBean);
    }
}

