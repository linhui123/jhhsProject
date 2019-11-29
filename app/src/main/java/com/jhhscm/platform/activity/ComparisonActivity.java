package com.jhhscm.platform.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.jhhscm.platform.activity.base.AbsToolbarActivity;
import com.jhhscm.platform.fragment.Mechanics.ComparisonFragment;
import com.jhhscm.platform.fragment.Mechanics.bean.GetGoodsDetailsBean;
import com.jhhscm.platform.fragment.base.AbsFragment;

public class ComparisonActivity extends AbsToolbarActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, ComparisonActivity.class);
        context.startActivity(intent);
    }

    public static void start(Context context, GetGoodsDetailsBean getGoodsDetailsBean) {
        Intent intent = new Intent(context, ComparisonActivity.class);
        intent.putExtra("getGoodsDetailsBean", getGoodsDetailsBean);
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
        return "机型对比";
    }

    @Override
    protected AbsFragment onCreateContentView() {
        return ComparisonFragment.instance();
    }

    @Override
    protected Bundle onPutArguments() {
        Bundle args = new Bundle();
        args.putSerializable("getGoodsDetailsBean", getIntent().getSerializableExtra("getGoodsDetailsBean"));
        return args;
    }

    @Override
    protected void onDestroy() {
        Log.e("onDestroy", "onDestroy");
        super.onDestroy();
    }
}

