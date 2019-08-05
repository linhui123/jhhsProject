package com.jhhscm.platform.statusbar;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Utils for status bar
 * Created by qiu on 3/29/16.
 */
public class StatusBarCompat {

    //Get alpha color
    static int calculateStatusBarColor(int color, int alpha) {
        float a = 1 - alpha / 255f;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return 0xff << 24 | red << 16 | green << 8 | blue;
    }

    /**
     * 改变小米的状态栏字体颜色为黑色, 要求MIUI6以上
     * Tested on: MIUIV7 5.0 Redmi-Note3
     */
    private static void processMIUI(Window window, boolean lightStatusBar) throws Exception {
        Class<? extends Window> clazz = window.getClass();
        int darkModeFlag;
        Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
        Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
        darkModeFlag = field.getInt(layoutParams);
        Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
        extraFlagField.invoke(window, lightStatusBar ? darkModeFlag : 0, darkModeFlag);
    }

    /**
     * 改变魅族的状态栏字体为黑色，要求FlyMe4以上
     */
    private static void processFlyMe(Window window, boolean isLightStatusBar) throws Exception {
        WindowManager.LayoutParams lp = window.getAttributes();
        Class<?> instance = Class.forName("android.view.WindowManager$LayoutParams");
        int value = instance.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON").getInt(lp);
        Field field = instance.getDeclaredField("meizuFlags");
        field.setAccessible(true);
        int origin = field.getInt(lp);
        if (isLightStatusBar) {
            field.set(lp, origin | value);
        } else {
            field.set(lp, (~value) & origin);
        }
    }

    /**
     * set statusBarColor
     *
     * @param statusColor color
     * @param alpha       0 - 255
     */
    public static void setStatusBarColor(@NonNull Activity activity, boolean fullScreen, boolean lightStatusBar, @ColorInt int statusColor, int alpha) {
        setStatusBarColor(activity, fullScreen, lightStatusBar, calculateStatusBarColor(statusColor, alpha));
    }

    public static void setStatusBarColor(@NonNull Activity activity, boolean fullScreen, boolean lightStatusBar, @ColorInt int statusColor) {
        if (fullScreen) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                translucentStatusBar(activity);
                if (lightStatusBar) {
                    processM(activity.getWindow());
                }
            } else {
                try {
                    processFlyMe(activity.getWindow(), lightStatusBar);
                    translucentStatusBar(activity);
                } catch (Exception e) {
                    try {
                        processMIUI(activity.getWindow(), lightStatusBar);
                        translucentStatusBar(activity);
                    } catch (Exception e2) {
                        translucentStatusBar(activity);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (lightStatusBar) {
                                processM(activity.getWindow());
                            }
                        }
                    }
                }
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    StatusBarCompatLollipop.setStatusBarColor(activity, statusColor);
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    StatusBarCompatKitKat.setStatusBarColor(activity, statusColor);
                }
                if (lightStatusBar) {
                    processM(activity.getWindow());
                }
            } else {
                try {
                    processFlyMe(activity.getWindow(), lightStatusBar);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        StatusBarCompatLollipop.setStatusBarColor(activity, statusColor);
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        StatusBarCompatKitKat.setStatusBarColor(activity, statusColor);
                    }
                } catch (Exception e) {
                    try {
                        processMIUI(activity.getWindow(), lightStatusBar);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            StatusBarCompatLollipop.setStatusBarColor(activity, statusColor);
                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            StatusBarCompatKitKat.setStatusBarColor(activity, statusColor);
                        }
                    } catch (Exception e2) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            StatusBarCompatLollipop.setStatusBarColor(activity, statusColor);
                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            StatusBarCompatKitKat.setStatusBarColor(activity, statusColor);
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (lightStatusBar) {
                                processM(activity.getWindow());
                            }
                        }
                    }
                }
            }
        }
    }

    public static void translucentStatusBar(@NonNull Activity activity) {
        translucentStatusBar(activity, false);
    }

    /**
     * change to full screen mode
     *
     * @param hideStatusBarBackground hide status bar alpha Background when SDK > 21, true if hide it
     */
    public static void translucentStatusBar(@NonNull Activity activity, boolean hideStatusBarBackground) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusBarCompatLollipop.translucentStatusBar(activity, hideStatusBarBackground);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarCompatKitKat.translucentStatusBar(activity);
        }
    }

    private static void processM(Window window) {
        int flag = window.getDecorView().getSystemUiVisibility();
        flag |= (WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        window.setStatusBarColor(Color.parseColor("#FFFFFF"));
        window.getDecorView().setSystemUiVisibility(flag);
    }

}
