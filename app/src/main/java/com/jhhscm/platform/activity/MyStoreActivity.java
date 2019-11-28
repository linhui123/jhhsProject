package com.jhhscm.platform.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jhhscm.platform.activity.base.AbsToolbarActivity;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.coupon.MyCouponFragment;
import com.jhhscm.platform.fragment.my.store.MyStoreFragment;

public class MyStoreActivity extends AbsToolbarActivity {

    public static void start(Context context, int type) {
        Intent intent = new Intent(context, MyStoreActivity.class);
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
        return "我的店铺";
    }

    @Override
    protected AbsFragment onCreateContentView() {
        return MyStoreFragment.instance();
    }

    @Override
    protected Bundle onPutArguments() {
        Bundle args = new Bundle();
        args.putInt("type", getIntent().getIntExtra("type", 0));
        return args;
    }
}

