package com.jhhscm.platform.views.dialog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

import com.jhhscm.platform.R;
import com.jhhscm.platform.databinding.DialogHomeAlterBinding;

public class HomeAlterDialog extends BaseDialog {
    private DialogHomeAlterBinding mDataBinding;
    private String mTitle;
    private String mContent;
    private boolean mCancelable = true;
    private CallbackListener mListener;

    public interface CallbackListener {
        void clickF();

        void clickS();

        void clickT();
    }

    public HomeAlterDialog(Context context) {
        super(context);
    }

    public HomeAlterDialog(Context context, CallbackListener listener) {
        this(context);
        setCanceledOnTouchOutside(true);
        this.mListener = listener;
    }

    public HomeAlterDialog(Context context, String content, CallbackListener listener) {
        this(context);
//        setCancelable(false);
        this.mContent = content;
        this.mListener = listener;
    }


    public void setCallbackListener(CallbackListener listener) {
        this.mListener = listener;
    }

    public void setCanBackDismiss(boolean canBackDismiss) {
        this.mCancelable = canBackDismiss;
    }

    @Override
    public void show() {
        super.show();
        Display d = this.getWindow().getWindowManager().getDefaultDisplay();
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        int h = (int) (DisplayUtils.getDeviceHeight(getContext()) * 0.7);
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.alpha = 0.9f;
        this.getWindow().setAttributes(lp);
        Window window = getWindow();
        window.setWindowAnimations(R.style.botton_animation);
        window.setGravity(Gravity.BOTTOM);
    }

    @Override
    protected View onInflateView(LayoutInflater inflater) {
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_home_alter, null, false);
        return mDataBinding.getRoot();
    }

    @Override
    protected void onInitView(View view) {
        mDataBinding.tv1.setAnimation(shakeAnimation(5));
        mDataBinding.tv2.setAnimation(shakeAnimation(5));
        mDataBinding.tv3.setAnimation(shakeAnimation(5));
        mDataBinding.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mDataBinding.tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) mListener.clickF();
                dismiss();
            }
        });
        mDataBinding.tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) mListener.clickS();
                dismiss();
            }
        });
        mDataBinding.tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) mListener.clickT();
                dismiss();
            }
        });
    }


    /**
     * 晃动动画
     *
     * @param counts 0.5秒钟晃动多少下
     * @return
     */
    public static Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 10);
        //设置一个循环加速器，使用传入的次数就会出现摆动的效果。
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }

    @Override
    public void onBackPressed() {
        if (mCancelable) {
            super.onBackPressed();
        }
    }

    private int dip2px(int value) {
        float density = getContext().getResources()
                .getDisplayMetrics().density;
        return (int) (density * value + 0.5f);
    }

}
