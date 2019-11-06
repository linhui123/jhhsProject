package com.jhhscm.platform.fragment.my;

import android.content.Context;

import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class BusCountAction extends AHttpService<BaseEntity<BusCountBean>> {

    private NetBean netBean;

    public static BusCountAction newInstance(Context context, NetBean netBean) {
        return new BusCountAction(context, netBean);
    }

    public BusCountAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.buscount(netBean);
    }
}
