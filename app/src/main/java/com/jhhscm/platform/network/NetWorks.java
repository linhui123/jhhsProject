//package com.jhhscm.platform.network;
//import com.jhhscm.platform.bean.GirlsBean;
//import com.jhhscm.platform.bean.LogingResultBean;
//import com.jhhscm.platform.bean.PayResultZhiFBean;
//import com.jhhscm.platform.bean.PayReusltBean;
//import com.jhhscm.platform.bean.PostLoginBean;
//import com.jhhscm.platform.bean.UserData;
//
//import okhttp3.RequestBody;
//import retrofit2.http.Body;
//import retrofit2.http.Field;
//import retrofit2.http.FormUrlEncoded;
//import retrofit2.http.GET;
//import retrofit2.http.Headers;
//import retrofit2.http.POST;
//import retrofit2.http.Path;
//import retrofit2.http.Query;
//import rx.Observable;
//import rx.Observer;
//import rx.android.schedulers.AndroidSchedulers;
//import rx.schedulers.Schedulers;
//
//
///**
// * 类名称：NetWorks
// * 创建人：wangliteng
// * 创建时间：2016-5-18 14:57:11
// * 类描述：网络请求的操作类
// */
//public class NetWorks extends RetrofitUtils {
//    public static final String BASE_URL = "http://192.168.0.243:8083/";//qa测试服
//    //支付宝回调
//    public static final String SUBMIT_CAR_4S_ALIPAY_CALL = BASE_URL + "/alipayRaisalNote";
//    //微信
//    protected static final String WXPAY = BASE_URL + "/bus/service";
//
//    protected static final NetService service = getRetrofit().create(NetService.class);
//
//    //设缓存有效期为1天
//    protected static final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
//    //查询缓存的Cache-Control设置，使用缓存
//    protected static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
//    //查询网络的Cache-Control设置。不使用缓存
//    protected static final String CACHE_CONTROL_NETWORK = "max-age=0";
//
//
//    private interface NetService {
//       /* 开源测试接口*/
//        @GET("api/data/{type}/{count}/{page}")
//        Observable<GirlsBean> getGirls(
//                @Path("type") String type,
//                @Path("count") int count,
//                @Path("page") int page
//        );
//        @FormUrlEncoded
//        @POST("bus/service")
//        Observable<PayReusltBean> postPayMsgMonery(@Field("sysFlag") String sysFlag, @Field("act") String act, @Field("method") String method, @Field("matchId") String matchId,
//                                                   @Field("driverId") String driverId, @Field("paySource") String paySource, @Field("loginToken") String loginToken);
//
//
//        @FormUrlEncoded
//        @POST("bus/service")
//        Observable<PayResultZhiFBean> postPayMsgMoneryZfu(@Field("sysFlag") String sysFlag, @Field("act") String act, @Field("method") String method, @Field("matchId") String matchId,
//                                                          @Field("driverId") String driverId, @Field("paySource") String paySource, @Field("loginToken") String loginToken);
//
//        @Headers({"Content-type:application/json;charset=UTF-8"})
//        @POST("wajueji/v1-0/user/login")
//        Observable<LogingResultBean> logingTest(@Body PostLoginBean content);
//
//        @Headers({"Content-type:application/json;charset=UTF-8"})
//        @POST("wajueji/v1-0/user/getCode")
//        Observable<LogingResultBean> codeTest(@Body UserData content);
//
//    }
//
//    public static void getGirls(String type, int count,int page,Observer<GirlsBean>observer){
//
//        setSubscribe(service.getGirls(type,count,page),observer);
//    }
//    public static void postPayMsgMonery(String sysFlag, String act, String method, String matchId,
//                                        String driverId, String paySource,String loginToken, Observer<PayReusltBean> observer) {
//        setSubscribe(service.postPayMsgMonery(sysFlag, act, method, matchId, driverId, paySource, loginToken), observer);
//    }
//
//    public static void postPayMsgMoneryZfu(String sysFlag, String act, String method, String matchId,
//                                           String driverId, String paySource,String loginToken, Observer<PayResultZhiFBean> observer) {
//        setSubscribe(service.postPayMsgMoneryZfu(sysFlag, act, method, matchId, driverId, paySource, loginToken), observer);
//    }
//
//
//    public static void logingTest(@Body PostLoginBean content,Observer<LogingResultBean> observer) {
//        setSubscribe(service.logingTest(content), observer);
//    }
//    public static void CodeTest(@Body UserData content, Observer<LogingResultBean> observer) {
//        setSubscribe(service.codeTest(content), observer);
//    }
//
//
//    /**
//     * 插入观察者
//     * @param observable
//     * @param observer
//     * @param <T>
//     */
//    public static <T> void setSubscribe(Observable<T> observable, Observer<T> observer) {
//        observable.subscribeOn(Schedulers.io())
//                .subscribeOn(Schedulers.newThread())//子线程访问网络
//                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
//                .subscribe(observer);
//    }
//    public static void setSubscribe2(Observable<Object> observable, Observer<Object> observer) {
//        observable.subscribeOn(Schedulers.io())
//                .subscribeOn(Schedulers.newThread())//子线程访问网络
//                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
//                .subscribe(observer);
//    }
//
//}
