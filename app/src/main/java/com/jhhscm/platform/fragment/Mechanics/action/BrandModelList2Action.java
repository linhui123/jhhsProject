package com.jhhscm.platform.fragment.Mechanics.action;

import android.content.Context;

import com.jhhscm.platform.fragment.Mechanics.bean.BrandModel2Bean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class BrandModelList2Action extends AHttpService<BaseEntity<BrandModel2Bean>> {

    private NetBean netBean;

    public static BrandModelList2Action newInstance(Context context, NetBean netBean) {
        return new BrandModelList2Action(context, netBean);
    }

    public BrandModelList2Action(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.brandModelList2Bean(netBean);
    }
}
