package com.jhhscm.platform.http.interceptor;

import android.content.Context;

import com.jhhscm.platform.tool.ConfigUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Administrator on 2016/10/11.
 */
public class SetCookiesInterceptor implements Interceptor {
    private Context mContext;

    public SetCookiesInterceptor(Context context) {
        super();
        this.mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request.Builder builder = chain.request().newBuilder();
        String cookie = ConfigUtils.getHttpHeadersCookie(mContext);
        Observable.just(cookie)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String cookie) {
                        builder.addHeader("Cookie", cookie);
                    }
                });
        return chain.proceed(builder.build());
    }
}
