package com.jhhscm.platform.permission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

/**
 * Created by Administrator on 2018/6/5.
 */

public class YXPermission {
    private static YXPermission mInstance;
    private YXPermissionManager mAcpManager;

    public static YXPermission getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new YXPermission(context);
        }
        return mInstance;
    }

    private YXPermission(Context context) {
        mAcpManager = new YXPermissionManager(context.getApplicationContext());
    }

    /**
     * 开始请求权限
     *
     * @param options
     * @param acpListener
     */
    public void request(AcpOptions options, AcpListener acpListener) {
        if (options == null) new NullPointerException("AcpOptions is null...");
        if (acpListener == null) new NullPointerException("AcpListener is null...");
        mAcpManager.request(options, acpListener);
    }

    void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        mAcpManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    void onActivityResult(int requestCode, int resultCode, Intent data) {
        mAcpManager.onActivityResult(requestCode, resultCode, data);
    }

    void requestPermissions(Activity activity) {
        mAcpManager.requestPermissions(activity);
    }
}
