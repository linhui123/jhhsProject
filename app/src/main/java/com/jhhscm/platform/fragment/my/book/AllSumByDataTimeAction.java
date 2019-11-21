package com.jhhscm.platform.fragment.my.book;

import android.content.Context;

import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class AllSumByDataTimeAction extends AHttpService<BaseEntity<AllSumByDataTimeBean>> {

    private NetBean netBean;

    public static AllSumByDataTimeAction newInstance(Context context, NetBean netBean) {
        return new AllSumByDataTimeAction(context, netBean);
    }

    public AllSumByDataTimeAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.allSumByDataTime(netBean);
    }
}
