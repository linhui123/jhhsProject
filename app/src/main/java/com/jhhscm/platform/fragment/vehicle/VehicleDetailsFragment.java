package com.jhhscm.platform.fragment.vehicle;


import android.location.Address;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jhhscm.platform.databinding.FragmentVehicleDetailsBinding;
import com.jhhscm.platform.fragment.base.AbsFragment;

import java.io.IOException;
import java.util.List;

public class VehicleDetailsFragment extends AbsFragment<FragmentVehicleDetailsBinding> {

    private GpsDetailBean.GpsListBean.ResultBean gpsListBean;

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
        gpsListBean = (GpsDetailBean.GpsListBean.ResultBean) getArguments().getSerializable("gpsListBean");
        if (gpsListBean != null) {
            mDataBinding.tvId.setText("系统编号：" + gpsListBean.getSystemNo());
            mDataBinding.tvNum.setText("车辆号码：" + gpsListBean.getVehNoF());
            mDataBinding.tvSim.setText(gpsListBean.getSimID());
            if (gpsListBean.getTime().length() > 20) {
                mDataBinding.tvData.setText(gpsListBean.getTime().substring(0, 19));
            } else {
                mDataBinding.tvData.setText(gpsListBean.getTime());
            }
            if ("1".equals(gpsListBean.getVehStatus())) {
                mDataBinding.tvAcc.setText("开启");
                mDataBinding.tvStatus.setText("在线");
            } else {
                mDataBinding.tvAcc.setText("关闭");
                mDataBinding.tvStatus.setText("离线");
            }

            //数据提供，速度要除以10
            mDataBinding.tvSudu.setText(Double.parseDouble(gpsListBean.getVelocity()) + " Km/h");
            //数据提供，油量要除以100
            mDataBinding.tvOil.setText(Integer.parseInt(gpsListBean.getOil()) + " L");
            mDataBinding.tvTem.setText(gpsListBean.getTemperature() + " ℃");
//            mDataBinding.tvVol.setText(gpsListBean.getOv() + " v");
//            mDataBinding.tvResistance.setText("- - Ω");
            mDataBinding.tvLatitude.setText(gpsListBean.getLatitude());
            mDataBinding.tvLongitude.setText(gpsListBean.getLongitude());
//            mDataBinding.tvParking.setText(DataUtil.getLongToTime(gpsListBean.getPk() * 1000, ""));
            getAddress(Double.parseDouble(gpsListBean.getLongitude()), Double.parseDouble(gpsListBean.getLatitude()));
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
