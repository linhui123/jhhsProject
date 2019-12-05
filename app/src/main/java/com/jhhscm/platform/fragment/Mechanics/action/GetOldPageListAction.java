package com.jhhscm.platform.fragment.Mechanics.action;

import android.content.Context;

import com.jhhscm.platform.fragment.Mechanics.bean.GetOldPageListBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class GetOldPageListAction extends AHttpService<BaseEntity<GetOldPageListBean>> {

    private NetBean netBean;

    public static GetOldPageListAction newInstance(Context context, NetBean netBean) {
        return new GetOldPageListAction(context, netBean);
    }

    public GetOldPageListAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.getOldPageList(netBean);
    }
}

