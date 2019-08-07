package com.jhhscm.platform.photopicker;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.jhhscm.platform.R;
import com.jhhscm.platform.databinding.DialogConfirmBinding;
import com.jhhscm.platform.tool.StringUtils;
import com.jhhscm.platform.views.dialog.BaseDialog;


/**
 * 类说明：
 * 作者：huangqiuxin on 2016/5/26 15:16
 * 邮箱：648859026@qq.com
 */
public class ConfirmDialog extends BaseDialog {
    private DialogConfirmBinding mDataBinding;
    private String mTitle;
    private String mContent;
    private String mNo;
    private String mYes;
    private boolean mCancelable = true;
    private CallbackListener mListener;

    public interface CallbackListener {
        void clickNo();

        void clickYes();
    }

    public ConfirmDialog(Context context, String content, String no, String yes) {
        this(context, content, no, yes, null);
    }

    public ConfirmDialog(Context context, String content, String no, String yes, ConfirmDialog.CallbackListener listener) {
        super(context);
        setCancelable(false);
        this.mContent = content;
        this.mNo = no;
        this.mYes = yes;
        this.mListener = listener;
    }

    public ConfirmDialog(Context context, String title, String content, String no, String yes, ConfirmDialog.CallbackListener listener) {
        this(context, content, no, yes, listener);
        this.mTitle = title;
    }

    public void setCallbackListener(ConfirmDialog.CallbackListener listener) {
        this.mListener = listener;
    }

    public void setCanBackDismiss(boolean canBackDismiss) {
        this.mCancelable = canBackDismiss;
    }

    @Override
    protected View onInflateView(LayoutInflater inflater) {
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_confirm, null, false);
        return mDataBinding.getRoot();
    }

    @Override
    protected void onInitView(View view) {
        mDataBinding.title.setVisibility(!TextUtils.isEmpty(mTitle) ? View.VISIBLE : View.INVISIBLE);
        mDataBinding.title.setText(StringUtils.filterNullAndTrim(mTitle));
        mDataBinding.content.setText(StringUtils.filterNullAndTrim(mContent));
        mDataBinding.cancel.setText(StringUtils.filterNullAndTrim(mNo));
        mDataBinding.sure.setText(StringUtils.filterNullAndTrim(mYes));
        mDataBinding.flCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mListener != null) mListener.clickNo();
            }
        });
        mDataBinding.flSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mListener != null) mListener.clickYes();
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
