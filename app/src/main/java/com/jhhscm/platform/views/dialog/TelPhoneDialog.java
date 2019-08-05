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
import com.jhhscm.platform.activity.MainActivity;
import com.jhhscm.platform.databinding.DialogTelphoneBinding;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.ToastUtils;

public class TelPhoneDialog extends BaseDialog {
    private DialogTelphoneBinding mDataBinding;
    private String mContent;
    private CallbackListener mListener;
    private boolean mCancelable = true;


    public interface CallbackListener {
        void clickYes(String phone);
    }

    public TelPhoneDialog(Context context) {
        this(context, null, null);
    }

    public TelPhoneDialog(Context context, CallbackListener listener) {
        this(context, null, listener);
    }

    public TelPhoneDialog(Context context, String content, CallbackListener listener) {
        super(context);
//        setCancelable(false);
        setCanceledOnTouchOutside(true);
        this.mContent = content;
        this.mListener = listener;
    }

    @Override
    protected View onInflateView(LayoutInflater inflater) {
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_telphone, null, false);
        return mDataBinding.getRoot();
    }

    @Override
    protected void onInitView(View view) {
        if (ConfigUtils.getCurrentUser(getContext()) != null
                && ConfigUtils.getCurrentUser(getContext()).getMobile() != null) {
            mDataBinding.edPhone.setText(ConfigUtils.getCurrentUser(getContext()).getMobile());
        }

        mDataBinding.tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDataBinding.edPhone.getText().toString().length() > 8) {
                    if (mListener != null)
                        mListener.clickYes(mDataBinding.edPhone.getText().toString());
                    dismiss();
                } else {
                    ToastUtils.show(getContext(), "请输入正确的手机号码");
                }

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

