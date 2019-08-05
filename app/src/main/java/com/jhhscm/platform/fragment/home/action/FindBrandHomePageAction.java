package com.jhhscm.platform.fragment.home.action;

import android.content.Context;

import com.jhhscm.platform.fragment.home.AdBean;
import com.jhhscm.platform.fragment.home.bean.FindBrandHomePageBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class FindBrandHomePageAction extends AHttpService<BaseEntity<FindBrandHomePageBean>> {

    private NetBean netBean;

    public static FindBrandHomePageAction newInstance(Context context, NetBean netBean) {
        return new FindBrandHomePageAction(context, netBean);
    }

    public FindBrandHomePageAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.findBrandHomePage(netBean);
    }
}

