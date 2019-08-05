package com.jhhscm.platform.views.dialog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.databinding.DialogAddressBinding;
import com.jhhscm.platform.databinding.DialogDropBinding;
import com.jhhscm.platform.event.GetRegionEvent;
import com.jhhscm.platform.fragment.Mechanics.action.GetRegionAction;
import com.jhhscm.platform.fragment.Mechanics.bean.GetRegionBean;
import com.jhhscm.platform.fragment.address.LocationAdapter;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtils;

import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class DropDialog extends BaseDialog {
    private DialogDropBinding mDataBinding;
    private String title;
    private CallbackListener mListener;
    private boolean mCancelable = true;

    public interface CallbackListener {
        void clickResult(String pid, String pNmae, String cityId,
                         String cName, String countryID, String countryName);
    }

    public DropDialog(Context context) {
        this(context, null, null);
    }

    public DropDialog(Context context, CallbackListener listener) {
        this(context, null, listener);
    }

    public DropDialog(Context context, String title, CallbackListener listener) {
        super(context);
        setCanceledOnTouchOutside(true);
        this.title = title;
        this.mListener = listener;
    }

    @Override
    protected View onInflateView(LayoutInflater inflater) {
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_drop, null, false);
        return mDataBinding.getRoot();
    }

    @Override
    protected void onInitView(View view) {
        EventBusUtil.registerEvent(this);
        initDrop();
        mDataBinding.tvTitle.setText(title);
        mDataBinding.imClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private LocationAdapter pAdapter;

    private void initDrop() {
        mDataBinding.rv1.setLayoutManager(new LinearLayoutManager(getContext()));
        pAdapter = new LocationAdapter(getContext());
        mDataBinding.rv1.setAdapter(pAdapter);
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
        int h = (int) (DisplayUtils.getDeviceHeight(getContext()) * 0.7);
        lp.height = h;
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

    @Override
    public void onStop() {
        super.onStop();
        EventBusUtil.unregisterEvent(this);
    }

    /**
     * 更新地区选择
     */
    public void onEvent(GetRegionEvent event) {

    }
}



