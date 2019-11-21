package com.jhhscm.platform.fragment.my.book;

import android.content.Context;

import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.ResultBean;

import retrofit2.Call;

public class DelToolAction extends AHttpService<BaseEntity<ResultBean>> {

    private NetBean netBean;

    public static DelToolAction newInstance(Context context, NetBean netBean) {
        return new DelToolAction(context, netBean);
    }

    public DelToolAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.del_tool(netBean);
    }
}
