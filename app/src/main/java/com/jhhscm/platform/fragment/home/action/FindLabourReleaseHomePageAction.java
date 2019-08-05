package com.jhhscm.platform.fragment.home.action;

import android.content.Context;

import com.jhhscm.platform.fragment.home.bean.FindBrandHomePageBean;
import com.jhhscm.platform.fragment.home.bean.FindLabourReleaseHomePageBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class FindLabourReleaseHomePageAction extends AHttpService<BaseEntity<FindLabourReleaseHomePageBean>> {

    private NetBean netBean;

    public static FindLabourReleaseHomePageAction newInstance(Context context, NetBean netBean) {
        return new FindLabourReleaseHomePageAction(context, netBean);
    }

    public FindLabourReleaseHomePageAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.findLabourReleaseHomePage(netBean);
    }
}

