package com.jhhscm.platform.http;


import com.jhhscm.platform.aliapi.AliPrePayBean;
import com.jhhscm.platform.fragment.GoodsToCarts.CalculateOrderBean;
import com.jhhscm.platform.fragment.GoodsToCarts.CreateOrderResultBean;
import com.jhhscm.platform.fragment.GoodsToCarts.FindAddressListBean;
import com.jhhscm.platform.fragment.GoodsToCarts.GetCartGoodsByUserCodeBean;
import com.jhhscm.platform.fragment.Mechanics.bean.FindBrandBean;
import com.jhhscm.platform.fragment.Mechanics.bean.FindCategoryBean;
import com.jhhscm.platform.fragment.Mechanics.bean.FindCategoryDetailBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GetComboBoxBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GetGoodsByBrandBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GetGoodsDetailsBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GetGoodsPageListBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GetOldDetailsBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GetOldPageListBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GetRegionBean;
import com.jhhscm.platform.fragment.Mechanics.push.OldMechanicsUpImageBean;
import com.jhhscm.platform.fragment.Mechanics.push.UploadInvalidOrderImgEntity;
import com.jhhscm.platform.fragment.home.AdBean;
import com.jhhscm.platform.fragment.home.bean.FindBrandHomePageBean;
import com.jhhscm.platform.fragment.home.bean.FindCategoryHomePageBean;
import com.jhhscm.platform.fragment.home.bean.FindLabourReleaseHomePageBean;
import com.jhhscm.platform.fragment.home.bean.GetArticleDetailsBean;
import com.jhhscm.platform.fragment.home.bean.GetPageArticleListBean;
import com.jhhscm.platform.fragment.labour.FindLabourReleaseDetailBean;
import com.jhhscm.platform.fragment.labour.FindLabourReleaseListBean;
import com.jhhscm.platform.fragment.labour.FindLabourWorkListBean;
import com.jhhscm.platform.fragment.msg.GetPushListBean;
import com.jhhscm.platform.fragment.my.CheckVersionBean;
import com.jhhscm.platform.fragment.my.collect.FindCollectListBean;
import com.jhhscm.platform.fragment.my.labour.FindLabourListBean;
import com.jhhscm.platform.fragment.my.mechanics.FindOldGoodByUserCodeBean;
import com.jhhscm.platform.fragment.my.order.FindOrderListBean;
import com.jhhscm.platform.fragment.repayment.ContractDetailBean;
import com.jhhscm.platform.fragment.repayment.ContractListBean;
import com.jhhscm.platform.fragment.repayment.ContractPayCreateOrderBean;
import com.jhhscm.platform.fragment.sale.FindGoodsAssessBean;
import com.jhhscm.platform.fragment.sale.FindOrderBean;
import com.jhhscm.platform.fragment.sale.OldGoodOrderHistoryBean;
import com.jhhscm.platform.fragment.vehicle.GpsDetailBean;
import com.jhhscm.platform.fragment.vehicle.GpsTrackDetailBean;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.ResultBean;
import com.jhhscm.platform.http.bean.SaveBean;
import com.jhhscm.platform.http.bean.UserBean;

import java.util.List;

import javax.xml.transform.Result;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


/**
 * 类说明：接口定义
 */
public interface ApiService {

    String USER_VERIFI_CATION = "appLogin/verification";
    String USER_LOGIN = "appLogin/toLogin";

    /**
     * 首页
     */
    String GET_ADVERTISEMENT = "appIndex/getAdvertisement";
    String GET_EXAMPLE = "appIndex/getExample";
    String GET_HOSPITAIL_AND_DOCTOR_FILE = "appIndex/getHospitailAndDoctorFile";

