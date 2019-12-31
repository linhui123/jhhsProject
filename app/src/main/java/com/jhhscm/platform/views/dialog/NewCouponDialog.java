package com.jhhscm.platform.views.dialog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;

import com.jhhscm.platform.R;
import com.jhhscm.platform.databinding.DialogCouponNewBinding;
import com.jhhscm.platform.fragment.coupon.GetNewCouponslistBean;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class NewCouponDialog extends BaseDialog {
    private DialogCouponNewBinding mDataBinding;
    private GetNewCouponslistBean.ResultBean.DataBean dataBean;

    private boolean isCheck;

    private CallbackListener mListener;
    private boolean mCancelable = true;

    public interface CallbackListener {
        void clickYes();
    }

    public NewCouponDialog(Context context, CallbackListener listener) {
        this(context, null, listener);
    }

    public NewCouponDialog(Context context, GetNewCouponslistBean.ResultBean.DataBean dataBean, CallbackListener listener) {
        super(context);
        setCanceledOnTouchOutside(false);
        this.dataBean = dataBean;
        this.mListener = listener;
    }

    @Override
    public void show() {
        super.show();
//        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
//        layoutParams.gravity = Gravity.CENTER;
//        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
//        getWindow().getDecorView().setPadding(0, 0, 0, 0);
//        getWindow().setAttributes(layoutParams);
    }

    @Override
    protected View onInflateView(LayoutInflater inflater) {
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_coupon_new, null, false);
        return mDataBinding.getRoot();
    }

    @Override
    protected void onInitView(View view) {
        mDataBinding.count.setText(StringUtils.replace(dataBean.getDiscount()) + "");
        mDataBinding.condition.setText(dataBean.getDesc());

        mDataBinding.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        mDataBinding.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) mListener.clickYes();
                dismiss();
            }
        });

        mDataBinding.imClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDataBinding.check.isChecked()) {
                    List<String> resultBean = ConfigUtils.getCoupon(getContext());
                    if (resultBean != null && resultBean.size() > 0) {
                        if (!resultBean.contains(dataBean.getCode())) {
                            resultBean.add(dataBean.getCode());
                        }
                    } else {
                        resultBean = new ArrayList<>();
                        resultBean.add(dataBean.getCode());
                    }
                    ConfigUtils.setCoupon(getContext(), resultBean);
                }
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