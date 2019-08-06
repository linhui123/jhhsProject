package com.jhhscm.platform.fragment.my.labour.action;

import android.content.Context;

import com.jhhscm.platform.fragment.my.labour.FindLabourListBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class FindLabourListAction extends AHttpService<BaseEntity<FindLabourListBean>> {

    private NetBean netBean;

    public static FindLabourListAction newInstance(Context context, NetBean netBean) {
        return new FindLabourListAction(context, netBean);
    }

    public FindLabourListAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.findLabourList(netBean);
    }
}
