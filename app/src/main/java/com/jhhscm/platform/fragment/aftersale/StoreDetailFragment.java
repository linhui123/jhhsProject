package com.jhhscm.platform.fragment.aftersale;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.BuildConfig;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.activity.StoreDetailActivity;
import com.jhhscm.platform.databinding.FragmentStoreDetailBinding;
import com.jhhscm.platform.event.FinishEvent;
import com.jhhscm.platform.fragment.Mechanics.action.FindCollectByUserCodeAction;
import com.jhhscm.platform.fragment.Mechanics.action.SaveAction;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.my.collect.CollectDeleteAction;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.ResultBean;
import com.jhhscm.platform.http.bean.SaveBean;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.http.sign.SignObject;
import com.jhhscm.platform.permission.YXPermission;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.MapUtil;
import com.jhhscm.platform.tool.StringUtils;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.tool.UdaUtils;
import com.jhhscm.platform.views.dialog.MapSelectDialog;
import com.jhhscm.platform.views.dialog.TelPhoneDialog;
import com.jhhscm.platform.views.selector.ImageSelectorItem;
import com.jhhscm.platform.views.selector.ImageSelectorPreviewActivity;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cn.bingoogolapple.bgabanner.BGABanner;
import retrofit2.Response;

public class StoreDetailFragment extends AbsFragment<FragmentStoreDetailBinding> {
    private String latitude;
    private String longitude;
    private boolean fast;
    public boolean isCollect;
    private String bus_code;
    private StorePeiJianFragment peiJianFragment;
    private BusinessDetailBean businessDetailBean;

    public static StoreDetailFragment instance() {
        StoreDetailFragment view = new StoreDetailFragment();
        return view;
    }

