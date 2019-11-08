package com.jhhscm.platform.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jhhscm.platform.activity.base.AbsToolbarActivity;
import com.jhhscm.platform.fragment.Mechanics.PushOldMechanicsFragment;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.my.mechanics.FindGoodsOwnerBean;
import com.jhhscm.platform.fragment.my.mechanics.MyMechanicsFragment;

public class PushOldMechanicsActivity extends AbsToolbarActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, PushOldMechanicsActivity.class);
        context.startActivity(intent);
    }

    public static void start(Context context, FindGoodsOwnerBean.DataBean dataBean) {
        Intent intent = new Intent(context, PushOldMechanicsActivity.class);
        intent.putExtra("data", dataBean);
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
        return "发布二手机";
    }

    @Override
    protected AbsFragment onCreateContentView() {
        return PushOldMechanicsFragment.instance();
    }

    @Override
    protected Bundle onPutArguments() {
        Bundle args = new Bundle();
        args.putSerializable("data", getIntent().getSerializableExtra("data"));
        return args;
    }
}
