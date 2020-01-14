package com.jhhscm.platform.fragment.Mechanics.action;

import android.content.Context;

import com.jhhscm.platform.fragment.Mechanics.bean.BrandModel1Bean;
import com.jhhscm.platform.fragment.Mechanics.bean.BrandModelBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class BrandModelList1Action extends AHttpService<BaseEntity<BrandModel1Bean>> {

    private NetBean netBean;

    public static BrandModelList1Action newInstance(Context context, NetBean netBean) {
        return new BrandModelList1Action(context, netBean);
    }

    public BrandModelList1Action(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.brandModelList1Bean(netBean);
    }
}