    @Override
    protected FragmentStoreDetailBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentStoreDetailBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);
        latitude = getArguments().getString("latitude");
        longitude = getArguments().getString("longitude");
        bus_code = getArguments().getString("bus_code");
        fast = getArguments().getBoolean("fast");

        peiJianFragment = new StorePeiJianFragment().instance(bus_code);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment, peiJianFragment).show(peiJianFragment).commitAllowingStateLoss();

        if (fast) {
            mDataBinding.tv3.setVisibility(View.VISIBLE);
        } else {
            mDataBinding.tv3.setVisibility(View.GONE);
        }
        if (bus_code != null) {
            if (latitude != null && longitude != null) {
                YXPermission.getInstance(getContext()).request(new AcpOptions.Builder()
                        .setDeniedCloseBtn(getContext().getString(R.string.permission_dlg_close_txt))
                        .setDeniedSettingBtn(getContext().getString(R.string.permission_dlg_settings_txt))
                        .setDeniedMessage(getContext().getString(R.string.permission_denied_txt, "读写"))
                        .setPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION).build(), new AcpListener() {
                    @Override
                    public void onGranted() {
                        getLocation();
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        if (permissions.contains(Manifest.permission.ACCESS_COARSE_LOCATION) &&
                                permissions.contains(Manifest.permission.ACCESS_FINE_LOCATION)) {

                        } else {
                            getLocation();
                        }
                    }
                });
            } else {
                business_detail();
            }
        } else {
            ToastUtil.show(getContext(), "数据错误");
            getActivity().finish();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (bus_code != null) {
            findCollectByUserCode(bus_code);
        }
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude() + "";
                longitude = location.getLongitude() + "";
                business_detail();
            } else {
                getLocation1(locationManager);
            }
        } else {
            getLocation1(locationManager);
        }
    }

    @SuppressLint("MissingPermission")
    private void getLocation1(LocationManager locationManager) {
        LocationListener locationListener = new LocationListener() {

            // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            // Provider被enable时触发此函数，比如GPS被打开
            @Override
            public void onProviderEnabled(String provider) {

            }

            // Provider被disable时触发此函数，比如GPS被关闭
            @Override
            public void onProviderDisabled(String provider) {

            }

            //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
//                        Log.e("Map", "Location changed : Lat: "
//                                + location.getLatitude() + " Lng: "
//                                + location.getLongitude());
                }
            }
        };
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null) {
            latitude = location.getLatitude() + "";
            longitude = location.getLongitude() + "";
            business_detail();
        } else {
//            ToastUtil.show(getContext(),"定位获取失败");
            business_detail();
        }
    }

    private void business_detail() {
        if (getContext() != null) {
            showDialog();
            Map<String, Object> map = new TreeMap<String, Object>();
            map.put("v1", longitude);
            map.put("v2", latitude);
            map.put("bus_code", bus_code);
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = SignObject.getSignKey(getActivity(), map, "business_detail");
            NetBean netBean = new NetBean();
            netBean.setToken("");
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(BusinessDetailAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<BusinessDetailBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<BusinessDetailBean>> response,
                                               BaseErrorInfo baseErrorInfo) {
                            if (getView() != null) {
                                closeDialog();
                                if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                    return;
                                }
                                if (response != null) {
                                    new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                    if (response.body().getCode().equals("200")) {
                                        businessDetailBean = response.body().getData();
                                        initView(response.body().getData());
                                    } else {
                                        ToastUtils.show(getContext(), response.body().getMessage());
                                    }
                                }
                            }
                        }
                    }));
        }
    }

    private void initView(final BusinessDetailBean data) {
        if (data != null && data.getResult().size() > 0) {
            mDataBinding.tv1.setText(data.getResult().get(0).getBus_name());

            String location = "";
            if (data.getResult().get(0).getProvince_name() != null) {
                location = data.getResult().get(0).getProvince_name() + " ";
            }
            if (data.getResult().get(0).getCity_name() != null) {
                location = location + data.getResult().get(0).getCity_name() + " ";
            }
            if (data.getResult().get(0).getCounty_name() != null) {
                location = location + data.getResult().get(0).getCounty_name() + " ";
            }
            if (data.getResult().get(0).getAddress_detail() != null) {
                location = location + data.getResult().get(0).getAddress_detail() + " ";
            }
            mDataBinding.tv2.setText(location);
            if (data.getResult().get(0).getMobile() != null) {
                mDataBinding.tv4.setText(UdaUtils.hiddenPhoneNumber(data.getResult().get(0).getMobile()));
                mDataBinding.tv4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //6.0权限处理
                        YXPermission.getInstance(getContext()).request(new AcpOptions.Builder()
                                .setDeniedCloseBtn(getContext().getString(R.string.permission_dlg_close_txt))
                                .setDeniedSettingBtn(getContext().getString(R.string.permission_dlg_settings_txt))
                                .setDeniedMessage(getContext().getString(R.string.permission_denied_txt, "拨打电话"))
                                .setPermissions(Manifest.permission.CALL_PHONE).build(), new AcpListener() {
                            @SuppressLint("MissingPermission")
                            @Override
                            public void onGranted() {
                                Uri uriScheme = Uri.parse("tel:" + data.getResult().get(0).getMobile());
                                Intent it = new Intent(Intent.ACTION_CALL, uriScheme);
                                getContext().startActivity(it);
                            }

                            @Override
                            public void onDenied(List<String> permissions) {

                            }
                        });
                    }
                });
            } else {
                mDataBinding.tv4.setText("");
            }
            mDataBinding.storeDetail.setText(data.getResult().get(0).getDesc());
            if (data.getResult().get(0).getDistance() != null) {
                mDataBinding.tvStore.setVisibility(View.VISIBLE);
                mDataBinding.tvStore.setText("距离" + data.getResult().get(0).getDistance() + "km");
            } else {
                mDataBinding.tvStore.setVisibility(View.GONE);
            }
            initBanner(data);
            initLocation(data);
            initTel();
        }
    }

    private void initBanner(final BusinessDetailBean data) {
        if (data.getResult().get(0).getPic_url() != null) {
            String jsonString = "{\"pic_url\":" + data.getResult().get(0).getPic_url() + "}";
            android.util.Log.e("jsonString", "jsonString  " + jsonString);
            final AfterSaleViewHolder.PicBean pic = JSON.parseObject(jsonString, AfterSaleViewHolder.PicBean.class);
            if (pic != null && pic.getPic_url() != null) {
                if (pic.getPic_url().size() > 1) {
                    mDataBinding.bgaBanner.setVisibility(View.VISIBLE);
                    mDataBinding.im.setVisibility(View.GONE);
                    ViewTreeObserver vto = mDataBinding.bgaBanner.getViewTreeObserver();
                    vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            mDataBinding.bgaBanner.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                            ViewGroup.LayoutParams lp1 = mDataBinding.bgaBanner.getLayoutParams();
                            lp1.width = mDataBinding.bgaBanner.getWidth();
                            lp1.height = (int) (mDataBinding.bgaBanner.getWidth() / 1.5);
                            mDataBinding.bgaBanner.setLayoutParams(lp1);
                        }
                    });
                    mDataBinding.bgaBanner.setAdapter(new BGABanner.Adapter() {
                        @Override
                        public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
                            ((ImageView) view).setScaleType(ImageView.ScaleType.FIT_XY);
                            ImageLoader.getInstance().displayImage(((ImageSelectorItem) model).imageUrl, (ImageView) view);
                        }
                    });
                    final ArrayList<ImageSelectorItem> pics = new ArrayList<>();
                    for (AfterSaleViewHolder.PicBean.PicUrlBean picBean : pic.getPic_url()) {
                        ImageSelectorItem imageSelectorItem = new ImageSelectorItem();
                        imageSelectorItem.imageUrl = picBean.getName();
                        pics.add(imageSelectorItem);
                    }

                    mDataBinding.bgaBanner.setData(pics, null);
                    mDataBinding.bgaBanner.setDelegate(new BGABanner.Delegate() {
                        @Override
                        public void onBannerItemClick(BGABanner banner, View itemViews, @Nullable Object model, int position) {
                            if (pics.size() > 0) {
                                ImageSelectorPreviewActivity.startActivity(getContext(), 0, pics, position);
                            }
                        }
                    });
                } else {
                    mDataBinding.bgaBanner.setVisibility(View.GONE);
                    mDataBinding.im.setVisibility(View.VISIBLE);
                    if (pic.getPic_url().size() > 0) {
                        ImageLoader.getInstance().displayImage(pic.getPic_url().get(0).getName(), mDataBinding.im);
                    }
                }
            }
        }
    }

    private void initLocation(BusinessDetailBean data) {
        final String v1 = data.getResult().get(0).getX();
        final String v2 = data.getResult().get(0).getY();
        final String adress = data.getResult().get(0).getProvince_name()
                + data.getResult().get(0).getCity_name()
                + data.getResult().get(0).getCounty_name()
                + data.getResult().get(0).getAddress_detail();
        if (!StringUtils.isNullEmpty(v1) && !StringUtils.isNullEmpty(v2)) {
            mDataBinding.tvStore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!StringUtils.isNullEmpty(v1) && !StringUtils.isNullEmpty(v2)) {
                        new MapSelectDialog(getContext(), adress, new MapSelectDialog.CallbackListener() {
                            @Override
                            public void clickGaode() {
                                if (MapUtil.checkMapAppsIsExist(getActivity(), MapUtil.GAODE_PKG)) {
                                    MapUtil.openGaoDe(getActivity(), Double.parseDouble(v2), Double.parseDouble(v1));
                                } else {
                                    ToastUtil.show(getActivity(), "检测到您没有安装高德地图");
                                }

                            }

                            @Override
                            public void clickBaidu() {
                                if (MapUtil.checkMapAppsIsExist(getActivity(), MapUtil.BAIDU_PKG)) {
                                    MapUtil.openBaidu(getActivity(), Double.parseDouble(v2), Double.parseDouble(v1));
                                } else {
                                    ToastUtil.show(getActivity(), "检测到您没有安装百度地图");
                                }
                            }

                            @Override
                            public void clickTenxun() {
                                if (MapUtil.checkMapAppsIsExist(getActivity(), MapUtil.PN_TENCENT_MAP)) {
                                    MapUtil.openTenxun(getActivity(), Double.parseDouble(v2), Double.parseDouble(v1));
                                } else {
                                    ToastUtil.show(getActivity(), "检测到您没有安装腾讯地图");
                                }
                            }
                        }).show();
                    } else {
                        ToastUtil.show(getActivity(), "该商户无准确地址");
                    }
                }
            });
        }
    }

    public void onEvent(FinishEvent event) {
        mDataBinding.appbarlayout.setExpanded(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }

    private void initTel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mDataBinding.coordinator.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                    if (Math.abs(scrollY - oldScrollY) > 5) {
                        imgTranslateAnimation(mDataBinding.tel, 0, 200);
                        mDataBinding.tel.setVisibility(View.GONE);
                    } else {
                        mDataBinding.tel.setVisibility(View.VISIBLE);
                        imgTranslateAnimation(mDataBinding.tel, 200, 0);
                    }
                }
            });
        }

        mDataBinding.tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //6.0权限处理
                YXPermission.getInstance(getContext()).request(new AcpOptions.Builder()
                        .setDeniedCloseBtn(getContext().getString(R.string.permission_dlg_close_txt))
                        .setDeniedSettingBtn(getContext().getString(R.string.permission_dlg_settings_txt))
                        .setDeniedMessage(getContext().getString(R.string.permission_denied_txt, "拨打电话"))
                        .setPermissions(Manifest.permission.CALL_PHONE).build(), new AcpListener() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onGranted() {
                        Uri uriScheme = Uri.parse("tel:" + businessDetailBean.getResult().get(0).getMobile());
                        Intent it = new Intent(Intent.ACTION_CALL, uriScheme);
                        getContext().startActivity(it);
                    }

                    @Override
                    public void onDenied(List<String> permissions) {

                    }
                });
            }
        });
    }

    //动画的左右进出平移动画
    private void imgTranslateAnimation(View view, float fromXDelta, float toXDelta) {
        TranslateAnimation translateAnimation = new TranslateAnimation(fromXDelta, toXDelta, 0, 0);
        translateAnimation.setFillAfter(true);//这句话会造成imageView.setVisibility(GONE)的时候，会停留在动画最后的地方，导致还没有隐藏的假象。
        translateAnimation.setDuration(800);
        view.setAnimation(translateAnimation);
        view.startAnimation(translateAnimation);
    }

    public void collect() {
        if (isCollect) {
            deleteCollect(bus_code);
        } else {
            save(bus_code);
        }
    }

    private void showCollect() {
        if (isCollect) {
            ((StoreDetailActivity) getActivity()).setToolBarRightAddButton(R.mipmap.ic_shoucang2);
        } else {
            ((StoreDetailActivity) getActivity()).setToolBarRightAddButton(R.mipmap.ic_shoucang3);
        }
    }

    /**
     * 收藏
     */
    private void save(String bus_code) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("user_code", ConfigUtils.getCurrentUser(getContext()).getUserCode());
        map.put("bus_code", bus_code);
        map.put("token", ConfigUtils.getCurrentUser(getContext()).getToken());
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getContext(), map, "save");
        NetBean netBean = new NetBean();
        netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
        netBean.setSign(sign);
        netBean.setContent(content);
        showDialog();
        onNewRequestCall(SaveBusAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity> response,
                                           BaseErrorInfo baseErrorInfo) {
                        closeDialog();
                        if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                            return;
                        }
                        if (response != null) {
                            new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                            if (response.body().getCode().equals("200")) {
                                isCollect = true;
//                                Drawable rightDrawable = getResources().getDrawable(R.mipmap.ic_shoucang2);
//                                rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());  // left, top, right, bottom
//                                mDataBinding.tvShoucang.setCompoundDrawables(null, rightDrawable, null, null);  // left, top, right, bottom
                                ToastUtils.show(getContext(), "收藏成功");
                            } else if (response.body().getCode().equals("1003")) {
                                ToastUtils.show(getContext(), "登录信息过期，请重新登录");
                                startNewActivity(LoginActivity.class);
                            } else {
                                ToastUtils.show(getContext(), response.body().getMessage());
                            }
                            showCollect();
                        }
                    }
                }));
    }

    /**
     * 取消收藏
     */
    private void deleteCollect(String bus_code) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("user_code", ConfigUtils.getCurrentUser(getContext()).getUserCode());
        map.put("good_code", bus_code);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getContext(), map, "deleteCollect");
        NetBean netBean = new NetBean();
        netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
        netBean.setSign(sign);
        netBean.setContent(content);
        showDialog();
        onNewRequestCall(CollectDeleteAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<ResultBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<ResultBean>> response,
                                           BaseErrorInfo baseErrorInfo) {
                        closeDialog();
                        if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                            return;
                        }
                        if (response != null) {
                            new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                            if (response.body().getCode().equals("200")) {
                                isCollect = false;
//                                Drawable rightDrawable = getResources().getDrawable(R.mipmap.ic_shoucang);
//                                rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());  // left, top, right, bottom
//                                mDataBinding.tvShoucang.setCompoundDrawables(null, rightDrawable, null, null);  // left, top, right, bottom
                                ToastUtils.show(getContext(), "取消收藏");
                            } else if (response.body().getCode().equals("1003")) {
                                ToastUtils.show(getContext(), "登录信息过期，请重新登录");
                                startNewActivity(LoginActivity.class);
                            } else {
                                ToastUtils.show(getContext(), "网络错误");
                            }
                            showCollect();
                        }
                    }
                }));
    }

    /**
     * 判断是否收藏
     */
    private void findCollectByUserCode(String bus_code) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("user_code", ConfigUtils.getCurrentUser(getContext()).getUserCode());
