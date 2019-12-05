package com.jhhscm.platform.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jhhscm.platform.activity.base.AbsToolbarActivity;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.lessee.Lessee2Fragment;
import com.jhhscm.platform.fragment.lessee.LesseeBean;
import com.jhhscm.platform.fragment.my.mechanics.FindGoodsOwnerBean;

public class Lessee2Activity extends AbsToolbarActivity {

    public static void start(Context context, LesseeBean lesseeBean) {
        Intent intent = new Intent(context, Lessee2Activity.class);
        intent.putExtra("lesseeBean", lesseeBean);
        context.startActivity(intent);
    }

    public static void start(Context context, LesseeBean lesseeBean, FindGoodsOwnerBean.DataBean dataBean) {
        Intent intent = new Intent(context, Lessee2Activity.class);
        intent.putExtra("data", dataBean);
        intent.putExtra("lesseeBean", lesseeBean);
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
        return "承租人信息";
    }

    @Override
    protected AbsFragment onCreateContentView() {
        return Lessee2Fragment.instance();
    }

    @Override
    protected Bundle onPutArguments() {
        Bundle args = new Bundle();
        args.putSerializable("lesseeBean", getIntent().getSerializableExtra("lesseeBean"));
        args.putSerializable("data", getIntent().getSerializableExtra("data"));
        return args;
    }
}


