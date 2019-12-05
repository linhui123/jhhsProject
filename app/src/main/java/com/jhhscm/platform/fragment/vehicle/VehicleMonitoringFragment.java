package com.jhhscm.platform.fragment.vehicle;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapsInitializer;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.google.gson.Gson;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.databinding.FragmentVehicleMonitoringBinding;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.sign.SignObject;
import com.jhhscm.platform.tool.AMapUtil;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.dialog.VehicleMessageDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class VehicleMonitoringFragment extends AbsFragment<FragmentVehicleMonitoringBinding> {

    private AMap mAMap;
    // 存放所有坐标的数组
    private final ArrayList<LatLng> mLatLngs = new ArrayList<>();

    public static VehicleMonitoringFragment instance() {
        VehicleMonitoringFragment view = new VehicleMonitoringFragment();
        return view;
    }

    @Override
    protected FragmentVehicleMonitoringBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentVehicleMonitoringBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDataBinding.map.onCreate(savedInstanceState);
    }

    @Override
    protected void setupViews() {
        try {
            MapsInitializer.initialize(getActivity());
            //获取基站信息
            AMapUtil.getTowerInfo(getActivity());
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (ConfigUtils.getCurrentUser(getContext()) != null
                && ConfigUtils.getCurrentUser(getContext()).getMobile() != null
                && ConfigUtils.getCurrentUser(getContext()).getToken() != null) {
            gpsDetail();
        } else {
            getActivity().finish();
            LoginActivity.start(getActivity());
        }


        if (mAMap == null) {
            mAMap = mDataBinding.map.getMap();
        }
        mAMap.setMapType(AMap.MAP_TYPE_NORMAL);
    }


    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        mDataBinding.map.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        mDataBinding.map.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mDataBinding.map.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mDataBinding.map.onDestroy();
    }

    /**
     * 根据手机号查询gps信息 156069990899  15927112992
     */
    private void gpsDetail() {
        Map<String, Object> map = new TreeMap<String, Object>();
        map.put("phone", ConfigUtils.getCurrentUser(getContext()).getMobile());
//        map.put("phone", "15927112992");
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = SignObject.getSignKey(getActivity(), map, "gpsDetail3");
        NetBean netBean = new NetBean();
        netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(GpsDetailAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<GpsDetailBean>>() {

                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<GpsDetailBean>> response, BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    if (response.body().getData() != null) {
                                        GpsDetailBean gpsDetailBean = response.body().getData();
                                        Gson gson = new Gson();
                                        GpsDetailBean.GpsListBean gpsListBean = gson.fromJson(gpsDetailBean.getGpsList(), GpsDetailBean.GpsListBean.class);

                                        if ("true".equals(gpsListBean.getRes()) && gpsListBean != null) {
                                            initView(gpsListBean);
                                        } else {
                                            ToastUtil.show(getContext(), "暂无车辆信息！");
                                        }
                                    }
                                } else if (response.body().getCode().equals("1001")) {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                } else {
                                    ToastUtils.show(getContext(), "网络异常");
                                }
                            }
                        }
                    }
                }));
    }

    private void initView(final GpsDetailBean.GpsListBean gpsDetailBean) {
//        mLatLngs.add(new LatLng(26.080648, 119.308806));
//        mLatLngs.add(new LatLng(26.084339, 119.316046));
//        mLatLngs.add(new LatLng(26.088752, 119.304117));
//        mLatLngs.add(new LatLng(26.076064, 119.32176));
//        mLatLngs.add(new LatLng(26.099719, 119.319711));
        if (gpsDetailBean != null
                && gpsDetailBean.getResult().size() > 0) {
            for (GpsDetailBean.GpsListBean.ResultBean gpsListBean : gpsDetailBean.getResult()) {
                mLatLngs.add(new LatLng(Double.parseDouble(gpsListBean.getLatitude()), Double.parseDouble(gpsListBean.getLongitude())));
            }
        }
//https://restapi.amap.com/v3/geocode/regeo?output=xml&location=116.310003,39.991957
// &key=<用户的key>&radius=1000&extensions=all

        for (LatLng latLng : mLatLngs) {
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(
                                    getResources(),
                                    R.mipmap.ic_map_v)));
            mAMap.addMarker(markerOptions);
        }
        if (gpsDetailBean != null && gpsDetailBean.getResult().size() > 1) {
            LatLngBounds bounds = getLatLngBounds(mLatLngs);
            mAMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 300));//设置地图的放缩
        } else if (gpsDetailBean.getResult().size() == 1) {
            CameraPosition cameraPosition = new CameraPosition(mLatLngs.get(0), 13, 0, 0);
            mAMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }


        mAMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.e("onMarkerClick", "LatLng : " + marker.getPosition().longitude + " - " + marker.getPosition().latitude);
                for (GpsDetailBean.GpsListBean.ResultBean gpsListBean : gpsDetailBean.getResult()) {
                    if (marker.getPosition().longitude == Double.parseDouble(gpsListBean.getLongitude())
                            && marker.getPosition().latitude == Double.parseDouble(gpsListBean.getLatitude())) {
                        new VehicleMessageDialog(getContext(), gpsListBean).show();
                    }
                }
                return false;
            }
        });
    }


    /**
     * 根据自定义内容获取缩放bounds
     */
    private LatLngBounds getLatLngBounds(List<LatLng> pointList) {
        LatLngBounds.Builder b = LatLngBounds.builder();
        for (int i = 0; i < pointList.size(); i++) {
            LatLng p = pointList.get(i);
            b.include(p);
        }
        return b.build();
    }
}
