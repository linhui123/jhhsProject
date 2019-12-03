package com.jhhscm.platform.fragment.vehicle;

import android.content.Context;

import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class GpsTrackDetailAction extends AHttpService<BaseEntity<GpsTrackDetailBean>> {

    private NetBean netBean;

    public static GpsTrackDetailAction newInstance(Context context, NetBean netBean) {
        return new GpsTrackDetailAction(context, netBean);
    }

    public GpsTrackDetailAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.gps_trackdetail(netBean);
    }
}

