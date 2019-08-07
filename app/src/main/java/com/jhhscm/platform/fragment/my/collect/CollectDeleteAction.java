package com.jhhscm.platform.fragment.my.collect;

import android.content.Context;

import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.ResultBean;

import retrofit2.Call;

public class CollectDeleteAction extends AHttpService<BaseEntity<ResultBean>> {

    private NetBean netBean;

    public static CollectDeleteAction newInstance(Context context, NetBean netBean) {
        return new CollectDeleteAction(context, netBean);
    }

    public CollectDeleteAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.collectDelete(netBean);
    }
}
