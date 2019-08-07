package com.jhhscm.platform.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jhhscm.platform.activity.base.AbsToolbarActivity;
import com.jhhscm.platform.fragment.base.AbsFragment;



/**
 * Created by Administrator on 2017/11/2.
 */

public class CaseBasePhotoActivity extends AbsToolbarActivity {

    public static void start(Context context, String caseId, String type) {
        Intent intent = new Intent(context, CaseBasePhotoActivity.class);
        intent.putExtra("caseId", caseId);
        intent.putExtra("type", type);
        context.startActivity(intent);
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
    protected boolean enableHomeButton() {
        return true;
    }

    @Override
    protected boolean enableDisplayHomeAsUp() {
        return true;
    }

    @Override
    protected String getToolBarTitle() {
        return "";
    }
    @Override
    protected AbsFragment onCreateContentView() {
//        return CaseBasePhotoFragment.instance();
        return null;
    }

    @Override
    protected Bundle onPutArguments() {
        Bundle args = new Bundle();
        args.putString("caseId", getIntent().getStringExtra("caseId"));
        args.putString("type", getIntent().getStringExtra("type"));
        return args;
    }

}
