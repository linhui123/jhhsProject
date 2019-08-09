package com.jhhscm.platform.fragment.my;

import android.content.Context;

import com.jhhscm.platform.fragment.my.order.DelOrderAction;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.ResultBean;

import retrofit2.Call;

public class CheckVersionAction extends AHttpService<BaseEntity<CheckVersionBean>> {

    private NetBean netBean;

    public static CheckVersionAction newInstance(Context context, NetBean netBean) {
        return new CheckVersionAction(context, netBean);
    }

    public CheckVersionAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.checkVersion(netBean);
    }
}

