package com.jhhscm.platform.http;

import android.content.Context;


import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.tool.NETUtils;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/4/18.
 */

public abstract class AHttpService<T> {
    protected Context context;
    protected ApiService apiService;

    public AHttpService(Context context) {
        this.context = context;
        apiService = ApiServiceModule.getInstance().providerApiService(context);
    }

    /**
     * 发送请求数据
     *
     * @param iResCallback
     * @return
     */
    public Call request(final IResCallback<T> iResCallback) {
        Call call = null;
        if (!NETUtils.isNetworkConnected(context)) {
            //处理无网络情况
            if (iResCallback != null) {
                iResCallback.onCallback(IResCallback.RESULT_NOT_NET_ERROR, null, null);
            }
            return call;
        }
        try {
            call = newRetrofitCall(apiService);
            call.enqueue(new retrofit2.Callback<T>() {

                @Override
                public void onResponse(Call<T> call, Response<T> response) {
                    int responseCode = response.code();
                    if (responseCode == 200) {
                        if (iResCallback != null) {
                            iResCallback.onCallback(IResCallback.RESULT_OK, response, null);
                        }
                    } else {
                        BaseErrorInfo baseErrorInfo = new BaseErrorInfo();
                        baseErrorInfo.setCode(responseCode);
                        baseErrorInfo.setMsg(response.message());
                        if (iResCallback != null) {
                            iResCallback.onCallback(IResCallback.RESULT_SERVICE_ERROR, null, baseErrorInfo);
                        }
                    }
                }

                @Override
                public void onFailure(Call<T> call, Throwable t) {
                    if (iResCallback != null) {
                        iResCallback.onCallback(IResCallback.RESULT_NET_ERROR, null, null);
                    }
                }
            });
        } catch (Throwable e) {
            e.printStackTrace();
            if (iResCallback != null) {
                iResCallback.onCallback(IResCallback.RESULT_OTHER_ERROR, null, null);
            }
        }
        return call;
    }

    //子类继承实现
    protected abstract Call newRetrofitCall(ApiService apiService);

    public interface IResCallback<T> {
        int RESULT_OK = 0;

        int RESULT_NOT_NET_ERROR = 1;

        int RESULT_NET_ERROR = 2;

        int RESULT_SERVICE_ERROR = 3;

        int RESULT_OTHER_ERROR = 4;

        void onCallback(int resultCode, Response<T> response, BaseErrorInfo baseErrorInfo);
    }
}