    //验证码获取
    String GET_CODE = "user/getCode";
    //登陆
    String LOGIN = "user/login";
    //获取用户信息
    String GET_USER = "user/getUser";
    //获取轮播、广告
    String GET_AD = "index/getAd";
    //查询首页品牌列表
    String FIND_BRANDHOMEPAGE = "brand/findBrandHomePage";
    //查询首页品牌列表
    String FIND_MOUNTINGSHOMEPAGE = "category/findCategoryHomePage";
    //查询首页劳务招聘
    String FIND_LABOURRELEASEHOMEPAGE = "labour/findLabourReleaseHomePage";
    //查询机械下拉框
    String GET_COMBOBOX = "kv/getComboBox";
    //查询新机列表
    String GET_GOODSPAGELIST = "goods/getGoodsPageList";
    //查询二手机列表
    String GET_OLDPAGELIST = "goods/getOldPageList";
    //信息咨询
    String SAVE_MSG = "msg/saveMsg";
    //收藏
    String SAVE = "collect/save";
    //查看是否收藏
    String FIND_COLLECTBYUSERCODE = "collect/findCollectByUserCode";

    //添加购物车
    String ADD_GOODSTOCARTS = "goodscart/addGoodsToCarts";
    //获取用户购物车列表
    String GET_CARTGOODSBYUSERCODE = "goodscart/getCartGoodsByUserCode";
    //删除购物车信息
    String DEL_GOODSTOCARTS = "goodscart/delGoodsToCarts";
    //修改购物车信息
    String UPDATE_GOODSTOCARTS = "goodscart/updateGoodsToCarts";

    //添加收货地址  addAddress
    String ADD_ADDRESS = "address/addAddress";
    //添加收货地址  delAddress
    String DEL_ADDRESS = "address/delAddress";
    //修改收货地址  updateAddress
    String UPDATE_ADDRESS = "address/updateAddress";
    //获取用户收货地址 findAddressList
    String FIND_ADDRESSLIST = "address/findAddressList";

    //创建订单
    String CREATE_ORDER = "order/createOrder";
    //微信支付下订单接口
    String WX_PREPAY = "pay/wxPrePay";
    //支付宝下订单接口
    String ALI_PREPAY = "pay/aliPrePay";
    //查询订单
    String FIND_ORDER = "order/findOrder";

    //获取行政区域列表
    String GET_REGION = "address/getRegion";
    //查询配件列表
    String FIND_CATEGORY = "category/findCategory";
    //查询配件详情
    String FIND_CATEGORYDETAIL = "category/findCategoryDetail";

    //二手机历史
    String OLD_GOODORDERHISTORY = "goods/oldGoodOrderHistory";
    //二手机估价
    String FIND_GOODSASSESS = "goodsAssess/findGoodsAssess";

    //订单列表
    String FIND_ORDERLIST = "order/findOrderList";

    //查询品牌列表
    String FIND_BRAND = "brand/findBrand";
    //根据品牌获取商品列表
    String GET_GOODSBYBRAND = "goods/getGoodsByBrand";

    @FormUrlEncoded
    @POST(GET_CODE)
    Call<BaseEntity> getCode(@Field("mobile") String mobile);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(GET_CODE)
    Call<BaseEntity> CodeTest(@Body NetBean content);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(GET_USER)
    Call<BaseEntity<UserBean>> getUser(@Body NetBean content);

    @FormUrlEncoded
    @POST(LOGIN)
    Call<BaseEntity> login(@Field("appid") String appid, @Field("content") String content, @Field("sign") String sign);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(LOGIN)
    Call<BaseEntity> LoginTest(@Body NetBean content);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(GET_AD)
    Call<BaseEntity<AdBean>> getAd(@Body NetBean content);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(FIND_BRANDHOMEPAGE)
    Call<BaseEntity<FindBrandHomePageBean>> findBrandHomePage(@Body NetBean content);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(FIND_MOUNTINGSHOMEPAGE)
    Call<BaseEntity<FindCategoryHomePageBean>> findCategoryHomePage(@Body NetBean content);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(FIND_LABOURRELEASEHOMEPAGE)
    Call<BaseEntity<FindLabourReleaseHomePageBean>> findLabourReleaseHomePage(@Body NetBean content);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(GET_COMBOBOX)
    Call<BaseEntity<GetComboBoxBean>> getComboBox(@Body NetBean content);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(GET_GOODSPAGELIST)
    Call<BaseEntity<GetGoodsPageListBean>> getGoodsPageList(@Body NetBean content);

