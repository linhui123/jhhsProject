package com.jhhscm.platform.fragment.Mechanics.action;

import android.content.Context;

import com.jhhscm.platform.fragment.Mechanics.bean.FindCategoryBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class FindCategoryAction extends AHttpService<BaseEntity<FindCategoryBean>> {

    private NetBean netBean;

    public static FindCategoryAction newInstance(Context context, NetBean netBean) {
        return new FindCategoryAction(context, netBean);
    }

    public FindCategoryAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.findCategory(netBean);
    }
}