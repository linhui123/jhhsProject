package com.jhhscm.platform.fragment.vehicle;

import android.content.Context;

import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class GpsDetailAction  extends AHttpService<BaseEntity<GpsDetailBean>> {

    private NetBean netBean;

    public static GpsDetailAction newInstance(Context context, NetBean netBean) {
        return new GpsDetailAction(context, netBean);
    }

    public GpsDetailAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.gpsDetail(netBean);
    }
}
