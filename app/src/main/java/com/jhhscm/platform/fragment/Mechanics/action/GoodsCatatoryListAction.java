package com.jhhscm.platform.fragment.Mechanics.action;

import android.content.Context;

import com.jhhscm.platform.fragment.Mechanics.bean.FindBrandBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GoodsCatatoryListBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class GoodsCatatoryListAction extends AHttpService<BaseEntity<GoodsCatatoryListBean>> {

    private NetBean netBean;

    public static GoodsCatatoryListAction newInstance(Context context, NetBean netBean) {
        return new GoodsCatatoryListAction(context, netBean);
    }

    public GoodsCatatoryListAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.goodscatatoryList(netBean);
    }
}
