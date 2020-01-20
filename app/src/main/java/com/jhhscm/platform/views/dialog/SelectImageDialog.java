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
import com.jhhscm.platform.databinding.DialogSelectImageBinding;

public class SelectImageDialog extends BaseDialog {
    private DialogSelectImageBinding mDataBinding;
    private String title;
    private SelectImageDialog.CallbackListener mListener;
    private boolean mCancelable = true;

    public interface CallbackListener {
        void clickCarmera();

        void clickImage();

        void clickCancle();
    }

    public SelectImageDialog(Context context) {
        this(context, null, null);
    }

    public SelectImageDialog(Context context, SelectImageDialog.CallbackListener listener) {
        this(context, null, listener);
    }

    public SelectImageDialog(Context context, String title, SelectImageDialog.CallbackListener listener) {
        super(context);
        setCanceledOnTouchOutside(true);
        this.title = title;
        this.mListener = listener;
    }

    @Override
    protected View onInflateView(LayoutInflater inflater) {
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_select_image, null, false);
        return mDataBinding.getRoot();
    }

    @Override
    protected void onInitView(View view) {

        mDataBinding.rlCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener!=null){
                    mListener.clickCancle();
                }
                dismiss();
            }
        });

        mDataBinding.rlCarmera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener!=null){
                    mListener.clickCarmera();
                }
                dismiss();
            }
        });

        mDataBinding.rlImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener!=null){
                    mListener.clickImage();
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
//        int h = (int) (DisplayUtils.getDeviceHeight(getContext()) * 0.7);
//        lp.height = h;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(lp);
        Window window = getWindow();
        window.setWindowAnimations(R.style.botton_animation);
        window.setGravity(Gravity.BOTTOM);
    }

    @Override
    public void onBackPressed() {
        if (mCancelable) {
            super.onBackPressed();
        }
    }
}

