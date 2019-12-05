package com.jhhscm.platform.fragment.my.store.action;

import android.content.Context;

import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class BusinessSumdataAction extends AHttpService<BaseEntity<BusinessSumdataBean>> {

    private NetBean netBean;

    public static BusinessSumdataAction newInstance(Context context, NetBean netBean) {
        return new BusinessSumdataAction(context, netBean);
    }

    public BusinessSumdataAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.business_sumdata(netBean);
    }
}

