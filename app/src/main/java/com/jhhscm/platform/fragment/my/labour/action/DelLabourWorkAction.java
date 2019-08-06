package com.jhhscm.platform.fragment.my.labour.action;

import android.content.Context;

import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.ResultBean;

import retrofit2.Call;

public class DelLabourWorkAction extends AHttpService<BaseEntity<ResultBean>> {

    private NetBean netBean;

    public static DelLabourWorkAction newInstance(Context context, NetBean netBean) {
        return new DelLabourWorkAction(context, netBean);
    }

    public DelLabourWorkAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.delLabourWork(netBean);
    }
}
