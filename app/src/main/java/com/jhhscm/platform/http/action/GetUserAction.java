package com.jhhscm.platform.http.action;

import android.content.Context;

import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.UserBean;

import retrofit2.Call;

public class GetUserAction extends AHttpService<BaseEntity<UserBean>> {

    private NetBean mobile;

    public static GetUserAction newInstance(Context context, NetBean mobile) {
        return new GetUserAction(context, mobile);
    }

    public GetUserAction(Context context, NetBean mobile) {
        super(context);
        this.mobile = mobile;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.getUser(mobile);
    }
}
