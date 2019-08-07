//package com.jhhscm.platform.views.dropdown;
//
//import android.content.Context;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.widget.PopupWindow;
//
//import com.jhhscm.platform.R;
//
///**
// * Created by cuijunling on 2016/11/1.
// */
//public class PopWinDownUtil {
//    private Context context;
//    private View contentView;
//    private View relayView;
//    private PopupWindow popupWindow;
//    public PopWinDownUtil(Context context, View contentView, View relayView, int width){
//        this.context = context;
//        this.contentView = contentView;
//        this.relayView = relayView;
//        init(width);
//    }
//    public void init(int width){
//        //内容，高度，宽度
//        if(width==0){
//            popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
//        }else {
//            popupWindow = new PopupWindow(contentView, width, ViewGroup.LayoutParams.WRAP_CONTENT, true);
//        }
//        //动画效果
////        popupWindow.setAnimationStyle(R.style.AnimationTopFade);
//
//
//        //菜单背景色
//        ColorDrawable dw = new ColorDrawable(Color.TRANSPARENT);
//        popupWindow.setBackgroundDrawable(dw);
//        popupWindow.setOutsideTouchable(true);
//        //关闭事件
//        popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
//        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//
//        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                if(onDismissLisener != null){
//                    onDismissLisener.onDismiss();
//                }
//            }
//        });
//    }
//    public void show(){
//
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { // Android 7.x中,PopupWindow高度为match_parent时,会出现兼容性问题,需要处理兼容性
////            int[] mLocation = new int[2];
////            relayView.getLocationInWindow(mLocation);
////            int offsetY = mLocation[1] + relayView.getHeight();
////            if (Build.VERSION.SDK_INT >= 25) { // Android 7.1中，PopupWindow高度为 match_parent 时，会占据整个屏幕
////                //故而需要在 Android 7.1上再做特殊处理
////                WindowManager wm = (WindowManager) context
////                        .getSystemService(Context.WINDOW_SERVICE);
////                Point point=new Point();
////                wm.getDefaultDisplay().getSize(point);
////                int width = point.x;
////                int height = point.y;
////                int screenHeight = height; // 获取屏幕高度
//////                Resources res = context.getResources();
//////                int result = 0;
//////                int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
//////                if (resourceId > 0) {
//////                    result = res.getDimensionPixelSize(resourceId);
//////                }
////
////                int result= StringUtils.getNavigationBarHeight(context);
////                popupWindow.setHeight(screenHeight+result - offsetY); // 重新设置 PopupWindow 的高度
////            }
////            popupWindow.showAtLocation(relayView, Gravity.NO_GRAVITY, 0, offsetY);
////        } else {
////            popupWindow.showAsDropDown(relayView);
////        }
//
//        //显示位置
//        popupWindow.showAsDropDown(relayView,0,context.getResources().getDimensionPixelSize(com.westcoast.blsapp.R.dimen.pop_win_top));
////        popupWindow.showAtLocation(relayView,0,0,100);
//    }
//    public void hide(){
//        if(popupWindow != null && popupWindow.isShowing()){
//            popupWindow.dismiss();
//        }
//    }
//
//    private OnDismissLisener onDismissLisener;
//    public void setOnDismissListener(OnDismissLisener onDismissLisener){
//        this.onDismissLisener = onDismissLisener;
//    }
//    public interface OnDismissLisener{
//        void onDismiss();
//    }
//    public boolean isShowing(){
//        return popupWindow.isShowing();
//    }
//}
