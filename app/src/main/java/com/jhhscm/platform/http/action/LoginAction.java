package com.jhhscm.platform.http.action;

import android.content.Context;

import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class LoginAction extends AHttpService<BaseEntity> {

    private NetBean netBean;

    public static LoginAction newInstance(Context context, NetBean netBean) {
        return new LoginAction(context, netBean);
    }

    public LoginAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.LoginTest(netBean);
    }
}
