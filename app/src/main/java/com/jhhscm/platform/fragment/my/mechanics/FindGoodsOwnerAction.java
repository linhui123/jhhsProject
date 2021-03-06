package com.jhhscm.platform.fragment.my.mechanics;

import android.content.Context;

import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class FindGoodsOwnerAction extends AHttpService<BaseEntity<FindGoodsOwnerBean>> {

    private NetBean netBean;

    public static FindGoodsOwnerAction newInstance(Context context, NetBean netBean) {
        return new FindGoodsOwnerAction(context, netBean);
    }

    public FindGoodsOwnerAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.findGoodsOwner(netBean);
    }
}
