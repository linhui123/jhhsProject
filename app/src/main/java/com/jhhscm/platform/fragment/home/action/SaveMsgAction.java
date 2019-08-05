package com.jhhscm.platform.fragment.home.action;

import android.content.Context;

import com.jhhscm.platform.fragment.home.AdBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class SaveMsgAction extends AHttpService<BaseEntity> {

    private NetBean netBean;

    public static SaveMsgAction newInstance(Context context, NetBean netBean) {
        return new SaveMsgAction(context, netBean);
    }

    public SaveMsgAction(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.saveMsg(netBean);
    }
}