package com.jhhscm.platform.views.dialog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;

import com.jhhscm.platform.R;
import com.jhhscm.platform.databinding.DialogAlertBinding;
import com.jhhscm.platform.databinding.DialogOrderSuccessBinding;
import com.jhhscm.platform.tool.StringUtils;

public class OrderSuccessDialog extends BaseDialog {
    private DialogOrderSuccessBinding mDataBinding;
    private String mContent;
    private CallbackListener mListener;
    private boolean mCancelable = true;
    private String sure;

    public interface CallbackListener {
        void clickYes();
    }

    public OrderSuccessDialog(Context context, CallbackListener listener) {
        this(context, null, listener, null);
    }

    public OrderSuccessDialog(Context context, String content, CallbackListener listener) {
        this(context, content, listener, null);
    }

    public OrderSuccessDialog(Context context, String content, CallbackListener listener, String sure) {
        super(context);
        setCanceledOnTouchOutside(true);
        this.mContent = content;
        this.mListener = listener;
        this.sure = sure;
    }

    @Override
    protected View onInflateView(LayoutInflater inflater) {
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_order_success, null, false);
        return mDataBinding.getRoot();
    }

    @Override
    protected void onInitView(View view) {
    }

    public void setCanDissmiss(boolean cancelable) {
        this.mCancelable = cancelable;
    }

    @Override
    public void onBackPressed() {
        if (mCancelable) {
            super.onBackPressed();
        }
    }
}

