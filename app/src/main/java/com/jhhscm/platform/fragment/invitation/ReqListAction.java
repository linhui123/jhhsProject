package com.jhhscm.platform.fragment.invitation;

import android.content.Context;

import com.jhhscm.platform.fragment.my.BusCountAction;
import com.jhhscm.platform.fragment.my.BusCountBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class ReqListAction extends AHttpService<BaseEntity<ReqListBean>> {

    private NetBean netBean;

    public static ReqListAction newInstance(Context context, NetBean netBean) {
        return new ReqListAction(context, netBean);
    }

    public ReqListAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.users_reqlist(netBean);
    }
}
