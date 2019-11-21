package com.jhhscm.platform.fragment.my.book;

import android.content.Context;

import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class DetailToolActin extends AHttpService<BaseEntity<DetailToolBean>> {

    private NetBean netBean;

    public static DetailToolActin newInstance(Context context, NetBean netBean) {
        return new DetailToolActin(context, netBean);
    }

    public DetailToolActin(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.detailTool(netBean);
    }
}

