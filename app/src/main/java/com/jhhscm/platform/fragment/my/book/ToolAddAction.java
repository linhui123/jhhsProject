package com.jhhscm.platform.fragment.my.book;

import android.content.Context;

import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.ResultBean;

import retrofit2.Call;

public class ToolAddAction extends AHttpService<BaseEntity<ResultBean>> {

    private NetBean netBean;

    public static ToolAddAction newInstance(Context context, NetBean netBean) {
        return new ToolAddAction(context, netBean);
    }

    public ToolAddAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.tool_add(netBean);
    }
}
