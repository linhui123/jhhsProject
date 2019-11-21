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

import com.jhhscm.platform.R;
import com.jhhscm.platform.databinding.DialogConfirmOrderBinding;
import com.jhhscm.platform.databinding.DialogLogisticsBinding;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.ToastUtil;

public class ConfirmOrderDialog extends BaseDialog {

    private DialogConfirmOrderBinding mDataBinding;
    private String name;
    private String no;
    private boolean mCancelable = true;
    private CallbackListener mListener;

    public interface CallbackListener {
        void clickY();
    }

    public ConfirmOrderDialog(Context context) {
        super(context);
    }

    public ConfirmOrderDialog(Context context, CallbackListener listener) {
        this(context);
        setCanceledOnTouchOutside(true);
        this.mListener = listener;
    }

    public ConfirmOrderDialog(Context context, String name, CallbackListener listener) {
        this(context);
        setCanceledOnTouchOutside(true);
        this.name = name;
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
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_confirm_order, null, false);
        return mDataBinding.getRoot();
    }

    @Override
    protected void onInitView(View view) {
        if (name != null) {
            mDataBinding.content.setText(name);
        }
        mDataBinding.tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mDataBinding.tvConfirm.setOnClickListener(new View.OnClickListener() {
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
}
