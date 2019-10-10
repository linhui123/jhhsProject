package com.jhhscm.platform.fragment.vehicle;


import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.Polyline;
import com.jhhscm.platform.R;
import com.jhhscm.platform.databinding.FragmentVehicleDetailsBinding;
import com.jhhscm.platform.databinding.FragmentVehicleMonitoringBinding;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.tool.DataUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VehicleDetailsFragment extends AbsFragment<FragmentVehicleDetailsBinding> {

    private GpsDetailBean.GpsListBean gpsListBean;

    public static VehicleDetailsFragment instance() {
        VehicleDetailsFragment view = new VehicleDetailsFragment();
        return view;
    }

    @Override
    protected FragmentVehicleDetailsBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentVehicleDetailsBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        gpsListBean = (GpsDetailBean.GpsListBean) getArguments().getSerializable("gpsListBean");
        if (gpsListBean != null) {
            mDataBinding.tvId.setText("系统编号：" + gpsListBean.getVid());
            mDataBinding.tvNum.setText("车辆号码：" + gpsListBean.getId());
            mDataBinding.tvSim.setText("- -");
            if (gpsListBean.getGt().length() > 20) {
                mDataBinding.tvData.setText(gpsListBean.getGt().substring(0, 19));
            } else {
                mDataBinding.tvData.setText(gpsListBean.getGt());
            }

            if (gpsListBean.getOl() == 1) {
                mDataBinding.tvAcc.setText("开启");
                mDataBinding.tvStatus.setText("在线");
            } else {
                mDataBinding.tvAcc.setText("关闭");
                mDataBinding.tvStatus.setText("离线");
            }
            //数据提供，速度要除以10
            mDataBinding.tvSudu.setText(Double.parseDouble(gpsListBean.getSp())/10 + " Km/h");
            //数据提供，油量要除以100
            mDataBinding.tvOil.setText(gpsListBean.getYl() / 100 + " L");
            mDataBinding.tvTem.setText(gpsListBean.getT1() + " ℃");
            mDataBinding.tvVol.setText(gpsListBean.getOv() + " v");
            mDataBinding.tvResistance.setText("- - Ω");
            mDataBinding.tvLatitude.setText(gpsListBean.getMlat());
            mDataBinding.tvLongitude.setText(gpsListBean.getMlng());
            mDataBinding.tvParking.setText(DataUtil.getLongToTime(gpsListBean.getPk() * 1000, ""));
            getAddress(Double.parseDouble(gpsListBean.getMlng()), Double.parseDouble(gpsListBean.getMlat()));
        }
    }


    public String getAddress(double lnt, double lat) {
        Geocoder geocoder = new Geocoder(getContext());
        boolean falg = geocoder.isPresent();
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //根据经纬度获取地理位置信息---这里会获取最近的几组地址信息，具体几组由最后一个参数决定
            List<Address> addresses = geocoder.getFromLocation(lat, lnt, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    //每一组地址里面还会有许多地址。这里我取的前2个地址。xxx街道-xxx位置
                    if (i == 0) {
                        stringBuilder.append(address.getAddressLine(i));
                    }

                    if (i == 1) {
                        stringBuilder.append(address.getAddressLine(i));
                        break;
                    }
                }
                mDataBinding.tvLocation.setText(stringBuilder);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