    //查询新机详情
    String GET_GOODSDETAILS = "goods/getGoodsDetails";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(GET_GOODSDETAILS)
    Call<BaseEntity<GetGoodsDetailsBean>> getGoodsDetails(@Body NetBean content);


    //查询二手机详情
    String GET_OLDDETAILS = "goods/getOldDetails";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(GET_OLDDETAILS)
    Call<BaseEntity<GetOldDetailsBean>> getOldDetails(@Body NetBean content);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(GET_OLDPAGELIST)
    Call<BaseEntity<GetOldPageListBean>> getOldPageList(@Body NetBean content);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(SAVE_MSG)
    Call<BaseEntity> saveMsg(@Body NetBean content);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(SAVE)
    Call<BaseEntity> save(@Body NetBean content);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(FIND_COLLECTBYUSERCODE)
    Call<BaseEntity<SaveBean>> findCollectByUserCode(@Body NetBean content);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(ADD_GOODSTOCARTS)
    Call<BaseEntity<ResultBean>> addGoodsToCarts(@Body NetBean content);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(GET_CARTGOODSBYUSERCODE)
    Call<BaseEntity<GetCartGoodsByUserCodeBean>> getCartGoodsByUserCode(@Body NetBean content);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(DEL_GOODSTOCARTS)
    Call<BaseEntity<ResultBean>> delGoodsToCarts(@Body NetBean content);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(UPDATE_GOODSTOCARTS)
    Call<BaseEntity<ResultBean>> updateGoodsToCarts(@Body NetBean content);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(GET_REGION)
    Call<BaseEntity<GetRegionBean>> getRegion(@Body NetBean content);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(FIND_CATEGORY)
    Call<BaseEntity<FindCategoryBean>> findCategory(@Body NetBean content);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(FIND_CATEGORYDETAIL)
    Call<BaseEntity<FindCategoryDetailBean>> findCategoryDetail(@Body NetBean content);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(ADD_ADDRESS)
    Call<BaseEntity<ResultBean>> addAddress(@Body NetBean content);

    //计算订单和运费  calculateOrder
    String CALCULATE_ORDER = "order/calculateOrder";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(CALCULATE_ORDER)
    Call<BaseEntity<CalculateOrderBean>> calculateOrder(@Body NetBean content);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(UPDATE_ADDRESS)
    Call<BaseEntity<ResultBean>> updateAddress(@Body NetBean content);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(DEL_ADDRESS)
    Call<BaseEntity<ResultBean>> delAddress(@Body NetBean content);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(FIND_ADDRESSLIST)
    Call<BaseEntity<FindAddressListBean>> findAddressList(@Body NetBean content);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(CREATE_ORDER)
    Call<BaseEntity<CreateOrderResultBean>> createOrder(@Body NetBean content);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(WX_PREPAY)
    Call<BaseEntity<ResultBean>> wxPrePay(@Body NetBean content);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(ALI_PREPAY)
    Call<BaseEntity<AliPrePayBean>> aliPrePay(@Body NetBean content);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(FIND_ORDER)
    Call<BaseEntity<FindOrderBean>> findOrder(@Body NetBean content);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(OLD_GOODORDERHISTORY)
    Call<BaseEntity<OldGoodOrderHistoryBean>> oldGoodOrderHistory(@Body NetBean content);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(FIND_GOODSASSESS)
    Call<BaseEntity<FindGoodsAssessBean>> findGoodsAssess(@Body NetBean content);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(FIND_ORDERLIST)
    Call<BaseEntity<FindOrderListBean>> findOrderList(@Body NetBean content);

