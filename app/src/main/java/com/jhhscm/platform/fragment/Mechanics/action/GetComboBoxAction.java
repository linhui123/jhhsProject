package com.jhhscm.platform.fragment.Mechanics.action;

import android.content.Context;

import com.jhhscm.platform.fragment.Mechanics.bean.GetComboBoxBean;
import com.jhhscm.platform.fragment.home.action.FindBrandHomePageAction;
import com.jhhscm.platform.fragment.home.bean.FindBrandHomePageBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class GetComboBoxAction extends AHttpService<BaseEntity<GetComboBoxBean>> {

    private NetBean netBean;

    public static GetComboBoxAction newInstance(Context context, NetBean netBean) {
        return new GetComboBoxAction(context, netBean);
    }

    public GetComboBoxAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.getComboBox(netBean);
    }
}

