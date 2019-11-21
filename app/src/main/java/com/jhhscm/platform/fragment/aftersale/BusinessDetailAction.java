package com.jhhscm.platform.fragment.aftersale;

import android.content.Context;

import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class BusinessDetailAction extends AHttpService<BaseEntity<BusinessDetailBean>> {

    private NetBean netBean;

    public static BusinessDetailAction newInstance(Context context, NetBean netBean) {
        return new BusinessDetailAction(context, netBean);
    }

    public BusinessDetailAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.business_detail(netBean);
    }
}

