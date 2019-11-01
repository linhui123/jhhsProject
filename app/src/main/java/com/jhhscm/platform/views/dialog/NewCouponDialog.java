package com.jhhscm.platform.views.dialog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;

import com.jhhscm.platform.R;
import com.jhhscm.platform.databinding.DialogCouponNewBinding;
import com.jhhscm.platform.databinding.DialogSimpleBinding;

public class NewCouponDialog extends BaseDialog {
    private DialogCouponNewBinding mDataBinding;
    private String title;
    private String count;
    private String condition;

    private CallbackListener mListener;
    private boolean mCancelable = true;

    public interface CallbackListener {
        void clickYes();
    }

    public NewCouponDialog(Context context, CallbackListener listener) {
        this(context, null, null, null, listener);
    }

    public NewCouponDialog(Context context, String title, String count, String condition, CallbackListener listener) {
        super(context);
        setCanceledOnTouchOutside(true);
        this.condition = condition;
        this.mListener = listener;
        this.title = title;
        this.count = count;
    }

    @Override
    protected View onInflateView(LayoutInflater inflater) {
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_coupon_new, null, false);
        return mDataBinding.getRoot();
    }

    @Override
    protected void onInitView(View view) {
        mDataBinding.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) mListener.clickYes();
                dismiss();
            }
        });

        mDataBinding.imClose.setOnClickListener(new View.OnClickListener() {
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