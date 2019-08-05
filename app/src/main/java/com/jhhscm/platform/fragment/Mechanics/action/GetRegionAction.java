package com.jhhscm.platform.fragment.Mechanics.action;

import android.content.Context;

import com.jhhscm.platform.fragment.Mechanics.bean.GetRegionBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class GetRegionAction extends AHttpService<BaseEntity<GetRegionBean>> {

    private NetBean netBean;

    public static GetRegionAction newInstance(Context context, NetBean netBean) {
        return new GetRegionAction(context, netBean);
    }

    public GetRegionAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.getRegion(netBean);
    }
}