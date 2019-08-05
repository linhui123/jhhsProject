package com.jhhscm.platform.fragment.labour;

import android.content.Context;

import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class FindLabourWorkDetailAction  extends AHttpService<BaseEntity<FindLabourReleaseDetailBean>> {

    private NetBean netBean;

    public static FindLabourWorkDetailAction newInstance(Context context, NetBean netBean) {
        return new FindLabourWorkDetailAction(context, netBean);
    }

    public FindLabourWorkDetailAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.findLabourWorkDetail(netBean);
    }
}
