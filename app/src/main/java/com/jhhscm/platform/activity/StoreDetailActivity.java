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

    public static void start(Context context) {
        Intent intent = new Intent(context, StoreDetailActivity.class);
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
        return args;
    }
}
