package com.jhhscm.platform.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jhhscm.platform.activity.base.AbsToolbarActivity;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.msg.GetPushListBean;
import com.jhhscm.platform.fragment.msg.MsgDetailFragment;

public class MsgDetailActivity extends AbsToolbarActivity {

    public static void start(Context context, GetPushListBean.DataBean dataBean) {
        Intent intent = new Intent(context, MsgDetailActivity.class);
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
        return false;
    }

    @Override
    protected boolean fullScreenMode() {
        return true;
    }

    @Override
    public boolean isSupportSwipeBack() {
        return true;
    }

    @Override
    protected String getToolBarTitle() {
        return "消息详情";
    }

    @Override
    protected AbsFragment onCreateContentView() {
        return MsgDetailFragment.instance();
    }

    @Override
    protected Bundle onPutArguments() {
        Bundle args = new Bundle();
        args.putSerializable("dataBean",getIntent().getSerializableExtra("dataBean"));
        return args;
    }
}
