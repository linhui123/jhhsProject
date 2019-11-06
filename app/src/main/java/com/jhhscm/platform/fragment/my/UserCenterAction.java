package com.jhhscm.platform.fragment.my;

import android.content.Context;

import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class UserCenterAction extends AHttpService<BaseEntity<UserCenterBean>> {

    private NetBean netBean;

    public static UserCenterAction newInstance(Context context, NetBean netBean) {
        return new UserCenterAction(context, netBean);
    }

    public UserCenterAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.users_center(netBean);
    }
}

