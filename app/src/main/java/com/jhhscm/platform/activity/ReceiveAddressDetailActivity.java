package com.jhhscm.platform.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jhhscm.platform.activity.base.AbsToolbarActivity;
import com.jhhscm.platform.fragment.GoodsToCarts.FindAddressListBean;
import com.jhhscm.platform.fragment.address.ReceiveAddressDetailFragment;
import com.jhhscm.platform.fragment.base.AbsFragment;

public class ReceiveAddressDetailActivity extends AbsToolbarActivity {

    public static void start(Context context, int type) {
        Intent intent = new Intent(context, ReceiveAddressDetailActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    public static void start(Context context, FindAddressListBean.ResultBean.DataBean dataBean, int type) {
        Intent intent = new Intent(context, ReceiveAddressDetailActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("dataBean", dataBean);
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
        return true;
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
        if (getIntent().getIntExtra("type", 1) == 1) {//新增
            return "添加新地址";
        } else {//修改
            setToolBarRightText("删除");
            return "编辑收货地址";

        }
    }

    @Override
    protected void onToolbarRightClick(Context context) {
        super.onToolbarRightClick(context);
        if (getIntent().getIntExtra("type", 1) == 1) {//新增

        } else {//修改 -- 右上角 删除功能
            ((ReceiveAddressDetailFragment) mFragment).delAddress();
        }
    }

    @Override
    protected AbsFragment onCreateContentView() {
        return ReceiveAddressDetailFragment.instance();
    }

    protected Bundle onPutArguments() {
        Bundle args = new Bundle();
        args.putInt("type", getIntent().getIntExtra("type", 1));
        args.putSerializable("dataBean", getIntent().getSerializableExtra("dataBean"));
        return args;
    }

    @Override
    protected void setToolBarRightText(String text) {
        super.setToolBarRightText(text);
    }
}
