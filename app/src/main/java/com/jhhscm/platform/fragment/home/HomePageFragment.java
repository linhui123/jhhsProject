package com.jhhscm.platform.fragment.home;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.google.gson.Gson;
import com.jhhscm.platform.MyApplication;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.MainActivity;
import com.jhhscm.platform.activity.SearchActivity;
import com.jhhscm.platform.activity.MsgActivity;
import com.jhhscm.platform.activity.h5.H5Activity;
import com.jhhscm.platform.databinding.FragmentHomePageBinding;
import com.jhhscm.platform.event.ConsultationEvent;
import com.jhhscm.platform.event.FinishEvent;
import com.jhhscm.platform.event.ForceCloseEvent;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.home.action.FindBrandHomePageAction;
import com.jhhscm.platform.fragment.home.action.FindCategoryHomePageAction;
import com.jhhscm.platform.fragment.home.action.FindLabourReleaseHomePageAction;
import com.jhhscm.platform.fragment.home.action.GetAdAction;
import com.jhhscm.platform.fragment.home.action.GetPageArticleListAction;
import com.jhhscm.platform.fragment.home.action.SaveMsgAction;
import com.jhhscm.platform.fragment.home.bean.FindBrandHomePageBean;
import com.jhhscm.platform.fragment.home.bean.FindCategoryHomePageBean;
import com.jhhscm.platform.fragment.home.bean.FindLabourReleaseHomePageBean;
import com.jhhscm.platform.fragment.home.bean.GetPageArticleListBean;
import com.jhhscm.platform.fragment.my.CheckVersionAction;
import com.jhhscm.platform.fragment.my.CheckVersionBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.http.sign.SignObject;
import com.jhhscm.platform.jpush.ExampleUtil;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.DataUtil;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.StringUtils;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.dialog.SimpleDialog;
import com.jhhscm.platform.views.dialog.TelPhoneDialog;
import com.jhhscm.platform.views.dialog.UpdateDialog;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;
import com.umeng.analytics.MobclickAgent;

import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

import static com.amap.api.location.AMapLocationClientOption.AMapLocationMode.Hight_Accuracy;

public class HomePageFragment extends AbsFragment<FragmentHomePageBinding> implements WeatherSearch.OnWeatherSearchListener {
    private HomePageItem homePageItem;
    private HomePageAdapter mAdapter;

    public static HomePageFragment instance() {
        HomePageFragment view = new HomePageFragment();
        return view;
    }

    @Override
    protected FragmentHomePageBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentHomePageBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        RelativeLayout.LayoutParams llParams = (RelativeLayout.LayoutParams) mDataBinding.rlTop.getLayoutParams();
        llParams.topMargin += DisplayUtils.getStatusBarHeight(getContext());
        mDataBinding.rlTop.setLayoutParams(llParams);

        EventBusUtil.registerEvent(this);
//        homePageItem = new HomePageItem();
        initView();
        setUpMap();
        mDataBinding.wrvRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new HomePageAdapter(getContext());
        mDataBinding.wrvRecycler.setAdapter(mAdapter);
        mDataBinding.wrvRecycler.loadComplete(true, false);
        mDataBinding.wrvRecycler.hideLoad();
        mDataBinding.wrvRecycler.autoRefresh();
        mDataBinding.wrvRecycler.setOnPullListener(new WrappedRecyclerView.OnPullListener() {
            @Override
            public void onRefresh(RecyclerView view) {
                initView();
                getAD(2);
                getAD(3);
                getAD(4);//首页广告位
                getAD(5);//首页活动位
                getAD(6);
                findBrandHomePage();
            }

            @Override
            public void onLoadMore(RecyclerView view) {
            }
        });

        initTel();
        checkVersion();
        mDataBinding.msgImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(getContext(), "msg_home");
                MsgActivity.start(getActivity());
            }
        });

        mDataBinding.homeEidt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(getContext(), "search_home");
                SearchActivity.start(getContext());
            }
        });
