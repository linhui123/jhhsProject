package com.jhhscm.platform.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jhhscm.platform.activity.base.AbsToolbarActivity;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.sale.AssessResultFragment;
import com.jhhscm.platform.fragment.sale.FindGoodsAssessBean;

public class AssessResultActivity extends AbsToolbarActivity {

    public static void start(Context context, FindGoodsAssessBean findGoodsAssessBean) {
        Intent intent = new Intent(context, AssessResultActivity.class);
        intent.putExtra("findGoodsAssessBean",findGoodsAssessBean);
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
        return "估价结果";
    }

    @Override
    protected AbsFragment onCreateContentView() {
        return AssessResultFragment.instance();
    }

    @Override
    protected Bundle onPutArguments() {
        Bundle args = new Bundle();
        args.putSerializable("findGoodsAssessBean",getIntent().getSerializableExtra("findGoodsAssessBean"));
        return args;
    }
}
