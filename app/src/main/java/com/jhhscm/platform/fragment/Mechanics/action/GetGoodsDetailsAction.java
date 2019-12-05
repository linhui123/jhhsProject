package com.jhhscm.platform.fragment.Mechanics.action;

import android.content.Context;

import com.jhhscm.platform.fragment.Mechanics.bean.GetGoodsDetailsBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class GetGoodsDetailsAction extends AHttpService<BaseEntity<GetGoodsDetailsBean>> {

    private NetBean netBean;

    public static GetGoodsDetailsAction newInstance(Context context, NetBean netBean) {
        return new GetGoodsDetailsAction(context, netBean);
    }

    public GetGoodsDetailsAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.getGoodsDetails(netBean);
    }
}
