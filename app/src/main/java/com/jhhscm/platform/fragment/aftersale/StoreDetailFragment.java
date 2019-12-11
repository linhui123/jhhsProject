package com.jhhscm.platform.fragment.aftersale;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.databinding.FragmentStoreDetailBinding;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.sign.SignObject;
import com.jhhscm.platform.permission.YXPermission;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.MapUtil;
import com.jhhscm.platform.tool.StringUtils;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.dialog.MapSelectDialog;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class StoreDetailFragment extends AbsFragment<FragmentStoreDetailBinding> {
    private String latitude;
    private String longitude;
    private boolean fast;
    private String bus_code;

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
        latitude = getArguments().getString("latitude");
        longitude = getArguments().getString("longitude");
        bus_code = getArguments().getString("bus_code");
        fast = getArguments().getBoolean("fast");
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

    @SuppressLint("MissingPermission")
    private void getLocation() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude() + "";
                longitude = location.getLongitude() + "";
                business_detail();
            }
        } else {
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
            }
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

    private void initView(BusinessDetailBean data) {
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


            mDataBinding.storeDetail.setText(data.getResult().get(0).getDesc());
            if (data.getResult().get(0).getDistance() != null) {
                mDataBinding.tvStore.setVisibility(View.VISIBLE);
                mDataBinding.tvStore.setText("距离" + data.getResult().get(0).getDistance() + "km");
            } else {
                mDataBinding.tvStore.setVisibility(View.GONE);
            }


            if (data.getResult().get(0).getPic_url() != null) {
                String jsonString = "{\"pic_url\":" + data.getResult().get(0).getPic_url() + "}";
                android.util.Log.e("jsonString", "jsonString  " + jsonString);
                AfterSaleViewHolder.PicBean pic = JSON.parseObject(jsonString, AfterSaleViewHolder.PicBean.class);
                if (pic != null
                        && pic.getPic_url() != null && pic.getPic_url().size() > 0) {
                    ImageLoader.getInstance().displayImage(pic.getPic_url().get(0).getUrl(), mDataBinding.im);
                }
            }

            final String v1 = data.getResult().get(0).getX();
            final String v2 = data.getResult().get(0).getY();
            final String adress = data.getResult().get(0).getProvince_name()
                    + data.getResult().get(0).getCity_name()
                    + data.getResult().get(0).getCounty_name()
                    + data.getResult().get(0).getAddress_detail();
            if (!StringUtils.isNullEmpty(v1) && !StringUtils.isNullEmpty(v2)) {
                mDataBinding.storeLocation.setOnClickListener(new View.OnClickListener() {
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
    }
}
