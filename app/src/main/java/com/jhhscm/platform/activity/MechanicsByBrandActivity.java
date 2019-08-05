package com.jhhscm.platform.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jhhscm.platform.activity.base.AbsToolbarActivity;
import com.jhhscm.platform.fragment.Mechanics.BrandFragment;
import com.jhhscm.platform.fragment.Mechanics.MechanicsByBrandFragment;
import com.jhhscm.platform.fragment.base.AbsFragment;

public class MechanicsByBrandActivity extends AbsToolbarActivity {

    public static void start(Context context, String brand_id) {
        Intent intent = new Intent(context, MechanicsByBrandActivity.class);
        intent.putExtra("brand_id", brand_id);
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
        return "选择机型";
    }

    @Override
    protected AbsFragment onCreateContentView() {
        return MechanicsByBrandFragment.instance();
    }

    @Override
    protected Bundle onPutArguments() {
        Bundle args = new Bundle();
        args.putString("brand_id",getIntent().getStringExtra("brand_id"));
        return args;
    }
}