package com.jhhscm.platform.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jhhscm.platform.activity.base.AbsToolbarActivity;
import com.jhhscm.platform.fragment.GoodsToCarts.GoodsToCartsFragment;
import com.jhhscm.platform.fragment.Mechanics.PeiJianFragment;
import com.jhhscm.platform.fragment.base.AbsFragment;

public class GoodsToCartsActivity extends AbsToolbarActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, GoodsToCartsActivity.class);
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
        return "购物车";
    }

    @Override
    protected AbsFragment onCreateContentView() {
        return GoodsToCartsFragment.instance();
    }

    @Override
    protected Bundle onPutArguments() {
        Bundle args = new Bundle();
        return args;
    }
}

