package com.jhhscm.platform.http;


import com.jhhscm.platform.aliapi.AliPrePayBean;
import com.jhhscm.platform.fragment.GoodsToCarts.CalculateOrderBean;
import com.jhhscm.platform.fragment.GoodsToCarts.CreateOrderResultBean;
import com.jhhscm.platform.fragment.GoodsToCarts.FindAddressListBean;
import com.jhhscm.platform.fragment.GoodsToCarts.GetCartGoodsByUserCodeBean;
import com.jhhscm.platform.fragment.Mechanics.bean.BrandModelBean;
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
import com.jhhscm.platform.fragment.aftersale.BusinessDetailBean;
import com.jhhscm.platform.fragment.aftersale.FindBusListBean;
import com.jhhscm.platform.fragment.coupon.CouponGetListBean;
import com.jhhscm.platform.fragment.coupon.CouponListBean;
import com.jhhscm.platform.fragment.coupon.GetNewCouponslistBean;
import com.jhhscm.platform.fragment.home.AdBean;
import com.jhhscm.platform.fragment.home.bean.FindBrandHomePageBean;
import com.jhhscm.platform.fragment.home.bean.FindCategoryHomePageBean;
import com.jhhscm.platform.fragment.home.bean.FindLabourReleaseHomePageBean;
import com.jhhscm.platform.fragment.home.bean.GetArticleDetailsBean;
import com.jhhscm.platform.fragment.home.bean.GetPageArticleListBean;
import com.jhhscm.platform.fragment.invitation.ReqListBean;
import com.jhhscm.platform.fragment.invitation.UserShareUrlBean;
import com.jhhscm.platform.fragment.labour.FindLabourReleaseDetailBean;
import com.jhhscm.platform.fragment.labour.FindLabourReleaseListBean;
import com.jhhscm.platform.fragment.msg.GetPushListBean;
import com.jhhscm.platform.fragment.my.BusCountBean;
import com.jhhscm.platform.fragment.my.CheckVersionBean;
import com.jhhscm.platform.fragment.my.UserCenterBean;
import com.jhhscm.platform.fragment.my.book.AllSumBean;
import com.jhhscm.platform.fragment.my.book.AllSumByDataTimeBean;
import com.jhhscm.platform.fragment.my.book.DetailToolBean;
import com.jhhscm.platform.fragment.my.collect.FindCollectListBean;
import com.jhhscm.platform.fragment.my.labour.FindLabourListBean;
import com.jhhscm.platform.fragment.my.mechanics.FindGoodsOwnerBean;
import com.jhhscm.platform.fragment.my.mechanics.FindOldGoodByUserCodeBean;
import com.jhhscm.platform.fragment.my.order.FindOrderListBean;
import com.jhhscm.platform.fragment.my.store.action.BusinessFindcategorybyBuscodeBean;
import com.jhhscm.platform.fragment.my.store.action.BusinessSumdataBean;
import com.jhhscm.platform.fragment.my.store.action.FindBusGoodsOwnerListByUserCodeBean;
import com.jhhscm.platform.fragment.my.store.action.FindBusGoodsOwnerOrderListByUserCodeBean;
import com.jhhscm.platform.fragment.my.store.action.FindBusOrderListBean;
import com.jhhscm.platform.fragment.my.store.action.FindBusUserServerListBean;
import com.jhhscm.platform.fragment.my.store.action.FindUserGoodsOwnerBean;
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

    String USER_VERIFI_CATION = "v1-0/appLogin/verification";
    String USER_LOGIN = "appLogin/toLogin";

    /**
     * 首页
     */
    String GET_ADVERTISEMENT = "v1-0/appIndex/getAdvertisement";
    String GET_EXAMPLE = "v1-0/appIndex/getExample";
    String GET_HOSPITAIL_AND_DOCTOR_FILE = "v1-0/appIndex/getHospitailAndDoctorFile";

    //验证码获取
    String GET_CODE = "v1-0/user/getCode";
    //登陆
    String LOGIN = "v1-0/user/login";
    //获取用户信息
    String GET_USER = "v1-0/user/getUser";
    //获取轮播、广告
    String GET_AD = "v1-0/index/getAd";
    //查询首页品牌列表
    String FIND_BRANDHOMEPAGE = "v1-0/brand/findBrandHomePage";
    //查询首页品牌列表
    String FIND_MOUNTINGSHOMEPAGE = "v1-0/category/findCategoryHomePage";
    //查询首页劳务招聘
    String FIND_LABOURRELEASEHOMEPAGE = "v1-0/labour/findLabourReleaseHomePage";
    //查询机械下拉框
    String GET_COMBOBOX = "v1-0/kv/getComboBox";
    //查询新机列表
    String GET_GOODSPAGELIST = "v1-0/goods/getGoodsPageList";
    //查询二手机列表
    String GET_OLDPAGELIST = "v1-0/goods/getOldPageList";
    //信息咨询
    String SAVE_MSG = "v1-0/msg/saveMsg";
    //收藏
    String SAVE = "v1-0/collect/save";
    //查看是否收藏
    String FIND_COLLECTBYUSERCODE = "v1-0/collect/findCollectByUserCode";

    //添加购物车
    String ADD_GOODSTOCARTS = "v1-0/goodscart/addGoodsToCarts";
    //获取用户购物车列表
    String GET_CARTGOODSBYUSERCODE = "v1-3/goodscart/getCartGoodsByUserCode";
    //删除购物车信息
    String DEL_GOODSTOCARTS = "v1-0/goodscart/delGoodsToCarts";
    //修改购物车信息
    String UPDATE_GOODSTOCARTS = "v1-0/goodscart/updateGoodsToCarts";

    //添加收货地址  addAddress
    String ADD_ADDRESS = "v1-0/address/addAddress";
    //添加收货地址  delAddress
    String DEL_ADDRESS = "v1-0/address/delAddress";
    //修改收货地址  updateAddress
    String UPDATE_ADDRESS = "v1-0/address/updateAddress";
    //获取用户收货地址 findAddressList
    String FIND_ADDRESSLIST = "v1-0/address/findAddressList";

    //创建订单
    String CREATE_ORDER = "v1-0/order/createOrder";
    //微信支付下订单接口
    String WX_PREPAY = "v1-0/pay/wxPrePay";
    //支付宝下订单接口
    String ALI_PREPAY = "v1-0/pay/aliPrePay";
    //查询订单
    String FIND_ORDER = "v1-0/order/findOrder";

    //获取行政区域列表
    String GET_REGION = "v1-0/address/getRegion";
    //查询配件列表
    String FIND_CATEGORY = "v1-0/category/findCategory";
    //查询配件详情
    String FIND_CATEGORYDETAIL = "v1-0/category/findCategoryDetail";

    //二手机历史
    String OLD_GOODORDERHISTORY = "v1-0/goods/oldGoodOrderHistory";
    //二手机估价
    String FIND_GOODSASSESS = "v1-0/goodsAssess/findGoodsAssess";

    //订单列表 v1-3/order/findOrderList  v1-0/order/findOrderList
    String FIND_ORDERLIST = "v1-0/order/findOrderList";

    //查询品牌列表
    String FIND_BRAND = "v1-0/brand/findBrand";
    //根据品牌获取商品列表
    String GET_GOODSBYBRAND = "v1-0/goods/getGoodsByBrand";

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
    String GET_GOODSDETAILS = "v1-0/goods/getGoodsDetails";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(GET_GOODSDETAILS)
    Call<BaseEntity<GetGoodsDetailsBean>> getGoodsDetails(@Body NetBean content);


    //查询二手机详情
    String GET_OLDDETAILS = "v1-0/goods/getOldDetails";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(GET_OLDDETAILS)
    Call<BaseEntity<GetOldDetailsBean>> getOldDetails(@Body NetBean content);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(GET_OLDPAGELIST)
    Call<BaseEntity<GetOldPageListBean>> getOldPageList(@Body NetBean content);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(SAVE_MSG)
    Call<BaseEntity> saveMsg(@Body NetBean content);

    //信息咨询 3期接口 与一期一起用
    String SAVE_MSG_3 = "v1-3/msg/saveMsg";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(SAVE_MSG_3)
    Call<BaseEntity> saveMsg3(@Body NetBean content);

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

    String FIND_CATEGORY_v3 = "v1-3/search/findCategory";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(FIND_CATEGORY_v3)
    Call<BaseEntity<FindCategoryBean>> findCategory_v3(@Body NetBean content);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(FIND_CATEGORYDETAIL)
    Call<BaseEntity<FindCategoryDetailBean>> findCategoryDetail(@Body NetBean content);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(ADD_ADDRESS)
    Call<BaseEntity<ResultBean>> addAddress(@Body NetBean content);

    //计算订单和运费  calculateOrder
    String CALCULATE_ORDER = "v1-0/order/calculateOrder";

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

    String FIND_ORDERDETAIL3 = "v1-3/order/findOrderDetail";
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(FIND_ORDERDETAIL3)
    Call<BaseEntity<FindOrderListBean.OrderDetail>> find_OrderDetail3(@Body NetBean content);

    //取消订单
    String DEL_ORDER = "v1-0/order/delOrder";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(DEL_ORDER)
    Call<BaseEntity<ResultBean>> delOrder(@Body NetBean content);

    //获取订单列表 v3
    String FIND_ORDERLIST3 = "v1-3/order/findOrderList";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(FIND_ORDERLIST3)
    Call<BaseEntity<FindOrderListBean>> findOrderList3(@Body NetBean content);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(FIND_BRAND)
    Call<BaseEntity<FindBrandBean>> findBrand(@Body NetBean content);

    //机型列表
    String BRAND_MODELLISTBEAN = "v1-3/brandmodel/list";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(BRAND_MODELLISTBEAN)
    Call<BaseEntity<BrandModelBean>> brandModelListBean(@Body NetBean content);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(GET_GOODSBYBRAND)
    Call<BaseEntity<GetGoodsByBrandBean>> getGoodsByBrand(@Body NetBean content);

    //查询劳务招聘列表
    String FIND_LABOURRELEASELIST = "v1-0/labour/findLabourReleaseList";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(FIND_LABOURRELEASELIST)
    Call<BaseEntity<FindLabourReleaseListBean>> findLabourReleaseList(@Body NetBean content);

    //查询劳务招聘列表
    String FIND_LABOURWORKLIST = "v1-0/labour/findLabourWorkList";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(FIND_LABOURWORKLIST)
    Call<BaseEntity<FindLabourReleaseListBean>> findLabourWorkList(@Body NetBean content);

    //查询劳务招聘详情
    String FIND_LABOURRELEASEDETAIL = "v1-0/labour/findLabourReleaseDetail";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(FIND_LABOURRELEASEDETAIL)
    Call<BaseEntity<FindLabourReleaseDetailBean>> findLabourReleaseDetail(@Body NetBean content);

    //查询劳务求职详情
    String FIND_LABOURWORKDETAIL = "v1-0/labour/findLabourWorkDetail";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(FIND_LABOURWORKDETAIL)
    Call<BaseEntity<FindLabourReleaseDetailBean>> findLabourWorkDetail(@Body NetBean content);

    //发布劳务招聘详情
    String SAVE_LABOURRELEASE = "v1-0/labour/saveLabourRelease";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(SAVE_LABOURRELEASE)
    Call<BaseEntity<ResultBean>> saveLabourRelease(@Body NetBean content);

    //发布劳务求职详情
    String SAVE_LABOURWORK = "v1-0/labour/saveLabourWork";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(SAVE_LABOURWORK)
    Call<BaseEntity<ResultBean>> saveLabourWork(@Body NetBean content);

    //查询个人劳务列表
    String FIND_LABOURLIST = "v1-0/labour/findLabourList";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(FIND_LABOURLIST)
    Call<BaseEntity<FindLabourListBean>> findLabourList(@Body NetBean content);

    //删除招聘信息
    String DEL_LABOURRELEASE = "v1-0/labour/delLabourRelease";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(DEL_LABOURRELEASE)
    Call<BaseEntity<ResultBean>> delLabourRelease(@Body NetBean content);

    //删除求职信息
    String DEL_LABOURWORK = "v1-0/labour/delLabourWork";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(DEL_LABOURWORK)
    Call<BaseEntity<ResultBean>> delLabourWork(@Body NetBean content);

    //修改劳务招聘
    String UPDATE_LABOURRELEASE = "v1-0/labour/updateLabourRelease";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(UPDATE_LABOURRELEASE)
    Call<BaseEntity<ResultBean>> updateLabourRelease(@Body NetBean content);

    //修改求职信息
    String UPDATE_LABOURWORK = "v1-0/labour/updateLabourWork";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(UPDATE_LABOURWORK)
    Call<BaseEntity<ResultBean>> updateLabourWork(@Body NetBean content);

    //根据用户编号查看收藏列表
    String FIND_COLLECTLIST = "v1-0/collect/findCollectList";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(FIND_COLLECTLIST)
    Call<BaseEntity<FindCollectListBean>> findCollectList(@Body NetBean content);

    //取消收藏
    String COLLECT_DELETE = "v1-0/collect/delete";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(COLLECT_DELETE)
    Call<BaseEntity<ResultBean>> collectDelete(@Body NetBean content);

    //我的机子
    String FIND_OLDGOODBYUSERCODE = "v1-0/goods/findOldGoodByUserCode";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(FIND_OLDGOODBYUSERCODE)
    Call<BaseEntity<FindOldGoodByUserCodeBean>> findOldGoodByUserCode(@Body NetBean content);

    //二手机上传图片
    String UPLOAD_IMAGE_URL = "v1-0/goods/uploadImg";

    @Multipart
    @POST(UPLOAD_IMAGE_URL)
    Call<OldMechanicsUpImageBean> uploadImages(@Part List<MultipartBody.Part> parts);

    //票据上传图片
    String UPLOAD_BUSORDER_IMAGE_URL = "v1-3/busorder/uploadImg";

    @Multipart
    @POST(UPLOAD_BUSORDER_IMAGE_URL)
    Call<OldMechanicsUpImageBean> uploadBusorderImages(@Part List<MultipartBody.Part> parts);

    //发布二手机
    String SAVE_OLDGOOD = "v1-0/goods/saveOldGood";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(SAVE_OLDGOOD)
    Call<BaseEntity<ResultBean>> saveOldGood(@Body NetBean content);

    //消息列表
    String GET_PUSHLIST = "v1-0/push/getPushList";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(GET_PUSHLIST)
    Call<BaseEntity<GetPushListBean>> getPushList(@Body NetBean content);

    //登出
    String LOGIN_OUT = "v1-0/user/loginOut";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(LOGIN_OUT)
    Call<BaseEntity<ResultBean>> loginOut(@Body NetBean content);

    //意见反馈
    String SAVE_FEEDBACK = "v1-0/feedback/save";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(SAVE_FEEDBACK)
    Call<BaseEntity<ResultBean>> saveFeedBack(@Body NetBean content);

    //意见反馈
    String CHECK_VERSION = "v1-0/update/checkVersion";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(CHECK_VERSION)
    Call<BaseEntity<CheckVersionBean>> checkVersion(@Body NetBean content);

    //校验身份证
    String CHECK_DATA = "v1-0/user/checkData";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(CHECK_DATA)
    Call<BaseEntity<ResultBean>> checkData(@Body NetBean content);

    //查首页文章列表
    String GET_PAGEARTICLELIST = "v1-0/article/getPageArticleList";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(GET_PAGEARTICLELIST)
    Call<BaseEntity<GetPageArticleListBean>> getPageArticleList(@Body NetBean content);

    //查文章列表
    String GET_ARTICLELIST = "v1-0/article/getArticleList";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(GET_ARTICLELIST)
    Call<BaseEntity<GetPageArticleListBean>> getArticleList(@Body NetBean content);

    //查文章详情
    String GET_ARTICLEDETAILS = "v1-0/article/getArticleDetails";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(GET_ARTICLEDETAILS)
    Call<BaseEntity<GetArticleDetailsBean>> getArticleDetails(@Body NetBean content);

    //租赁上传图片
    String LEASE_UPLOAD_IMG = "v1-0/lease/uploadImg";

    @Multipart
    @POST(LEASE_UPLOAD_IMG)
    Call<OldMechanicsUpImageBean> uploadLeaseImg(@Part List<MultipartBody.Part> parts);

    //新增租赁
    String LEASE_CREATE = "v1-0/lease/create";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(LEASE_CREATE)
    Call<BaseEntity<ResultBean>> createLease(@Body NetBean content);

    //根据手机号查询gps信息v1-0/gps/detail3
    String GPS_DETAIL = "v1-0/gps/detail3";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(GPS_DETAIL)
    Call<BaseEntity<GpsDetailBean>> gpsDetail(@Body NetBean content);

    //查询设备轨迹回放 v1-0/gps/trackDetail3
    String GPS_TRACKDETAIL = "v1-0/gps/trackDetail3";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(GPS_TRACKDETAIL)
    Call<BaseEntity<GpsTrackDetailBean>> gps_trackdetail(@Body NetBean content);


    //合同列表
    String CONTRACT_LIST = "v1-0/contract/list";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(CONTRACT_LIST)
    Call<BaseEntity<ContractListBean>> contract_list(@Body NetBean content);

    //还款计划
    String CONTRACT_DETAIL = "v1-0/contract/detail";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(CONTRACT_DETAIL)
    Call<BaseEntity<ContractDetailBean>> contract_detail(@Body NetBean content);

    //微信下订单
    String CONTRACTPAY_WXPREPAY = "v1-0/contractPay/wxPrePay";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(CONTRACTPAY_WXPREPAY)
    Call<BaseEntity<ResultBean>> contractPay_wxPrePay(@Body NetBean content);

    //支付宝下订单
    String CONTRACTPAY_ALIPREPAY = "v1-0/contractPay/aliPrePay";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(CONTRACTPAY_ALIPREPAY)
    Call<BaseEntity<ResultBean>> contractpay_aliprepay(@Body NetBean content);

    //创建合同订单
    String CONTRACTPAY_CREATEORDER = "v1-0/contractPay/createOrder";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(CONTRACTPAY_CREATEORDER)
    Call<BaseEntity<ContractPayCreateOrderBean>> contractpay_createorder(@Body NetBean content);

    //个人中心数据统计
    String USERS_CENTER = "v1-3/users/center";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(USERS_CENTER)
    Call<BaseEntity<UserCenterBean>> users_center(@Body NetBean content);

    //个人中心我的店铺统计
    String BUSCOUNT = "v1-3/users/buscount";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(BUSCOUNT)
    Call<BaseEntity<BusCountBean>> buscount(@Body NetBean content);

    //个人中心我的邀请人列表(商户会员列表也是调用此接口)
    String USERS_REQLIST = "v1-3/users/reqlist";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(USERS_REQLIST)
    Call<BaseEntity<ReqListBean>> users_reqlist(@Body NetBean content);

    //个人中心我的设备列表
    String FINDGOODSOWNER = "v1-3/business/findGoodsOwner";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(FINDGOODSOWNER)
    Call<BaseEntity<FindGoodsOwnerBean>> findGoodsOwner(@Body NetBean content);

    //个人中心我的设备添加
    String ADDGOODSOWNER = "v1-3/business/AddGoodsOwner";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(ADDGOODSOWNER)
    Call<BaseEntity<ResultBean>> addGoodsOwner(@Body NetBean content);

    //个人中心我的设备更新
    String UPDATEGOODSOWNER = "v1-3/business/updateGoodsOwner";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(UPDATEGOODSOWNER)
    Call<BaseEntity<ResultBean>> updateGoodsOwner(@Body NetBean content);

    //个人中心我的设备删除
    String DEL_GOODSOWNER = "v1-3/business/delGoodsOwner";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(DEL_GOODSOWNER)
    Call<BaseEntity<ResultBean>> delGoodsOwner(@Body NetBean content);

    //我的店铺信息统计
    String BUSINESS_SUMDATA = "v1-3/business/sumdata";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(BUSINESS_SUMDATA)
    Call<BaseEntity<BusinessSumdataBean>> business_sumdata(@Body NetBean content);

    //我的店铺信息统计
    String BUSINESS_FINDCATEGORYBYBUSCODE = "v1-3/business/findCategoryByBusCode";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(BUSINESS_FINDCATEGORYBYBUSCODE)
    Call<BaseEntity<BusinessFindcategorybyBuscodeBean>> business_findcategorybybuscode(@Body NetBean content);

    //个人中心邀请码URL
    String USERS_SHAREURL = "v1-3/users/shareurl";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(USERS_SHAREURL)
    Call<BaseEntity<UserShareUrlBean>> users_shareurl(@Body NetBean content);

    //我的券列表
    String COUPON_LIST = "v1-3/coupon/list";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(COUPON_LIST)
    Call<BaseEntity<CouponListBean>> coupon_list(@Body NetBean content);

    //支付可用券列表
    String PAY_USELIST = "v1-3/coupon/payUseList";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(PAY_USELIST)
    Call<BaseEntity<CouponListBean>> payUseList(@Body NetBean content);

    //券领取列表
    String COUPON_GETLIST = "v1-3/coupon/getlist";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(COUPON_GETLIST)
    Call<BaseEntity<CouponGetListBean>> coupon_getlist(@Body NetBean content);

    //券领取
    String GET_COUPON = "v1-3/coupon/get";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(GET_COUPON)
    Call<BaseEntity<ResultBean>> get_coupon(@Body NetBean content);

    //我的店铺分员服务记录
    String FIND_BUSUSERSERVERLIST = "v1-3/business/findBusUserServerList";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(FIND_BUSUSERSERVERLIST)
    Call<BaseEntity<FindBusUserServerListBean>> findBusUserServerList(@Body NetBean content);

    //售后列表（店铺列表）
    String FIND_BUSLIST = "v1-3/search/findBusList";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(FIND_BUSLIST)
    Call<BaseEntity<FindBusListBean>> findBusList(@Body NetBean content);

    //查询商户详情
    String BUSINESS_DETAIL = "v1-3/business/detail";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(BUSINESS_DETAIL)
    Call<BaseEntity<BusinessDetailBean>> business_detail(@Body NetBean content);

    //我的店铺订单列表（商户角度查询）
    String FIND_BUSORDERLIST = "v1-3/business/findBusOrderList";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(FIND_BUSORDERLIST)
    Call<BaseEntity<FindBusOrderListBean>> findBusOrderList(@Body NetBean content);

    //首页判断是否是新人
    String ISNEWUSER = "v1-3/coupon/isNewUser";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(ISNEWUSER)
    Call<BaseEntity<ResultBean>> isNewUser(@Body NetBean content);

    //我的券列表-弹出首页券列表
    String GET_FIRSTPAGECOUPONSLIST = "v1-3/coupon/getFirstPageCouponslist";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(GET_FIRSTPAGECOUPONSLIST)
    Call<BaseEntity<GetNewCouponslistBean>> getFirstPageCouponslist(@Body NetBean content);

    //商户创建订单选择用户列表
    String FIND_USERGOODSOWNER = "v1-3/busorder/findUserGoodsOwner";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(FIND_USERGOODSOWNER)
    Call<BaseEntity<FindUserGoodsOwnerBean>> findUserGoodsOwner(@Body NetBean content);

    //我的店铺商户提交订单
    String BUSORDER_CREATEORDER = "v1-3/busorder/createOrder";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(BUSORDER_CREATEORDER)
    Call<BaseEntity<ResultBean>> busorder_createOrder(@Body NetBean content);

    //我的店铺分员服务记录
    String FIND_BUSGOODSOWNERLISTBYUSERCODE = "v1-3/business/findBusGoodsOwnerListByUserCode";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(FIND_BUSGOODSOWNERLISTBYUSERCODE)
    Call<BaseEntity<FindBusGoodsOwnerListByUserCodeBean>> findBusGoodsOwnerListByUserCode(@Body NetBean content);

    //我的店铺分员服务记录-通过点击设备列表查看订单
    String FIND_BUSGOODSOWNERORDERLISTBYUSERCODE = "v1-3/business/findBusGoodsOwnerOrderListByUserCode";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(FIND_BUSGOODSOWNERORDERLISTBYUSERCODE)
    Call<BaseEntity<FindBusGoodsOwnerOrderListByUserCodeBean>> findBusGoodsOwnerOrderListByUserCode(@Body NetBean content);

    //我的工具首页汇总统计
    String ALL_SUM = "v1-3/tool/allSum";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(ALL_SUM)
    Call<BaseEntity<AllSumBean>> allSum(@Body NetBean content);

    //我的工具首页明细按天汇总统计
    String ALL_SUMBYDATATIME = "v1-3/tool/allSumByDataTime";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(ALL_SUMBYDATATIME)
    Call<BaseEntity<AllSumByDataTimeBean>> allSumByDataTime(@Body NetBean content);

    //我的工具记帐明细
    String DETAIL_TOOL = "v1-3/tool/detail";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(DETAIL_TOOL)
    Call<BaseEntity<DetailToolBean>> detailTool(@Body NetBean content);

    //我的工具记帐明细 删除
    String DEL_TOOL = "v1-3/tool/del";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(DEL_TOOL)
    Call<BaseEntity<ResultBean>> del_tool(@Body NetBean content);

    //我的工具收入支出添加
    String TOOL_ADD = "v1-3/tool/add";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(TOOL_ADD)
    Call<BaseEntity<ResultBean>> tool_add(@Body NetBean content);

    //用户确认商户提交的订单状态
    String UPDATE_ORDERSTATUS = "v1-3/order/updateOrderStatus";

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(UPDATE_ORDERSTATUS)
    Call<BaseEntity<ResultBean>> updateOrderStatus(@Body NetBean content);
}
