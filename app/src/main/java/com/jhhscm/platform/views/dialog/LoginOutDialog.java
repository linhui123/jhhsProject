package com.jhhscm.platform.views.dialog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.DialogDropTBinding;
import com.jhhscm.platform.databinding.DialogLoginOutBinding;
import com.jhhscm.platform.databinding.ItemLocationBinding;
import com.jhhscm.platform.fragment.Mechanics.bean.GetComboBoxBean;
import com.jhhscm.platform.views.recyclerview.DividerItemDecoration;

import java.util.List;

public class LoginOutDialog extends BaseDialog {
    private DialogLoginOutBinding mDataBinding;
    private String title;
    private CallbackListener mListener;
    private boolean mCancelable = true;

    public interface CallbackListener {
        void clickResult();
    }

    public LoginOutDialog(Context context) {
        this(context, null, null);
    }

    public LoginOutDialog(Context context, CallbackListener listener) {
        this(context, null, listener);
    }

    public LoginOutDialog(Context context, String title, CallbackListener listener) {
        super(context);
        setCanceledOnTouchOutside(true);
        this.title = title;
        this.mListener = listener;
    }

    @Override
    protected View onInflateView(LayoutInflater inflater) {
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_login_out, null, false);
        return mDataBinding.getRoot();
    }

    @Override
    protected void onInitView(View view) {

        mDataBinding.rlCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mDataBinding.rlOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener!=null){
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
