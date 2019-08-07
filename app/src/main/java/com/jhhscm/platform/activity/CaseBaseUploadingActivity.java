//package com.jhhscm.platform.activity;
//
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//
//import com.jhhscm.platform.activity.base.AbsToolbarActivity;
//import com.jhhscm.platform.fragment.base.AbsFragment;
//import com.jhhscm.platform.fragment.casebase.CaseBaseUploadingFragment;
//
//
//import java.util.ArrayList;
//
///**
// * Created by Administrator on 2017/11/2.
// */
//
//public class CaseBaseUploadingActivity extends AbsToolbarActivity {
//
//    public static void start(Context context, ArrayList<String> images, String caseBaseId, String timeNodeId) {
//        Intent intent = new Intent(context, CaseBaseUploadingActivity.class);
//        intent.putExtra("images",images);
//        intent.putExtra("caseBaseId",caseBaseId);
//        intent.putExtra("timeNodeId",timeNodeId);
//        context.startActivity(intent);
//    }
//
//    public static void start(Context context, String caseBaseId) {
//        Intent intent = new Intent(context, CaseBaseUploadingActivity.class);
//        intent.putExtra("caseBaseId",caseBaseId);
//        context.startActivity(intent);
//    }
//
//    @Override
//    protected boolean fullScreenMode() {
//        return true;
//    }
//
//    @Override
//    public boolean isSupportSwipeBack() {
//        return false;
//    }
//    @Override
//    protected boolean enableHomeButton() {
//        return true;
//    }
//
//    @Override
//    protected boolean enableDisplayHomeAsUp() {
//        return true;
//    }
//
//    @Override
//    protected String getToolBarTitle() {
//        return "";
//    }
//    @Override
//    protected AbsFragment onCreateContentView() {
//        return CaseBaseUploadingFragment.instance();
//    }
//
//    @Override
//    protected Bundle onPutArguments() {
//        Bundle args = new Bundle();
//        args.putStringArrayList("images", getIntent().getStringArrayListExtra("images"));
//        args.putString("caseBaseId", getIntent().getStringExtra("caseBaseId"));
//        args.putString("timeNodeId",getIntent().getStringExtra("timeNodeId"));
//        return args;
//    }
//
//}
