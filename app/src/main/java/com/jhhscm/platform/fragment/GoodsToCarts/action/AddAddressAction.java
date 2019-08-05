package com.jhhscm.platform.fragment.GoodsToCarts.action;

import android.content.Context;

import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.ResultBean;

import retrofit2.Call;

public class AddAddressAction extends AHttpService<BaseEntity<ResultBean>> {

    private NetBean netBean;

    public static AddAddressAction newInstance(Context context, NetBean netBean) {
        return new AddAddressAction(context, netBean);
    }

    public AddAddressAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.addAddress(netBean);
    }
}
