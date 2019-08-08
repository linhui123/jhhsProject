package com.jhhscm.platform.fragment.my.set;

import android.content.Context;

import com.jhhscm.platform.fragment.my.collect.CollectDeleteAction;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.ResultBean;

import retrofit2.Call;

public class LoginOutAciton extends AHttpService<BaseEntity<ResultBean>> {

    private NetBean netBean;

    public static LoginOutAciton newInstance(Context context, NetBean netBean) {
        return new LoginOutAciton(context, netBean);
    }

    public LoginOutAciton(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.loginOut(netBean);
    }
}