    //取消订单
    String DEL_ORDER = "order/delOrder";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(DEL_ORDER)
    Call<BaseEntity<ResultBean>> delOrder(@Body NetBean content);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(FIND_BRAND)
    Call<BaseEntity<FindBrandBean>> findBrand(@Body NetBean content);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(GET_GOODSBYBRAND)
    Call<BaseEntity<GetGoodsByBrandBean>> getGoodsByBrand(@Body NetBean content);

    //查询劳务招聘列表
    String FIND_LABOURRELEASELIST = "labour/findLabourReleaseList";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(FIND_LABOURRELEASELIST)
    Call<BaseEntity<FindLabourReleaseListBean>> findLabourReleaseList(@Body NetBean content);

    //查询劳务招聘列表
    String FIND_LABOURWORKLIST = "labour/findLabourWorkList";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(FIND_LABOURWORKLIST)
    Call<BaseEntity<FindLabourReleaseListBean>> findLabourWorkList(@Body NetBean content);

    //查询劳务招聘详情
    String FIND_LABOURRELEASEDETAIL = "labour/findLabourReleaseDetail";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(FIND_LABOURRELEASEDETAIL)
    Call<BaseEntity<FindLabourReleaseDetailBean>> findLabourReleaseDetail(@Body NetBean content);

    //查询劳务求职详情
    String FIND_LABOURWORKDETAIL = "labour/findLabourWorkDetail";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(FIND_LABOURWORKDETAIL)
    Call<BaseEntity<FindLabourReleaseDetailBean>> findLabourWorkDetail(@Body NetBean content);

    //发布劳务招聘详情
    String SAVE_LABOURRELEASE = "labour/saveLabourRelease";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(SAVE_LABOURRELEASE)
    Call<BaseEntity<ResultBean>> saveLabourRelease(@Body NetBean content);

    //发布劳务求职详情
    String SAVE_LABOURWORK = "labour/saveLabourWork";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(SAVE_LABOURWORK)
    Call<BaseEntity<ResultBean>> saveLabourWork(@Body NetBean content);

    //查询个人劳务列表
    String FIND_LABOURLIST = "labour/findLabourList";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(FIND_LABOURLIST)
    Call<BaseEntity<FindLabourListBean>> findLabourList(@Body NetBean content);

    //删除招聘信息
    String DEL_LABOURRELEASE = "labour/delLabourRelease";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(DEL_LABOURRELEASE)
    Call<BaseEntity<ResultBean>> delLabourRelease(@Body NetBean content);

    //删除求职信息
    String DEL_LABOURWORK = "labour/delLabourWork";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(DEL_LABOURWORK)
    Call<BaseEntity<ResultBean>> delLabourWork(@Body NetBean content);

    //修改劳务招聘
    String UPDATE_LABOURRELEASE = "labour/updateLabourRelease";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(UPDATE_LABOURRELEASE)
    Call<BaseEntity<ResultBean>> updateLabourRelease(@Body NetBean content);

    //修改求职信息
    String UPDATE_LABOURWORK = "labour/updateLabourWork";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(UPDATE_LABOURWORK)
    Call<BaseEntity<ResultBean>> updateLabourWork(@Body NetBean content);

    //根据用户编号查看收藏列表
    String FIND_COLLECTLIST = "collect/findCollectList";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(FIND_COLLECTLIST)
    Call<BaseEntity<FindCollectListBean>> findCollectList(@Body NetBean content);

    //取消收藏
    String COLLECT_DELETE = "collect/delete";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(COLLECT_DELETE)
    Call<BaseEntity<ResultBean>> collectDelete(@Body NetBean content);

    //我的机子
    String FIND_OLDGOODBYUSERCODE = "goods/findOldGoodByUserCode";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(FIND_OLDGOODBYUSERCODE)
    Call<BaseEntity<FindOldGoodByUserCodeBean>> findOldGoodByUserCode(@Body NetBean content);

    //二手机上传图片
    String UPLOAD_IMAGE_URL = "goods/uploadImg";

    @Multipart
    @POST(UPLOAD_IMAGE_URL)
    Call<OldMechanicsUpImageBean> uploadImages(@Part List<MultipartBody.Part> parts);

