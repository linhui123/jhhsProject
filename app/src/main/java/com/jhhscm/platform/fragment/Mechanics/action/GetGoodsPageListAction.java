package com.jhhscm.platform.fragment.Mechanics.action;

import android.content.Context;

import com.jhhscm.platform.fragment.Mechanics.bean.GetComboBoxBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GetGoodsPageListBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class GetGoodsPageListAction extends AHttpService<BaseEntity<GetGoodsPageListBean>> {

    private NetBean netBean;

    public static GetGoodsPageListAction newInstance(Context context, NetBean netBean) {
        return new GetGoodsPageListAction(context, netBean);
    }

    public GetGoodsPageListAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.getGoodsPageList(netBean);
    }
}

