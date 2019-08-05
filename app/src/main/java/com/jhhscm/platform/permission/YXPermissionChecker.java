package com.jhhscm.platform.permission;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;

/**
 * Created by Administrator on 2018/6/8.
 */

public class YXPermissionChecker {

    public static boolean checkSelfPermission(final Context context, String... permissions) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        try {
            final PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            int targetSdkVersion = info.applicationInfo.targetSdkVersion;
            if (targetSdkVersion >= Build.VERSION_CODES.M) {
                for (String perm : permissions) {
                    boolean hasPerm = (ContextCompat.checkSelfPermission(context, perm) ==
                            PackageManager.PERMISSION_GRANTED);
                    if (!hasPerm) {
                        return false;
                    }
                }
            } else {
                for (String perm : permissions) {
                    boolean hasPerm = (PermissionChecker.checkSelfPermission(context, perm) ==
                            PackageManager.PERMISSION_GRANTED);
                    if (!hasPerm) {
                        return false;
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }
}
