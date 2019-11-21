package com.jhhscm.platform.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jhhscm.platform.activity.base.AbsToolbarActivity;
import com.jhhscm.platform.fragment.aftersale.AfterSaleFragment;
import com.jhhscm.platform.fragment.aftersale.StoreDetailFragment;
import com.jhhscm.platform.fragment.base.AbsFragment;

public class StoreDetailActivity extends AbsToolbarActivity {

    public static void start(Context context, String bus_code, String latitude, String longitude) {
        Intent intent = new Intent(context, StoreDetailActivity.class);
        intent.putExtra("bus_code", bus_code);
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        context.startActivity(intent);
    }

    public static void start(Context context, String bus_code, String latitude, String longitude, boolean fast) {
        Intent intent = new Intent(context, StoreDetailActivity.class);
        intent.putExtra("bus_code", bus_code);
        intent.putExtra("fast", fast);
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
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
        return "维修店";
    }

    @Override
    protected AbsFragment onCreateContentView() {
        return StoreDetailFragment.instance();
    }

    @Override
    protected Bundle onPutArguments() {
        Bundle args = new Bundle();
        args.putString("bus_code", getIntent().getStringExtra("bus_code"));
        args.putString("latitude", getIntent().getStringExtra("latitude"));
        args.putString("longitude", getIntent().getStringExtra("longitude"));
        args.putBoolean("fast", getIntent().getBooleanExtra("fast", false));
        return args;
    }
}
