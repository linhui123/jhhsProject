package com.jhhscm.platform.fragment.GoodsToCarts.action;

import android.content.Context;

import com.jhhscm.platform.fragment.GoodsToCarts.FindAddressListBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class FindAddressListAction extends AHttpService<BaseEntity<FindAddressListBean>> {

    private NetBean netBean;

    public static FindAddressListAction newInstance(Context context, NetBean netBean) {
        return new FindAddressListAction(context, netBean);
    }

    public FindAddressListAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.findAddressList(netBean);
    }
}

