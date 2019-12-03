package com.jhhscm.platform.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jhhscm.platform.activity.base.AbsToolbarActivity;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.vehicle.GpsDetailBean;
import com.jhhscm.platform.fragment.vehicle.VehicleDetailsFragment;
import com.jhhscm.platform.fragment.vehicle.VehicleMonitoringFragment;

public class VehicleDetailsActivity extends AbsToolbarActivity {

    public static void start(Context context, GpsDetailBean.GpsListBean.ResultBean gpsListBean) {
        Intent intent = new Intent(context, VehicleDetailsActivity.class);
        intent.putExtra("gpsListBean", gpsListBean);
        context.startActivity(intent);
    }

    @Override
    protected boolean enableHomeButton() {
        return true;
    }

    @Override
    protected boolean enableDisplayHomeAsUp() {
        return true;
    }

    @Override
    protected boolean enableOperateText() {
        return false;
    }

    @Override
    protected boolean fullScreenMode() {
        return true;
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    protected String getToolBarTitle() {
        return "车辆详情";
    }

    @Override
    protected AbsFragment onCreateContentView() {
        return VehicleDetailsFragment.instance();
    }

    @Override
    protected Bundle onPutArguments() {
        Bundle args = new Bundle();
        args.putSerializable("gpsListBean", getIntent().getSerializableExtra("gpsListBean"));
        return args;
    }
}
