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
import com.jhhscm.platform.databinding.DialogMapSelectBinding;
import com.jhhscm.platform.databinding.DialogOrderSuccessBinding;
import com.jhhscm.platform.tool.DisplayUtils;

public class MapSelectDialog extends BaseDialog {
    private DialogMapSelectBinding mDataBinding;
    private String mContent;
    private CallbackListener mListener;
    private boolean mCancelable = true;
    private String sure;

    public interface CallbackListener {
        void clickGaode();

        void clickBaidu();

        void clickTenxun();
    }

    public MapSelectDialog(Context context) {
        this(context, null, null);
    }

    public MapSelectDialog(Context context, CallbackListener listener) {
        this(context, null, listener);
    }

    public MapSelectDialog(Context context, String content, CallbackListener listener) {
        super(context);
        setCanceledOnTouchOutside(true);
        this.mContent = content;
        this.mListener = listener;

    }

    @Override
    public void show() {
        super.show();
        Display d = this.getWindow().getWindowManager().getDefaultDisplay();
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        int h = (int) (DisplayUtils.getDeviceWidth(getContext()) * 0.9);
        lp.width = h;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(lp);
        Window window = getWindow();
        window.setWindowAnimations(R.style.botton_animation);
        window.setGravity(Gravity.BOTTOM);
    }

    @Override
    protected View onInflateView(LayoutInflater inflater) {
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_map_select, null, false);
        return mDataBinding.getRoot();
    }

    @Override
    protected void onInitView(View view) {
        mDataBinding.aim.setText("导航到:" + mContent);
        mDataBinding.cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mDataBinding.baidu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.clickBaidu();
                }
            }
        });

        mDataBinding.gaode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.clickGaode();
                }
            }
        });

        mDataBinding.tenxun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.clickTenxun();
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