package com.jhhscm.platform.views.dialog;

import android.content.ClipboardManager;
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
import com.jhhscm.platform.databinding.DialogLogisticsBinding;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.ToastUtil;

public class LogisticsDialog extends BaseDialog {
    private DialogLogisticsBinding mDataBinding;
    private String name;
    private String no;
    private boolean mCancelable = true;
    private CallbackListener mListener;

    public interface CallbackListener {
        void clickY();
    }

    public LogisticsDialog(Context context) {
        super(context);
    }

    public LogisticsDialog(Context context, CallbackListener listener) {
        this(context);
        setCanceledOnTouchOutside(true);
        this.mListener = listener;
    }

    public LogisticsDialog(Context context, String name, String no) {
        this(context);
        setCanceledOnTouchOutside(true);
        this.name = name;
        this.no = no;
    }

    public LogisticsDialog(Context context, String name, String no, CallbackListener listener) {
        this(context);
        setCanceledOnTouchOutside(true);
        this.name = name;
        this.no = no;
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
        int w = (int) (DisplayUtils.getDeviceWidth(getContext()) * 0.7);
        lp.width = w;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(lp);
        Window window = getWindow();
        window.setWindowAnimations(R.style.botton_animation);
        window.setGravity(Gravity.CENTER);
    }

    @Override
    protected View onInflateView(LayoutInflater inflater) {
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_logistics, null, false);
        return mDataBinding.getRoot();
    }

    @Override
    protected void onInitView(View view) {
        mDataBinding.tvWuliu.setText(name);
        mDataBinding.tvWuliuNo.setText(no);
        mDataBinding.tvWuliuNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copy(no, getContext());
            }
        });

        mDataBinding.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) mListener.clickY();
                dismiss();
            }
        });
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

    /**
     * 实现文本复制功能
     * add by wangqianzhou
     *
     * @param content
     */
    public static void copy(String content, Context context) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
        ToastUtil.show(context, "复制成功!");
    }
}

