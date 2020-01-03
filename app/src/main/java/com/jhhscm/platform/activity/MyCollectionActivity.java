package com.jhhscm.platform.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jhhscm.platform.activity.base.AbsToolbarActivity;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.my.collect.MyCollectionFragment;

public class MyCollectionActivity extends AbsToolbarActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, MyCollectionActivity.class);
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
        return "我的收藏";
    }

    @Override
    protected AbsFragment onCreateContentView() {
        return MyCollectionFragment.instance();
    }

    @Override
    protected Bundle onPutArguments() {
        Bundle args = new Bundle();
        return args;
    }
}
