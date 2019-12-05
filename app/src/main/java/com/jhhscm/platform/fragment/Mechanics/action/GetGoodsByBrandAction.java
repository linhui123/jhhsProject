package com.jhhscm.platform.fragment.Mechanics.action;

import android.content.Context;

import com.jhhscm.platform.fragment.Mechanics.bean.GetGoodsByBrandBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class GetGoodsByBrandAction  extends AHttpService<BaseEntity<GetGoodsByBrandBean>> {

    private NetBean netBean;

    public static GetGoodsByBrandAction newInstance(Context context, NetBean netBean) {
        return new GetGoodsByBrandAction(context, netBean);
    }

    public GetGoodsByBrandAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.getGoodsByBrand(netBean);
    }
}
