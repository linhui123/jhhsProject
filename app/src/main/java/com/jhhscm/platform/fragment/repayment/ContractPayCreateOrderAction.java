package com.jhhscm.platform.fragment.repayment;

import android.content.Context;

import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class ContractPayCreateOrderAction extends AHttpService<BaseEntity<ContractPayCreateOrderBean>> {

    private NetBean netBean;

    public static ContractPayCreateOrderAction newInstance(Context context, NetBean netBean) {
        return new ContractPayCreateOrderAction(context, netBean);
    }

    public ContractPayCreateOrderAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.contractpay_createorder(netBean);
    }
}