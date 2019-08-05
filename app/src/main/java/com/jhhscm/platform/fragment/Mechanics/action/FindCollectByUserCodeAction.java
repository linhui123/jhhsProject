package com.jhhscm.platform.fragment.Mechanics.action;

import android.content.Context;

import com.jhhscm.platform.fragment.Mechanics.bean.FindCategoryDetailBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.SaveBean;

import retrofit2.Call;

public class FindCollectByUserCodeAction extends AHttpService<BaseEntity<SaveBean>> {

    private NetBean netBean;

    public static FindCollectByUserCodeAction newInstance(Context context, NetBean netBean) {
        return new FindCollectByUserCodeAction(context, netBean);
    }

    public FindCollectByUserCodeAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.findCollectByUserCode(netBean);
    }
}