package com.jhhscm.platform.fragment.my.mechanics;

import android.content.Context;

import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class FindOldGoodByUserCodeAction extends AHttpService<BaseEntity<FindOldGoodByUserCodeBean>> {

    private NetBean netBean;

    public static FindOldGoodByUserCodeAction newInstance(Context context, NetBean netBean) {
        return new FindOldGoodByUserCodeAction(context, netBean);
    }

    public FindOldGoodByUserCodeAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.findOldGoodByUserCode(netBean);
    }
}
