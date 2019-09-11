package com.jhhscm.platform.views.dialog;

import android.content.ClipboardManager;
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
import com.jhhscm.platform.databinding.DialogLogisticsBinding;
import com.jhhscm.platform.databinding.DialogVehicleMessageBinding;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.ToastUtil;

public class VehicleMessageDialog extends BaseDialog {
    private DialogVehicleMessageBinding mDataBinding;
    private String name;
    private String no;
    private boolean mCancelable = true;
    private LogisticsDialog.CallbackListener mListener;

    public interface CallbackListener {
        void clickY();
    }

    public VehicleMessageDialog(Context context) {
        super(context);
    }

    public VehicleMessageDialog(Context context, LogisticsDialog.CallbackListener listener) {
        this(context);
        setCanceledOnTouchOutside(true);
        this.mListener = listener;
    }

    public VehicleMessageDialog(Context context, String name, String no) {
        this(context);
        setCanceledOnTouchOutside(true);
        this.name = name;
        this.no = no;
    }

    public VehicleMessageDialog(Context context, String name, String no, LogisticsDialog.CallbackListener listener) {
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
        window.setGravity(Gravity.BOTTOM);
    }

    @Override
    protected View onInflateView(LayoutInflater inflater) {
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_vehicle_message

                , null, false);
        return mDataBinding.getRoot();
    }

    @Override
    protected void onInitView(View view) {
        mDataBinding.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VehicleDetailsActivity.start(getContext());
                dismiss();
            }
        });

        mDataBinding.reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TraceReloadActivity.start(getContext());
                dismiss();
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


