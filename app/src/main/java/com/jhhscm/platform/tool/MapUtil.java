package com.jhhscm.platform.tool;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.util.List;

public class MapUtil {
    public final static String BAIDU_PKG = "com.baidu.BaiduMap"; //百度地图的包名

    public final static String GAODE_PKG = "com.autonavi.minimap";//高德地图的包名

    public static final String PN_TENCENT_MAP = "com.tencent.map"; // 腾讯地图包名

    public static void openGaoDe(Context context, double latitude, double longitude) {
        Intent intent = new Intent("android.intent.action.VIEW", android.net.Uri.parse("androidamap://navi?sourceApplication=挖矿来&lat=" + latitude + "&lon=" + longitude + "&dev=0"));
        intent.setPackage("com.autonavi.minimap");
        context.startActivity(intent);
    }

    public static void openBaidu(Context context, double latitude, double longitude) {
        Intent i1 = new Intent();
        double[] position = GPSUtil.gcj02_To_Bd09(latitude, longitude);
        i1.setData(Uri.parse("baidumap://map/geocoder?location=" + position[0] + "," + position[1]));
        context.startActivity(i1);
    }

    public static void openTenxun(Context context, double endlatitude, double endlongititude) {
        Uri uri = Uri.parse("qqmap://map/routeplan?type=drive&tocoord=" + endlatitude + "," + endlongititude + "&to=" + "");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    /**
     * 检测地图应用是否安装
     *
     * @param context
     * @param pkgName
     * @return
     */
    public static boolean checkMapAppsIsExist(Context context, String pkgName) {
        if (pkgName == null || pkgName.isEmpty()) {
            return false;
        }
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> info = packageManager.getInstalledPackages(0);
        if (info == null || info.isEmpty()) {
            return false;
        }
        for (int i = 0; i < info.size(); i++) {
            if (pkgName.equals(info.get(i).packageName)) {
                return true;
            }
        }
        return false;
    }
}