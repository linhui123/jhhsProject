package com.jhhscm.platform.fragment.my.store.action;

import android.content.Context;

import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class FindUserGoodsOwnerAction extends AHttpService<BaseEntity<FindUserGoodsOwnerBean>> {

    private NetBean netBean;

    public static FindUserGoodsOwnerAction newInstance(Context context, NetBean netBean) {
        return new FindUserGoodsOwnerAction(context, netBean);
    }

    public FindUserGoodsOwnerAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.findUserGoodsOwner(netBean);
    }
}
