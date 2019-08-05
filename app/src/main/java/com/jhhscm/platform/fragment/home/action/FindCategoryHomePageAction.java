package com.jhhscm.platform.fragment.home.action;

import android.content.Context;

import com.jhhscm.platform.fragment.home.bean.FindCategoryHomePageBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class FindCategoryHomePageAction extends AHttpService<BaseEntity<FindCategoryHomePageBean>> {

    private NetBean netBean;

    public static FindCategoryHomePageAction newInstance(Context context, NetBean netBean) {
        return new FindCategoryHomePageAction(context, netBean);
    }

    public FindCategoryHomePageAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.findCategoryHomePage(netBean);
    }
}

