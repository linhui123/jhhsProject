package com.jhhscm.platform.tool;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

//启动edittext优化，解决被挡住问题
public class KeyboardPatch {
    private Activity activity;
    private View decorView;
    private View contentView;
    private int srcPaddingBottm;
    private boolean keyboardVisiable;
    private int screenHeight;

    /**
     * @param act         需要解决bug的activity
     * @param contentView 界面容器，最好是可滑动的View，比如ScrollView等
     */
    public KeyboardPatch(Activity act, View contentView) {
        this.activity = act;
        this.decorView = act.getWindow().getDecorView();
        this.contentView = contentView;
        srcPaddingBottm = contentView.getPaddingBottom();
        screenHeight = decorView.getContext().getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 监听layout变化
     */
    public void enable() {
        //activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        if (Build.VERSION.SDK_INT >= 19) {
            decorView.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
            //主要为了体验优化，滑动或者触摸非焦点EditTexit的时候隐藏键盘，当然也可以去掉该监听
            contentView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                        if (keyboardVisiable) {
//                            KeyboardUtils.hideSoftInput(contentView);
                            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
                        }
                    }
                    return false;
                }
            });
        }
    }

    /**
     * 取消监听
     */
    public void disable() {
        if (Build.VERSION.SDK_INT >= 19) {
            decorView.getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
        }
    }

    private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            Rect r = new Rect();
            //获取可见窗口的frame，主要是获取r.bottom
            decorView.getWindowVisibleDisplayFrame(r);
            //判断软键盘是否弹起
            keyboardVisiable = screenHeight - r.bottom >= 0;
            //寻找获得焦点的view
            View focusView = decorView.findFocus();
            if (focusView != null && focusView instanceof EditText) {
                int[] location = new int[2];
                focusView.getLocationOnScreen(location);
                //如果focusView的高度很高的话，可以不必都显示出来
                int focusBottom = location[1] + focusView.getMeasuredHeight();
                //获取焦点的EditText的最底部和可见窗口的最底部的差值
                int diff = focusBottom - r.bottom;
                //如果大于0，说明被遮挡了，需要添加paddingBottom的值增加diff，并且需要把焦点view滑动上来，如果小于0，说明不存在遮挡，还原即可
                if (diff > 0) {
                    if (srcPaddingBottm != diff) {
                        contentView.setPadding(contentView.getPaddingLeft(), contentView.getPaddingTop(), contentView.getPaddingRight(), diff + srcPaddingBottm);
                        contentView.scrollBy(0, diff);
                    }
                } else {
                    if (contentView.getPaddingBottom() != 0) {
                        contentView.setPadding(contentView.getPaddingLeft(), contentView.getPaddingTop(), contentView.getPaddingRight(), srcPaddingBottm);
                    }
                }
            }

        }
    };

}

