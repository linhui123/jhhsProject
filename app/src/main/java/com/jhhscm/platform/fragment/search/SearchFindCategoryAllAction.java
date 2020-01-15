package com.jhhscm.platform.fragment.search;

import android.content.Context;

import com.jhhscm.platform.fragment.sale.FindGoodsAssessAction;
import com.jhhscm.platform.fragment.sale.FindGoodsAssessBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class SearchFindCategoryAllAction extends AHttpService<BaseEntity<SearchBean>> {

    private NetBean netBean;

    public static SearchFindCategoryAllAction newInstance(Context context, NetBean netBean) {
        return new SearchFindCategoryAllAction(context, netBean);
    }

    public SearchFindCategoryAllAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.findCategoryAll(netBean);
    }
}
