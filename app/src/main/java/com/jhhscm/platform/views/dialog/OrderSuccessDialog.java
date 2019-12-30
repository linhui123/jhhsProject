package com.jhhscm.platform.views.dialog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;

import com.jhhscm.platform.R;
import com.jhhscm.platform.databinding.DialogOrderSuccessBinding;

public class OrderSuccessDialog extends BaseDialog {
    private DialogOrderSuccessBinding mDataBinding;
    private String name;
    private CallbackListener mListener;
    private boolean mCancelable = true;
    private String sure;

    public interface CallbackListener {
        void clickYes();
    }

    public OrderSuccessDialog(Context context, CallbackListener listener) {
        this(context, null, listener, null);
    }

    public OrderSuccessDialog(Context context, String name, CallbackListener listener) {
        this(context, name, listener, null);
    }

    public OrderSuccessDialog(Context context, String name, CallbackListener listener, String sure) {
        super(context);
        setCanceledOnTouchOutside(true);
        this.name = name;
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
        if (name != null) {
            mDataBinding.name.setText(name);
        }
        mDataBinding.tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
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

