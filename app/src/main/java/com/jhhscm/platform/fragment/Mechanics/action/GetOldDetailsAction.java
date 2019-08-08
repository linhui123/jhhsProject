package com.jhhscm.platform.fragment.Mechanics.action;

import android.content.Context;

import com.jhhscm.platform.fragment.Mechanics.bean.GetGoodsDetailsBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GetOldDetailsBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class GetOldDetailsAction extends AHttpService<BaseEntity<GetOldDetailsBean>> {

    private NetBean netBean;

    public static GetOldDetailsAction newInstance(Context context, NetBean netBean) {
        return new GetOldDetailsAction(context, netBean);
    }

    public GetOldDetailsAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.getOldDetails(netBean);
    }
}
