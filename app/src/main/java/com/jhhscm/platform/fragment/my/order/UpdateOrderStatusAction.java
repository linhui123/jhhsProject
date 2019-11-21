package com.jhhscm.platform.fragment.my.order;

import android.content.Context;

import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.ResultBean;

import retrofit2.Call;

public class UpdateOrderStatusAction extends AHttpService<BaseEntity<ResultBean>> {

    private NetBean netBean;

    public static UpdateOrderStatusAction newInstance(Context context, NetBean netBean) {
        return new UpdateOrderStatusAction(context, netBean);
    }

    public UpdateOrderStatusAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.updateOrderStatus(netBean);
    }
}
