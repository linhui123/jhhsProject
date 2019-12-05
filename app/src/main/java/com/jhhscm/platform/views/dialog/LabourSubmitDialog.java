package com.jhhscm.platform.views.dialog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;

import com.jhhscm.platform.R;
import com.jhhscm.platform.databinding.DialogSimpleBinding;
import com.jhhscm.platform.event.FinishEvent;
import com.jhhscm.platform.tool.EventBusUtil;

public class LabourSubmitDialog extends BaseDialog {
    private DialogSimpleBinding mDataBinding;
    private String mContent;
    private LabourSubmitDialog.CallbackListener mListener;
    private boolean mCancelable = true;
    private String sure;


    public interface CallbackListener {
        void clickYes();
    }
    public LabourSubmitDialog(Context context) {
        this(context, null, null, null);
    }
    public LabourSubmitDialog(Context context, String content, LabourSubmitDialog.CallbackListener listener) {
        this(context, content, listener, null);
    }

    public LabourSubmitDialog(Context context, String content, LabourSubmitDialog.CallbackListener listener, String sure) {
        super(context);
        setCanceledOnTouchOutside(true);
        this.mContent = content;
        this.mListener = listener;
        this.sure = sure;
    }

    @Override
    protected View onInflateView(LayoutInflater inflater) {
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_simple, null, false);
        return mDataBinding.getRoot();
    }

    @Override
    protected void onInitView(View view) {
        mDataBinding.content.setText("我们将在1个工作日内与您联系，请保持电话通畅。");
        mDataBinding.title.setVisibility(View.VISIBLE);
        mDataBinding.imClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBusUtil.post(new FinishEvent());
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

