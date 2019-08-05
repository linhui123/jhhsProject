package com.jhhscm.platform.views.dialog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.jhhscm.platform.R;
import com.jhhscm.platform.databinding.DialogShareBinding;
import com.jhhscm.platform.tool.DisplayUtils;


/**
 * Created by Administrator on 2017/11/28.
 */

public class ShareDialog extends BaseDialog {
    private DialogShareBinding mBinding;
    private boolean mCancelable;
    private CallbackListener mListener;

    public ShareDialog(Context context , CallbackListener listener) {
        super(context);
        this.mListener=listener;

    }

    public interface CallbackListener {
        void wechat();
        void friends();
    }

    public void setCanBackDismiss(boolean canBackDismiss) {
        this.mCancelable = canBackDismiss;
    }

    @Override
    public void show() {
        super.show();
        //设置大小
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width = DisplayUtils.getDeviceWidth(getContext());
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(lp);
        Window window = getWindow();
        window.setWindowAnimations(R.style.botton_animation);
        window.setGravity(Gravity.BOTTOM);
    }

    @Override
    protected View onInflateView(LayoutInflater inflater) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_share, null, false);
        return mBinding.getRoot();
    }

    @Override
    protected void onInitView(View view) {
        mBinding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mBinding.tvWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                mListener.wechat();
            }
        });
        mBinding.tvFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                mListener.friends();
            }
        });
    }
}
