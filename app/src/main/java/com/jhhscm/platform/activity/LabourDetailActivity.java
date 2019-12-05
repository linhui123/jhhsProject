package com.jhhscm.platform.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jhhscm.platform.activity.base.AbsToolbarActivity;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.labour.FindLabourReleaseListBean;
import com.jhhscm.platform.fragment.labour.LabourDetailFragment;

public class LabourDetailActivity extends AbsToolbarActivity {
    private int type;//0 查看；1编辑

    public static void start(Context context, int type, FindLabourReleaseListBean.DataBean dataBean) {
        Intent intent = new Intent(context, LabourDetailActivity.class);
        intent.putExtra("dataBean", dataBean);
        intent.putExtra("type", type);
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
        FindLabourReleaseListBean.DataBean dataBean = (FindLabourReleaseListBean.DataBean) getIntent().getSerializableExtra("dataBean");
        if (dataBean.getType().equals("0")) {
            return "招聘详情";
        } else {
            return "求职详情";
        }
    }

    @Override
    protected AbsFragment onCreateContentView() {
        return LabourDetailFragment.instance();
    }

    @Override
    protected Bundle onPutArguments() {
        Bundle args = new Bundle();
        args.putSerializable("dataBean", getIntent().getSerializableExtra("dataBean"));
        args.putInt("type", getIntent().getIntExtra("type", 0));
        return args;
    }
}
