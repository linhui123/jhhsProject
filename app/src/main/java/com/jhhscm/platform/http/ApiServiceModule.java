package com.jhhscm.platform.http;

import android.content.Context;

import com.jhhscm.platform.BuildConfig;
import com.jhhscm.platform.MyApplication;
import com.jhhscm.platform.R;
import com.jhhscm.platform.http.interceptor.CommonInterceptor;
import com.jhhscm.platform.http.interceptor.FetchCookiesInterceptor;
import com.jhhscm.platform.http.interceptor.SetCookiesInterceptor;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 类说明：Retrofit初始化
 * 作者：yangxiaoping on 2016/5/11 16:23
 */
public class ApiServiceModule {
    private static ApiServiceModule ourInstance = new ApiServiceModule();
    //URL定义
    public static String BASE_URL2 = "http://47.106.110.219/#/newMachine";//显示
    public static String BASE_URL3 = "http://192.168.0.209:8083/wajueji/";//测试
    public static String BASE_URL4 = "http://120.76.101.183:9090/wajueji/";//外网测试
    public static String BASE_URL5 = "http://api.jhhscm.cn/wajueji/";

    private Retrofit mApiRetrofit = null;
    private Retrofit mApiTaskRetrofit = null;
    private Retrofit mApiFileUploadRetrofit = null;
    private Retrofit mApiWXPayRetrofit = null;
    private ApiFileUploadService mApiFileUploadService = null;
    private ApiService mApiService = null;

    private ApiServiceModule() {
    }

    public static ApiServiceModule getInstance() {
//        Log.e("http","getBaseUrl : "+ MyApplication.getBaseUrl());

        if (MyApplication.getBaseUrl() == null) {
            MyApplication.setBaseUrl(BASE_URL5);
        }
        return ourInstance;
    }

    Retrofit providerApiRetrofit(Context context) {
        if (mApiFileUploadRetrofit == null) {
            try {
                mApiFileUploadRetrofit = new Retrofit.Builder()
                        .baseUrl(MyApplication.getBaseUrl())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(providerFileUploadOkHttpClient(context))
                        .build();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                mApiFileUploadRetrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL5)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(providerFileUploadOkHttpClient(context))
                        .build();
            }
        }
        return mApiFileUploadRetrofit;
    }

//    public ApiFileUploadService providerApiFileUploadService(Context context) {
//        if (mApiFileUploadService == null) {
//            mApiFileUploadService = providerApiFileUploadRetrofit(context).create(ApiFileUploadService.class);
//        }
//        return mApiFileUploadService;
//    }
//    Retrofit providerApiFileUploadRetrofit(Context context) {
//        if (mApiFileUploadRetrofit == null) {
//            mApiFileUploadRetrofit = new Retrofit.Builder()
//                    .baseUrl(BASE_URLS)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .client(providerFileUploadOkHttpClient(context))
//                    .build();
//        }
//        return mApiFileUploadRetrofit;
//    }

    public ApiService providerApiService(Context context) {
        if (mApiService == null) {
            mApiService = providerApiRetrofit(context).create(ApiService.class);
        }
        return mApiService;
    }


//    private OkHttpClient getClient(){
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        if (BuildConfig.DEBUG) {
//            // Log信息拦截器
//            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//这里可以选择拦截级别
//
//            //设置 Debug Log 模式
//            builder.addInterceptor(loggingInterceptor);
//        }
//
//        OkHttpClient client = builder.build();
//        return client;
//    }

    OkHttpClient providerFileUploadOkHttpClient(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(100, TimeUnit.SECONDS);
        builder.writeTimeout(100, TimeUnit.SECONDS);
        builder.connectTimeout(100, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
            builder.addNetworkInterceptor(logging);
        }
        builder.addInterceptor(new FetchCookiesInterceptor(context));
        builder.addInterceptor(new SetCookiesInterceptor(context));
        builder.addInterceptor(new CommonInterceptor(context));

//        try {
//            builder.sslSocketFactory(getSSLSocketFactory(context));
//        } catch (CertificateException e) {
//            e.printStackTrace();
//        } catch (KeyStoreException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (KeyManagementException e) {
//            e.printStackTrace();
//        }
//        builder.hostnameVerifier(new HostnameVerifier() {
//            @Override
//            public boolean verify(String hostname, SSLSession session) {
//                return true;
//            }
//        });
        return builder.build();
    }

    private SSLSocketFactory getSSLSocketFactory(Context context) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, KeyManagementException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        InputStream inputStream = context.getResources().openRawResource(R.raw.uda_91uda);
        Certificate ca = cf.generateCertificate(inputStream);
        inputStream.close();

        KeyStore keyStore = KeyStore.getInstance("bks");
        keyStore.load(null, null);
        keyStore.setCertificateEntry("uda", ca);
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        TrustManager[] wrappedTrustManagers = getWrappedTrustManagers(tmf.getTrustManagers());
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, wrappedTrustManagers, null);
        return sslContext.getSocketFactory();
    }

    private TrustManager[] getWrappedTrustManagers(TrustManager[] trustManagers) {
        final X509TrustManager originalTrustManager = (X509TrustManager) trustManagers[0];
        return new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                try {
                    originalTrustManager.checkClientTrusted(chain, authType);
                } catch (CertificateException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                try {
                    originalTrustManager.checkServerTrusted(chain, authType);
                } catch (CertificateException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return originalTrustManager.getAcceptedIssuers();
            }
        }};
    }

}
