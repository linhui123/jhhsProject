package com.jhhscm.platform.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jhhscm.platform.activity.base.AbsToolbarActivity;
import com.jhhscm.platform.fragment.Mechanics.MechanicsByBrandFragment;
import com.jhhscm.platform.fragment.base.AbsFragment;

public class MechanicsByBrandActivity extends AbsToolbarActivity {

    private int type = 0;//0 返回；1进入详情；

    public static void start(Context context, String brand_id, int type) {
        Intent intent = new Intent(context, MechanicsByBrandActivity.class);
        intent.putExtra("brand_id", brand_id);
        intent.putExtra("type", type);
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
        args.putString("brand_id", getIntent().getStringExtra("brand_id"));
        args.putInt("type", getIntent().getIntExtra("type",0));
        return args;
    }
}