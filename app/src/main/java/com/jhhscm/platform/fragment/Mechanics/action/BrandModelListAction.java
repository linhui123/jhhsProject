package com.jhhscm.platform.fragment.Mechanics.action;

import android.content.Context;

import com.jhhscm.platform.fragment.Mechanics.bean.BrandModelBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class BrandModelListAction extends AHttpService<BaseEntity<BrandModelBean>> {

    private NetBean netBean;

    public static BrandModelListAction newInstance(Context context, NetBean netBean) {
        return new BrandModelListAction(context, netBean);
    }

    public BrandModelListAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.brandModelListBean(netBean);
    }
}
