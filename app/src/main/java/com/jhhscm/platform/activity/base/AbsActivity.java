package com.jhhscm.platform.activity.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;


import com.jhhscm.platform.R;
import com.jhhscm.platform.event.LoginErrorEvent;
import com.jhhscm.platform.runtime.RuntimePermission;
import com.jhhscm.platform.statusbar.StatusBarCompat;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.StatisticalUtil;
import com.jhhscm.platform.tool.UdaUtils;
import com.jhhscm.platform.views.YXProgressDialog;
import com.jhhscm.platform.views.dialog.AlertDialog;
import com.jhhscm.platform.views.dialog.DialogCallback;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;
import de.greenrobot.event.EventBus;
import retrofit2.Call;


/**
 * Created by Administrator on 2017/10/23.
 */

public abstract class AbsActivity extends AppCompatActivity implements RuntimePermission.PermissionCallbacks, BGASwipeBackHelper.Delegate, DialogCallback {
    private static final String TAG = "ARP";
    private List<Call> mRequestCalls;
    private YXProgressDialog mYXDialog;
    protected BGASwipeBackHelper mSwipeBackHelper;
    private static List<AlertDialog> alartDialogList = new ArrayList<>();
    private static AlertDialog alertDialog;

    protected boolean fullScreenMode() {
        return false;
    }

    protected boolean lightStatusBar() {
        return true;
    }

    protected String getPageName() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initSwipeBackLayout();
        super.onCreate(savedInstanceState);
        EventBusUtil.registerEvent(this);
//        PushAgent.getInstance(this).onAppStart();
//        BroadcastUtils.registerPushAgentBroadcast(this, mPushAgentReceiver);
    }

//    private BroadcastReceiver mPushAgentReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (intent.getAction().equals(BroadcastUtils.UMENG_MESSAGE_REGISTRATIONID)) {
//                onNewRequestCall(UpdateDeviceInfo.newInstance(context, intent.getStringExtra("EXTRA_MSG")).request(new AHttpService.IResCallback<PbBean.PbResSystemUpdateDeviceInfo>() {
//                    @Override
//                    public void onCallback(int resultCode, Response<PbBean.PbResSystemUpdateDeviceInfo> response, PbBean.PbBaseServerInfo baseServerInfo) {
//                    }
//                }));
//            }
//        }
//    };

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, fullScreenMode(), lightStatusBar(), getResources().getColor(R.color.colorPrimary));
    }

    private void initSwipeBackLayout() {
        mSwipeBackHelper = new BGASwipeBackHelper(this, this);
        mSwipeBackHelper.setSwipeBackEnable(true);
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(true);
        mSwipeBackHelper.setIsWeChatStyle(true);
        mSwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow);
        mSwipeBackHelper.setIsNeedShowShadow(true);
        mSwipeBackHelper.setIsShadowAlphaGradient(true);
        mSwipeBackHelper.setSwipeBackThreshold(0.3f);
        float maxAspect = (float) DisplayUtils.getDeviceHeight(this) / (float) DisplayUtils.getDeviceWidth(this);
        mSwipeBackHelper.setIsNavigationBarOverlap(Float.valueOf(getString(R.string.format_aspect, maxAspect)).floatValue() > 1.8f ? true : false);
    }

    @Override
    public boolean isSupportSwipeBack() {
        return true;
    }

    @Override
    public void onSwipeBackLayoutSlide(float slideOffset) {
    }

    @Override
    public void onSwipeBackLayoutCancel() {
    }

    @Override
    public void onSwipeBackLayoutExecuted() {
        mSwipeBackHelper.swipeBackward();
    }

    @Override
    public void onBackPressed() {
        UdaUtils.hideInputMethod(this, getCurrentFocus());
        FragmentManager manager = getSupportFragmentManager();
        int count = manager.getBackStackEntryCount();
        if (count == 0) {
            if (mSwipeBackHelper.isSliding()) {
                return;
            }
            finish();
        } else {
            try {
                manager.popBackStackImmediate();
            } catch (IllegalStateException ignored) {
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(getPageName())) {
            StatisticalUtil.onPageStart(getPageName());
        } else {
            StatisticalUtil.onPageStart(this);
        }
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!TextUtils.isEmpty(getPageName())) {
            StatisticalUtil.onPageEnd(getPageName());
        } else {
            StatisticalUtil.onPageEnd(this);
        }
        MobclickAgent.onPause(this);
    }

    public void onEventMainThread(LoginErrorEvent event) {
        startLoginActivity();
//        finish();
    }
