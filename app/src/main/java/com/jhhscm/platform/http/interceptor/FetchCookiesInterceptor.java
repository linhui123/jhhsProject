package com.jhhscm.platform.http.interceptor;

import android.content.Context;

import com.jhhscm.platform.tool.ConfigUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Administrator on 2016/10/11.
 */
public class FetchCookiesInterceptor implements Interceptor {
    private Context mContext;

    public FetchCookiesInterceptor(Context context) {
        super();
        this.mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            final StringBuffer cookieBuffer = new StringBuffer();
            Observable.from(originalResponse.headers("Set-Cookie"))
                    .map(new Func1<String, String>() {
                        @Override
                        public String call(String s) {
                            String[] cookieArray = s.split(";");
                            return cookieArray[0];
                        }
                    })
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String cookie) {
                            if(!cookie.contains("PBCSTOKEN")) {
                                cookieBuffer.append(cookie).append("; PBCSTOKEN=");
                            }else{
                                String[] tokenArray=cookie.split("=");
                                String sid= ConfigUtils.getHttpHeadersCookie(mContext);
                                cookieBuffer.append(sid+tokenArray[1]);
                            }
                        }
                    });
            ConfigUtils.setHttpHeadersCookie(mContext, cookieBuffer.toString());
        }
        return originalResponse;
    }
}
