package com.jhhscm.platform.views.dialog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.jhhscm.platform.R;
import com.jhhscm.platform.databinding.DialogCallPhoneBinding;
import com.jhhscm.platform.tool.DisplayUtils;

public class ConfirmCallPhoneDialog extends BaseDialog {
    private DialogCallPhoneBinding mDataBinding;
    private String title;
    private CallbackListener mListener;
    private boolean mCancelable = true;

    public interface CallbackListener {
        void clickResult();
    }

    public ConfirmCallPhoneDialog(Context context) {
        this(context, null, null);
    }

    public ConfirmCallPhoneDialog(Context context, CallbackListener listener) {
        this(context, null, listener);
    }

    public ConfirmCallPhoneDialog(Context context, String title, CallbackListener listener) {
        super(context);
        setCanceledOnTouchOutside(true);
        this.title = title;
        this.mListener = listener;
    }

    @Override
    protected View onInflateView(LayoutInflater inflater) {
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_call_phone, null, false);
        return mDataBinding.getRoot();
    }

    @Override
    protected void onInitView(View view) {
        if (title != null) {
            mDataBinding.tvCall.setText("呼叫（" + title + ")");
        }
        mDataBinding.rlCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mDataBinding.rlCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.clickResult();
                }
                dismiss();
            }
        });
    }

    public void setCanDissmiss(boolean cancelable) {
        this.mCancelable = cancelable;
    }

    @Override
    public void show() {
        super.show();
        Display d = this.getWindow().getWindowManager().getDefaultDisplay();
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        int width = (int) (DisplayUtils.getDeviceWidth(getContext()) * 0.7);
        lp.width = width;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(lp);
        Window window = getWindow();
        window.setWindowAnimations(R.style.botton_animation);
        window.setGravity(Gravity.CENTER);
    }

    @Override
    public void onBackPressed() {
        if (mCancelable) {
            super.onBackPressed();
        }
    }
}

