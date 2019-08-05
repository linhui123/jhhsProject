package com.jhhscm.platform.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jhhscm.platform.activity.base.AbsToolbarActivity;
import com.jhhscm.platform.fragment.GoodsToCarts.CreateOrderFragment;
import com.jhhscm.platform.fragment.GoodsToCarts.GetCartGoodsByUserCodeBean;
import com.jhhscm.platform.fragment.GoodsToCarts.GoodsToCartsFragment;
import com.jhhscm.platform.fragment.base.AbsFragment;

import java.util.List;

public class CreateOrderActivity extends AbsToolbarActivity {

    public static void start(Context context, GetCartGoodsByUserCodeBean getCartGoodsByUserCodeBean) {
        Intent intent = new Intent(context, CreateOrderActivity.class);
        intent.putExtra("getCartGoodsByUserCodeBean", getCartGoodsByUserCodeBean);
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
        return "确认订单";
    }

    @Override
    protected AbsFragment onCreateContentView() {
        return CreateOrderFragment.instance();
    }

    @Override
    protected Bundle onPutArguments() {
        Bundle args = new Bundle();
        args.putSerializable("getCartGoodsByUserCodeBean", (GetCartGoodsByUserCodeBean) getIntent().getSerializableExtra("getCartGoodsByUserCodeBean"));
        return args;
    }
}

