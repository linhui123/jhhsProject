package com.jhhscm.platform.fragment.aftersale;

import android.content.Context;

import com.jhhscm.platform.fragment.Mechanics.action.SaveAction;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class SaveBusAction extends AHttpService<BaseEntity> {

    private NetBean netBean;

    public static SaveBusAction newInstance(Context context, NetBean netBean) {
        return new SaveBusAction(context, netBean);
    }

    public SaveBusAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.saveBus(netBean);
    }
}
