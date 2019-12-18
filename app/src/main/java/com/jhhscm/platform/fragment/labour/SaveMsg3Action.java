package com.jhhscm.platform.fragment.labour;

import android.content.Context;

import com.jhhscm.platform.fragment.home.action.SaveMsgAction;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;

import retrofit2.Call;

public class SaveMsg3Action extends AHttpService<BaseEntity> {

    private NetBean netBean;

    public static SaveMsg3Action newInstance(Context context, NetBean netBean) {
        return new SaveMsg3Action(context, netBean);
    }

    public SaveMsg3Action(Context context, NetBean netBean) {
        super(context);
        this.netBean = netBean;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.saveMsg3(netBean);
    }
}
