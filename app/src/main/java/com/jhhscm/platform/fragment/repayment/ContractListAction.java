package com.jhhscm.platform.fragment.repayment;

import android.content.Context;

import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class ContractListAction extends AHttpService<BaseEntity<ContractListBean>> {

    private NetBean netBean;

    public static ContractListAction newInstance(Context context, NetBean netBean) {
        return new ContractListAction(context, netBean);
    }

    public ContractListAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.contract_list(netBean);
    }
}

