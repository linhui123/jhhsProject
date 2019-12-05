package com.jhhscm.platform.fragment.repayment;

import android.content.Context;

import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.ResultBean;

import retrofit2.Call;

public class ContractWXPrePayAction  extends AHttpService<BaseEntity<ResultBean>> {

    private NetBean netBean;

    public static ContractWXPrePayAction newInstance(Context context, NetBean netBean) {
        return new ContractWXPrePayAction(context, netBean);
    }

    public ContractWXPrePayAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.contractPay_wxPrePay(netBean);
    }
}
