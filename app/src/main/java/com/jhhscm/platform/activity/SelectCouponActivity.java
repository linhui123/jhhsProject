package com.jhhscm.platform.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jhhscm.platform.activity.base.AbsToolbarActivity;
import com.jhhscm.platform.fragment.Mechanics.bean.GetComboBoxBean;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.coupon.CouponListBean;
import com.jhhscm.platform.fragment.coupon.SelectCouponFragment;

import java.util.List;

public class SelectCouponActivity extends AbsToolbarActivity {

    public static void start(Context context, String order_code, String coupon_code) {
        Intent intent = new Intent(context, SelectCouponActivity.class);
        intent.putExtra("order_code", order_code);
        intent.putExtra("coupon_code", coupon_code);
        context.startActivity(intent);
    }

    public static void start(Context context, CouponListBean list, String coupon_code) {
        Intent intent = new Intent(context, SelectCouponActivity.class);
        intent.putExtra("list", list);
        intent.putExtra("coupon_code", coupon_code);
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
        return "选择优惠券";
    }

    @Override
    protected AbsFragment onCreateContentView() {
        return SelectCouponFragment.instance();
    }

    @Override
    protected Bundle onPutArguments() {
        Bundle args = new Bundle();
        args.putString("order_code", getIntent().getStringExtra("order_code"));
        args.putString("coupon_code", getIntent().getStringExtra("coupon_code"));
        args.putSerializable("list", getIntent().getSerializableExtra("list"));
        return args;
    }
}
