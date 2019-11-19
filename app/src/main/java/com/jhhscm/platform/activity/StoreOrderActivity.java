package com.jhhscm.platform.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jhhscm.platform.activity.base.AbsToolbarActivity;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.my.store.StoreOrderFragment;
import com.jhhscm.platform.fragment.my.store.action.FindBusGoodsOwnerListByUserCodeBean;

public class StoreOrderActivity extends AbsToolbarActivity {

    public static void start(Context context, String bus_code, FindBusGoodsOwnerListByUserCodeBean.ResultBean.DataBean goods_code) {
        Intent intent = new Intent(context, StoreOrderActivity.class);
        intent.putExtra("bus_code",bus_code);
        intent.putExtra("goods_code",goods_code);
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
        return "订单";
    }

    @Override
    protected AbsFragment onCreateContentView() {
        return StoreOrderFragment.instance();
    }

    @Override
    protected Bundle onPutArguments() {
        Bundle args = new Bundle();
        args.putString("bus_code",getIntent().getStringExtra("bus_code"));
        args.putSerializable("goods_code",getIntent().getSerializableExtra("goods_code"));
        return args;
    }
}
