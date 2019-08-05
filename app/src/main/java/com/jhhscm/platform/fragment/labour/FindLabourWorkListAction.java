package com.jhhscm.platform.fragment.labour;

import android.content.Context;

import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class FindLabourWorkListAction extends AHttpService<BaseEntity<FindLabourReleaseListBean>> {

    private NetBean netBean;

    public static FindLabourWorkListAction newInstance(Context context, NetBean netBean) {
        return new FindLabourWorkListAction(context, netBean);
    }

    public FindLabourWorkListAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.findLabourWorkList(netBean);
    }
}