//    public void onEventMainThread(HandleInvalidEvent event) {
//        startLoginActivity();
////        finish();
//    }
//
//    public void onEvent(CardEvent event) {
//        finish();
//    }

    protected void handleInvalidLogin() {
//        ConfigUtils.removeCurrentUser(getApplicationContext());
//        ConfigUtils.setHttpHeadersCookie(getApplicationContext(), "");
//        ConfigUtils.setHandledInvalid(getApplicationContext(), false);
//        closeAlerDialog();
//        EventBus.getDefault().post(new HandleInvalidEvent());
    }

    protected void showInvalidLoginAlert(String errMsg) {
        alertDialog = new AlertDialog(this, errMsg, new AlertDialog.CallbackListener() {
            @Override
            public void clickYes() {
                handleInvalidLogin();
            }
        });
        addAlertDialog(alertDialog);
        alertDialog.setCanDissmiss(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    public void addAlertDialog(AlertDialog alertDialog) {
        alartDialogList.add(alertDialog);
    }

    public void closeAlerDialog() {
        for (AlertDialog alertDialog : alartDialogList) {
            try {
                alertDialog.dismiss();
            } catch (Exception e) {

            }
        }
        alartDialogList.clear();
    }

//    public void onEventMainThread(LoginErrorEvent event) {
//        showInvalidLoginAlert(event.extraMsg);
//    }

    protected void startNewActivity(Class<?> targetActivity) {
        startNewActivity(targetActivity, null);
    }

    protected void startNewActivity(Class<?> targetActivity, Bundle argument) {
        Intent intent = new Intent();
        intent.setClass(this, targetActivity);
        if (argument != null) {
            intent.putExtras(argument);
        }
        startActivity(intent);
    }

    protected void startLoginActivity() {
//        Intent intent = new Intent(this, LoginActivity.class);
//    startActivity(intent);
//    overridePendingTransition(R.anim.show_anim, R.anim.hide_anim);
    }

    public void onNewRequestCall(Call call) {
        addRequestCall(call);
    }

    private void addRequestCall(Call call) {
        if (mRequestCalls == null) {
            mRequestCalls = new ArrayList<>();
        }
        mRequestCalls.add(call);
    }

    public void cancelRequest() {
        if (mRequestCalls == null) return;
        for (int i = 0; i < mRequestCalls.size(); i++) {
            Call call = mRequestCalls.get(i);
            if (call == null) return;
            if (!call.isCanceled()) {
                call.cancel();
            }
        }
        mRequestCalls.clear();
    }

    @Override
    protected void onDestroy() {
//        ConfigUtils.setHandledInvalid(getApplicationContext(), false);
        EventBusUtil.unregisterEvent(this);
        cancelRequest();
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        RuntimePermission.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> params) {
        Log.d(TAG, "PermissionsGranted");
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> params) {
        Log.d(TAG, "PermissionsDenied");
    }

    protected void showDialog(String message) {
        if (mYXDialog == null) {
            mYXDialog = new YXProgressDialog(this, message);
        }
        mYXDialog.show();
    }

    protected void showDialog() {
        if (mYXDialog == null) {
            mYXDialog = new YXProgressDialog(this);
        }
        mYXDialog.show();
    }

    protected void closeDialog() {
        if (mYXDialog != null) {
            mYXDialog.dismiss();
        }
    }

    @Override
    public void onCancelRequest() {
        cancelRequest();
    }
}
