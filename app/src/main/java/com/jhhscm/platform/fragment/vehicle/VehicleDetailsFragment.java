package com.jhhscm.platform.fragment.vehicle;


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

import java.util.ArrayList;

public class VehicleDetailsFragment extends AbsFragment<FragmentVehicleDetailsBinding> {


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

    }

}
