package com.jhhscm.platform.fragment.aftersale;

import android.content.Context;

import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class FindBusListAction extends AHttpService<BaseEntity<FindBusListBean>> {

    private NetBean netBean;

    public static FindBusListAction newInstance(Context context, NetBean netBean) {
        return new FindBusListAction(context, netBean);
    }

    public FindBusListAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.findBusList(netBean);
    }
}
