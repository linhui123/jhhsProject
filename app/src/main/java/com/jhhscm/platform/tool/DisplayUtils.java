package com.jhhscm.platform.tool;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import java.util.Locale;

/**
 * 类说明：
 * 作者：yangxiaoping on 2016/5/17 16:25
 * 邮箱：82650979@qq.com
 */
public class DisplayUtils {
    private static DisplayUtils instance;

    private static DisplayMetrics dm;

    private DisplayUtils(Context context) {
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
    }

    public static DisplayUtils getInstance(Context context) {
        if (instance == null) {
            instance = new DisplayUtils(context);
        }
        return instance;
    }
    /**
     * 获取设备宽度和高度
     *
     * @return
     */
    public static int getDeviceWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }
    public static int getDeviceHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }
    /**
     * 获取屏幕密度（像素比例：0.75/1.0/1.5/2.0）
     */
    public static int getPXByDP(Context context, int dp) {
        return (int) (getDensity(context) * dp);
    }
    /**
     * 获取屏幕密度（像素比例：0.75/1.0/1.5/2.0）
     */
    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }
    /**
     * 屏幕密度（每寸像素：120/160/240/320）
     */
    public static int getDensityDpi(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi;
    }
    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue
     * @param  （DisplayMetrics类中属性density）
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }
    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * @param  （DisplayMetrics类中属性density）
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5f);
    }


    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @param  （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(pxValue / fontScale + 0.5f);
    }
    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param  （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(spValue * fontScale + 0.5f);
    }
    /**
     * 根据dip值转化成px值
     *
     * @param context
     * @param dip
     * @return
     */
    public static int dipToPix(Context context, int dip) {
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
        return size;
    }
    public static float spToPx(Context context, float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }
    public static int getDimenValue(Context context, int dimenId) {
        return (int) context.getResources().getDimension(dimenId);
    }
    public static DisplayMetrics getDisplayMetrics(Context context) {
        // DisplayMetrics dm = new DisplayMetrics();
        // activity.getResources().getDisplayMetrics();
        // activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return context.getResources().getDisplayMetrics();
    }
    /**
     * 判断设备是否是模拟器
     *
     * @return
     */
    public static boolean isEmulator() {
        return "sdk".equals(Build.PRODUCT) || "google_sdk".equals(Build.PRODUCT) || "generic".equals(Build.BRAND.toLowerCase(Locale.getDefault()));
    }
    /**
     * 得到设备id
     *
     * @param context
     * @return
     */
    public static String getAndroidId(Context context) {
        String android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return android_id;
    }
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void copyToClipboard(Context context, String text) {
        // if()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setPrimaryClip(ClipData.newPlainText(null, text));
        }
    }
    public int getWidthPixels() {
        return dm.widthPixels;
    }
    public int getHeightPixels() {
        return dm.heightPixels;
    }

    public static int getMeasuredWidth(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.AT_MOST);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.AT_MOST);
        view.measure(w, h);
        return view.getMeasuredWidth();
    }

    public static int getMeasuredHeight(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.AT_MOST);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.AT_MOST);
        view.measure(w, h);
        return view.getMeasuredHeight();
    }

}