    //发布二手机
    String SAVE_OLDGOOD = "goods/saveOldGood";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(SAVE_OLDGOOD)
    Call<BaseEntity<ResultBean>> saveOldGood(@Body NetBean content);

    //消息列表
    String GET_PUSHLIST = "push/getPushList";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(GET_PUSHLIST)
    Call<BaseEntity<GetPushListBean>> getPushList(@Body NetBean content);

    //登出
    String LOGIN_OUT = "user/loginOut";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(LOGIN_OUT)
    Call<BaseEntity<ResultBean>> loginOut(@Body NetBean content);

    //意见反馈
    String SAVE_FEEDBACK = "feedback/save";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(SAVE_FEEDBACK)
    Call<BaseEntity<ResultBean>> saveFeedBack(@Body NetBean content);

    //意见反馈
    String CHECK_VERSION = "update/checkVersion";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(CHECK_VERSION)
    Call<BaseEntity<CheckVersionBean>> checkVersion(@Body NetBean content);

    //校验身份证
    String CHECK_DATA = "user/checkData";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(CHECK_DATA)
    Call<BaseEntity<ResultBean>> checkData(@Body NetBean content);

    //查首页文章列表
    String GET_PAGEARTICLELIST = "article/getPageArticleList";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(GET_PAGEARTICLELIST)
    Call<BaseEntity<GetPageArticleListBean>> getPageArticleList(@Body NetBean content);

    //查文章列表
    String GET_ARTICLELIST = "article/getArticleList";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(GET_ARTICLELIST)
    Call<BaseEntity<GetPageArticleListBean>> getArticleList(@Body NetBean content);

    //查文章详情
    String GET_ARTICLEDETAILS = "article/getArticleDetails";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(GET_ARTICLEDETAILS)
    Call<BaseEntity<GetArticleDetailsBean>> getArticleDetails(@Body NetBean content);

    //租赁上传图片
    String LEASE_UPLOAD_IMG = "lease/uploadImg";

    @Multipart
    @POST(LEASE_UPLOAD_IMG)
    Call<OldMechanicsUpImageBean> uploadLeaseImg(@Part List<MultipartBody.Part> parts);

    //新增租赁
    String LEASE_CREATE = "lease/create";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(LEASE_CREATE)
    Call<BaseEntity<ResultBean>> createLease(@Body NetBean content);

    //根据手机号查询gps信息
    String GPS_DETAIL = "gps/detail";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(GPS_DETAIL)
    Call<BaseEntity<GpsDetailBean>> gpsDetail(@Body NetBean content);

    //查询设备轨迹回放
    String GPS_TRACKDETAIL = "gps/trackDetail";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(GPS_TRACKDETAIL)
    Call<GpsTrackDetailBean> gps_trackdetail(@Body NetBean content);


    //合同列表
    String CONTRACT_LIST = "contract/list";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(CONTRACT_LIST)
    Call<BaseEntity<ContractListBean>> contract_list(@Body NetBean content);

    //还款计划
    String CONTRACT_DETAIL = "contract/detail";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(CONTRACT_DETAIL)
    Call<BaseEntity<ContractDetailBean>> contract_detail(@Body NetBean content);

    //微信下订单
    String CONTRACTPAY_WXPREPAY = "contractPay/wxPrePay";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(CONTRACTPAY_WXPREPAY)
    Call<BaseEntity<ResultBean>> contractPay_wxPrePay(@Body NetBean content);

    //支付宝下订单
    String CONTRACTPAY_ALIPREPAY = "contractPay/aliPrePay";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(CONTRACTPAY_ALIPREPAY)
    Call<BaseEntity<ResultBean>> contractpay_aliprepay(@Body NetBean content);

    //创建合同订单
    String CONTRACTPAY_CREATEORDER = "contractPay/createOrder";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(CONTRACTPAY_CREATEORDER)
    Call<BaseEntity<ContractPayCreateOrderBean>> contractpay_createorder(@Body NetBean content);


}
