package com.jhhscm.platform.fragment.home.action;

import android.content.Context;

import com.jhhscm.platform.fragment.home.bean.GetArticleDetailsBean;
import com.jhhscm.platform.fragment.home.bean.GetPageArticleListBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class GetArticleDetailsAction extends AHttpService<BaseEntity<GetArticleDetailsBean>> {

    private NetBean netBean;

    public static GetArticleDetailsAction newInstance(Context context, NetBean netBean) {
        return new GetArticleDetailsAction(context, netBean);
    }

    public GetArticleDetailsAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.getArticleDetails(netBean);
    }
}

