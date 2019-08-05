package com.jhhscm.platform.views.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.jhhscm.platform.R;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.views.YXProgressDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;


/**
 * 类说明：弹出框
 */
public abstract class BaseDialog extends Dialog implements DialogCallback, DialogInterface.OnDismissListener {

    private List<Call> requestCalls;
    private YXProgressDialog mYXDialog;

    protected abstract View onInflateView(LayoutInflater inflater);

    protected abstract void onInitView(View view);

    public BaseDialog(Context context) {
        super(context, R.style.transparent_dialog_style);
    }

    public BaseDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = onInflateView(inflater);
        onInitView(view);
        setContentView(view);
    }

    @Override
    public void show() {
        super.show();
        //设置大小
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();

        lp.width = (int) (DisplayUtils.getDeviceWidth(getContext()) - getContext().getResources().getDimension(R.dimen.dialog_frame_interval_width));
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(lp);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dismiss();
    }

    public void onNewRequestCall(Call call) {
        addRequestCall(call);
    }

    private void addRequestCall(Call call) {
        if (requestCalls == null) {
            requestCalls = new ArrayList<>();
        }
        requestCalls.add(call);
    }

    public void cancelRequest() {
        if (requestCalls == null) return;
        for (int i = 0; i < requestCalls.size(); i++) {
            Call call = requestCalls.get(i);
            if (call == null)
                return;
            if (!call.isCanceled()) {
                call.cancel();
            }
        }
        requestCalls.clear();
    }


    protected void showDialog(String message) {
        if (mYXDialog == null) {
            mYXDialog = new YXProgressDialog(getContext(), message);
        }
        mYXDialog.show();
    }

    protected void showDialog() {
        if (mYXDialog == null) {
            mYXDialog = new YXProgressDialog(getContext());
        }
        mYXDialog.show();
    }

    protected void closeDialog() {
        if (mYXDialog != null) {
            mYXDialog.dismiss();
            mYXDialog = null;
        }
    }

    @Override
    public void onCancelRequest() {
        cancelRequest();
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        cancelRequest();
    }
}
