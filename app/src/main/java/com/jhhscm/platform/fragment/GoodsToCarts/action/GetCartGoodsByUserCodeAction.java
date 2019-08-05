package com.jhhscm.platform.fragment.GoodsToCarts.action;

import android.content.Context;

import com.jhhscm.platform.fragment.GoodsToCarts.GetCartGoodsByUserCodeBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class GetCartGoodsByUserCodeAction extends AHttpService<BaseEntity<GetCartGoodsByUserCodeBean>> {

    private NetBean netBean;

    public static GetCartGoodsByUserCodeAction newInstance(Context context, NetBean netBean) {
        return new GetCartGoodsByUserCodeAction(context, netBean);
    }

    public GetCartGoodsByUserCodeAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.getCartGoodsByUserCode(netBean);
    }
}