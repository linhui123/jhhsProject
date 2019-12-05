package com.jhhscm.platform.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jhhscm.platform.activity.base.AbsToolbarActivity;
import com.jhhscm.platform.fragment.Mechanics.BrandFragment;
import com.jhhscm.platform.fragment.base.AbsFragment;

public class BrandActivity extends AbsToolbarActivity {

    private int type = 1;// 1 选择品牌； 2 跳转新机列表;3选择机型返回

    public static void start(Context context, int type) {
        Intent intent = new Intent(context, BrandActivity.class);
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
        return "品牌";
    }

    @Override
    protected AbsFragment onCreateContentView() {
        return BrandFragment.instance();
    }

    @Override
    protected Bundle onPutArguments() {
        Bundle args = new Bundle();
        args.putInt("type", getIntent().getIntExtra("type", 1));
        return args;
    }
}