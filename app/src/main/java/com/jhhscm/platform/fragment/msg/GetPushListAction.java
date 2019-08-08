package com.jhhscm.platform.fragment.msg;

import android.content.Context;

import com.jhhscm.platform.fragment.labour.FindLabourReleaseDetailAction;
import com.jhhscm.platform.fragment.labour.FindLabourReleaseDetailBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class GetPushListAction extends AHttpService<BaseEntity<GetPushListBean>> {

    private NetBean netBean;

    public static GetPushListAction newInstance(Context context, NetBean netBean) {
        return new GetPushListAction(context, netBean);
    }

    public GetPushListAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.getPushList(netBean);
    }
}
