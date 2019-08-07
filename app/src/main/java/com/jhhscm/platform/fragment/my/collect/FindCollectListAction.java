package com.jhhscm.platform.fragment.my.collect;

import android.content.Context;

import com.jhhscm.platform.fragment.my.labour.action.DelLabourReleaseAction;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.ResultBean;

import retrofit2.Call;

public class FindCollectListAction extends AHttpService<BaseEntity<FindCollectListBean>> {

    private NetBean netBean;

    public static FindCollectListAction newInstance(Context context, NetBean netBean) {
        return new FindCollectListAction(context, netBean);
    }

    public FindCollectListAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.findCollectList(netBean);
    }
}
