package com.jhhscm.platform.fragment.sale;

import android.content.Context;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;
import retrofit2.Call;

public class FindGoodsAssessAction extends AHttpService<BaseEntity<OldGoodOrderHistoryBean>> {

    private NetBean netBean;

    public static FindGoodsAssessAction newInstance(Context context, NetBean netBean) {
        return new FindGoodsAssessAction(context, netBean);
    }

    public FindGoodsAssessAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.findGoodsAssess(netBean);
    }
}