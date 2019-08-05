package com.jhhscm.platform.fragment.labour;

import android.content.Context;

import com.jhhscm.platform.fragment.home.action.FindBrandHomePageAction;
import com.jhhscm.platform.fragment.home.bean.FindBrandHomePageBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class FindLabourReleaseListAction extends AHttpService<BaseEntity<FindLabourReleaseListBean>> {

    private NetBean netBean;

    public static FindLabourReleaseListAction newInstance(Context context, NetBean netBean) {
        return new FindLabourReleaseListAction(context, netBean);
    }

    public FindLabourReleaseListAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.findLabourReleaseList(netBean);
    }
}

