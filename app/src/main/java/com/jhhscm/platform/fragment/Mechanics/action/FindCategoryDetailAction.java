package com.jhhscm.platform.fragment.Mechanics.action;

import android.content.Context;

import com.jhhscm.platform.fragment.Mechanics.bean.FindCategoryBean;
import com.jhhscm.platform.fragment.Mechanics.bean.FindCategoryDetailBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class FindCategoryDetailAction extends AHttpService<BaseEntity<FindCategoryDetailBean>> {

    private NetBean netBean;

    public static FindCategoryDetailAction newInstance(Context context, NetBean netBean) {
        return new FindCategoryDetailAction(context, netBean);
    }

    public FindCategoryDetailAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.findCategoryDetail(netBean);
    }
}