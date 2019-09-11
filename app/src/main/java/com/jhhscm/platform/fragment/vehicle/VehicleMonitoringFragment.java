package com.jhhscm.platform.fragment.vehicle;


import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapsInitializer;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.Polyline;
import com.jhhscm.platform.R;
import com.jhhscm.platform.databinding.FragmentLessee3Binding;
import com.jhhscm.platform.databinding.FragmentVehicleMonitoringBinding;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.lessee.Lessee3Fragment;
import com.jhhscm.platform.fragment.lessee.LesseeBean;
import com.jhhscm.platform.tool.AMapUtil;
import com.jhhscm.platform.views.dialog.VehicleMessageDialog;

import java.util.ArrayList;
import java.util.List;

public class VehicleMonitoringFragment extends AbsFragment<FragmentVehicleMonitoringBinding> {

    private AMap mAMap;
    private Polyline mPolyline;
    // 存放所有坐标的数组
    private final ArrayList<LatLng> mLatLngs = new ArrayList<>();
    private final ArrayList<LatLng> mTraceLatLngs = new ArrayList<>();
    private final ArrayList<Marker> markers = new ArrayList<>();

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
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        //获取基站信息
        AMapUtil.getTowerInfo(getActivity());
        if (mAMap == null) {
            mAMap = mDataBinding.map.getMap();
        }
        mAMap.setMapType(AMap.MAP_TYPE_NORMAL);

        mLatLngs.add(new LatLng(26.080648, 119.308806));
        mLatLngs.add(new LatLng(26.084339, 119.316046));
        mLatLngs.add(new LatLng(26.088752, 119.304117));
        mLatLngs.add(new LatLng(26.076064, 119.32176));
        mLatLngs.add(new LatLng(26.099719, 119.319711));


        for (LatLng latLng : mLatLngs) {
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(
                                    getResources(),
                                    R.mipmap.ic_map_v)));


            mAMap.addMarker(markerOptions);
        }
        LatLngBounds bounds = getLatLngBounds(mLatLngs);
        mAMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 300));
        mAMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.e("onMarkerClick", "LatLng : " + marker.getPosition().longitude + " - " + marker.getPosition().latitude);
                new VehicleMessageDialog(getContext()).show();
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
}