//        map.put("bus_code", bus_code);
        map.put("good_code", bus_code);
        map.put("token", ConfigUtils.getCurrentUser(getContext()).getToken());
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getContext(), map, "save");
        NetBean netBean = new NetBean();
        netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
        netBean.setSign(sign);
        netBean.setContent(content);
        showDialog();
        onNewRequestCall(FindCollectByUserCodeAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<SaveBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<SaveBean>> response,
                                           BaseErrorInfo baseErrorInfo) {
                        if (getContext() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                if (response.body().getCode().equals("200")) {
                                    if (response.body().getData().isResult()) {
                                        isCollect = true;
//                                    Drawable rightDrawable = getResources().getDrawable(R.mipmap.ic_shoucang2);
//                                    rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());  // left, top, right, bottom
//                                    mDataBinding.tvShoucang.setCompoundDrawables(null, rightDrawable, null, null);  // left, top, right, bottom
                                    } else {
                                        isCollect = false;
//                                    Drawable rightDrawable = getResources().getDrawable(R.mipmap.ic_shoucang);
//                                    rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());  // left, top, right, bottom
//                                    mDataBinding.tvShoucang.setCompoundDrawables(null, rightDrawable, null, null);  // left, top, right, bottom
                                    }
                                } else if (response.body().getCode().equals("1003")) {
                                    isCollect = false;
                                    ConfigUtils.removeCurrentUser(getContext());
                                } else if (!BuildConfig.DEBUG && response.body().getCode().equals("1006")) {
                                    isCollect = false;
                                    ToastUtils.show(getContext(), "网络错误");
                                } else {
                                    isCollect = false;
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                }
                                showCollect();
                            }
                        }
                    }
                }));
    }
}
