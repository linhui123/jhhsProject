package com.jhhscm.platform.fragment.invitation;

import android.content.Context;

import com.jhhscm.platform.fragment.my.store.action.BusinessFindcategorybyBuscodeAction;
import com.jhhscm.platform.fragment.my.store.action.BusinessFindcategorybyBuscodeBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.ResultBean;

import retrofit2.Call;

public class getUserShareUrlAction extends AHttpService<BaseEntity<UserShareUrlBean>> {

    private NetBean netBean;

    public static getUserShareUrlAction newInstance(Context context, NetBean netBean) {
        return new getUserShareUrlAction(context, netBean);
    }

    public getUserShareUrlAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.users_shareurl(netBean);
    }
}
