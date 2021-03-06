package com.jhhscm.platform.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jhhscm.platform.activity.base.AbsToolbarActivity;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.my.order.FindOrderListBean;
import com.jhhscm.platform.fragment.my.order.OrderDetail2Fragment;
import com.jhhscm.platform.fragment.my.order.OrderDetailFragment;

public class OrderDetailActivity extends AbsToolbarActivity {

    /**
     * 商品订单
     */
    public static void start(Context context, String orderGood, int Type) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra("orderGood", orderGood);
        intent.putExtra("type", Type);
        context.startActivity(intent);
    }

    /**
     * 维修订单1
     */
    public static void start(Context context, FindOrderListBean.DataBean dataBean, int Type) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra("dataBean", dataBean);
        intent.putExtra("type", Type);
        context.startActivity(intent);
    }

    /**
     * 维修订单2
     */
    public static void start(Context context, String orderGood, String sign, int Type) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra("sign", sign);
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
        return false;
    }

    @Override
    protected String getToolBarTitle() {
        return "订单详情";
    }

    @Override
    protected AbsFragment onCreateContentView() {
        if ((FindOrderListBean.DataBean) getIntent().getSerializableExtra("dataBean") != null) {
            return OrderDetail2Fragment.instance();
        } else if (getIntent().getStringExtra("sign") != null) {
            return OrderDetail2Fragment.instance();
        } else {
            return OrderDetailFragment.instance();
        }
    }

    @Override
    protected Bundle onPutArguments() {
        Bundle args = new Bundle();
        args.putString("orderGood", getIntent().getStringExtra("orderGood"));
        args.putString("sign", getIntent().getStringExtra("sign"));
        args.putSerializable("dataBean", getIntent().getSerializableExtra("dataBean"));
        args.putInt("type", getIntent().getIntExtra("type", 1));
        return args;
    }
}
