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
import com.jhhscm.platform.activity.TraceReloadActivity;
import com.jhhscm.platform.activity.VehicleDetailsActivity;
import com.jhhscm.platform.databinding.DialogPayBinding;
import com.jhhscm.platform.databinding.DialogVehicleMessageBinding;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.ToastUtil;
import com.umeng.analytics.MobclickAgent;

public class PayDialog extends BaseDialog {
    private DialogPayBinding mDataBinding;
    private String name;
    private String no;
    private boolean mCancelable = true;
    private LogisticsDialog.CallbackListener mListener;

    private static final int ALI_PAY_FLAG = 1;
    private static final int WX_PAY_FLAG = 2;
    private static int type = ALI_PAY_FLAG;

    public interface CallbackListener {
        void clickY();
    }

    public PayDialog(Context context) {
        super(context);
    }

    public PayDialog(Context context, LogisticsDialog.CallbackListener listener) {
        this(context);
        setCanceledOnTouchOutside(true);
        this.mListener = listener;
    }

    public PayDialog(Context context, String name, String no) {
        this(context);
        setCanceledOnTouchOutside(true);
        this.name = name;
        this.no = no;
    }

    public PayDialog(Context context, String name, String no, LogisticsDialog.CallbackListener listener) {
        this(context);
        setCanceledOnTouchOutside(true);
        this.name = name;
        this.no = no;
        this.mListener = listener;
    }

    public void setCallbackListener(LogisticsDialog.CallbackListener listener) {
        this.mListener = listener;
    }

    public void setCanBackDismiss(boolean canBackDismiss) {
        this.mCancelable = canBackDismiss;
    }

    @Override
    public void show() {
        super.show();
        Display d = this.getWindow().getWindowManager().getDefaultDisplay();
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width = (int) (DisplayUtils.getDeviceWidth(getContext()) - getContext().getResources().getDimension(R.dimen.head_title_height));
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(lp);
        Window window = getWindow();
        window.setWindowAnimations(R.style.botton_animation);
        window.setGravity(Gravity.CENTER);
    }

    @Override
    protected View onInflateView(LayoutInflater inflater) {
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_pay, null, false);
        return mDataBinding.getRoot();
    }

    @Override
    protected void onInitView(View view) {
        mDataBinding.imClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mDataBinding.rlAli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = ALI_PAY_FLAG;
                mDataBinding.imAli.setImageResource(R.mipmap.ic_shoping_s1);
                mDataBinding.imWx.setImageResource(R.mipmap.ic_shoping_s);
            }
        });

        mDataBinding.rlWx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = WX_PAY_FLAG;
                mDataBinding.imAli.setImageResource(R.mipmap.ic_shoping_s);
                mDataBinding.imWx.setImageResource(R.mipmap.ic_shoping_s1);
            }
        });

        mDataBinding.tvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDataBinding.num.getText().toString().length() > 0) {
                    if (type == ALI_PAY_FLAG) {
                    } else if (type == WX_PAY_FLAG) {
                    }
                    dismiss();
                } else {
                    ToastUtil.show(getContext(), "请先输入还款金额");
                }
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

