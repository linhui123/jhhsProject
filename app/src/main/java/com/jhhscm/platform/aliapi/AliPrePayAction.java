package com.jhhscm.platform.aliapi;

import android.content.Context;

import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.ResultBean;
import com.jhhscm.platform.wxapi.WxPrePayAction;

import retrofit2.Call;

public class AliPrePayAction extends AHttpService<BaseEntity<AliPrePayBean>> {

    private NetBean netBean;

    public static AliPrePayAction newInstance(Context context, NetBean netBean) {
        return new AliPrePayAction(context, netBean);
    }

    public AliPrePayAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.aliPrePay(netBean);
    }
}

