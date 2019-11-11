package com.jhhscm.platform.views.dialog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;

import com.jhhscm.platform.R;
import com.jhhscm.platform.databinding.DialogCouponListNewBinding;
import com.jhhscm.platform.databinding.DialogCouponNewBinding;
import com.jhhscm.platform.fragment.address.LocationAdapter;
import com.jhhscm.platform.fragment.coupon.CouponListBean;
import com.jhhscm.platform.fragment.coupon.CouponListDialogAdapter;

import java.util.List;

public class NewCouponListDialog extends BaseDialog {
    private DialogCouponListNewBinding mDataBinding;
    private String title;
    private String count;
    private String condition;
    private boolean isCheck;
    List<CouponListBean.ResultBean> list;

    private CouponListDialogAdapter pAdapter;
    private CallbackListener mListener;
    private boolean mCancelable = true;

    public interface CallbackListener {
        void clickYes();
    }

    public NewCouponListDialog(Context context, CallbackListener listener) {
        this(context, null, null, listener);
    }

    public NewCouponListDialog(Context context, String title, List<CouponListBean.ResultBean> list, CallbackListener listener) {
        super(context);
        setCanceledOnTouchOutside(true);
        this.list = list;
        this.mListener = listener;
        this.title = title;
    }

    @Override
    protected View onInflateView(LayoutInflater inflater) {
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_coupon_list_new, null, false);
        return mDataBinding.getRoot();
    }

    @Override
    protected void onInitView(View view) {
        mDataBinding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        pAdapter = new CouponListDialogAdapter(getContext());
        mDataBinding.recyclerview.setAdapter(pAdapter);
        pAdapter.setData(list);
        mDataBinding.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        mDataBinding.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) mListener.clickYes();
//                dismiss();
            }
        });

        mDataBinding.imClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