//ExampleUtil.GetVersion(getContext())
        Log.e("home", "getChannel :" + StringUtils.getChannel(getContext()));
//        ToastUtil.show(getContext(), "getChannel :" + StringUtils.getChannel(getContext()));
        if (ConfigUtils.getHomePageItem(getContext()) != null) {
            mAdapter.setDetail(ConfigUtils.getHomePageItem(getContext()));
        }
    }

    private void initTel() {
        mDataBinding.wrvRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    /**new State 一共有三种状态
                     * SCROLL_STATE_IDLE：目前RecyclerView不是滚动，也就是静止
                     * SCROLL_STATE_DRAGGING：RecyclerView目前被外部输入如用户触摸输入。
                     * SCROLL_STATE_SETTLING：RecyclerView目前动画虽然不是在最后一个位置外部控制。**/

                    mDataBinding.tel.setVisibility(View.VISIBLE);
                    imgTranslateAnimation(100, 0);
                } else if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    imgTranslateAnimation(0, 100);
                    mDataBinding.tel.setVisibility(View.GONE);
                }
            }
        });

        mDataBinding.tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TelPhoneDialog(getContext(), new TelPhoneDialog.CallbackListener() {

                    @Override
                    public void clickYes(String phone) {
                        MobclickAgent.onEvent(getContext(), "consult_home");
                        saveMsg(phone);
                    }
                }).show();
            }
        });
    }

    //动画的左右进出平移动画
    private void imgTranslateAnimation(float fromXDelta, float toXDelta) {
        TranslateAnimation translateAnimation = new TranslateAnimation(fromXDelta, toXDelta, 0, 0);
        translateAnimation.setFillAfter(true);//这句话会造成imageView.setVisibility(GONE)的时候，会停留在动画最后的地方，导致还没有隐藏的假象。
        translateAnimation.setDuration(800);
        mDataBinding.tel.setAnimation(translateAnimation);
        mDataBinding.tel.startAnimation(translateAnimation);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }

    /**
     * 获取轮播、广告
     */
    private void getAD(final int position) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("position", position + "");
        map.put("app_version", ExampleUtil.GetVersion(MyApplication.getInstance()));
        map.put("app_platform", StringUtils.getChannel(MyApplication.getInstance()));
        map.put("appid", "336abf9e97cd4276bf8aecde9d32ed99");
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getActivity(), map, "getAD type:" + position);
        NetBean netBean = new NetBean();
        netBean.setToken("");
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(GetAdAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<AdBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<AdBean>> response, BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    if (position == 2) {
//                                        homePageItem.setAdBean1(response.body().getData());
                                        homePageItem.adBean1 = response.body().getData();
                                    } else if (position == 3) {
//                                        homePageItem.setAdBean3(response.body().getData());
                                        homePageItem.adBean3 = response.body().getData();
                                    } else if (position == 4) {
//                                        homePageItem.setAdBean2(response.body().getData());
                                        homePageItem.adBean2 = response.body().getData();
                                    } else if (position == 5) {
//                                        homePageItem.setAdBean4(response.body().getData());
                                        homePageItem.adBean4 = response.body().getData();
                                    } else if (position == 6) {
                                        if (response.body().getData().getResult().size() > 0 &&
                                                response.body().getData().getResult().get(0) != null) {
                                            Gson gson = new Gson();
                                            AdBean.ResultBean adBean = gson.fromJson(response.body().getData().getResult().get(0).getContent(), AdBean.ResultBean.class);
                                            if (adBean.getPARAM() != null) {
                                                MyApplication.getInstance().setZulinUrl(adBean.getPARAM().getHREF_URL());
                                            }
                                        }
                                    }
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }

    /**
     * 获取品牌
     */
    private void findBrandHomePage() {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("app_version", ExampleUtil.GetVersion(getContext()));
        map.put("brand_type", "1");
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getActivity(), map, "findBrandHomePage");
        NetBean netBean = new NetBean();
        netBean.setToken("");
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(FindBrandHomePageAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<FindBrandHomePageBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<FindBrandHomePageBean>> response, BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    homePageItem.findBrandHomePageBean = response.body().getData();

                                    findCategoryHomePage();
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                    findCategoryHomePage();
                                }
                            }
                        }
                    }
                }));
    }

    /**
     * 获取配件
     */
    private void findCategoryHomePage() {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("app_version", ExampleUtil.GetVersion(getContext()));
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getActivity(), map, "findCategoryHomePage");
        NetBean netBean = new NetBean();
        netBean.setToken("");
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(FindCategoryHomePageAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<FindCategoryHomePageBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<FindCategoryHomePageBean>> response, BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    homePageItem.findCategoryHomePageBean = response.body().getData();
                                    findLabourReleaseHomePage();
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                    findLabourReleaseHomePage();
                                }
                            }
                        }
                    }
                }));
    }

    /**
     * 获取首页文章列表
     */
    private void getPageArticleList() {
        Map<String, Object> map = new TreeMap<String, Object>();
        map.put("page", 1);
        map.put("limit", 5);
        map.put("article_type_list", 1);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = SignObject.getSignKey(getActivity(), map, "getPageArticleList");
        NetBean netBean = new NetBean();
        netBean.setToken("");
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(GetPageArticleListAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<GetPageArticleListBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<GetPageArticleListBean>> response, BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    homePageItem.getPageArticleListBean = response.body().getData();
                                    setView();
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                    setView();
                                }
                            }
                        }
                    }
                }));
    }

    /**
     * 获取劳务资讯
     */
    private void findLabourReleaseHomePage() {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("app_version", ExampleUtil.GetVersion(getContext()));
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getActivity(), map, "findLabourReleaseHomePage");
        NetBean netBean = new NetBean();
        netBean.setToken("");
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(FindLabourReleaseHomePageAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<FindLabourReleaseHomePageBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<FindLabourReleaseHomePageBean>> response, BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    homePageItem.findLabourReleaseHomePageBean = response.body().getData();
                                    getPageArticleList();
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                    getPageArticleList();
                                }
                            }
                        }
                    }
                }));
    }

    private void setView() {
        mAdapter.setDetail(homePageItem);
        mDataBinding.wrvRecycler.loadComplete(true, false);

        ConfigUtils.setHomePageItem(getContext(), homePageItem);
        Log.e("home", "setView homePageItem :" + homePageItem);
        Log.e("home", "setView HomePageItem :" + ConfigUtils.getHomePageItem(getContext()));
    }

    /**
     * 咨询
     */
    public void onEvent(ConsultationEvent event) {
        if (event.phone != null && event.phone.length() > 0) {
            saveMsg(event.phone);
        }
    }

    public void onEvent(ForceCloseEvent event) {
        getActivity().finish();
    }

    /**
     * 信息咨询
     */
    private void saveMsg(final String phone) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("mobile", phone);
        map.put("type", "0");
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getActivity(), map, "saveMsg");
        NetBean netBean = new NetBean();
        netBean.setToken("");
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(SaveMsgAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity> response, BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    new SimpleDialog(getContext(), phone, new SimpleDialog.CallbackListener() {
                                        @Override
                                        public void clickYes() {

                                        }
                                    }).show();
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }

    /**
     * 版本更新查询
     */
    private void checkVersion() {
        Map<String, String> map = new TreeMap<String, String>();
        String version = ExampleUtil.GetVersion(getContext()).contains("V")
                ? ExampleUtil.GetVersion(getContext()).replace("V", "")
                : ExampleUtil.GetVersion(getContext());
        Log.e("", "version " + version);
        map.put("app_version", version);
        map.put("app_type", "0");
        map.put("app_platform", StringUtils.getChannel(MyApplication.getInstance()));
        //336abf9e97cd4276bf8aecde9d32ed99
        //336abf9e97cd4276bf8aecde9d32ed0a
        map.put("appid", "336abf9e97cd4276bf8aecde9d32ed99");
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getActivity(), map, "checkVersion");
        NetBean netBean = new NetBean();
        netBean.setToken("");
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(CheckVersionAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<CheckVersionBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<CheckVersionBean>> response, BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    CheckVersionBean checkVersionBean = response.body().getData();
                                    updateToast(checkVersionBean);
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }

    /**
     * 更新提示
     * 相同更新地址；一周提醒一次
     */
    private void updateToast(CheckVersionBean checkVersionBean) {
        if ("0".equals(checkVersionBean.getIs_update())) {
            //需要更新
            if ("0".equals(checkVersionBean.getIs_must_update())) {//需要强制更新
                final UpdateDialog alertDialog = new UpdateDialog(getContext(),
                        checkVersionBean.getUrl(), new UpdateDialog.CallbackListener() {
                    @Override
                    public void clickYes() {
                    }
                }, true);
                alertDialog.show();
            } else {
                //相同更新地址；一周内提醒一次
                if (ConfigUtils.getUpdataUrl(getContext()).equals(checkVersionBean.getUrl())) {
                    //一周内提醒一次
                    if (ConfigUtils.getUpdataTime(getContext()).length() > 0) {
                        long time = DataUtil.getLongTime(ConfigUtils.getUpdataTime(getContext())
                                , DataUtil.getCurDate("yyyy-MM-dd HH:mm:ss")
                                , "yyyy-MM-dd HH:mm:ss");
                        //getLongToMintues  getLongToHours
                        Log.e("更新提示时间差", "小时： " + DataUtil.getLongToHours(time, "yyyy-MM-dd HH:mm:ss"));
                        if (Integer.parseInt(DataUtil.getLongToMintues(time, "yyyy-MM-dd HH:mm:ss")) >= 168) {
                            //时间差间隔超过12小时
                            final UpdateDialog alertDialog = new UpdateDialog(getContext(),
                                    checkVersionBean.getUrl(), new UpdateDialog.CallbackListener() {
                                @Override
                                public void clickYes() {
                                }
                            }, false);
                            alertDialog.show();
                            ConfigUtils.setUpdataTime(getContext(), DataUtil.getCurDate("yyyy-MM-dd HH:mm:ss"));
                        }
                    } else {
                        ConfigUtils.setUpdataTime(getContext(), DataUtil.getCurDate("yyyy-MM-dd HH:mm:ss"));
                        final UpdateDialog alertDialog = new UpdateDialog(getContext(),
                                checkVersionBean.getUrl(), new UpdateDialog.CallbackListener() {
                            @Override
                            public void clickYes() {

                            }
                        }, false);
                        alertDialog.show();
                    }
                } else {
                    final UpdateDialog alertDialog = new UpdateDialog(getContext(),
                            checkVersionBean.getUrl(), new UpdateDialog.CallbackListener() {
                        @Override
                        public void clickYes() {
                        }
                    }, false);
                    alertDialog.show();
                    ConfigUtils.setUpdataUrl(getContext(), checkVersionBean.getUrl());
                }

            }
        } else {
            ConfigUtils.setUpdataUrl(getContext(), "");
            ConfigUtils.setUpdataTime(getContext(), "");
        }
    }

    //天气
    public AMapLocationClient mLocationClient = null;    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    private WeatherSearchQuery mquery;
    private WeatherSearch mweathersearch;
    private LocalWeatherLive weatherlive;
    int month;
    int day;
    Calendar cal;

    private void initView() {
        cal = Calendar.getInstance();
        month = cal.get(Calendar.MONTH) + 1;
        day = cal.get(Calendar.DAY_OF_MONTH);
        mDataBinding.wetherDate.setText(month + "/" + day);
        mLocationClient = new AMapLocationClient(getActivity());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);//设置其为定位完成后的回调函数
    }

    private void setUpMap() {
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(Hight_Accuracy);        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);        //给

        mLocationClient.setLocationOption(mLocationOption);        //启动定位
        mLocationClient.startLocation();
    }

    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    double lat = amapLocation.getLatitude();
                    double lon = amapLocation.getLongitude();
                    mDataBinding.cityText.setText(amapLocation.getCity());
                    getWetherData();
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("joe", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    };

    private void getWetherData() {
        mquery = new WeatherSearchQuery(mDataBinding.cityText.getText().toString(), WeatherSearchQuery.WEATHER_TYPE_LIVE);
        mweathersearch = new WeatherSearch(getActivity());
        mweathersearch.setOnWeatherSearchListener(this);
        mweathersearch.setQuery(mquery);        //异步搜索
        mweathersearch.searchWeatherAsyn();
        mquery = new WeatherSearchQuery(mDataBinding.cityText.getText().toString(), WeatherSearchQuery.WEATHER_TYPE_FORECAST);
        mweathersearch = new WeatherSearch(getActivity());
        mweathersearch.setOnWeatherSearchListener(this);
        mweathersearch.setQuery(mquery);//异步搜索
        mweathersearch.searchWeatherAsyn();
    }

    @Override
    public void onWeatherLiveSearched(LocalWeatherLiveResult weatherLiveResult, int rCode) {
        if (rCode == 1000) {
            if (weatherLiveResult != null && weatherLiveResult.getLiveResult() != null) {
                weatherlive = weatherLiveResult.getLiveResult();
//                Log.e("weatherlive", "weatherlive :" + weatherlive.getProvince());
//                Log.e("weatherlive", "weatherlive :" + weatherlive.getCity());
//                Log.e("weatherlive", "weatherlive :" + weatherlive.getWeather());
//                Log.e("weatherlive", "getTemperature :" + weatherlive.getTemperature());
                mDataBinding.wetherTemperature.setText(weatherlive.getTemperature() + "℃");
                if ("阴".equals(weatherlive.getWeather())) {
                    mDataBinding.wetherImg.setBackgroundResource(R.mipmap.ic_yin);
                }
                if ("晴".equals(weatherlive.getWeather())) {
                    mDataBinding.wetherImg.setBackgroundResource(R.mipmap.qingtian_bg);
                }
                if ("阵雨".equals(weatherlive.getWeather())) {
                    mDataBinding.wetherImg.setBackgroundResource(R.mipmap.ic_zhenyu);
                }
                if ("多云".equals(weatherlive.getWeather())) {
                    mDataBinding.wetherImg.setBackgroundResource(R.mipmap.ic_duoyun);
                }
                if ("小雨".equals(weatherlive.getWeather())) {
                    mDataBinding.wetherImg.setBackgroundResource(R.mipmap.ic_xiaoyu);
                }
                if ("中雨".equals(weatherlive.getWeather())) {
                    mDataBinding.wetherImg.setBackgroundResource(R.mipmap.ic_zhongyu);
                }
                if ("大雨".equals(weatherlive.getWeather())) {
                    mDataBinding.wetherImg.setBackgroundResource(R.mipmap.ic_dayu);
                }
                if ("暴雨".equals(weatherlive.getWeather())) {
                    mDataBinding.wetherImg.setBackgroundResource(R.mipmap.ic_baoyu);
                }
                if ("雷阵雨".equals(weatherlive.getWeather())) {
                    mDataBinding.wetherImg.setBackgroundResource(R.mipmap.ic_leizhenyu);
                }
                if (weatherlive.getWeather().contains("雪")) {
                    mDataBinding.wetherImg.setBackgroundResource(R.mipmap.ic_xue);
                }
                if ("雾".equals(weatherlive.getWeather())) {
                    mDataBinding.wetherImg.setBackgroundResource(R.mipmap.ic_wu);
                }
                if ("霾".equals(weatherlive.getWeather())) {
                    mDataBinding.wetherImg.setBackgroundResource(R.mipmap.ic_mai);
                }
            } else {
//                ToastUtil.show(getActivity(), "天气无数据");
            }
        } else {
//            ToastUtil.showerror(WeatherSearchActivity.this, rCode);
        }
    }

    @Override
    public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int i) {

    }
}