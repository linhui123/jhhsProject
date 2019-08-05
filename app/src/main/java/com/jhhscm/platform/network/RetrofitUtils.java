package com.jhhscm.platform.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 类名称：RetrofitUtils
 * 创建人：wangliteng
 * 创建时间：2016-5-18 14:57:11
 * 类描述：封装一个retrofit集成0kHttp3的抽象基类
 */
public abstract class RetrofitUtils {
    // /http://47.100.170.22:8080
//http://10.1.100.7:8090/merchant/index/login
     //http://192.168.2.31:9090/app/home/banner
    //http://127.0.0.1:8083/wajueji/v1-0/user/login
    //服务器路径
    private static final String API_SERVER = "http://merchant.fecar.com";
       //http://47.100.170.22:8080
    //public static final String GANHUO_API = "http://192.168.2.22:9091";
    public static final String GANHUO_API = "http://192.168.0.248:8083";
//https://api.github.com/
    //http://192.168.16.147:8080
    private static Retrofit mRetrofit;
    private static OkHttpClient mOkHttpClient;

    /**
     * 获取Retrofit对象
     *
     * @return
     */
    protected static Retrofit getRetrofit() {

        if (null == mRetrofit) {

            if (null == mOkHttpClient) {
                mOkHttpClient = OkHttp3Utils.getOkHttpClient();
            }

            //Retrofit2后使用build设计模式
            mRetrofit = new Retrofit.Builder()
                    //设置服务器路径
                    .baseUrl(GANHUO_API + "/")
                    //添加转化库，默认是Gson
                    .addConverterFactory(GsonConverterFactory.create())
                    //添加回调库，采用RxJava
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    //设置使用okhttp网络请求
                    .client(mOkHttpClient)

                    .build();

        }
        return mRetrofit;
    }


}
