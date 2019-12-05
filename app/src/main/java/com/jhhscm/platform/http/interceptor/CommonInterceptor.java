package com.jhhscm.platform.http.interceptor;

import android.content.Context;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/10/18/018.
 */

public class CommonInterceptor implements Interceptor {
    private Context mContext;

    public CommonInterceptor(Context context) {
        super();
        this.mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        String mToken = "";
        String mAppointmentDepartment="";
//        if (ConfigUtils.getCurrentUser(mContext) != null) {
//        mToken = ConfigUtils.getCurrentUser(BLSCApp.getInstance()).getToken();
//            mAppointmentDepartment=ConfigUtils.getDepartMentId(BLSCApp.getInstance());
//        }
        Request oldRequest = chain.request();
        // 添加新的参数
        HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                .newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host());
//                .addQueryParameter(Fields.From_Type, "7");
//                .addQueryParameter(Fields.Token, mToken)

//                .addQueryParameter(Fields.AppointmentDepartment,mAppointmentDepartment);

        // 新的请求
        Request newRequest = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(authorizedUrlBuilder.build())
                .build();

        return chain.proceed(newRequest);
    }
}

