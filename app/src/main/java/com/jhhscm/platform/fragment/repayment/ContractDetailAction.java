package com.jhhscm.platform.fragment.repayment;

import android.content.Context;

import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class ContractDetailAction extends AHttpService<BaseEntity<ContractDetailBean>> {

    private NetBean netBean;

    public static ContractDetailAction newInstance(Context context, NetBean netBean) {
        return new ContractDetailAction(context, netBean);
    }

    public ContractDetailAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.contract_detail(netBean);
    }
}

