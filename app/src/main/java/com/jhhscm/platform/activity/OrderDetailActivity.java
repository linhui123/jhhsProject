package com.jhhscm.platform.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jhhscm.platform.activity.base.AbsToolbarActivity;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.my.order.MyPeiJianListFragment;
import com.jhhscm.platform.fragment.my.order.OrderDetailFragment;

public class OrderDetailActivity extends AbsToolbarActivity {

    public static void start(Context context, String orderGood, int Type) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra("orderGood", orderGood);
        intent.putExtra("type", Type);
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
        return true;
    }

    @Override
    protected String getToolBarTitle() {
        return "订单详情";
    }

    @Override
    protected AbsFragment onCreateContentView() {
        return OrderDetailFragment.instance();
    }

    @Override
    protected Bundle onPutArguments() {
        Bundle args = new Bundle();
        args.putString("orderGood", getIntent().getStringExtra("orderGood"));
        args.putInt("type", getIntent().getIntExtra("type", 1));
        return args;
    }
}
