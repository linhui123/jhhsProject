package com.jhhscm.platform.http.action;

import android.content.Context;

import com.jhhscm.platform.bean.UserData;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.Fields;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.UploadImageResponse;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

public class GetCodeAction extends AHttpService<BaseEntity> {

    private NetBean mobile;

    public static GetCodeAction newInstance(Context context, NetBean mobile) {
        return new GetCodeAction(context, mobile);
    }

    public GetCodeAction(Context context, NetBean mobile) {
        super(context);
        this.mobile = mobile;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        return apiService.CodeTest(mobile);
    }
}
