package com.jhhscm.platform.fragment.home.action;

import android.content.Context;

import com.jhhscm.platform.fragment.home.AdBean;
import com.jhhscm.platform.fragment.home.bean.GetPageArticleListBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class GetPageArticleListAction extends AHttpService<BaseEntity<GetPageArticleListBean>> {

    private NetBean netBean;

    public static GetPageArticleListAction newInstance(Context context, NetBean netBean) {
        return new GetPageArticleListAction(context, netBean);
    }

    public GetPageArticleListAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.getPageArticleList(netBean);
    }
}


