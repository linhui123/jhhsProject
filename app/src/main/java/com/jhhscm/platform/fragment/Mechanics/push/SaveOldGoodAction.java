package com.jhhscm.platform.fragment.Mechanics.push;

import android.content.Context;

import com.jhhscm.platform.fragment.Mechanics.action.GetGoodsByBrandAction;
import com.jhhscm.platform.fragment.Mechanics.bean.GetGoodsByBrandBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.ResultBean;

import retrofit2.Call;

public class SaveOldGoodAction extends AHttpService<BaseEntity<ResultBean>> {

    private NetBean netBean;

    public static SaveOldGoodAction newInstance(Context context, NetBean netBean) {
        return new SaveOldGoodAction(context, netBean);
    }

    public SaveOldGoodAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.saveOldGood(netBean);
    }
}

