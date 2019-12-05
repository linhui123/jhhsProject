package com.jhhscm.platform.fragment.my.store.action;

import android.content.Context;

import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class FindBusGoodsOwnerListByUserCodeAction extends AHttpService<BaseEntity<FindBusGoodsOwnerListByUserCodeBean>> {

    private NetBean netBean;

    public static FindBusGoodsOwnerListByUserCodeAction newInstance(Context context, NetBean netBean) {
        return new FindBusGoodsOwnerListByUserCodeAction(context, netBean);
    }

    public FindBusGoodsOwnerListByUserCodeAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.findBusGoodsOwnerListByUserCode(netBean);
    }
}
